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
    private Coordinate publicKey;
    private BigInteger privateKey;
    
    // satu kordinat dienkripsi menjadi 2 kordinat
    // hal tersebut karena kordinat 1 = random * kordinat basis, dan kordinat 2 = plain + random * publickey
    public Coordinate[] encrypt(Coordinate plain) {
        return null;
    }
    
    public Coordinate decrypt(Coordinate[] cipher) {
        return null;
    }
    
    
}
