/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

/**
 *
 * @author Michael
 */
public class BitCoordinate {
    private int region;
    private int bitplane;
    private char color;
    
    public BitCoordinate(int _region, int _bitplane, char _color) {
        this.region = _region;
        this.bitplane = _bitplane;
        this.color = _color;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getBitplane() {
        return bitplane;
    }

    public void setBitplane(int bitplane) {
        this.bitplane = bitplane;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }
    
    
}
