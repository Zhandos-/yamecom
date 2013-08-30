/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author TWINS
 */
public class UserUtil {

    public static String createHash(String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(password.getBytes(), 0, password.length());
            String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
            if (hashedPass.length() < 32) {
                hashedPass = "0" + hashedPass;
            }
            return hashedPass;
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
