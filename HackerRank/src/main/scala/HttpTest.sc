import scalaj.http.{Http, HttpResponse}
import upickle.default



case class ResponseJson(id: Int, title: String)

implicit val ResponseJsonRW: default.ReadWriter[ResponseJson] =
  upickle.default.macroRW[ResponseJson]

//mutable.LinkedHashMap[String,]
val response: HttpResponse[ResponseJson] =
  Http("https://mockend.com/Ferdia-Fagan/ProgrammingProblems_Scala/posts/1")
    .execute(parser = {inputStream =>
      upickle.default.read[ResponseJson](ujson.read(inputStream))
  })
val requestResponseBody = response.body
response.code
response.headers
response.cookies

val caseAsJsonString = upickle.default.write(requestResponseBody)
val caseAsJsonObj = ujson.read(caseAsJsonString)

