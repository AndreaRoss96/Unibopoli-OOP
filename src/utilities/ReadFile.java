package utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class private a public static method to read File.
 *  
 * @author Matteo Alesiani 
 */

public class ReadFile {

	/**
	 * Return a Stream of String read from a specific file.
	 * 
	 * @param  path of file that must be read.
     * @throws IOException if the specified path is  not valid.
	 * @return <tt>Stream of String</tt> of record inside the file.
	 */
	public static Stream<String> readFile(String pathfile) throws IOException {	
		return Files.lines(Paths.get(pathfile), Charset.defaultCharset());
	}
}
