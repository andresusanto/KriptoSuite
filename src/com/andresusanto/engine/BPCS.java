/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import java.io.IOException;

/**
 *
 * @author Andre
 */
public class BPCS {
    // class payload data yang disimpan ke picture
    public static class BPCSPayload{
        public int size;
        public String extention;
        public byte data[];
        
        public BPCSPayload(int size, String extention, byte data[]){
            this.size = size;
            this.extention = extention;
            this.data = data;
        }
    }
    
    private Picture picture;
    
    public BPCS(String key, Picture picture){
        
    }
    
    // sisipkan data ke pic
    public void embed(BPCSPayload data) throws IOException{ // throw exception jika data > payload
        
    }
    
    // ekstrak data ke pic
    public BPCSPayload extract(){
        return null;
    }
}
