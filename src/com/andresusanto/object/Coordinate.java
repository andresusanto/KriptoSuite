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
    public static final BigInteger K = BigInteger.valueOf((long)20);
    
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
    // ada parameter tambahan k (dr paper) yg gue gak tau itu masukan pengguna atau random atau gimana
    public Coordinate(byte data, Curve curve){
        int charCode = data & 0xFF;
        boolean foundPoint = false;
        BigInteger a = curve.a;//new BigInteger("-1");
        BigInteger b = curve.b;//new BigInteger("188");
        BigInteger p = curve.p;//new BigInteger("751");
        BigInteger m = new BigInteger(""+charCode);
        BigInteger x,y,y2;
        
        BigInteger i = new BigInteger("1");
        while(i.compareTo(K)==-1 && !foundPoint) {
            x = K.multiply(m).add(i);
            y2 = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//            System.out.println(x.pow(3).add(a.multiply(x)).add(b));
            if(y2.modPow(p.subtract(new BigInteger("1")).divide(new BigInteger("2")), p).compareTo(new BigInteger("1"))==0) {
                y = y2.modPow(p.add(new BigInteger("1")).divide(new BigInteger("4")), p);
                foundPoint = true;
                this.X = x;
                this.Y = y;
            } else {
                i = i.add(new BigInteger("1"));
            }
        }
//        System.out.println("X:"+this.X+" Y:"+this.Y);
    }
    
    public BigInteger squareRoot(BigInteger num) {
        if(num.compareTo(new BigInteger("0"))==-1)
            return new BigInteger("-1");
        else {
            boolean found = false;
            BigInteger i = new BigInteger("0");
            BigInteger sqr = i;
            while(sqr.compareTo(num)<1 && !found) {
                if(sqr.compareTo(num)==0)
                    found = true;
                else {
                    i = i.add(new BigInteger("1"));
                    sqr = i.pow(2);
                }
            }
            if(found)
                return i;
            else
                return new BigInteger("-1");
        }
    }
    
    // menghasilkan byte dari titik. Proses decode dilakukan disini. Masih belom FIX Ukurannya (bisa jadi 1 point butuh 2 ato lebih byte)
    public byte toByte(){
        return (this.X.subtract(new BigInteger("1"))).divide(this.K).byteValue();
    }
}
