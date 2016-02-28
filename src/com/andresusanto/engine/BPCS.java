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
         * 1. Ambil total blok-blok piksel 8x8
         * 2. Ubah setiap bitplane menjadi CGC sambil menghitung dan menyimpan
         *      posisi dimana pesan bisa dimasukkan
         * 3. Bandingkan ukuran pesan dengan jumlah bitplane yang bisa dimasukkan
         * 4. Jika memungkinkan, acak segmen segmen pesan
         * 5. Masukkan segmen yang telah diacak ke dalam bitplane
         * 6. Lakukan konversi balik dari CGC menjadi PBC
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
                    if (complexity > threshold) {
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
        int[] randomizedIndex = Tools.getShuffledInts(key, 0, dataSegmen.size()-1);
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
            //picture.setBitPlane(region, layer, colorCode, currentData);
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
                    //convertToPBC(i, j, colorCode);
                }
            }
        }
    }
    
    // ekstrak data ke pic
    public Payload extract() throws Exception{
        /**
         * 1. Ambil jumlah blok pixel
         * 2. Ubah setiap bitplane menjadi CGC sambil menyimpan letak koordinat pesan
         * 3. Susun kembali semua pesan yang sudah diacak sebelumnya ketika embed
         * 4. Bentuk payload baru
         * 5. Ubah kembali ke PBC (cleanup)
         */
        int bitplaneCount = picture.getTotalRegions();
        ArrayList<BitCoordinate> messageBitplaneLoc = new ArrayList<>();
        int i,j,k;
        /**
         * Ubah coding jadi CGC sambil simpan letak koordinat
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
                    //Convert to CGC while counting message bitplane, then add to messageBitplaneLoc
                    convertToCGC(i, j, colorCode);
                    boolean[] currentBitplane = picture.getBitPlane(i, j, colorCode);
                    float complexity = calculateComplexity(currentBitplane, j, colorCode);
                    if (complexity > threshold) {
                        //Pesan found
                        messageBitplaneLoc.add(new BitCoordinate(i,j,colorCode));
                    }
                }
            }
        }
        
        /**
         * Proceeding to insert the data
         */
        int[] randomizedIndex = Tools.getShuffledInts(key, 0, messageBitplaneLoc.size()-1);
        ArrayList<boolean[]> descrambledDataSegmen = new ArrayList<>();
        while(descrambledDataSegmen.size() < messageBitplaneLoc.size()) descrambledDataSegmen.add(null);
        for (i=0; i < messageBitplaneLoc.size(); i++) {
            BitCoordinate currentCoordinate = messageBitplaneLoc.get(i);
            int region = currentCoordinate.getRegion();
            int layer = currentCoordinate.getBitplane();
            char colorCode = currentCoordinate.getColor();
            boolean[] currentBitplane = picture.getBitPlane(region, layer, colorCode);
            descrambledDataSegmen.set(randomizedIndex[i], currentBitplane);
        }
        
        /**
         * Put bitplane to new payload for return
         */
        
        Payload ret = new Payload(descrambledDataSegmen, key);
        
        /**
         * Turn back coding to PBC (cleanup)
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
        return ret;
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
        boolean[] currentBitplane = picture.getBitPlane(region, layer, colorCode);
        if(region < 10 && layer < 7) {
            System.out.println("Current bitplane: "+ region+" " + layer+" "+ colorCode);
            for(int a=0; a < 64; a++) {
                System.out.print(currentBitplane[a] ? "1 " : "0 ");
            }
            System.out.println();
        }
        /**
         * Transform to 2D array
         */
        int x,y,i;
        i = 0;
        boolean[][] transformedBitplane = new boolean[8][8];
        for (y=0; y < transformedBitplane.length; y++) {
            for (x=0; x < transformedBitplane.length; x++) {
                transformedBitplane[x][y] = currentBitplane[i];
                i++;
            }
        }
        boolean[][] convertedBitplane = new boolean[8][8];
        for (y=0; y < transformedBitplane.length; y++) {
            for (x=0; x < transformedBitplane.length; x++) {
                if (x==0) {
                    convertedBitplane[x][y] = transformedBitplane[x][y];
                } else {
                    convertedBitplane[x][y] = transformedBitplane[x-1][y] ^ transformedBitplane[x][y];
                }
            }
        }
        /**
         * Retransform back to 1D array
         */
        boolean[] currentConvertedBitplane = new boolean[currentBitplane.length];
        i = 0;
        for (y=0; y < convertedBitplane.length; y++) {
            for (x=0; x < convertedBitplane.length; x++) {
                currentConvertedBitplane[i] = convertedBitplane[x][y];
                i++;
            }
        }
        /**
         * Set bitplane
         */
        picture.setBitPlane(region, layer, colorCode, currentBitplane);
        boolean[] afterSetPlane = picture.getBitPlane(region, layer, colorCode);
        if(region < 10 && layer < 7) {
            System.out.println("AfterSet bitplane: " + region+" " + layer+" "+ colorCode);
            for(int a=0; a < 64; a++) {
                System.out.print(afterSetPlane[a] ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
    
    private void convertToPBC(int region, int layer, char colorCode){
        boolean[] currentBitplane = picture.getBitPlane(region, layer, colorCode);
        /**
         * Transform to 2D array
         */
        int x,y,i;
        i = 0;
        boolean[][] transformedBitplane = new boolean[8][8];
        for (y=0; y < transformedBitplane.length; y++) {
            for (x=0; x < transformedBitplane.length; x++) {
                transformedBitplane[x][y] = currentBitplane[i];
                i++;
            }
        }
        boolean[][] convertedBitplane = new boolean[8][8];
        for (y=0; y < transformedBitplane.length; y++) {
            for (x=0; x < transformedBitplane.length; x++) {
                if (x==0) {
                    convertedBitplane[x][y] = transformedBitplane[x][y];
                } else {
                    convertedBitplane[x][y] = convertedBitplane[x-1][y] ^ transformedBitplane[x][y];
                }
            }
        }
        /**
         * Retransform back to 1D array
         */
        boolean[] currentConvertedBitplane = new boolean[currentBitplane.length];
        i = 0;
        for (y=0; y < convertedBitplane.length; y++) {
            for (x=0; x < convertedBitplane.length; x++) {
                currentConvertedBitplane[i] = convertedBitplane[x][y];
                i++;
            }
        }
        /**
         * Set bitplane
         */
        picture.setBitPlane(region, layer, colorCode, currentConvertedBitplane);
        
    }
}
