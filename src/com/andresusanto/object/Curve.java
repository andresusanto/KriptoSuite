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
public class Curve {
    
    // rumus dari kurva eliptik adalah: y^2 = x^3 + ax + b mod p, dimana P = bilangan prima
    public BigInteger a;
    public BigInteger b;
    public BigInteger p;
    
    public Curve(BigInteger a, BigInteger b, BigInteger p){
        this.a = a;
        this.b = b;
        this.p = p;
    }
    
    public Curve(Curve other){
        this.a = new BigInteger(other.a.toString());
        this.b = new BigInteger(other.b.toString());
        this.p = new BigInteger(other.p.toString());
    }
    
    // akan dibutuhkan untuk keperluan validasi
    public boolean isEqual(Curve other){
        return this.a.equals(other.a) && this.b.equals(other.b) && this.p.equals(other.p);
    }
}
