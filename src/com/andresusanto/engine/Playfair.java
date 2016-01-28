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
public class Playfair {
    private String key;
    private SpacingOption spacing;
    
    public Playfair(String key, SpacingOption spacing){
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
        
        this.key = tmp.toString();
        this.spacing = spacing;
    }
    
    private int findNextChar(int start, StringBuilder plain){
        if (start >= plain.length()) return -1;
        
        for (int i = start; i < plain.length(); i++){
            if (plain.charAt(i) != ' ') return i;
        }
        return -1;
    }
    
    private StringBuilder preprocess(String plain){
        StringBuilder plaintxt = new StringBuilder(plain.replace('J', 'I'));
        
        if (spacing != SpacingOption.DEFAULT)
            plaintxt = new StringBuilder(plaintxt.toString().replace(" ", ""));
        
        
        int i = findNextChar(0, plaintxt);
        int j = findNextChar(i + 1, plaintxt);

        while (i != -1 && j != -1){
            if (plaintxt.charAt(i) == plaintxt.charAt(j)){
                plaintxt.insert(j, 'Z');
            }

            i = findNextChar(j + 1, plaintxt);
            j = findNextChar(i + 1, plaintxt);
        }
        
        if (plaintxt.toString().replace(" ", "").length() % 2 != 0)
            plaintxt.append('Z');
        
        return plaintxt;
    }
    
    private Point getCharPos(char C){
        int index = key.indexOf(C);
        int x = index % 5;
        int y = index / 5;
        
        return new Point(x, y);
    }
    
    private char getCharByPos(Point pos){
        int index = pos.x + pos.y * 5;
        return key.charAt(index);
    }
    
    public String encrypt(String text) {
        StringBuilder plain = preprocess(text);
        StringBuilder encrypted = new StringBuilder(plain);
        
        int i = findNextChar(0, plain);
        int j = findNextChar(i + 1, plain);
        
        while (i != -1 && j != -1){
            Point pos1 = getCharPos(plain.charAt(i));
            Point pos2 = getCharPos(plain.charAt(j));
            
            if (pos1.x == pos2.x){
                encrypted.setCharAt(i, getCharByPos(new Point(pos1.x, (pos1.y + 1) % 5)));
                encrypted.setCharAt(j, getCharByPos(new Point(pos2.x, (pos2.y + 1) % 5)));
            }else if (pos1.y == pos2.y){
                encrypted.setCharAt(i, getCharByPos(new Point((pos1.x + 1) % 5, pos1.y)));
                encrypted.setCharAt(j, getCharByPos(new Point((pos2.x + 1) % 5, pos2.y)));
            }else{
                encrypted.setCharAt(i, getCharByPos(new Point(pos2.x, pos1.y)));
                encrypted.setCharAt(j, getCharByPos(new Point(pos1.x, pos2.y)));
            }
            
            i = findNextChar(j + 1, plain);
            j = findNextChar(i + 1, plain);
        }
        
        
        if (spacing == SpacingOption.GROUP_5){
            int sumSpace = encrypted.length() / 5;
            for (int k = 1; k <= sumSpace; k++){
                encrypted.insert(k * 5 + (k - 1), ' ');
            }
        }
        
        return encrypted.toString();
    }
    
    public String decrypt(String text) {
        StringBuilder encrypted = new StringBuilder(text);
        StringBuilder decrypted = new StringBuilder(text);
        
        int i = findNextChar(0, encrypted);
        int j = findNextChar(i + 1, encrypted);
        
        while (i != -1 && j != -1){
            Point pos1 = getCharPos(text.charAt(i));
            Point pos2 = getCharPos(text.charAt(j));
            
            if (pos1.x == pos2.x){
                decrypted.setCharAt(i, getCharByPos(new Point(pos1.x, (pos1.y + 4) % 5)));
                decrypted.setCharAt(j, getCharByPos(new Point(pos2.x, (pos2.y + 4) % 5)));
            }else if (pos1.y == pos2.y){
                decrypted.setCharAt(i, getCharByPos(new Point((pos1.x + 4) % 5, pos1.y)));
                decrypted.setCharAt(j, getCharByPos(new Point((pos2.x + 4) % 5, pos2.y)));
            }else{
                decrypted.setCharAt(i, getCharByPos(new Point(pos2.x, pos1.y)));
                decrypted.setCharAt(j, getCharByPos(new Point(pos1.x, pos2.y)));
            }
            
            i = findNextChar(j + 1, encrypted);
            j = findNextChar(i + 1, encrypted);
        }
        
        return decrypted.toString();
    }
    
    private static class Point{
        public int x, y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
