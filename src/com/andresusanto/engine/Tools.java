/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Picture;
import com.andresusanto.option.SpacingOption;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andre
 */
public class Tools {
    public static SpacingOption intValToSpacingOption(int value){
        if (value == 0) return SpacingOption.DEFAULT;
        if (value == 1) return SpacingOption.NO_SPACE;
        return SpacingOption.GROUP_5;
    }
    
    public static int[] getShuffledInts(String strSeed, int min, int max){
        // bentuk seed untuk random
        long seed = 0;
        for (int i = 0; i < strSeed.length(); i++)
            seed += (long)strSeed.charAt(i);
        
        // buat array berisi angka dari min sd max
        List<Integer> arrayToShuffle = new ArrayList<Integer>();
        for (int i = min; i <= max; i++)
            arrayToShuffle.add(i); 
        
        // acak-acak dengan seed yang diberikan
        Collections.shuffle(arrayToShuffle, new Random(seed));
        
        // convert ke primitive
        int shuffledInts[] = new int[max - min + 1];
        for (int i = 0; i < shuffledInts.length; i++){
            shuffledInts[i] = arrayToShuffle.get(i);
        }
        
        return shuffledInts;
    }
    
    public static float calculatePSNR(Picture picture1, Picture picture2){ 
        float sumDifference = 0;
        float rms = 0;
        
        for (int i = 0; i < picture1.pixels.length; i++){
            byte r1 = (byte)((picture1.pixels[i] >> 16 ) & 0xFF);
            byte g1 = (byte)((picture1.pixels[i] >> 8 ) & 0xFF);
            byte b1 = (byte)((picture1.pixels[i]) & 0xFF);
         
            byte r2 = (byte)((picture2.pixels[i] >> 16 ) & 0xFF);
            byte g2 = (byte)((picture2.pixels[i] >> 8 ) & 0xFF);
            byte b2 = (byte)((picture2.pixels[i]) & 0xFF);
            
            sumDifference += Math.pow(r2 - r1, 2);
            sumDifference += Math.pow(g2 - g1, 2);
            sumDifference += Math.pow(b2 - b1, 2);
            
        }
        
        sumDifference /= 3; // karena RGB
        rms = (float) Math.pow(sumDifference / (picture1.width * picture1.height), 0.5);
        return (float) (20 * Math.log10(255 / rms));
    }
    
    // Konversi Boolean >< Byte
    public static boolean[] convertToBoolArray(byte[] bytes) {
        return Tools.convert(bytes, bytes.length * 8);
    }
    
    public static byte[] convertToByte(boolean[] booleanOfData) {
        int length = booleanOfData.length / 8;
        int mod = booleanOfData.length % 8;
        if(mod != 0){
                ++length;
        }
        byte[] retVal = new byte[length];
        int boolIndex = 0;
        for (int byteIndex = 0; byteIndex < retVal.length; ++byteIndex) {
                for (int bitIndex = 7; bitIndex >= 0; --bitIndex) {
                        // Another bad idea
                        if (boolIndex >= booleanOfData.length) {
                                return retVal;
                        }
                        if (booleanOfData[boolIndex++]) {
                                retVal[byteIndex] |= (byte) (1 << bitIndex);
                        }
                }
        }
        return retVal;
    }
    private static boolean[] convert(byte[] bits, int significantBits) {
        boolean[] retVal = new boolean[significantBits];
        int boolIndex = 0;
        for (int byteIndex = 0; byteIndex < bits.length; ++byteIndex) {
                for (int bitIndex = 7; bitIndex >= 0; --bitIndex) {
                        if (boolIndex >= significantBits) {
                                // Bad to return within a loop, but it's the easiest way
                                return retVal;
                        }

                        retVal[boolIndex++] = (bits[byteIndex] >> bitIndex & 0x01) == 1 ? true
                                        : false;
                }
        }
        return retVal;
    }
    
    public static void printMatriks(boolean [] matriks)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                System.err.print(matriks[i * 8 + j] == true? 1: 0);
            }
            System.err.println("");
        }
    }
    
    public static void printArray(boolean[] array)
    {
        for(boolean b : array)
            System.err.print(b? 1:0);
        System.err.println("");
    }
    
    public static byte[] intToBytes(int value) {
    return new byte[] {
            (byte)(value >> 24),
            (byte)(value >> 16),
            (byte)(value >> 8),
            (byte)value};
    }
    
    public static int bytesToInt(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }
    
    public static String bytesToString(byte [] bytes)
    {
        return new String(bytes);
    }
    
    public static byte [] stringToBytes(String string)
    {
        return string.getBytes();
    }
    
    public static byte [] floatToByte(float value)
    {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
    
    public static float bytesToFloat(byte [] value)
    {
        return ByteBuffer.wrap(value).getFloat();
    }
    
    public static int oneByteToInt(boolean [] value)
    {
        int result = 0;
        for(int i = value.length - 1; i >= 0; i--)
        {
            if(value[i])
                result += Math.pow(2, 7 - i);
        }
        return result;
    }
    
    public static void showImage(Picture picture, String title){
        final BufferedImage bi = new BufferedImage(picture.width, picture.height, BufferedImage.TYPE_INT_ARGB);
        int[] biData = ( (DataBufferInt) bi.getRaster().getDataBuffer() ).getData();
        System.arraycopy(picture.pixels, 0, biData, 0, picture.pixels.length);
        
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bi, 0, 0, null);
            }
        };
        
        
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(picture.width, picture.height);
        frame.setVisible(true);
    }
    
    public static void writeStringToFile(String content, String fileName){
        BufferedWriter writer = null;
        try {
            File logFile = new File(fileName);
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static String readStringFile(String fileName) throws IOException{
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    
    
}
