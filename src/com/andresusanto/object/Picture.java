/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.object;

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
        BufferedImage original = ImageIO.read(new File(address));
        BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(original, 0, 0, null);
        
        width = img.getWidth();
        height = img.getHeight();
        
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        
        if (address.endsWith(".png"))
            this.pictureType = PICTURE_PNG;
        else
            this.pictureType = PICTURE_BMP;
        
    }
    
    public int getTotalRegions(){
        return (this.width / 8) * ((int)(this.height / 8));
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
                byte colorByte = 0;
                
                // geser warna, format warna pada int (4 byte) adalah: A R G B, masing2 1 byte
                switch (colorCode){
                    case COLOR_RED:
                        colorByte = (byte)((pixel >> 16 ) & 0xFF); // geser 2 byte, lalu ambil 1 byte
                        break;
                    case COLOR_GREEN:
                        colorByte = (byte)((pixel >> 8 ) & 0xFF); // geser 1 byte, lalu ambil 1 byte
                        break;
                    case COLOR_BLUE:
                        colorByte = (byte)((pixel ) & 0xFF); // gak geser, lalu ambil 1 byte saja
                        break;
                }
                
                result[y * 8 + x] = ((colorByte >> layer) & 1) == 1;
                
                // Fungsi ini untuk testing (format ke boolean image)
//                if (((colorByte >> layer) & 1) == 1)
//                    this.pixels[Xconstant + x + Ypos] = intFromRGB(0, 0, 0);
//                else
//                    this.pixels[Xconstant + x + Ypos] = intFromRGB(255, 255, 255);
            }
        }
        
        return result;
    }
    
    
    public void setBitPlane(int region, int layer, char colorCode, boolean[] bitPlane){
        int regionsPerLine = this.width / 8;
        int regionX = region % regionsPerLine;
        int regionY = region / regionsPerLine;
        
        
        int Xconstant = regionX * 8;
        int Yconstant = regionY * 8 * this.width;
        
        for (int y = 0; y < 8; y++){
            int Ypos = Yconstant + y * this.width;
            
            for (int x = 0; x < 8; x++){
                int pixel = this.pixels[Xconstant + x + Ypos];
                byte colorByte = 0;
                byte colorPlane = (byte) ((bitPlane[y * 8 + x] ? 1 : 0) << layer); // memposisikan bit sesuai layer
                
                // ambil warna dari pixel
                switch (colorCode){
                    case COLOR_RED:
                        colorByte = (byte)((pixel >> 16 ) & 0xFF); // geser 2 byte, lalu ambil 1 byte
                        break;
                    case COLOR_GREEN:
                        colorByte = (byte)((pixel >> 8 ) & 0xFF); // geser 1 byte, lalu ambil 1 byte
                        break;
                    case COLOR_BLUE:
                        colorByte = (byte)((pixel ) & 0xFF); // gak geser, lalu ambil 1 byte saja
                        break;
                }
                
                // buat bit pada layer menjadi 0
                colorByte = (byte) (colorByte & (0xFF & (0 << layer)));
                colorByte = (byte) (colorByte | colorPlane);
                
                // kembalikan lagi warna
                switch (colorCode){
                    case COLOR_RED:
                        pixel = pixel & 0xFF00FFFF;
                        pixel = pixel | (colorByte << 16);
                        break;
                    case COLOR_GREEN:
                        pixel = pixel & 0xFFFF00FF;
                        pixel = pixel | (colorByte << 8);
                        break;
                    case COLOR_BLUE:
                        pixel = pixel & 0xFFFFFF00;
                        pixel = pixel | (colorByte);
                        break;
                }
                
                this.pixels[Xconstant + x + Ypos] = pixel;
            }
        }
    }
    
    private int intFromRGB(int r, int g, int b){
        return 0xFF000000 | (r << 16) | (g << 8) | (b);
    }
    
    public void save(String address) throws IOException{ // handle file type dan save ke format tersebut
        BufferedImage bi;
        
        if (pictureType == PICTURE_PNG)
            bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        else
            bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        
        int[] a = ( (DataBufferInt) bi.getRaster().getDataBuffer() ).getData();
        System.arraycopy(pixels, 0, a, 0, pixels.length);
        
        if (pictureType == PICTURE_PNG)
            ImageIO.write(bi, "png", new File(address));
        else
            ImageIO.write(bi, "BMP", new File(address));
    }
    
}
