import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;


/**
 * @author Kovalev Viktor
 */
public class Main
{
    private static final int CONNECT_TIMEOUT_MS = 100;

    private static final int READ_TIMEOUT_MS = 100;

    public static void main(String[] args) throws IOException
    {
        System.out.println("Write path to file with download list: ");
        final var path = new Scanner(System.in).nextLine();
        final var lines = Files.lines(Path.of(path));
        final var nameToUrl = lines.collect(Collectors.toMap(line -> line.split(" ")[0], line -> line.split(" ")[1]));
        nameToUrl.forEach((name, url) ->
        {
            try
            {
                final var url1 = new URL(url);
                final var fileExtension = url1.getPath().substring(url1.getFile().lastIndexOf("."));
                FileUtils.copyURLToFile(
                        url1,
                        new File(name + fileExtension),
                        CONNECT_TIMEOUT_MS,
                        READ_TIMEOUT_MS);
            }
            catch (IOException e)
            {
                e.printStackTrace(); // ебал рот ваши java
            }
        });
    }
}
