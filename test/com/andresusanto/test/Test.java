/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.test;

import com.andresusanto.engine.IkedaPRNG;
import com.andresusanto.engine.Tools;
import com.andresusanto.engine.TreeCipher;
import com.andresusanto.object.Coordinate;
import com.andresusanto.object.Curve;
import com.andresusanto.object.Payload;
import com.andresusanto.object.Picture;
import com.andresusanto.object.Segmen;
import com.andresusanto.object.TreeCipherBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akhfa
 */
public class Test {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        Curve curve = new Curve(new BigInteger("4"), new BigInteger("5"), new BigInteger("173"));
//        File file = new File("./test.bmp");
//        byte[] fileData = new byte[(int) file.length()];
//        FileInputStream in = new FileInputStream(file);
//        in.read(fileData);
//        in.close();
//        byte[] decodedFile = new byte[fileData.length];
//        for(int i=0; i<fileData.length; i++) {
//            Coordinate c = new Coordinate(fileData[i], curve);
//            decodedFile[i] = c.toByte();
//        }
//        FileOutputStream fw = new FileOutputStream(new File("./decoded.bmp"));
//        fw.write(decodedFile);
//        fw.close();
        
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

//        Test.bitPlane();
//        Test.psnr();
//        Test.resetBitplane();
//        Test.randomizer();
//        Test.treeTest();
//        Test.treeStructureTest();
//        Test.treeGeneratorTest();
//        Test.CFBTest();

         //Test.tesFloat();
         //tesFloat();
         
         Test.tesPRNG();
         
