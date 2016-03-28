/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Coordinate;
import com.andresusanto.object.Curve;
import java.math.BigInteger;

/**
 *
 * @author Andre
 */
public class ECC {
    private Curve curve;
    private Coordinate baseCoordinate;
    
    public Coordinate publicKey;
    public BigInteger privateKey;
    
    public ECC (Curve curve, Coordinate base){
        this.curve = curve;
        this.baseCoordinate = base;
    }
    
    // satu kordinat dienkripsi menjadi 2 kordinat
    // hal tersebut karena kordinat 1 = random * kordinat basis, dan kordinat 2 = plain + random * publickey
    public Coordinate[] encrypt(Coordinate plain) {
        return null;
    }
    
    public Coordinate decrypt(Coordinate[] cipher) {
        return null;
    }
    
    // operasi penambahan titik dalam kurva
    public Coordinate addCoordinate(Coordinate a, Coordinate b) {
        // Digunakan untuk melakukan perhitungan lambda
        BigInteger three = new BigInteger("3");
        BigInteger two = new BigInteger("2");
        BigInteger temp = new BigInteger(a.X.toString());

        if (a.isEqual(b)){
            // Hitung lamda untuk titik yang sama
            BigInteger lambda = temp.modPow(two, curve.p);
            lambda = three.multiply(lambda);
            lambda = lambda.add(curve.a);
            BigInteger den = two.multiply(a.Y);
            lambda = lambda.multiply(den.modInverse(curve.p));

            // Hitung nilai X dan Y yang baru
            BigInteger newX = lambda.multiply(lambda).subtract(a.X).subtract(a.X).mod(curve.p);
            BigInteger newY = (lambda.multiply(a.X.subtract(newX))).subtract(a.Y).mod(curve.p);
            return new Coordinate(newX, newY);
        }else if (isCoordinateMirror(a, b)){
            return new Coordinate(BigInteger.ZERO, BigInteger.ZERO);
        }else{
            // Hitung lambda untuk titik berbeda
            BigInteger lambda = b.Y.subtract(a.Y);
            BigInteger den = b.X.subtract(a.X);
            lambda = lambda.multiply(den.modInverse(curve.p));

            // Hitung nilai X dan Y yang baru
            BigInteger newX = lambda.multiply(lambda).subtract(a.X).subtract(b.X).mod(curve.p);
            BigInteger newY = (lambda.multiply(a.X.subtract(newX))).subtract(a.Y).mod(curve.p);
            return new Coordinate(newX, newY);	
        }
    }
    
    public boolean isCoordinateMirror(Coordinate a, Coordinate b){
        return a.X.equals(b.X) && a.Y.equals(curve.p.subtract(b.Y));
    }
    
    public Coordinate negateCoordinate(Coordinate a){
        return new Coordinate(a.X, curve.p.subtract(a.Y));
    }
}
