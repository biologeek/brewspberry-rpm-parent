package net.brewspberry.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
	
	

	public static String encryptPassword (String password, String algorithm){
		MessageDigest  md;
		String messageMD5ed = null;
		
		
		if (algorithm == null || algorithm.equals("")){
			algorithm = "MD5";
		}
		try {
			md = MessageDigest.getInstance(algorithm);
			
			byte[] passByte = password.getBytes();
			
			md.update(passByte);
			byte[] dgested = md.digest();
			
			StringBuilder bd = new StringBuilder();
			for (byte oct : dgested){
				
				bd.append(Integer.toHexString(oct));
				
			}
			messageMD5ed = bd.toString();
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
		return messageMD5ed;
	}

}
