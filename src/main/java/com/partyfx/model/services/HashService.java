package com.partyfx.model.services;

import java.security.MessageDigest;

public class HashService {
    public String toHash(String value){
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            byte[] digest = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            result = hexString.toString();
        }catch (Exception e){
            System.out.println("Hubo un fallo al hashear la palabra");
            result = "";
        }
        return result;
    }
}
