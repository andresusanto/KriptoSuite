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
    public TreeCipherBlock internalQueue; // antrian yang digunakan pada modus CFB
    
    public TreeCipher(TreeCipherBlock key){
        internalKey = new TreeCipherBlock[15];
        this.generateInternalKey(key);
        this.populateTree();
        this.generateInternalQueue(key);
    }
    
    private void generateInternalQueue(TreeCipherBlock key){
        internalQueue = new TreeCipherBlock(key);
        internalQueue.cutShuffle(TreeCipherBlock.BLOCK_SIZE / 2);
        internalQueue.rotaryShiftLeft(5);
        internalQueue.cutShuffle(TreeCipherBlock.BLOCK_SIZE / 4);
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
    
    private void modifyTree(TreeCipherBlock currentBlock){
        TreeCipherBlock rootTransform = new TreeCipherBlock(internalKey[0]);
        
        if (currentBlock.content[0]){
            rootTransform.rotaryShiftRight(2);
        }else{
            rootTransform.rotaryShiftLeft(1);
        }
        
        int leafNumber = 0;
        int numLeafBits = (int)(Math.log(encryptionLine.size()) / Math.log(2));
        for (int i = 0; i < numLeafBits; i++){
            if (currentBlock.content[i]) leafNumber |= 1 << i;
        }
        encryptionLine.get(leafNumber)[0].push(rootTransform);
    }
    
    private void doFistel(TreeCipherBlock data, TreeCipherBlock key, char direction){
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
    
    private void doFistelLine(TreeCipherBlock data, char direction){
        if (direction == DIRECTION_DOWN){
            for (int j = 0; j < encryptionLine.size(); j++){ //(TreeCipherBlock[] fistelLine : encryptionLine){
                TreeCipherBlock[] fistelLine = encryptionLine.get(j);
                for (int i = fistelLine.length - 1; i >= 0; i--){ // karena 0 = leaf, akar = length - 1,enkripsi dilakukan dari akar
                    doFistel(data, fistelLine[i], DIRECTION_DOWN);
                }
            }   
        }else{
            for (int j = encryptionLine.size() - 1; j >= 0; j--){ 
                TreeCipherBlock[] fistelLine = encryptionLine.get(j);
                for (int i = 0; i < fistelLine.length; i++){ // karena 0 = leaf, akar = length - 1,dekripsi dilakukan dari leaf
                    doFistel(data, fistelLine[i], DIRECTION_UP);
                }
            }
        }
    }
    
    public byte encrypt(byte input){
        TreeCipherBlock data[] = { new TreeCipherBlock(internalQueue) };
        encrypt(data);
        byte result = (byte)(input ^ data[0].getBytes()[0]);
        internalQueue.pad(result);
        return result;
    }
    
    public byte decrypt(byte input){
        TreeCipherBlock data[] = { new TreeCipherBlock(internalQueue) };
        encrypt(data);
        internalQueue.pad(input);
        return (byte)(input ^ data[0].getBytes()[0]);
    }
    
    public void encrypt(TreeCipherBlock datas[]){
        for (TreeCipherBlock data: datas){
            doFistelLine(data, DIRECTION_DOWN); // tahap enkripsi pohon 1
            TreeCipherBlock dataPhase1 = new TreeCipherBlock(data); //simpan data hasil enkripsi 1 untuk memodifikasi pohon
            doFistelLine(data, DIRECTION_DOWN); // tahap enkripsi pohon 2
            modifyTree(dataPhase1);
        }
    }
    
    public void decrypt(TreeCipherBlock datas[]){
        for (TreeCipherBlock data: datas){
            doFistelLine(data, DIRECTION_UP); // tahap enkripsi pohon 1
            TreeCipherBlock dataPhase1 = new TreeCipherBlock(data); //simpan data hasil enkripsi 1 untuk memodifikasi pohon
            doFistelLine(data, DIRECTION_UP); // tahap enkripsi pohon 2
            modifyTree(dataPhase1);
        }
    }
    
    public void printInternal(){
        for (int j = 0; j < encryptionLine.size(); j++){
            System.out.print("LINE ");
            System.out.print(j);
            System.out.print("\t: ");
            
            for(TreeCipherBlock data : encryptionLine.get(j)){
                data.printData();
            }
        }
    }
    
    public void printInternalKey(){
        for (int i = 0 ; i < internalKey.length; i++){
            System.out.print(i);
            System.out.print("\t:");
            
            byte kb[] = internalKey[i].getBytes();
            for (byte b : kb){
                System.out.printf("%02X", b);
                System.out.print(" ");
            } System.out.println();
            
        }
    }
}
