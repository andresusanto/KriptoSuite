/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.object.Coordinate;
import com.andresusanto.object.Curve;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author Andre
 */
public class ECC {
    private MessageDigest hash;
    private Curve curve;
    private Coordinate baseCoordinate;
    private BigInteger privateKey;


    public ECC (Curve curve, Coordinate base, BigInteger privateKey) throws NoSuchAlgorithmException{
        this.curve = curve;
        this.baseCoordinate = base;
        this.privateKey = privateKey;
        hash = MessageDigest.getInstance("SHA-1");
    }

    public Coordinate generatePublic(){
        return multiplyCoordinate(baseCoordinate, privateKey);
    }
    
    // fungsi untuk memverifikasi tandatangan digital
    public boolean verify(byte[] message, byte[] signature, Coordinate publicKey){
        BigInteger r = new BigInteger(Arrays.copyOfRange(signature, 0, signature.length/2));
        BigInteger s = new BigInteger(Arrays.copyOfRange(signature, signature.length/2, signature.length));
        BigInteger e = calculateE(curve.n, message);
        
        System.out.println("R' : " + r.toString());
        System.out.println("S' : " + s.toString());
        
        if (r.compareTo(BigInteger.ONE) < 0 || r.compareTo(curve.n) >= 0) return false; // r berada di [1, p - 1]
        if (s.compareTo(BigInteger.ONE) < 0 || s.compareTo(curve.n) >= 0) return false; // s berada di [1, p - 1]
        
        System.out.println("OKE");
        
        BigInteger w = s.modInverse(curve.n);
        
        BigInteger u1 = e.multiply(w).mod(curve.n);
        BigInteger u2 = r.multiply(w).mod(curve.n);
        
        Coordinate curvePoint = addCoordinate( multiplyCoordinate(this.baseCoordinate, u1), multiplyCoordinate(publicKey, u2) );
        
        System.out.println("X : " + curvePoint.X.mod(curve.n).toString());
        
        return r.equals(curvePoint.X.mod(curve.n));
    }
    
    // fungsi untuk menghasilkan digital signature
    public byte[] sign(byte[] input) throws IOException{
        BigInteger e = calculateE(curve.n, input);
        BigInteger k = new BigInteger(curve.n.bitLength(), new Random());
        
        BigInteger r, s;
        
        do { // generate s
            
            do // generate r
            {
                Coordinate p = multiplyCoordinate(baseCoordinate, k);
                r = p.X.mod(curve.n);
            
            } while (r.equals(BigInteger.ZERO));

            s = k.modInverse(curve.n).multiply(e.add(this.privateKey.multiply(r))).mod(curve.n);
        
        }while (s.equals(BigInteger.ZERO));
        
        System.out.println("R : " + r.toString());
        System.out.println("S : " + s.toString());
        
        int len = r.toByteArray().length;
        
        byte result[] = new byte[len * 2];
        System.arraycopy(r.toByteArray(), 0, result, 0, len);
        System.arraycopy(s.toByteArray(), 0, result, len, len);
        
        return result;
    }

    // fungsi untuk melakukan enkripsi data byte
    public byte[] encrypt(byte[] input, Coordinate publicKey) throws IOException{
        if (curve.pLength != input.length) throw new IOException("Input size not match!");
        
        byte[] encryptedFile = new byte[(curve.pLength + 1) * 2];
        BigInteger k = new BigInteger(curve.p.bitLength(), new Random());
        
        Coordinate gamma = multiplyCoordinate(this.baseCoordinate, k);
	Coordinate sec = multiplyCoordinate(publicKey, k);
        System.arraycopy(gamma.toByte(curve), 0, encryptedFile, 0, curve.pLength + 1);
        
        hash.reset();
        hash.update(sec.X.toByteArray());
	hash.update(sec.Y.toByteArray());
        
        byte[] digest = hash.digest();
        
        // isi data ke byte
        for(int j = 0; j < input.length; j++) {
	    encryptedFile[ j + curve.pLength + 1 ] = (byte)(input[j]^digest[j]);
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

    public byte[] decrypt(byte[] input) throws IOException{
        if (curve.pLength != (input.length / 2) - 1) throw new IOException("Input size not match!");
        byte[] decryptedFile = new byte[curve.pLength];
        byte[] gammacom = new byte[curve.pLength + 1];
        
        System.arraycopy(input, 0, gammacom, 0, curve.pLength + 1);
        Coordinate gamma = new Coordinate(gammacom, curve);
        Coordinate sec = multiplyCoordinate(gamma, this.privateKey);
        
        hash.reset();
        hash.update(sec.X.toByteArray());
        hash.update(sec.Y.toByteArray());
        
        byte[] digest = hash.digest();
	for(int j = 0; j < decryptedFile.length; j++) {
	    decryptedFile[j] = (byte)(input[j + curve.pLength + 1] ^ digest[j]);
	}
        
        return decryptedFile;
    }

    public Coordinate decrypt(Coordinate[] cipher){
        Coordinate multiWithPrivate = multiplyCoordinate(cipher[0], privateKey);
        return subtractCoordinate(cipher[1], multiWithPrivate);
    }
    
    // fungsi untuk menghitung nilai E untuk keperluan ECC DSA
    private BigInteger calculateE(BigInteger n, byte[] message){
        int log2n = n.bitLength();
        int messageBitLength = message.length * 8;

        BigInteger e = new BigInteger(1, message);
        if (log2n < messageBitLength){
            e = e.shiftRight(messageBitLength - log2n);
        }
        
        return e;
    }

    // operasi penambahan titik dalam kurva
    public Coordinate addCoordinate(Coordinate a, Coordinate b) {
        if (a.isZero()) return new Coordinate(b);
        else if (b.isZero()) return new Coordinate(a);
        BigInteger lambda = null;
        BigInteger three = new BigInteger("3");
        BigInteger two = new BigInteger("2");
        
        if (a.X.equals(b.X)){
            if (a.Y.equals(b.Y)){
                lambda = ((a.X.modPow(two,curve.p)).multiply(three)).add(curve.a);
                lambda = (lambda.multiply((two.multiply(a.Y)).modInverse(curve.p))).mod(curve.p);
            }else{
                return new Coordinate(BigInteger.ZERO, BigInteger.ZERO);
            }
            
        }else{
            lambda = ((b.Y.subtract(a.Y)).multiply((b.X.subtract(a.X)).modInverse(curve.p))).mod(curve.p);
        }
        
        // Hitung nilai X dan Y yang baru
        BigInteger newX = (((lambda.modPow(two,curve.p)).subtract(b.X)).subtract(a.X)).mod(curve.p);
	BigInteger newY = ((lambda.multiply(a.X.subtract(newX))).subtract(a.Y)).mod(curve.p);
        
        //return new Coordinate(newX, newY);
        return new Coordinate(newX, newY, curve);
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