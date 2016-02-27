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
    
    // format header data yang disisipkan
    // 1. 4 bytes (int) = ukuran file yang disisipkan
    // 2. 1 byte = panjang nama file
    // 3. N byte (N ditentukan oleh panjang nama file) = nama file
    // Sehingga payload yang disisipkan : [4 bytes | 1 byte | N byte | KONTEN]
    
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
     * Menyimpan array of segmen menjadi data semula. 
     * @param savePath
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save(String savePath) throws FileNotFoundException, IOException
    {
        for(Segmen s : Segments)
        {
            
        }
        try
        (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(Tools.convertToByte(this.dataAwal));
        }
    }

    /**
     * Men-generate array of segment yang sudah siap untuk disisipkan ke gambar.
     */
    private void generateArrayOfSegments()
    {
        int jumlahSegmen;
        
        // convert header jadi bit boolean array
        // filesize dan nfilename dalam bit
        boolean[] fileSize = Tools.convertToBoolArray(Tools.intToBytes(this.size));
        boolean[] fileName = Tools.convertToBoolArray(Tools.stringToBytes(this.filename));
        byte [] fileNameLengthByte = {(byte)fileName.length};
        boolean[] nFileName = Tools.convertToBoolArray(fileNameLengthByte);
//        System.err.println("nDataAwal = " + this.dataAwal.length);
//        Tools.printArray(dataAwal);
//        System.err.println("bitFileSize = " + fileSize.length);
//        Tools.printArray(fileSize);
//        System.err.println("bitFileName = " + fileName.length);
//        Tools.printArray(fileName);
//        System.err.println("nFileName = " + nFileName.length + " bit");
//        Tools.printArray(nFileName);
        
        //Gabungkan header dengan data menjadi data baru
        boolean [] headerAndData = new boolean[fileSize.length + fileName.length + 
                                    nFileName.length + this.dataAwal.length];
        System.arraycopy(fileSize, 0, headerAndData, 0, fileSize.length);
        System.arraycopy(nFileName, 0, headerAndData, fileSize.length, nFileName.length);
        System.arraycopy(fileName, 0, headerAndData, fileSize.length + nFileName.length, fileName.length);
        System.arraycopy(this.dataAwal, 0, headerAndData, fileSize.length + nFileName.length + fileName.length, 
                        this.dataAwal.length);
        
        if(headerAndData.length % 63 == 0)
            jumlahSegmen = headerAndData.length / 63;
        else
            jumlahSegmen = (headerAndData.length / 63) + 1;
        
        boolean [] dataGenap63 = new boolean[jumlahSegmen * 63]; 
        //data63 adalah data yang akan disisipkan ke pic 
        //data ini sudah dalam boolean array dengan panjang % 63 == 0
        
        //inisialisasi dengan 0
//        System.err.println("data.length = " + this.dataAwal.length);
        Arrays.fill(dataGenap63, false);
//        System.err.println("datagenap63.length = " + dataGenap63.length);
//        System.err.println("init data genap 63 false: "); Tools.printArray(dataGenap63);
        
        //copy semua data ke data63
        System.arraycopy(headerAndData, 0, dataGenap63, 0, headerAndData.length);
//        System.err.println("headerAndData genap 63 : "); Tools.printArray(dataGenap63);
//        System.err.println("panjang headerAndData = " + dataGenap63.length); 
        
        Segments = new ArrayList<>();
        
        for(int i = 0; i < dataGenap63.length; i+=63)
        {
//            System.err.println("i = " + i);
            boolean [] dataSegmen = new boolean[63];
            System.arraycopy(dataGenap63, i, dataSegmen, 0, 63);
//            System.err.println("63 bit dataSegmen ke " + i);
//            Tools.printArray(dataSegmen);
            Segmen segment = new Segmen(false, dataSegmen, this.threshold);
//            System.err.println("64 bit segmen ke " + i);Tools.printArray(segment.getData());
            this.Segments.add(segment);
        }
    }
    public ArrayList<Segmen> getAllSegments()
    {
        return Segments;
    }
    public boolean [] getSegmenData(int index)
    {
        return Segments.get(index).getData();
    }
}
