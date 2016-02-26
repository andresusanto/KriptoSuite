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
    private boolean [] data;
    
    /**
     * Membuat sebuah segmen of data yang sisi kiri atasnya adalah konjugasi, da sisanya data
     * @param konjugasi
     * @param data 
     */
    public Segmen(boolean konjugasi, boolean [] data)
    {
        data = new boolean[64];
        data[0] = konjugasi;
        for(int i = 1; i < 64; i++)
        {
            this.data[i] = data[i-1];
        }
    }
    
    public boolean [] getData()
    {
        return data;
    }
}
