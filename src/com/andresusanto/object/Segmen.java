/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import com.andresusanto.engine.Tools;

/**
 *
 * @author akhfa
 */
// Segmen adalah array of boolean berukuran 8x8 dimana sisi kiri atas merupakan boolean konjugasi
public class Segmen {
    public static final int SEGMEN_SIZE = 64;
    private final boolean [] data; //kompleksitas dari data ini sudah pasti > threshold karena 
                            //sudah diatur di constructor jika masih kurang kompleks
    
    /**
     * Constructor untuk create segmen tanpa melakukan pengecekan konjugasi
     */
    public Segmen ()
    {
        this.data = new boolean[64];
    }
    
    /**
     * Membuat sebuah segmen of data yang sisi kiri atasnya adalah konjugasi, 
     * dan sisanya data (63 bit)
     * @param konjugasi konjugasi 
     * @param datasource Data 63 bit yang akan dijadikan segmen
     * @param threshold Threshold kompleksitas
     */
    public Segmen(boolean konjugasi, boolean [] datasource, float threshold)
    {
        // bikin data 64 bit
        this.data = new boolean[64];
        this.data[0] = konjugasi;
        for(int i = 1; i < 64; i++)
        {
            this.data[i] = datasource[i-1];
        }
        
//        System.err.println("64 bit segmen sebelum konjugasi");
//        Tools.printArray(this.data);
        //cek kompleksitas, jika < threshold, conjugate
//        System.err.println("kompleksitas = " + this.getComplexity());
//        System.err.println(this.getComplexity() < threshold);
        if(this.getComplexity() < threshold)
            this.conjugate();
//        System.err.println("setelah konjugasi");
//        Tools.printArray(data);
    }
    
    public Segmen(boolean [] datasource, float threshold)
    {
        this.data = datasource;
        if(this.getComplexity() < threshold)
            this.conjugate();
    }
    public boolean [] getData()
    {
        return this.data;
    }
    
    public boolean [] getDataForExtract()
    {
        if(this.data[0]) //jika data[0] = true -> konjugate
        {
//            System.err.println("Sebelum konjugasi");
//            Tools.printArray(this.data);
            this.conjugate();
//            System.err.println("Setelah konjugasi");
//            Tools.printArray(this.data);
        }
        return this.data;
    }
    
    public boolean isConjugate()
    {
        return this.data[0];
    }
    
    /**
     * Men-generate papan catur BC
     * @return Papan catur wc dalam boolean
     */
    private boolean [] getBC()
    {
        boolean [] bc = new boolean[64];
        boolean initBaris = true;
        boolean kolom = true;
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                bc[i * 8 + j] = kolom;
                kolom = !kolom;
            }
            initBaris = !initBaris;
            kolom = initBaris;
        }
        
//        System.err.println("BC");
//        Tools.printMatriks(bc);
        
        return bc;
    }
    
    /**
     * Men-generate papan catur BC
     * @return Papan catur wc dalam boolean
     */
    private static boolean [] getBCStatic()
    {
        boolean [] bc = new boolean[64];
        boolean initBaris = true;
        boolean kolom = true;
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                bc[i * 8 + j] = kolom;
                kolom = !kolom;
            }
            initBaris = !initBaris;
            kolom = initBaris;
        }
        
//        System.err.println("BC");
//        Tools.printMatriks(bc);
        
        return bc;
    }
    
    /**
     * Menghitung complexity dari byte pesan
     * @param data Data berupa segmen dari pesan berukuran exactly 8x8 dan kiri atasnya sudah berupa konjugation map
     * @return kompleksitas pesan dalam float
     */
    private float getComplexity()
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
     * Melakukan konjugasi terhadap data
     */
    private void conjugate()
    {
        boolean [] bc = this.getBC();
        
        for(int i = 0; i < this.data.length; i++)
        {
            this.data[i] = this.data[i] ^ bc[i];
        }
    }
    
    public static boolean [] conjugate(boolean [] data)
    {
        boolean [] bc = getBCStatic();
        
        for(int i = 0; i < data.length; i++)
        {
            data[i] = data[i] ^ bc[i];
        }
        
        return data;
    }
}
