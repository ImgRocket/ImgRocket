package cn.imgrocket.imgrocket.tool;

import android.text.TextUtils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Function_Java {
    public static String salt(String string, String salt) {
        String text = string + salt;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(text.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
