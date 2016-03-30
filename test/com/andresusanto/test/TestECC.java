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
        Curve curve = new Curve(new BigInteger("4"), new BigInteger("5"), new BigInteger("98764321261"));
        Coordinate base = new Coordinate(new BigInteger("127"), new BigInteger("113"));
        BigInteger privateKey = new BigInteger("123");
        
        ECC ecc = new ECC(curve, base, privateKey);
        
        File file = new File("./test.png");
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileData);
        in.close();
        byte[] encryptedFile = new byte[2*fileData.length];
        byte[] decodedFile = new byte[fileData.length];
        for(int i=0; i<fileData.length; i++) {
//            System.out.printf("asli : %2x\n", fileData[i]);
            Coordinate plain = new Coordinate(fileData[i], curve);
//            System.out.print("Asli :\n");
//            plain.print();
            Coordinate[] enkrip = ecc.encrypt(plain, ecc.generatePublic());
            encryptedFile[i*2] = enkrip[0].toByte();
            encryptedFile[i*2+1] = enkrip[1].toByte();
//            System.out.print("Enkrip :\n");
//            enkrip[0].print();
//            enkrip[1].print();
            
            
            
            Coordinate dekrip = ecc.decrypt(enkrip);
            decodedFile[i] = dekrip.toByte();
//            System.out.printf("Dekrip : %2x\n", decodedFile[i]);
        }
        FileOutputStream fw = new FileOutputStream(new File("./decoded.png"));
        fw.write(decodedFile);
        fw.close();
        
        fw = new FileOutputStream(new File("./encrypted.png"));
        fw.write(encryptedFile);
        fw.close();
//        Coordinate plain = new Coordinate(new BigInteger("119"), new BigInteger("1211"));
//        plain.print();
//        
//        Coordinate[] enkrip = ecc.encrypt(plain, ecc.generatePublic());
//        
//        for (Coordinate cor : enkrip){
//            cor.print();
//        }
//        
//        Coordinate dekrip = ecc.decrypt(enkrip);
//        dekrip.print();
    }
}
