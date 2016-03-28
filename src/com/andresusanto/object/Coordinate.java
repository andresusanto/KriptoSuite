/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import java.math.BigInteger;

/**
 *
 * @author Andre
 */
public class Coordinate {
    public BigInteger X;
    public BigInteger Y;
    
    public Coordinate(BigInteger X, BigInteger Y){
        this.X = X;
        this.Y = Y;
    }
    
    public boolean isEqual(Coordinate other){
        return this.X.equals(other.X) && this.Y.equals(other.Y);
    }
    
    public void print(){
        System.out.printf("{%s,%s}\n", this.X.toString(), this.Y.toString());
    }
    
    // copy constructor
    public Coordinate(Coordinate other){
        this.X = new BigInteger(other.X.toString());
        this.Y = new BigInteger(other.Y.toString());
    }
    
    // buat titik dari byte. Proses encode byte ke titik dilakukan disini. Masih belom FIX Ukurannya (bisa jadi 1 point butuh 2 ato lebih byte)
    public Coordinate(byte data){
        
    }
    
    // menghasilkan byte dari titik. Proses decode dilakukan disini. Masih belom FIX Ukurannya (bisa jadi 1 point butuh 2 ato lebih byte)
    public byte toByte(){
        return 0;
    }
}
