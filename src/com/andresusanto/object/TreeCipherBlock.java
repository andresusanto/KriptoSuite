/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

/**
 *
 * @author power
 */
public class TreeCipherBlock {
    public static final int HALF_LEFT = 0;
    public static final int HALF_RIGHT = 1;
    public static final int BLOCK_SIZE = 128; // dalam satuan bit
    private boolean content[];
    
    public TreeCipherBlock(byte data[]){
        this.content = new boolean[BLOCK_SIZE];
        
        for (int i = 0; i < BLOCK_SIZE/8; i++){
            for (int j = 0; j < 8; j++){
                this.content[i * 8 + j] = (((data[i] >> j) & 1) == 1);
            }
        }
    }
    
    public TreeCipherBlock(TreeCipherBlock other){
        this.content = new boolean[other.content.length];
        System.arraycopy(other.content, 0, this.content, 0, other.content.length);
    }
    
    public void xor(TreeCipherBlock other){
        for (int i = 0; i < BLOCK_SIZE; i++){
            this.content[i] = other.content[i] ^ this.content[i];
        }
    }
    
    public void halfXor(TreeCipherBlock other, int originalHalf, int otherHalf){
        
    }
    
    public void cutShuffle(int k){
        boolean result[] = new boolean[BLOCK_SIZE];
        System.arraycopy(this.content, k, result, 0, this.content.length - k);
        for (int i = 0; i < k; i++){
            result[this.content.length - k + i] = this.content[i];
        }
        this.content = result;
    }
    
    public byte[] getData(){
        return null;
    }
    
    public void rotaryShiftRight(int k){
        boolean result[] = new boolean[BLOCK_SIZE];
        
        for (int i = 0; i < k; i++){
            result[BLOCK_SIZE - k + i] = this.content[i];
        }
        
        for (int i = k; i < BLOCK_SIZE; i++){
            result[i - k] = this.content[i];
        }
        
        this.content = result;
    }
    
    public void rotaryShiftLeft(int k){
        boolean result[] = new boolean[BLOCK_SIZE];
        
        for (int i = BLOCK_SIZE; i < BLOCK_SIZE - k; i--){
            result[BLOCK_SIZE - i] = this.content[i];
        }
        
        for (int i = 0; i < BLOCK_SIZE - k; i++){
            result[k + i] = this.content[i];
        }
        
        this.content = result;
    }
    
    public void printData(){
        for (int i = BLOCK_SIZE - 1 ; i >= 0; i--){
            System.out.print(content[i] ? "1" : "0");
        }
        System.out.println();
    }
    
    public int sumBits(){
        int sum = 0;
        for (int i = 0; i < BLOCK_SIZE; i++){
            sum += this.content[i] ? 1 : 0;
        }
        return sum;
    }
}
