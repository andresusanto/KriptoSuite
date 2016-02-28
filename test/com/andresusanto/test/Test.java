/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.test;

import com.andresusanto.engine.Tools;
import com.andresusanto.object.Payload;
import com.andresusanto.object.Segmen;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akhfa
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        Test.testFileByteConvertion();
//        Test.fileLength();
//        Test.dataComplexity();
//        Test.generateBC();
//        Test.testArrayCopy();
//        Test.testPayload();
//        Test.testStringByte();
//        Test.testNamaFile();
//        Test.testPayload();
//        Test.testScramblerDescrambler();
//        Test.testFloatByte();

        Test.bitPlane();
    }
    
    public static void bitPlane(){
        Picture pic = new Picture("");
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
//        Payload payload = new Payload();
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
        Tools.printMatriks(bc);
        return bc;
    }
    
    private static void testArrayCopy()
    {
        int arr1[] = { 0, 1, 2,};
        int arr2[] = { 3, 4, 5};
        int [] arrGabung = {6,7,8,9,10,11};

        // copies an array from the specified source array
//        System.arraycopy(arr1, 0, arrGabung, 0, 3);
//        System.arraycopy(arr2, 0, arrGabung, 3, 3);
//        System.arraycopy(arrGabung, 0, arr1, 0, 3);
//        System.arraycopy(arrGabung, 3, arr2, 0, 3);
        for(int in = 0; in < arrGabung.length; in+=3)
        {
            int [] newArr = new int[3];
            System.arraycopy(arrGabung, in, newArr, 0, 3);
            for (int i : newArr)
                System.err.println(i + " ");
        }
        
//        for (int i : arrGabung)
//            System.err.print(i + " ");
//        for (int i : arr1)
//            System.err.print(i + " ");
//        for (int i : arr2)
//            System.err.print(i + " ");
//        System.out.print("array2 = ");
//        System.out.print(arr2[0] + " ");
//        System.out.print(arr2[1] + " ");
//        System.out.print(arr2[2] + " ");
//        System.out.print(arr2[3] + " ");
//        System.out.print(arr2[4] + " ");
//        System.out.print(arr2[5] + " ");
    }
    
    private static void testPayload() throws IOException
    {
//        System.err.println("data awal");Tools.printArray(Tools.convertToBoolArray(Files.readAllBytes(Paths.get("test.txt"))));
        Payload payload = new Payload(true, "key","sample.bmp", Files.readAllBytes(Paths.get("sample.bmp")), 0.3f);
        ArrayList<Segmen> Segments = payload.getAllSegments();

//        System.err.println("banyaknya segmen = " + Segments.size());
//        System.err.println("hasil");
//        for(Segmen s : Segments)
//        {
//            Tools.printArray(s.getData());
//            Tools.printMatriks(s.getData());
//            System.err.println("");
//        }
        ArrayList <boolean []> bitplanes = new ArrayList<>();
        for(Segmen segmen : Segments)
            bitplanes.add(segmen.getData());
        
        try {
            Payload payload2 = new Payload(bitplanes, "key");
            payload2.save("testpayloadsave.bmp");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    private static void testIntByte()
    {
        int a = 3439854;
        byte [] ab;
        ab = Tools.intToBytes(a);
        System.err.println("4 byte = " + Tools.convertToBoolArray(ab).length);
        System.err.println(ab.length);
        for (byte b : ab) {
            System.out.format("0x%x ", b);
        }
        System.err.println("from byte " + Tools.bytesToInt(ab));
    }
    
    private static void testStringByte()
    {
        String a= "aa";
        byte[] bytes = Tools.stringToBytes(a);
        for (byte b : bytes) {
            System.out.format("0x%x ", b);
        }
        
        System.err.println("string: " + Tools.bytesToString(bytes));
    }
    
    private static void testNamaFile()
    {
        boolean[] FileName = Tools.convertToBoolArray(Tools.stringToBytes("sjdwicenice"));
        System.err.println(FileName.length);
        byte []filenameLenth = {(byte)("sjdwicenice".length())};
        boolean [] filenameLenthBool = Tools.convertToBoolArray(filenameLenth);
        System.out.println((int)filenameLenth[0]);
        System.err.println(filenameLenthBool.length);
    }
    
    private static void testScramblerDescrambler() {
        ArrayList<Integer> dataSegmen = new ArrayList<>();
        int i;
        for(i = 0; i < 20; i++) {
            dataSegmen.add(i*10);
        }
        int[] randomizedIndex = Tools.getShuffledInts("ASDF", 0, dataSegmen.size()-1);
        System.out.println("randomized index: ");
        for(i=0; i < randomizedIndex.length ; i++) {
            System.out.print(randomizedIndex[i] + ", ");
        }
        System.out.println();
        ArrayList<Integer> scrambledDataSegmen = new ArrayList<>();
        for (i=0; i < dataSegmen.size(); i++) {
            scrambledDataSegmen.add(dataSegmen.get(randomizedIndex[i]));
        }
        System.out.println("Scrambled data: ");
        for(i=0; i < scrambledDataSegmen.size() ; i++) {
            System.out.print(scrambledDataSegmen.get(i) + ", ");
        }
        System.out.println();

        int[] randomizedIndex2 = Tools.getShuffledInts("ASDF", 0, scrambledDataSegmen.size()-1);
        System.out.println("randomized index2: ");
        for(i=0; i < randomizedIndex2.length ; i++) {
            System.out.print(randomizedIndex2[i] + ", ");
        }
        System.out.println();
        ArrayList<Integer> descrambledDataSegmen = new ArrayList<>();
        while(descrambledDataSegmen.size() < scrambledDataSegmen.size()) descrambledDataSegmen.add(0);
        for (i=0; i < scrambledDataSegmen.size(); i++) {
            descrambledDataSegmen.set(randomizedIndex2[i], scrambledDataSegmen.get(i));
        }
        System.out.println("Descrambled data: ");
        for(i=0; i < descrambledDataSegmen.size() ; i++) {
            System.out.print(descrambledDataSegmen.get(i) + ", ");
        }
        System.out.println();
        System.out.println("Original data: ");
        for(i=0; i < dataSegmen.size() ; i++) {
            System.out.print(dataSegmen.get(i) + ", ");
        }
        System.out.println();
    }
    
    private static void testFloatByte()
    {
        float a = 0.3f;
        Tools.printArray(Tools.convertToBoolArray(Tools.floatToByte(a)));
        System.err.println(Tools.bytesToFloat(Tools.convertToByte(Tools.convertToBoolArray(Tools.floatToByte(a)))));
    }
}
