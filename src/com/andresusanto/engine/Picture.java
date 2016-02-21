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
public class Picture {
    public static final char PICTURE_PNG = 'P';
    public static final char PICTURE_BMP = 'B';
    
    private char pictureType;
    private int[] pixels; // 4 byte data - A R G B
    
    public Picture(String address){ // handle file type dan serahkan ke loader khusus
        
    }
    
    // fungsi loader file bitmap
    private void loadBmp(String address){
        
    }
    
    // fungsi loader file Png
    private void loadPng(String address){
        
    }
}
