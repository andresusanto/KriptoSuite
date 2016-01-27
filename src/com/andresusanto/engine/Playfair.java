/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

/**
 *
 * @author Andre
 */
public class Playfair {
    private String key;
    
    public Playfair(String key){
        // bentuk tabel key dari key yang dimasukkan oleh pengguna
        StringBuilder tmp = new StringBuilder();
        
        for(int i = 0; i < key.length() && i < 25; i++){
            if (tmp.indexOf("" + key.charAt(i)) == -1)
                tmp.append(key.charAt(i));
        }
        
        if (tmp.length() < 25){
            for(int i = 'A'; i <= 'Z' && tmp.length() < 25; i++){
                if (i == 'J') continue; // hilangkan J
                
                if (tmp.indexOf("" + (char)i) == -1)
                    tmp.append((char)i);
            }
        }
        
        key = tmp.toString();
        System.out.println(key);
    }
}
