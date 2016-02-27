/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

/**
 *
 * @author akhfa
 */
// Segmen adalah array of boolean berukuran 8x8 dimana sisi kiri atas merupakan boolean konjugasi
public class Segmen {
    private boolean [] data; //kompleksitas dari data ini sudah pasti > threshold karena 
                            //sudah diatur di constructor jika masih kurang kompleks
    
    /**
     * Membuat sebuah segmen of data yang sisi kiri atasnya adalah konjugasi, 
     * dan sisanya data (63 bit)
     * @param konjugasi
     * @param data 63 bit data
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
        
        //cek kompleksitas, jika < threshold, conjugate
        if(this.getComplexity() < threshold)
            this.conjugate();
    }
    
    public boolean [] getData()
    {
        return data;
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
        boolean [] wc = this.getWC();
        
        //set index pertama jadi true
        this.data[0] = true;
        
        //conjugate data sisanya
        for(int i = 1; i < this.data.length; i++)
        {
            this.data[i] = this.data[i] ^ wc[i];
        }
    }
}
