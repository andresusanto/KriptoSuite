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
    private TreeCipherBlock[] internalKey;
    
    private void generateInternalKey(TreeCipherBlock key){
        internalKey[0] = new TreeCipherBlock(key);
        for (int j = 0; j < 7; j++){
            Random random = new Random(internalKey[j].sumBits());
            for (int i = 0; i < 15 + random.nextInt(TreeCipherBlock.BLOCK_SIZE); i++){
                key.cutShuffle(random.nextInt(TreeCipherBlock.BLOCK_SIZE));
            }
            
            if (j < 6) internalKey[j + 1] = new TreeCipherBlock(internalKey[j]);
        }
    }
    
    public void TreeCipher(TreeCipherBlock key){
        this.generateInternalKey(key);
    }
    
    public void printInternal(){
        for (int j = 0; j < 7; j++){
            internalKey[j].printData();
        }
    }
}
