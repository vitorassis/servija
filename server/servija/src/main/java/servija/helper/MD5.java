package servija.helper;

import java.security.*;
import java.math.*;

public class MD5 {
    public static String hash(String plain){
       MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       m.update(plain.getBytes(),0,plain.length());
       return new BigInteger(1,m.digest()).toString(16);
    }
}