package hu.unideb.inf.tesla;

import sun.security.provider.DSAPrivateKey;

import java.security.*;

public class DigitalSignature {

	public static final String PROVIDER_SUN = "SUN";
	public static final String SHA256_WITH_DSA = "SHA256withDSA";

	private DigitalSignature() {
	}

	public static byte[] sign(byte[] message, byte[] privateKey) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		Signature dsa = Signature.getInstance(SHA256_WITH_DSA, PROVIDER_SUN);

		PrivateKey pk = new DSAPrivateKey(privateKey);
		dsa.initSign(pk);

		dsa.update(message);

		byte[] realSig = dsa.sign();

		return realSig;

	}


}
