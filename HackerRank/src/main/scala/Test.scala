import scalaj.http._


object Test {

  def main(args: Array[String]): Unit = {
    val response: HttpResponse[String] = Http("http://foo.com/search").param("q","monkeys").asString
    response.body

  }

}
