package com.angelhazmato.proyecto.servicio.controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PwdUtils {

    private static final String PBKDF2 = "PBKDF2WithHmacSHA256";

    public static String salt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String pwd, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec pbeks = new PBEKeySpec(pwd.toCharArray(), Base64.getDecoder().decode(salt), 100000, 256);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2);
        byte[] hash = skf.generateSecret(pbeks).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }
}
