package hu.unideb.inf.tesla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

	public static byte[] getKeyFromFile(String keyFilePath) throws IOException {

		File keyFile = new File(keyFilePath);

		if (!keyFile.exists()) {
			return null;
		}

		FileInputStream fileInputStream = new FileInputStream(keyFile);

		byte[] key = new byte[fileInputStream.available()];
		fileInputStream.read(key);

		fileInputStream.close();

		return key;

	}

}
