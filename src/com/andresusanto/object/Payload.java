/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import com.andresusanto.engine.Tools;
import com.andresusanto.object.Segmen;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author akhfa
 */
// class payload data yang disimpan ke picture
public class Payload{
    public int size;
    public String filename; // jadi filename karena ketika save as, defaultnya nama filenya adalah nama file asli
    public boolean dataAwal[]; // pesan awal yang sudah diubah dari byte ke boolean
    private ArrayList<Segmen> Segments; // data awal yang sudah diolah sehingga menjadi bagian bagian (segmen)
                                        //yang sudah berupa 1 bit map konjugasi + 63 bit data
    private float threshold;

    public byte[] createPayload(){ // fungsi untuk mengkonstruk konten menjadi data dengan header
        return null;
    }

    public Payload(){

    }

    /**
     * Menyimpan payload dalam bentuk boolean array
     * @param filename Nama file 
     * @param datasource Data awal dalam byte
     * @param threshold Threshold kompleksitas
     */
    public Payload(String filename, byte datasource[], float threshold){
        this.size = datasource.length;
        this.filename = filename;
        this.dataAwal = Tools.convertToBoolArray(datasource);
        this.threshold = threshold;
        this.generateArrayOfSegments();
    }

    /**
     * Menyimpan payload dalam bentuk boolean array
     * @param filename Nama file
     * @param data Data yang sudah dalam bentuk boolean
     * @param threshold Threshold untuk kompleksitas
     */
    public Payload(String filename, boolean data[], float threshold){
        this.size = data.length;
        this.filename = filename;
        this.dataAwal = data;
        this.threshold = threshold;
        this.generateArrayOfSegments();
    }

    /**
     * Menyimpan array of segmen menjadi data semula
     * @param savePath
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save(String savePath) throws FileNotFoundException, IOException
    {
        try
        (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(Tools.convertToByte(this.dataAwal));
        }
    }

    /**
     * Men-generate array of segment yang sudah siap untuk disisipkan ke gambar.
     */
    private void generateArrayOfSegments() //Tolong cek lagi ya. Terutama index arraycopynya
    {
        int jumlahSegmen;
        if(this.dataAwal.length % 63 == 0)
            jumlahSegmen = this.dataAwal.length / 63;
        else
            jumlahSegmen = (this.dataAwal.length / 63) + 1;
        
        boolean [] dataGenap63 = new boolean[jumlahSegmen * 63]; 
        //data63 adalah data yang akan disisipkan ke pic 
        //data ini sudah dalam boolean array dengan panjang % 63 == 0
        
        //inisialisasi dengan 0
        Arrays.fill(dataGenap63, false);
        
        //copy semua data ke data63
        System.arraycopy(this.dataAwal, 0, dataGenap63, 0, dataAwal.length);
        
        Segments = new ArrayList<>();
        int indeks = 0;
        for(int i = 0; i < dataGenap63.length; i+=63)
        {
            boolean [] dataSegmen = new boolean[63];
            System.arraycopy(dataGenap63, indeks, dataSegmen, 0, 63);
            Segmen segment = new Segmen(false, dataSegmen, this.threshold);
            this.Segments.add(segment);
        }
    }
}
