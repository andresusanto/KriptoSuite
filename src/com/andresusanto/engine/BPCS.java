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
    // format header data yang disisipkan
    // 1. 4 bytes (int) = ukuran file yang disisipkan
    // 2. 1 byte = panjang ekstensi file
    // 3. N byte (N ditentukan oleh panjang ekstensi) = ekstensi file
    // Sehingga payload yang disisipkan : [4 bytes | 1 byte | N byte | KONTEN]
    
    // class payload data yang disimpan ke picture
    public static class BPCSPayload{
        public int size;
        public String extention;
        public byte data[];
        
        public byte[] createPayload(){ // fungsi untuk mengkonstruk konten menjadi data dengan header
            return null;
        }
        
        public BPCSPayload(int size, String extention, byte data[]){
            this.size = size;
            this.extention = extention;
            this.data = data;
        }
        
    }
    
    private Picture picture;
    private float threshold;
    
    public BPCS(String key, Picture picture, float threshold){ // key menjadi seed dari random number generator tempat menyimpan data
        
    }
    
    // sisipkan data ke pic
    public void embed(BPCSPayload data) throws IOException{ // throw exception jika data > payload
        
    }
    
    // ekstrak data ke pic
    public BPCSPayload extract(){
        return null;
    }
    
    public int calculateSpace(){
        return 0;
    }
    
    
    // ---
    // BPCS
    // catatan: semua manipulasi terhadap gambar langsung kepada objek picture
    // ---
    
    private int calculateComplexity(int region){ // karena gambar dibagi menjadi 8 x 8 pixel, maka setiap bagian dinyatakan sbg region
        return 0;
    }
    
    private void convertToCGC(int region){
        
    }
    
    private void convertToPBC(int region){
        
    }
}
