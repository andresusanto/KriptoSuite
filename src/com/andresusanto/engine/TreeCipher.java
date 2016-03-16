/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.TreeCipherBlock;
import java.util.Random;

/**
 *
 * @author power
 */
public class TreeCipher {
    public TreeCipherBlock[] internalKey;
    
    private void generateInternalKey(TreeCipherBlock key){
        internalKey[0] = new TreeCipherBlock(key);
        for (int j = 0; j < 7; j++){
            Random random = new Random(internalKey[j].sumBits());
            for (int i = 0; i < 15 + random.nextInt(TreeCipherBlock.BLOCK_SIZE); i++){
                internalKey[j].cutShuffle(random.nextInt(TreeCipherBlock.BLOCK_SIZE));
            }
            
            if (j < 6) internalKey[j + 1] = new TreeCipherBlock(internalKey[j]);
        }
    }
    
    public TreeCipher(TreeCipherBlock key){
        internalKey = new TreeCipherBlock[8];
        this.generateInternalKey(key);
    }
    
    public void printInternal(){
        for (int j = 0; j < 7; j++){
            internalKey[j].printData();
        }
    }
    
    public void doFistel(TreeCipherBlock data, TreeCipherBlock key){
        data.cutShuffle(64); // tukar kiri menjadi kanan
        data.halfXor(key, TreeCipherBlock.HALF_RIGHT, TreeCipherBlock.HALF_RIGHT);
//        data.cutShuffle(64);
        
        //TreeCipherBlock tmp = new TreeCipherBlock(key);
        //tmp.halfXor(data, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_LEFT);
        // TODO: s-box 1
        //tmp.halfXor(tmp, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_RIGHT);
        // TODO: s-box 2
        //data.halfXor(tmp, TreeCipherBlock.HALF_RIGHT, TreeCipherBlock.HALF_LEFT);
        //data.halfXor(tmp, TreeCipherBlock.HALF_RIGHT, TreeCipherBlock.HALF_LEFT);
    }
}
