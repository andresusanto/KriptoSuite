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
        public String filename; // jadi filename karena ketika save as, defaultnya nama filenya adalah nama file asli
        public byte data[];
        
        public byte[] createPayload(){ // fungsi untuk mengkonstruk konten menjadi data dengan header
            return null;
        }
        
        public BPCSPayload(int size, String filename, byte data[]){
            this.size = size;
            this.filename = filename;
            this.data = data;
        }
        
    }
    
    private Picture picture;
    private float threshold;
    private String key;
    
    public BPCS(String key, Picture picture, float threshold){ // key menjadi seed dari random number generator tempat menyimpan data
        this.key = key;
        this.picture = picture;
        this.threshold = threshold;
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
    
    // fungsi untuk menghitung komplesitas suatu bitplane
    private float calculateComplexity(boolean bitPlane[], int layer, char colorCode){ // karena gambar dibagi menjadi 8 x 8 pixel, maka setiap bagian dinyatakan sbg region
        //boolean[] bitPlane = picture.getBitPlane(region, layer, colorCode);
        float complexity = 0;
        
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (!bitPlane[y * 8 + x]) continue;
                
                if (y > 0 && !bitPlane[(y - 1) * 8 + x]) complexity++;
                if (x > 0 && !bitPlane[y * 8 + (x - 1)]) complexity++;
                if (y + 1 < 8 && !bitPlane[(y + 1) * 8 + x]) complexity++;
                if (x + 1 < 8 && !bitPlane[y * 8 + (x + 1)]) complexity++;
            }
        }
        return complexity / 112.0f; // maks kompleksitas adl:  ((nrows-1)*ncols) + ((ncols-1)*nrows)
    }
    
    
    // ---
    // CGC PBC
    // catatan: semua manipulasi terhadap gambar langsung kepada objek picture
    // ---
    
    private void convertToCGC(int region, int layer){
        
    }
    
    private void convertToPBC(int region, int layer){
        
    }
}
