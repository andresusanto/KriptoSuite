/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

/**
 *
 * @author Andre
 */
public class IkedaPRNG {
    private double u;
    private double xn;
    private double yn;
    private boolean alternate; // boolean yang digunakan untuk menandai pergantian nilai x dan y
    
    public IkedaPRNG(double u, double xn, double yn){
        this.u = u;
        this.xn = xn;
        this.yn = yn;
        this.alternate = false;
    }
    
    private void calculate(){
        double xn1, yn1;
        
        double tn = 0.4 - (6.0 / (1 + Math.pow(this.xn, 2) + Math.pow(this.yn, 2)));
        
        xn1 = 1.0 + this.u * (this.xn * Math.cos(tn) - this.yn * Math.sin(tn));
        yn1 = this.u * (this.xn * Math.sin(tn) - this.yn * Math.cos(tn));
        
        this.xn = xn1;
        this.yn = yn1;
    }
    
    private byte compress(double value){
        byte result = 0;
        byte[] bytes = new byte[8];
        
        long bits = Double.doubleToLongBits(value);

        bytes[0] = (byte)(bits & 0xff);
        bytes[1] = (byte)((bits >> 8) & 0xff);
        bytes[2] = (byte)((bits >> 16) & 0xff);
        bytes[3] = (byte)((bits >> 24) & 0xff);
        bytes[4] = (byte)((bits >> 32) & 0xff);
        bytes[5] = (byte)((bits >> 40) & 0xff);
        bytes[6] = (byte)((bits >> 48) & 0xff);
        bytes[7] = (byte)((bits >> 56) & 0xff);
        
        for (byte b : bytes){
            result = (byte)(result ^ b);
            result = (byte)((result >>> 2) | (result << (6)));    
        }
        
        return result;
    }
    
    public byte next(){
        if (this.alternate){
            this.alternate = false;
            
            return compress(this.yn);
        }else{
            this.alternate = true;
            
            calculate();
            return compress(this.xn);
        }
    }
    
    public void print(){
        System.out.println("" + this.xn + "," + this.yn);
        //System.out.println("Yn = " + this.yn);
    }
    
    @Override
    public String toString(){
        return "" + this.xn + " " + this.yn;
    }
    
    public void calcPrint(){
        calculate();
        //print();
    }
}
