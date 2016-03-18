/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.TreeCipherBlock;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author power
 */
public class TreeCipher {
    public static final char DIRECTION_UP = 'U';
    public static final char DIRECTION_DOWN = 'D';
    
    public TreeCipherBlock[] internalKey;
    public List<TreeCipherBlock[]> encryptionLine; // dari leaf sampai ke root untuk semua jalur pohon
    
    public TreeCipher(TreeCipherBlock key){
        internalKey = new TreeCipherBlock[15];
        this.generateInternalKey(key);
        this.populateTree();
    }
    
    private void generateInternalKey(TreeCipherBlock key){
        internalKey[0] = new TreeCipherBlock(key);
        for (int j = 0; j < 15; j++){
            Random random = new Random(internalKey[j].sumBits());
            for (int i = 0; i < 15 + random.nextInt(TreeCipherBlock.BLOCK_SIZE); i++){
                internalKey[j].cutShuffle(random.nextInt(TreeCipherBlock.BLOCK_SIZE));
            }
            
            if (j < 14) internalKey[j + 1] = new TreeCipherBlock(internalKey[j]);
        }
    }
    
    private void populateTree(){
        encryptionLine = new LinkedList<>();
        
        Queue<TreeCipherBlock> treeQueue = new LinkedBlockingQueue<>();
        int i = 1;
        treeQueue.add(this.internalKey[0]);
        
        while (i < internalKey.length){
            TreeCipherBlock parent = treeQueue.poll();
            
            treeQueue.add(this.internalKey[i]);
            this.internalKey[i++].setParent(parent);
            treeQueue.add(this.internalKey[i]); // binary, 2x
            this.internalKey[i++].setParent(parent);   
        }
        
        while(!treeQueue.isEmpty()){ // sisanya itu leaf, buat jalur enkripsi dengan leaf tsb
            List<TreeCipherBlock> line = new LinkedList<>();
            TreeCipherBlock element = treeQueue.poll();
            
            while (element != null){
                line.add(element);
                element = element.getParent();
            }
            
            TreeCipherBlock lineArray[] = new TreeCipherBlock[line.size()];
            encryptionLine.add(line.toArray(lineArray));
        }
    }
    
    public void printInternal(){
        for (int j = 0; j < 15; j++){
            System.out.print(j);
            System.out.print("\t: ");
            
            internalKey[j].printData();
        }
    }
    
    public void doFistel(TreeCipherBlock data, TreeCipherBlock key, char direction){
        TreeCipherBlock tmp = new TreeCipherBlock(key);
        
        if (direction == DIRECTION_DOWN){
            data.cutShuffle(64);
            tmp.halfXor(data, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_LEFT);
            // TODO: s-box 1
            tmp.halfXor(tmp, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_RIGHT);
            // TODO: s-box 2
            data.halfXor(tmp, TreeCipherBlock.HALF_RIGHT, TreeCipherBlock.HALF_LEFT);
        }else{
            tmp.halfXor(data, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_LEFT);
            // TODO: s-box 1
            tmp.halfXor(tmp, TreeCipherBlock.HALF_LEFT, TreeCipherBlock.HALF_RIGHT);
            // TODO: s-box 2
            data.halfXor(tmp, TreeCipherBlock.HALF_RIGHT, TreeCipherBlock.HALF_LEFT);
            
            data.cutShuffle(64);
        }
    }
}
