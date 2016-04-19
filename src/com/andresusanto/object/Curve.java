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

    public byte[] pb;
    public int pLength;

    public Curve(BigInteger a, BigInteger b, BigInteger p){
        this.a = a;
        this.b = b;
        this.p = p;

        calculatePByte();
    }

    public Curve(Curve other){
        this.a = new BigInteger(other.a.toString());
        this.b = new BigInteger(other.b.toString());
        this.p = new BigInteger(other.p.toString());

        calculatePByte();
    }

    public void calculatePByte(){
        this.pb = p.toByteArray();
        this.pLength = this.pb.length;
    }
}