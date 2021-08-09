import java.util.Arrays;
import java.util.HashSet;

public class TestQ1 {

    private final static HashSet<String> music_FileTypeExtensions =
            new HashSet<>(Arrays.asList("mp3", "aac", "flac"));
    private final static HashSet<String> image_FileTypeExtensions =
            new HashSet<>(Arrays.asList("jpg", "bmp", "gif"));
    private final static HashSet<String> movie_FileTypeExtensions =
            new HashSet<>(Arrays.asList("mp4", "avi", "mkv"));
    private final static HashSet<String> other_FileTypeExtensions =
            new HashSet<>(Arrays.asList("7z", "txt", "zip"));


    public static String solution(String S) {
        // write your code in Java SE 8
        String[] files = S.split("\n");

        int music_TotalFileSize = 0;
        int image_TotalFileSize = 0;
        int movie_TotalFileSize = 0;
        int other_TotalFileSize = 0;

        for(String file: files){
            String[] fileNameAndSize = file.split(" ");

            String[] fileNameInfo = fileNameAndSize[0].split("\\.");

            String fileType = fileNameInfo[fileNameInfo.length - 1];

            String fileSizeInBytes = fileNameAndSize[1];
            int fileSize = Integer.parseInt(fileSizeInBytes.substring(0,fileSizeInBytes.length() - 1));

            if(music_FileTypeExtensions.contains(fileType)){
                music_TotalFileSize += fileSize;
            }
            else if(image_FileTypeExtensions.contains(fileType)){
                image_TotalFileSize += fileSize;
            }
            else if(movie_FileTypeExtensions.contains(fileType)){
                movie_TotalFileSize += fileSize;
            }
            else if(other_FileTypeExtensions.contains(fileType)){
                other_TotalFileSize += fileSize;
            }
        }

        return
                "music " + music_TotalFileSize + "\n" +
                "images " + image_TotalFileSize + "\n" +
                "movies " + movie_TotalFileSize + "\n" +
                "other " + other_TotalFileSize;
    }

    public static void main(String[] args) {
        String test = "my.song.mp3 11b\n"+
                        "greatSong.flac 1000b\n" +
                        "not3.txt 5b\n" +
                        "game.exe 100b\n" +
                        "mov!e.mkv 10000b\n" +
                        "video.mp4 200b\n";

        System.out.println(solution(test));
    }
}
