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
            if (tmp.indexOf("" + key.charAt(i)) == -1 && key.charAt(i) != 'J')
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
        System.out.println(preprocess("DSASU"));
    }
    
    private StringBuilder preprocess(String plain){
        StringBuilder plaintxt = new StringBuilder(plain.replace('J', 'I'));
        
        char prev = '\0';
        for (int i = 0; i < plaintxt.length(); i++){
           if (plaintxt.charAt(i) == prev){
               plaintxt.insert(i, 'Z');
           }
           prev = plaintxt.charAt(i);
        }
        
        if (plaintxt.length() % 2 != 0)
            plaintxt.append('Z');
        
        return plaintxt;
    }
}
