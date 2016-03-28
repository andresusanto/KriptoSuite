/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.test;

import com.andresusanto.object.Curve;
import com.andresusanto.engine.ECC;
import com.andresusanto.object.Coordinate;
import java.math.BigInteger;

/**
 *
 * @author Andre
 */
public class TestECC {
    
    public static void main(String[] args){
        tesTambah();
    }
    
    public static void tesTambah(){
        Curve curve = new Curve(new BigInteger("1"), new BigInteger("1"), new BigInteger("173"));
        Coordinate base = new Coordinate(new BigInteger("118"), new BigInteger("29"));
        
        ECC ecc = new ECC(curve, base);

        Coordinate coba = new Coordinate(new BigInteger("38"), new BigInteger("12"));
        Coordinate tes = new Coordinate(new BigInteger("38"), new BigInteger("12"));
        Coordinate tes2 = new Coordinate(new BigInteger("13"), new BigInteger("21"));
        Coordinate tes3 = ecc.negateCoordinate(coba);
        
        Coordinate dua = ecc.multiply(tes, new BigInteger("2"));
        dua.print();
        ecc.multiply(tes, new BigInteger("4")).print();
        ecc.addCoordinate(coba, tes2).print();
        ecc.addCoordinate(dua, dua).print();
    }
}
