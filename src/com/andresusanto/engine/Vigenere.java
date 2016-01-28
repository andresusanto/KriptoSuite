/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.option.SpacingOption;

/**
 *
 * @author Andre
 */
public class Vigenere {
    private String key;         // key yang digunakan untuk mengenkripsi
    private boolean fullASCII;  // apakah digunakan full ASCII table (karena pake persamaan matematik, modulo 256)
    private SpacingOption spacing;
    
    public Vigenere(String key, boolean fullASCII, SpacingOption spacing){ // public constructor
        this.key = key;
        this.fullASCII = fullASCII;
        this.spacing = spacing;
    }
    
    public String encrypt(String text) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
                        
            if (fullASCII) // jika full ASCII, pakai module 256
                res.append ( (char)((c + key.charAt(j) - 255) % 256) );
            else {// jika tidak full ASCII, mulai dari karakter 'A', lalu mod dengan 26 (shg hanya A-Z atau 26 karakter)
                if (spacing == SpacingOption.DEFAULT && c == ' ') res.append(' ');
                if (c < 'A' || c > 'Z') continue;
                res.append( (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A') );
            }
            j = ++j % key.length();
        }
        return res.toString();
    }

    public String decrypt(String text) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            if (fullASCII) // jika full ASCII, pakai module 256
                res.append ( (char)((c - key.charAt(j) + 255) % 256) );
            else {// jika tidak full ASCII, mulai dari karakter 'A', lalu mod dengan 26 (shg hanya A-Z atau 26 karakter)
                if (c == ' ') res.append(' ');
                if (c < 'A' || c > 'Z') continue;
                res.append( (char) ((c - key.charAt(j) + 26) % 26 + 'A') );
            }
            j = ++j % key.length();
        }
        return res.toString();
    }
}