         //System.out.println(generateTOTP("kakau"));
    }
    
    public static void tesPRNG(){
        IkedaPRNG ikeda = new IkedaPRNG(0.98425f, 0.12332455536f, 0.678764532435f);
        
        ikeda.print();
        
        for (int i = 0 ; i < 1000; i ++){
            ikeda.calcPrint();
            System.out.println();
        }
    }
    
    public static void tesPadding() throws FileNotFoundException, IOException{
        File file = new File("./test.txt");
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileData);
        in.close();
        
        System.out.printf("Input %d: ", fileData.length);
        for (byte b : fileData){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        TreeCipherBlock a[] = TreeCipherBlock.build(fileData);
        
        byte x[] = TreeCipherBlock.toBytes(a);
        
        System.out.printf("Input %d: ", x.length);
        for (byte b : x){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
    }
    
    public static void tesFloat(){
        float value = 1.5e-3f;

        byte bytes[] = tesF(value);
        //printB(bytes);
        System.out.printf("%02X \n", sumBytes(bytes));
        value += 0.100000531f;
        
        
        bytes = tesF(value);
        System.out.printf("%02X \n", sumBytes(bytes));
        
    }
    
    public static void printB(byte[] bytes){
        System.out.print("Input: ");
        for (byte b : bytes){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
    }
    
    public static byte[] tesF(float f){
        int bits = Float.floatToIntBits(f);
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(bits & 0xff);
        bytes[1] = (byte)((bits >> 8) & 0xff);
        bytes[2] = (byte)((bits >> 16) & 0xff);
        bytes[3] = (byte)((bits >> 24) & 0xff);
        
        return bytes;
    }
    
    public static byte sumBytes(byte[] bytes){
        byte tmp = 0;
        for (byte b : bytes){
            //tmp = (byte)((b + tmp) % 256);
            tmp = (byte)(tmp ^ b);
            tmp = (byte)((tmp >>> 2) | (tmp << (6)));
        }
        return tmp;
    }
    
    public static TreeCipherBlock testKey(){
        byte data = 13;
        byte datas[] = new byte[16];
        
        for (int i = 0 ; i < 16; i++){
            datas[i] = (byte)(data + (i % 2) * (i % 4) + i);
        }
        //datas[15]--;
        //System.out.println();
        return new TreeCipherBlock(datas);
    }
    
    public static void CFBTest(){
        byte datas[] = new byte[256];
        for (int i = 0 ; i < 256; i++)
            datas[i] = (byte)((1 + (i % 9) * 3 + (i * i) % 223) % 256);
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        TreeCipherBlock key = testKey();
        TreeCipher cip = new TreeCipher(key);
        
        for (int i = 0 ; i < datas.length ; i++){
            datas[i] = cip.encrypt(datas[i]);
        }
        
        datas[0]--;
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        cip = new TreeCipher(key);
        
        for (int i = 0 ; i < datas.length ; i++){
            datas[i] = cip.decrypt(datas[i]);
        }
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
    }
    
    public static byte[] getSHA256Hash(String input) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        return digest.digest(input.getBytes());
    }
    
     public static String generateTOTP(String passwordHash) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        int totpTime = (int) ((System.currentTimeMillis() / 1000L) / 30);

        sb.append("231123213"); sb.append(passwordHash);
        byte hash[] = getSHA256Hash(sb.toString());

        printB(hash);
               
        for (byte b : hash){
            sb.append(String.format("%02X", b));
        }

        String strHash = sb.toString();
        int truncateAt = Integer.parseInt(String.format("%02X", hash[hash.length - 1]), 16);
        
        System.out.println(truncateAt);

        sb = new StringBuffer();
        for (int i = truncateAt; i < truncateAt + 8; ++i) {
            sb.append(String.format("%02x", hash[i % 32]));
        }

        return sb.toString();
    }
    
    public static void padTest(){
        TreeCipherBlock key = testKey();
        
        for (byte b : key.getBytes()){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        key.pad( (byte)121 );
        key.pad( (byte)123 );
        key.pad( (byte)221 );
        key.pad( (byte)12 );
        
        System.out.printf("%02X\n", (byte)121);
        System.out.printf("%02X\n", (byte)123);
        System.out.printf("%02X\n", (byte)221);
        System.out.printf("%02X\n", (byte)12);
        
        
        for (byte b : key.getBytes()){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
    }
    
    public static void cipherTest() throws IOException{
        
        TreeCipherBlock key = testKey();
        TreeCipher cip = new TreeCipher(key);
        
        byte kb[] = key.getBytes();
        for (byte b : kb){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        byte datas[] = new byte[256];
        for (int i = 0 ; i < 256; i++)
            datas[i] = (byte)((1 + (i % 9) * 3 + (i * i) % 223) % 256);
        
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        
        TreeCipherBlock dataBlocks [] = TreeCipherBlock.build(datas);
        cip.encrypt(dataBlocks);
        
        
        dataBlocks[0].content[0] = !dataBlocks[0].content[0];
        
        datas = TreeCipherBlock.toBytes(dataBlocks);
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
        
        
        cip = new TreeCipher(testKey());
        cip.decrypt(dataBlocks);
        
        datas = TreeCipherBlock.toBytes(dataBlocks);
        
        for (byte b : datas){
            System.out.printf("%02X", b);
            System.out.print(" ");
        } System.out.println();
    }
    
    public static void treeModificationTest(){
        TreeCipherBlock key = testKey();
        TreeCipher cip = new TreeCipher(key);
        key.printData();
        
        TreeCipherBlock tes = new TreeCipherBlock(key);
        tes.cutShuffle(2);
        tes.rotaryShiftLeft(2);
        tes.cutShuffle(43);
        tes.printData();
        
        cip.printInternal();
        //cip.modifyTree(tes);
        cip.printInternal();
    }
    
    public static void treeBlockBuilderTest() throws IOException{
        byte datas[] = new byte[256];
        for (int i = 0 ; i < 256; i++)
            datas[i] = (byte)(12 + i % 100);
        
        for (byte b : datas){
            System.out.print(b);
            System.out.print(" ");
        } System.out.println();
        
        TreeCipherBlock[] data = TreeCipherBlock.build(datas);
        
        System.out.println("OKE");
        
        for (TreeCipherBlock bx : data){
            for (byte b : bx.getBytes()){
                System.out.print(b);
                System.out.print(" ");
            } 
        }
        
    }
    
    public static void getByteTest(){
        TreeCipherBlock tes = testKey();
        
        byte dataz = 13;
        byte datas[] = new byte[16];
        
        for (int i = 0 ; i < 16; i++)
            datas[i] = dataz;
        
        TreeCipher cip = new TreeCipher(new TreeCipherBlock(datas));
        
        //cip.doFistel(tes, cip.internalKey[0], TreeCipher.DIRECTION_DOWN);
        
       // cip.doFistel(tes, cip.internalKey[0], TreeCipher.DIRECTION_UP);
        
        byte data[] = tes.getBytes();
        
        
        for (byte b : data){
            System.out.print(b);
            System.out.print(" ");
        } System.out.println();
        
        
    }
    
    public static void treeGeneratorTest(){
        System.out.print("Key\t: ");
        testKey().printData();
        
        TreeCipher cip = new TreeCipher(testKey());
        cip.printInternalKey();
        
        //TreeCipherStructure structure = new TreeCipherStructure(cip.internalKey);
        
    }
    
    public static void treeStructureTest(){
        byte data = 13;
        byte datas[] = new byte[16];
        
        for (int i = 0 ; i < 16; i++)
            datas[i] = data;
        
        TreeCipherBlock a = new TreeCipherBlock(datas);
        TreeCipherBlock b = new TreeCipherBlock(datas);
        TreeCipherBlock c = new TreeCipherBlock(datas);
        TreeCipherBlock d = new TreeCipherBlock(datas);
        TreeCipherBlock e = new TreeCipherBlock(datas);
        TreeCipherBlock f = new TreeCipherBlock(datas);
        TreeCipherBlock g = new TreeCipherBlock(datas);
        
        b.setParent(a);
        c.setParent(a);
        d.setParent(b);
        e.setParent(b);
        f.setParent(c);
        g.setParent(c);
        
        a.rotaryShiftLeft(2);
        b.rotaryShiftLeft(5);
        c.rotaryShiftLeft(6);
        d.rotaryShiftLeft(9);
        e.rotaryShiftLeft(10);
        f.rotaryShiftLeft(15);
        g.rotaryShiftLeft(1);
        
        System.out.print("A : ");
        a.printData();
        System.out.print("B : ");
        b.printData();
        System.out.print("C : ");
        c.printData();
        System.out.print("D : ");
        d.printData();
        System.out.print("E : ");
        e.printData();
        System.out.print("F : ");
        f.printData();
        System.out.print("G : ");
        g.printData();
        
        g.push(new TreeCipherBlock(a));
        
        System.out.print("A : ");
        a.printData();
        System.out.print("B : ");
        b.printData();
        System.out.print("C : ");
        c.printData();
        System.out.print("D : ");
        d.printData();
        System.out.print("E : ");
        e.printData();
        System.out.print("F : ");
        f.printData();
        System.out.print("G : ");
        g.printData();
    }
    
    public static void treeTest(){
        byte data = 26;
        byte datas[] = new byte[16];
        
        for (int i = 0 ; i < 16; i++)
            datas[i] = data;
        
        TreeCipherBlock a = new TreeCipherBlock(datas);
        TreeCipherBlock b = new TreeCipherBlock(datas);
        TreeCipherBlock c = new TreeCipherBlock(datas);
        
        b.rotaryShiftLeft(5);
        a.xor(b);
        a.cutShuffle(21);
        b.xor(a);
        b.rotaryShiftLeft(12);
        a.cutShuffle(1);
        a.halfXor(c, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_LEFT);
        //System.out.println("Internal Key");
        TreeCipher cip = new TreeCipher(b);
        //cip.printInternal();
        
        a.printData();
        
//        cip.doFistel(a, cip.internalKey[1], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[2], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[3], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[4], TreeCipher.DIRECTION_DOWN);
//        
//        cip.doFistel(a, cip.internalKey[1], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[2], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[3], TreeCipher.DIRECTION_DOWN);
//        cip.doFistel(a, cip.internalKey[4], TreeCipher.DIRECTION_DOWN);
        
        
        a.printData();
//        cip.doFistel(a, cip.internalKey[4], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[3], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[2], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[1], TreeCipher.DIRECTION_UP);
//        
//        cip.doFistel(a, cip.internalKey[4], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[3], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[2], TreeCipher.DIRECTION_UP);
//        cip.doFistel(a, cip.internalKey[1], TreeCipher.DIRECTION_UP);
        
        a.printData();
        
    }
    
    public static void psnr() throws IOException{
        Picture pic1 = new Picture("D:\\Tugas Sekolah\\Kriptografi\\KriptoSuite\\sample.bmp");
        Picture pic2 = new Picture("D:\\Tugas Sekolah\\Kriptografi\\KriptoSuite\\sample.bmp");
        
        boolean[] bp = pic1.getBitPlane(9, 1, Picture.COLOR_RED);
        pic2.setBitPlane(0, 0, Picture.COLOR_BLUE, bp);
        
        System.out.println(Tools.calculatePSNR(pic1, pic2));
    }
    
    public static void bitPlane() throws IOException{
        Picture pic = new Picture("D:\\Tugas Sekolah\\Kriptografi\\KriptoSuite\\sample.bmp");
        
        boolean bool[] = new boolean[64];
        for (int i = 0 ; i < 64; i++){
            if (i % 2 == 0) bool[i] = true; else bool[i] = false;
        }
        
        pic.setBitPlane(0, 3, Picture.COLOR_RED, bool);
        pic.setBitPlane(0, 3, Picture.COLOR_GREEN, bool);
        pic.setBitPlane(0, 3, Picture.COLOR_BLUE, bool);
        
        pic.setBitPlane(1, 3, Picture.COLOR_GREEN, bool);
        
        for (int i = 0; i < 10; i++){
            boolean[] bp = pic.getBitPlane(i, 3, Picture.COLOR_RED);
            System.out.print("REGION " + i + " R: ");
            for (int j = 0 ; j < 64; j++)
                System.out.print(bp[j] ? "1 " : "0 ");
            System.out.println();
            
            bp = pic.getBitPlane(i, 3, Picture.COLOR_GREEN);
            System.out.print("REGION " + i + " G: ");
            for (int j = 0 ; j < 64; j++)
                System.out.print(bp[j] ? "1 " : "0 ");
            System.out.println();
            
            bp = pic.getBitPlane(i, 3, Picture.COLOR_BLUE);
            System.out.print("REGION " + i + " B: ");
            for (int j = 0 ; j < 64; j++)
                System.out.print(bp[j] ? "1 " : "0 ");
            System.out.println();
        }
        
        
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
    
    private static void resetBitplane() throws IOException
    {
        Picture pic = new Picture("D:\\Tugas Sekolah\\Kriptografi\\KriptoSuite\\sampel1.bmp");
        int i,j,k;
        int bitplaneCount = pic.getTotalRegions();
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
                    //j = 3;
                    
                    boolean bitplanes[][] = new boolean[8][];
                    boolean mbitplanes[][] = new boolean[8][];
                    
                    
                    for (int x = 0; x < 8; x++)
                        bitplanes[x] = pic.getBitPlane(i, x, colorCode);
                    //if (i == 1)
                    pic.setBitPlane(i, j, colorCode, bitplanes[j]);
                    
                    for (int x = 0; x < 8; x++)
                        mbitplanes[x] = pic.getBitPlane(i, x, colorCode);
                    
                    
                    boolean equals = true;
                    for (int z = 0; z < 64 && equals; z++){
                        for (int x = 0; x < 8 && equals; x++){
                            if (bitplanes[x][z] != mbitplanes[x][z]) equals = false;
                        }
                        
                    }
                    equals = true;
                    if (!equals){
                        System.out.println("ERR " + i + " " + j + " " + k + " ");
                        for (int z = 0; z < 64; z++)
                            System.out.print(bitplanes[j][z] ? "1 " : "0 ");
                        System.out.println();
                        for (int z = 0; z < 64; z++)
                            System.out.print(mbitplanes[j][z] ? "1 " : "0 ");
                        System.out.println();
                        
                    }
                }
            }
        }
        pic.save("D:\\Tugas Sekolah\\Kriptografi\\KriptoSuite\\sampel1_result.bmp");
    }
    
    private static void randomizer() {
        String strSeed = "KUNCI";
        int len1 = 5;
        int len2 = 12;
        int[] arr1 = Tools.getShuffledInts(strSeed, 0, len1-1);
        int[] arr2 = Tools.getShuffledInts(strSeed, 0, len2-1);
        int i;
        System.out.print("arr1: ");
        for (i=0;i<arr1.length;i++)
            System.out.print(arr1[i] + " ");
        System.out.println();
        System.out.print("arr2: ");
        for (i=0;i<arr2.length;i++)
            System.out.print(arr2[i] + " ");
        System.out.println();
    }
}
