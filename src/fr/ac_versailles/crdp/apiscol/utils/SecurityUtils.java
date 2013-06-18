package fr.ac_versailles.crdp.apiscol.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.jersey.core.util.Base64;

public class SecurityUtils {

	public static String hashWithSharedSecret(String token, String salt) {

		byte[] bytesOfMessage = null;
		String mix = token + salt;
		bytesOfMessage = mix.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			// impossible
			e.printStackTrace();
		}
		byte[] result = md.digest(bytesOfMessage);
		
		try {
			return new String(Base64.encode(result), "ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
