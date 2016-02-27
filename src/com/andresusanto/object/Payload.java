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
     * @param data 
     */
    public Payload(String filename, byte datasource[], float threshold){
        this.size = datasource.length;
        this.filename = filename;
        this.dataAwal = Tools.convertToBoolArray(datasource);
        this.threshold = threshold;
        this.generateArrayOfSegments();
//        boolean [] dataArray = Tools.convertToBoolArray(datasource);
//        int jumlahSegmen;
//        if(datasource.length % 63 == 0)
//            jumlahSegmen = (data.length / 63);
//        else
//            jumlahSegmen = (data.length / 63) + 1;
//        
//        this.data = new boolean[64 * jumlahSegmen]; // 64 karena digunakan untuk menampung konjugasi map
//        for (int i = 0; i < data.length; i += 63)
//        {
//            // inisialisasi mapping konjugasi jadi false
//            this.data[i] = false;
//            
//            //copy 63 datasource ke this.data
//            System.arraycopy(data, i, this.data, i+1, 63);
//        }
        
        
    }

    /**
     * Menyimpan payload dalam bentuk boolean array
     * @param filename Nama file
     * @param data Data yang sudah dalam bentuk boolean
     */
    public Payload(String filename, boolean data[]){
        this.size = data.length;
        this.filename = filename;
        this.dataAwal = data;
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
            fos.write(Tools.convertToByte(dataAwal));
        }
        finally{
            fos.close();
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
            Segments.add(segment);
        }
    }
}
