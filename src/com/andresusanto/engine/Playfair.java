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
        
        this.key = tmp.toString();
    }
    
    private StringBuilder preprocess(String plain){
        StringBuilder plaintxt = new StringBuilder(plain.replace('J', 'I'));
        
        for (int i = 0; i < plaintxt.length() - 1; i += 2){
           if (plaintxt.charAt(i) == plaintxt.charAt(i + 1)){
               plaintxt.insert(i + 1, 'Z');
           }
        }
        
        if (plaintxt.length() % 2 != 0)
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
        StringBuilder encrypted = new StringBuilder();
        
        for (int i = 0; i < plain.length(); i += 2){
            Point pos1 = getCharPos(plain.charAt(i));
            Point pos2 = getCharPos(plain.charAt(i + 1));
            
            if (pos1.x == pos2.x){
                encrypted.append(getCharByPos(new Point(pos1.x, (pos1.y + 1) % 5)));
                encrypted.append(getCharByPos(new Point(pos2.x, (pos2.y + 1) % 5)));
            }else if (pos1.y == pos2.y){
                encrypted.append(getCharByPos(new Point((pos1.x + 1) % 5, pos1.y)));
                encrypted.append(getCharByPos(new Point((pos2.x + 1) % 5, pos2.y)));
            }else{
                encrypted.append(getCharByPos(new Point(pos2.x, pos1.y)));
                encrypted.append(getCharByPos(new Point(pos1.x, pos2.y)));
            }
        }
        
        return encrypted.toString();
    }
    
    public String decrypt(String text) {
        return "";
    }
    
    private static class Point{
        public int x, y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
