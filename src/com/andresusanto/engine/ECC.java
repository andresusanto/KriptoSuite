/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Coordinate;
import com.andresusanto.object.Curve;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Andre
 */
public class ECC {
    
    private Curve curve;
    private Coordinate baseCoordinate;
    private BigInteger privateKey;
    
    
    public ECC (Curve curve, Coordinate base, BigInteger privateKey){
        this.curve = curve;
        this.baseCoordinate = base;
        this.privateKey = privateKey;
    }
    
    public Coordinate generatePublic(){
        return multiplyCoordinate(baseCoordinate, privateKey);
    }
    
    // fungsi untuk melakukan enkripsi data byte
    public byte[] encrypt(byte[] input, Coordinate publicKey){
        byte[] encryptedFile = new byte[2 * input.length];
        
        for(int i=0; i< input.length; i++) {
            Coordinate plain = new Coordinate(input[i], curve);
            Coordinate[] encrypted = encrypt(plain, publicKey);
            encryptedFile[i*2] = encrypted[0].toByte();
            encryptedFile[i*2+1] = encrypted[1].toByte();
        }
        
        return encryptedFile;
    }
    
    // satu kordinat dienkripsi menjadi 2 kordinat (ECC El Gamal)
    // hal tersebut karena kordinat 1 = random * kordinat basis, dan kordinat 2 = plain + random * publickey
    public Coordinate[] encrypt(Coordinate plain, Coordinate publicKey){
        // pilih sebuah bilangan random r
        BigInteger k = new BigInteger(curve.p.bitLength(), new Random());
        
        // membuat tupel el gamal hasil enkripsi
        Coordinate[] ans = new Coordinate[2];
        ans[0] = multiplyCoordinate(baseCoordinate, k); // bilangan random * kordinat basis
        ans[1] = addCoordinate(plain, multiplyCoordinate(publicKey, k)); // plain + random * public key
        return ans; // kembalikan tupel
    }
    
    public byte[] decrypt(byte[] input){
        byte[] decryptedFile = new byte[input.length / 2];
        
        for(int i = 0; i < input.length; i += 2) {
            Coordinate[] encrypted = new Coordinate[2];
            encrypted[0] = new Coordinate(input[i], curve);
            encrypted[1] = new Coordinate(input[i + 1], curve);
            Coordinate decrypted = decrypt(encrypted);
            decryptedFile[i / 2] = decrypted.toByte();
        }
        
        return decryptedFile;
    }
    
    public Coordinate decrypt(Coordinate[] cipher){
        Coordinate multiWithPrivate = multiplyCoordinate(cipher[0], privateKey);
        return subtractCoordinate(cipher[1], multiWithPrivate);
    }
    
    // operasi penambahan titik dalam kurva
    public Coordinate addCoordinate(Coordinate a, Coordinate b) {
        if (a.isEqual(b)){
            // Hitung lamda untuk titik yang sama
            BigInteger three = new BigInteger("3");
            BigInteger two = new BigInteger("2");
            BigInteger temp = new BigInteger(a.X.toString());

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
    
    public Coordinate subtractCoordinate(Coordinate a, Coordinate b) {
        return addCoordinate(a, negateCoordinate(b));
    }
    
    public Coordinate multiplyCoordinate(Coordinate a, BigInteger n) {
        BigInteger two = new BigInteger("2");

        // Basis untuk rekursif
        if (n.equals(BigInteger.ONE)) return new Coordinate(a);
        if (n.equals(two)) return addCoordinate(a, a);

        
        if (n.mod(two).equals(BigInteger.ZERO)) {
            Coordinate sqrt = multiplyCoordinate(a, n.divide(two));
            return addCoordinate(sqrt, sqrt);
        }else {
            n = n.subtract(BigInteger.ONE);
            return addCoordinate(a, (multiplyCoordinate(a, n)));
        }
    }
    
}
