/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import com.andresusanto.engine.Tools;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author akhfa
 */
// class payload data yang disimpan ke picture
    public class Payload{
        public int size;
        public String filename; // jadi filename karena ketika save as, defaultnya nama filenya adalah nama file asli
        public boolean data[]; // pesan yang sudah diubah dari byte ke boolean
        private ArrayList<Segmen> Segments = new ArrayList<>();
        
        public byte[] createPayload(){ // fungsi untuk mengkonstruk konten menjadi data dengan header
            return null;
        }
        
        public Payload(){
            
        }
        
        /**
         * Menyimpan payload dalam bentuk boolean array
         * @param filename Nama file
         * @param data 
         */
        public Payload(String filename, byte data[]){
            this.size = data.length;
            this.filename = filename;
            this.data = Tools.convertToBoolArray(data);
        }
        
        /**
         * Menyimpan payload dalam bentuk boolean array
         * @param filename Nama file
         * @param data Data yang sudah dalam bentuk boolean
         */
        public Payload(String filename, boolean data[]){
            this.size = data.length;
            this.filename = filename;
            this.data = data;
        }
        
        /**
         * Menghitung complexity dari byte pesan
         * @param data Data berupa segmen dari pesan berukuran exactly 8x8
         * @return kompleksitas pesan dalam float
         */
        public float getComplexity(boolean [] data)
        {
            float complexity = 0;
        
            for (int y = 0; y < 8; y++){
                for (int x = 0; x < 8; x++){
                    if (!data[y * 8 + x]) continue;

                    if (y > 0 && !data[(y - 1) * 8 + x]) complexity++;
                    if (x > 0 && !data[y * 8 + (x - 1)]) complexity++;
                    if (y + 1 < 8 && !data[(y + 1) * 8 + x]) complexity++;
                    if (x + 1 < 8 && !data[y * 8 + (x + 1)]) complexity++;
                }
            }
            return complexity / 112.0f;
        }
        
        /**
         * Menyimpan payload menjadi suatu file
         * @param savePath
         * @throws FileNotFoundException
         * @throws IOException 
         */
        public void save(String savePath) throws FileNotFoundException, IOException
        {
            FileOutputStream fos = new FileOutputStream(savePath);
            try
            {
                fos.write(Tools.convertToByte(data));
            }
            finally{
                fos.close();
            }
        }
        
        /**
         * Men-generate papan catur WC
         * @return Papan catur wc dalam boolean
         */
        private boolean [] getWC()
        {
            boolean [] wc = new boolean[64];
            boolean initBaris = false;
            boolean kolom = false;
            for(int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    wc[i * 8 + j] = kolom;
                    kolom = !kolom;
                }
                initBaris = !initBaris;
                kolom = initBaris;
            }
            return wc;
        }
    }
