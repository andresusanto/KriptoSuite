/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Picture;
import com.andresusanto.object.Payload;
import com.andresusanto.object.BitCoordinate;
import com.andresusanto.object.Segmen;
import java.io.IOException;
import java.util.ArrayList;

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
    
    private Picture picture;
    private float threshold;
    private String key;
    
    public BPCS(String key, Picture picture, float threshold){ // key menjadi seed dari random number generator tempat menyimpan data
        this.key = key;
        this.picture = picture;
        this.threshold = threshold;
    }
    
    // sisipkan data ke pic
    public void embed(Payload data) throws IOException{ // throw exception jika data > payload
        /**
         * 1. Bagi image jadi 8x8 pixel (done by Picture class), getTotalRegions()
         * 2. Ubah data jadi segmen, Segmen class
         * 3. Compare size dan segmen muat apa gk
         * 2. getBitPlane() itu PBC
         * 3. ubah jadi CGC
         * 4. Tentukan kompleksitas, calculateComplexity()
         * 5. if noise like (< threshold) 
         *  cek complexity message, kalau kurang, konjugasi dulu
         *  if passed threshold then
         *  replace bitplane pakai setBitPlane()
         * 6. else ignore
         * 7. di akhir ubah setiap bitplane yg tadi jadi CGC jadi PBC
         */
        int bitplaneCount = picture.getTotalRegions();
        ArrayList<BitCoordinate> insertableBitplaneLoc = new ArrayList<>();
        int i,j,k;
        /**
         * Following parts will only convert and count insertable bitplane location
         * i = region
         * j = layer
         * k = color code
         */
        for (i=0; i < bitplaneCount; i++) {
            for (j=0; j < 8; j++) {
                for(k=0; k < 3; k++) {
                    char colorCode;
                    switch(k) {
                        case (0): colorCode = 'R';
                            break;
                        case (1): colorCode = 'G';
                            break;
                        case (2): colorCode = 'B';
                            break;
                        default: colorCode = 'E'; //actually just to silence the compiler
                            break;
                    }
                    //Convert to CGC while counting insertable bitplane, then add to insertableBitplaneLoc
                    convertToCGC(i, j, colorCode);
                    boolean[] currentBitplane = picture.getBitPlane(i, j, colorCode);
                    float complexity = calculateComplexity(currentBitplane, j, colorCode);
                    if (complexity < threshold) {
                        //Insertable bitplane found
                        insertableBitplaneLoc.add(new BitCoordinate(i,j,colorCode));
                    }
                }
            }
        }
        /**
         * Below would be setting up various payload to be inserted to the picture
         * and counting the insertable size vs payload size
         */
        ArrayList<Segmen> dataSegmen = data.getAllSegments();
        if(insertableBitplaneLoc.size() < dataSegmen.size()) {
            //Inserted data too big, refusing
            throw new IOException("Data yang akan dimasukkan terlalu besar!");
        } 
        
        /**
         * Proceeding to insert the data
         */
        int[] randomizedIndex = Tools.getShuffledInts(key, 0, dataSegmen.size());
        ArrayList<Segmen> scrambledDataSegmen = new ArrayList<>();
        for (i=0; i < dataSegmen.size(); i++) {
            scrambledDataSegmen.add(dataSegmen.get(randomizedIndex[i]));
        }
        for (i=0; i < scrambledDataSegmen.size(); i++) {
            Segmen currentSegmen = scrambledDataSegmen.get(i);
            BitCoordinate currentCoordinate = insertableBitplaneLoc.get(i);
            int region = currentCoordinate.getRegion();
            int layer = currentCoordinate.getBitplane();
            char colorCode = currentCoordinate.getColor();
            boolean[] currentData = currentSegmen.getData();
            picture.setBitPlane(region, layer, colorCode, currentData);
        }
        
        /**
         * Turn back coding to PBC
         */
        for (i=0; i < bitplaneCount; i++) {
            for (j=0; j < 8; j++) {
                for(k=0; k < 3; k++) {
                    char colorCode;
                    switch(k) {
                        case (0): colorCode = 'R';
                            break;
                        case (1): colorCode = 'G';
                            break;
                        case (2): colorCode = 'B';
                            break;
                        default: colorCode = 'E'; //actually just to silence the compiler
                            break;
                    }
                    convertToPBC(i, j, colorCode);
                }
            }
        }
    }
    
    // ekstrak data ke pic
    public Payload extract(){
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
    
    private void convertToCGC(int region, int layer, char colorCode){
        
    }
    
    private void convertToPBC(int region, int layer, char colorCode){
        
    }
}
