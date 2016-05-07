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
    private float u;
    private float xn;
    private float yn;
    
    public IkedaPRNG(float u, float xn, float yn){
        this.u = u;
        this.xn = xn;
        this.yn = yn;
    }
    
    private void calculate(){
        float xn1, yn1;
        
        float tn = 0.4f - (float)(6.0 / (1 + Math.pow(this.xn, 2) + Math.pow(this.yn, 2)));
        
        xn1 = 1.0f + (float)(this.u * (this.xn * Math.cos(tn) - this.yn * Math.sin(tn)));
        yn1 = (float)(this.u * (this.xn * Math.sin(tn) - this.yn * Math.cos(tn)));
        
        this.xn = xn1;
        this.yn = yn1;
    }
    
    public void print(){
        System.out.println("Xn = " + this.xn);
        System.out.println("Yn = " + this.yn);
    }
    
    public void calcPrint(){
        calculate();
        print();
    }
}
