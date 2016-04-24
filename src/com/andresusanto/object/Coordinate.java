/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

import java.io.IOException;
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
    
    public boolean isZero(){
        return this.X.equals(BigInteger.ZERO) && this.Y.equals(BigInteger.ZERO);
    }
    
    public Coordinate(BigInteger X, BigInteger Y, Curve curve){
        this.X = X;
        this.Y = Y;
        
        BigInteger ppodbf = curve.p.add(BigInteger.ONE).shiftRight(2);
        BigInteger nY = this.X.multiply(X).add(curve.a).multiply(this.X).add(curve.b).modPow(ppodbf, curve.p); // hitung y
        
        if (!nY.equals(this.Y) && !this.Y.equals(curve.p.subtract(nY))){
            System.out.println("ERR");
        }
        
        
    }

    public boolean isEqual(Coordinate other){
        return this.X.equals(other.X) && this.Y.equals(other.Y);
    }

    public void print(){
        System.out.printf("{%s,%s}\n", this.X.toString(), this.Y.toString());
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(this.X.toString());
        sb.append(",");
        sb.append(this.Y.toString());
        sb.append("}");
        return sb.toString();
    }

    // copy constructor
    public Coordinate(Coordinate other){
        this.X = new BigInteger(other.X.toString());
        this.Y = new BigInteger(other.Y.toString());
    }
    
    private void calculateY(Curve curve, boolean isNegative){
        BigInteger ppodbf = curve.p.add(BigInteger.ONE).shiftRight(2);
        this.Y = this.X.multiply(X).add(curve.a).multiply(this.X).add(curve.b).modPow(ppodbf, curve.p); // hitung y
        
        if (this.Y.testBit(0) != isNegative){
            this.Y = curve.p.subtract(this.Y);
        }
    }
    
    public Coordinate(BigInteger X, Curve curve, boolean isNegative){
        this.X = X;
        calculateY(curve, isNegative);
    }

    // buat titik dari byte. Proses encode byte ke titik dilakukan disini. Masih belom FIX Ukurannya (bisa jadi 1 point butuh 2 ato lebih byte)
    // ada parameter tambahan k (dr paper) yg gue gak tau itu masukan pengguna atau random atau gimana
    public Coordinate(byte[] data, Curve curve) throws IOException{
        if (data.length != curve.pLength + 1) throw new IOException("Invalid bytes length");
        boolean isNegative = (data[0] != 0);
        data[0] = 0; // byte yang digunakan untuk menyimpan sign

        this.X = new BigInteger(data);
        calculateY(curve, isNegative);
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
    public byte[] toByte(Curve curve){
        byte data[] = new byte[curve.pLength + 1];
        byte xBytes[] = this.X.toByteArray();
        System.arraycopy(xBytes, 0, data, data.length - xBytes.length, xBytes.length);
            
        if (this.Y.testBit(0)) data[0] = 1; // simpan sign Y
        return data;
        
    }
}