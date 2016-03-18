/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author power
 */
public class TreeCipherStructure {
    public TreeCipherBlock elements[];
    public List<TreeCipherBlock[]> encryptionLine; // dari leaf sampai ke root untuk semua jalur pohon
    
    public TreeCipherStructure(TreeCipherBlock internalKey[]){
        this.elements = internalKey;
        encryptionLine = new LinkedList<>();
        
        Queue<TreeCipherBlock> treeQueue = new LinkedBlockingQueue<>();
        int i = 1;
        treeQueue.add(this.elements[0]);
        
        while (i < elements.length){
            TreeCipherBlock parent = treeQueue.poll();
            
            treeQueue.add(this.elements[i]);
            this.elements[i++].setParent(parent);
            treeQueue.add(this.elements[i]);
            this.elements[i++].setParent(parent);   
        }
        
        while(!treeQueue.isEmpty()){
            List<TreeCipherBlock> line = new LinkedList<>();
            TreeCipherBlock element = treeQueue.poll();
            
            while (element != null){
                line.add(element);
                element = element.getParent();
            }
            
            TreeCipherBlock lineArray[] = new TreeCipherBlock[line.size()];
            encryptionLine.add(line.toArray(lineArray));
        }
        
        System.out.println(encryptionLine.get(0).length);
    }
}
