package hu.unideb.inf.tesla;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		// check if a private key file is provided as a command line argument
		int port;
		String privateKeyFileName;

		if (args.length == 2) {
			port = Integer.parseInt(args[0]);
			privateKeyFileName = args[1];
		} else {
			System.err.println("You must provide a port number and a private key file location to run the server!");
			return;
		}

		// check if file exist
		File privateKeyFile = new File(privateKeyFileName);

		if (!privateKeyFile.exists() || !privateKeyFile.isFile()) {
			System.err.println("The given file doesn't exists, or its not a file!");
			return;
		}

		// read key
		byte[] privateKey;

		try {
			privateKey = FileUtils.getKeyFromFile(privateKeyFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't read key from file!");
			return;
		}

		// start server
		TimeSynchronizationServer timeSynchronizationServer = new TimeSynchronizationServer(port, privateKey);
		timeSynchronizationServer.start();

	}

}
