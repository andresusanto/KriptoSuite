/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.test;

import com.andresusanto.object.Curve;
import com.andresusanto.engine.ECC;
import com.andresusanto.object.Coordinate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 *
 * @author Andre
 */
public class TestECC {
    
    public static void main(String[] args) throws Exception{
        //tesTambah();
        tesEnkripDekrip();
    }
    
    public static void tesTambah(){
        Curve curve = new Curve(new BigInteger("1"), new BigInteger("1"), new BigInteger("173"));
        Coordinate base = new Coordinate(new BigInteger("118"), new BigInteger("29"));
        
        BigInteger privateKey = new BigInteger("212");
        
        ECC ecc = new ECC(curve, base, privateKey);

        Coordinate coba = new Coordinate(new BigInteger("38"), new BigInteger("12"));
        Coordinate tes = new Coordinate(new BigInteger("38"), new BigInteger("12"));
        Coordinate tes2 = new Coordinate(new BigInteger("13"), new BigInteger("21"));
        Coordinate tes3 = ecc.negateCoordinate(coba);
        
        Coordinate dua = ecc.multiplyCoordinate(tes, new BigInteger("2"));
        dua.print();
        ecc.multiplyCoordinate(tes, new BigInteger("4")).print();
        ecc.addCoordinate(coba, tes2).print();
        ecc.addCoordinate(dua, dua).print();
    }
    
    public static void tesEnkripDekrip() throws Exception{
        Curve curve = new Curve(new BigInteger("4"), new BigInteger("5"), new BigInteger("173"));
        Coordinate base = new Coordinate(new BigInteger("118"), new BigInteger("29"));
        BigInteger privateKey = new BigInteger("2213123213213412");
        
        ECC ecc = new ECC(curve, base, privateKey);
        
        File file = new File("./test.bmp");
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileData);
        in.close();
        byte[] decodedFile = new byte[fileData.length];
        for(int i=0; i<fileData.length; i++) {
            Coordinate plain = new Coordinate(fileData[i], curve);
            Coordinate[] enkrip = ecc.encrypt(plain, ecc.generatePublic());
            Coordinate dekrip = ecc.decrypt(enkrip);
            decodedFile[i] = plain.toByte();
        }
        FileOutputStream fw = new FileOutputStream(new File("./decoded.bmp"));
        fw.write(decodedFile);
        fw.close();
        
        //Coordinate plain = new Coordinate(new BigInteger("38"), new BigInteger("12"));
        //plain.print();
        
        //Coordinate[] enkrip = ecc.encrypt(plain, ecc.generatePublic());
        
//        for (Coordinate cor : enkrip){
//            cor.print();
//        }
        
        //Coordinate dekrip = ecc.decrypt(enkrip);
        //dekrip.print();
    }
}
