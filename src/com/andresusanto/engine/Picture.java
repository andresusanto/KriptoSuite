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
    
    public char pictureType;
    private int[] pixels; // 4 byte data - A R G B
    
    public Picture(String address){ // handle file type dan serahkan ke loader khusus
        
    }
    
    // mengambil bitplane pada region tertentu dan pada layer tertentu
    public boolean[] getBitPlane(int region, int layer){
        return null;
    }
    
    // fungsi loader file bitmap
    private void loadBmp(String address){
        
    }
    
    // fungsi loader file Png
    private void loadPng(String address){
        
    }
    
    public void save(String address){ // handle file type dan save ke format tersebut
        
    }
    
    private void saveBmp(String address){
        
    }
    
    private void savePng(String address){
        
    }
}
