/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.test;

import com.andresusanto.engine.Tools;
import com.andresusanto.object.Payload;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author akhfa
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        Test.testFileByteConvertion();
//        Test.fileLength();
//        Test.dataComplexity();
        Test.generateBC();
//        Test.testArrayCopy();
    }
    
    private static void fileLength() throws IOException
    {
        //byte [] file = Files.readAllBytes(Paths.get("test.txt"));
        byte [] file = Files.readAllBytes(Paths.get("sample.bmp"));
        System.err.println(file.length);
    }
    
    private static void dataComplexity()
    {
        boolean [] data = new boolean[64];
        for (int i = 0; i < 8; i++)
        {
            for (int j=0; j < 8; j++)
            {
                data[i * 8 + j] = true;
            }
        }
        data[28] = false;
        data[30] = false;
        Payload payload = new Payload();
//        System.err.println( payload.getComplexity(data));
    }
    
    private static void testBitBoolean()
    {
        System.out.println(Arrays.toString(Tools.convertToBoolArray("b".getBytes())));
        
        byte [] bytes = Tools.convertToByte(Tools.convertToBoolArray("b".getBytes()));
        for(byte b : bytes)
        {
            System.out.println(Integer.toHexString(b & 0xFF) + ", ");
        }
    }
    private static void printXOR()
    {
        System.err.println("true ^ true = " + (true ^ true));
        System.err.println("true ^ false = " + (true ^ false));
        System.err.println("false ^ true = " + (false ^ true));
        System.err.println("false ^ false = " + (false ^ false));
    }
    
    private static void testFileByteConvertion() throws IOException
    {
        byte [] file = Files.readAllBytes(Paths.get("sample.bmp"));
        FileOutputStream fos = new FileOutputStream("image/sample1.bmp");
        try
        {
            fos.write(file);
        }
        finally{
            fos.close();
        }
    }
    
    private static boolean [] generateBC()
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
        
        // print wc
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                System.err.print(bc[i * 8 + j] == true? 1: 0);
            }
            System.err.println("");
        }
        return bc;
    }
    
    private static void testArrayCopy()
    {
        int arr1[] = { 0, 1, 2, 3, 4, 5, 6, 7};
        int arr2[] = { 0, 10, 20, 30, 40, 50 };

        // copies an array from the specified source array
        System.arraycopy(arr1, 0, arr2, 0, 7);
        
        System.out.print("array2 = ");
        System.out.print(arr2[0] + " ");
        System.out.print(arr2[1] + " ");
        System.out.print(arr2[2] + " ");
        System.out.print(arr2[3] + " ");
        System.out.print(arr2[4] + " ");
    }
}
