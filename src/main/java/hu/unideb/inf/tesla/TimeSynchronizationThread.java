package hu.unideb.inf.tesla;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.time.Instant;

public class TimeSynchronizationThread implements Runnable {

	private Socket client;
	private final byte[] privateKey;

	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	public TimeSynchronizationThread(Socket client, byte[] privateKey) {
		this.client = client;
		this.privateKey = privateKey;
	}

	@Override
	public void run() {

		// open the input streams
		try {

			inputStream = new DataInputStream(client.getInputStream());
			outputStream = new DataOutputStream(client.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// time synchronization

		try {

			// read nonce
			int nonceSize = inputStream.readInt();
			byte[] nonce = new byte[nonceSize];
			inputStream.read(nonce, 0, nonceSize);

			// create signature for nonce
			byte[] signature = DigitalSignature.sign(nonce, privateKey);

			// save time stamp and send back with once signature
			Instant instant = Instant.now();
			long timeStampInMillis = instant.toEpochMilli();
			outputStream.writeLong(timeStampInMillis);
			outputStream.writeInt(signature.length);
			outputStream.write(signature);

		} catch (IOException e) {
			e.printStackTrace();
			closeConnection();
			return;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			closeConnection();
			return;
		} catch (SignatureException e) {
			e.printStackTrace();
			closeConnection();
			return;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			closeConnection();
			return;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			closeConnection();
			return;
		}

		closeConnection();

	}

	public void closeConnection() {

		try {

			inputStream.close();
			outputStream.close();

			client.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
