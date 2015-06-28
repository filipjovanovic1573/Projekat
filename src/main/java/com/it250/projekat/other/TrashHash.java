package com.it250.projekat.other;

public class TrashHash {

    /**
     * Turns given password into hash.
     * @param password
     * @return
     */
    public static String toHash(String password){
        long hash = 0;
        
        for (int i = 0; i < password.length(); i++) {
            hash = password.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        
        if(hash < 0){
            hash *= -1;
        }
        
        return Long.toString(hash);
    }
}
