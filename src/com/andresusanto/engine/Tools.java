/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Picture;
import com.andresusanto.option.SpacingOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        return 0;
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
}
