/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andre
 */
public class Picture {
    public static final char PICTURE_PNG = 'P';
    public static final char PICTURE_BMP = 'B';
    
    public static final char COLOR_RED = 'R';
    public static final char COLOR_GREEN = 'G';
    public static final char COLOR_BLUE = 'B';
    
    public char pictureType;
    public int[] pixels; // 4 byte data - A R G B
    public int width;
    public int height;
    
    public Picture(String address) throws IOException { // handle file type dan serahkan ke loader khusus
        BufferedImage img = ImageIO.read(new File(address));
        
        width = img.getWidth();
        height = img.getHeight();
        
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        
        if (address.endsWith(".png"))
            this.pictureType = PICTURE_PNG;
        else
            this.pictureType = PICTURE_BMP;
        
    }
    
    // mengambil bitplane pada region tertentu dan pada layer tertentu
    public boolean[] getBitPlane(int region, int layer, char colorCode){
        // region dimulai dari kiri atas = region 0
        // jika lebar pixel tidak habis dibagi 8, maka sisa pembagiannya akan diabaikan (misal size = 803, maka akan ada 3 pixel pada lebar tsb yang tidak dipakai)
        // banyaknya region = (width % 8) x (height % 8)
        
        int regionsPerLine = this.width / 8;
        int regionX = region % regionsPerLine;
        int regionY = region / regionsPerLine;
        
        int Xconstant = regionX * 8;
        int Yconstant = regionY * 8 * this.width;
        
        boolean result[] = new boolean[64];
        
        for (int y = 0; y < 8; y++){
            int Ypos = Yconstant + y * this.width;
            
            for (int x = 0; x < 8; x++){
                int pixel = this.pixels[Xconstant + x + Ypos];
                this.pixels[Xconstant + x + Ypos] = intFromRGB(0,0,0);
                /*byte colorByte;
                
                switch (colorCode){
                    case COLOR_RED:
                        
                        break;
                    case COLOR_GREEN:
                        break;
                    case COLOR_BLUE:
                        break;
                }*/
            }
        }
        
        return result;
    }
    
    private int intFromRGB(int r, int g, int b){
        return 0xFF000000 | (r << 16) | (g << 8) | (b);
    }
    
    public void save(String address) throws IOException{ // handle file type dan save ke format tersebut
        BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        int[] a = ( (DataBufferInt) bi.getRaster().getDataBuffer() ).getData();
        System.arraycopy(pixels, 0, a, 0, pixels.length);
        
        if (pictureType == PICTURE_PNG)
            ImageIO.write(bi, "png", new File(address));
        else
            ImageIO.write(bi, "BMP", new File(address));
    }
    
}
