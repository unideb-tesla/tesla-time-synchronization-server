package hu.unideb.inf.tesla;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeSynchronizationServer {

	private int port;
	private final byte[] privateKey;

	private ServerSocket serverSocket;

	private boolean isRunning;

	public TimeSynchronizationServer(int port, byte[] privateKey) {
		this.port = port;
		this.privateKey = privateKey;
	}

	public void start() {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		isRunning = true;

		while (isRunning) {

			Socket client;

			try {
				client = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

			TimeSynchronizationThread timeSynchronizationThread = new TimeSynchronizationThread(client, privateKey);
			new Thread(timeSynchronizationThread).start();

		}

	}

	public void stop() {

		isRunning = false;

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
