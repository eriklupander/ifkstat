package se.ifkgoteborg.stat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class PasswordUtil {
	
	public static final String BASE64_ENCODING = "BASE64";
	
	public static String getPasswordHash(String password) {
		String hashAlgorithm = "MD5";
		String hashEncoding = "base64";
		String hashCharset = "UTF-8";

		
		byte[] passBytes;
		String passwordHash = null;

		// convert password to byte data
		try {			
			passBytes = password.getBytes(hashCharset);
		} catch (UnsupportedEncodingException uee) {
			System.out.println("charset " + hashCharset
					+ " not found. Using platform default.");
			passBytes = password.getBytes();
		}

		// calculate the hash and apply the encoding.
		try {
			MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
			md.update(passBytes);

			byte[] hash = md.digest();
			if (hashEncoding.equalsIgnoreCase(BASE64_ENCODING)) {
				passwordHash = encodeBase64(hash);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Password hash calculation failed");
		}
		
		return passwordHash;

	}

	private static String encodeBase64(byte[] bytes) {
		String base64 = null;
		try {
			base64 = Base64Encoder.encode(bytes);
		} catch (Exception e) {
		}
		return base64;
	}

}
