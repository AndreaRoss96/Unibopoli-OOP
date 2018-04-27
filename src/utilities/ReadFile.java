package utilities;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ReadFile {

	public static Stream<String> readFile(String pathfile) throws IOException {
		return Files.lines(FileSystems.getDefault().getPath(pathfile));
	}
}
