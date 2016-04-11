/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class OpticPane extends JPanel implements Optic{
    
    private boolean transparent;
    private boolean drawImage;
    
    public OpticPane(){
        super();
        this.setDoubleBuffered(true);
        this.drawImage = true;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        this.setTransparent();
    }
    
    public OpticPane(boolean transparent){
        super();
        this.setDoubleBuffered(true);
        this.drawImage = true;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        if(transparent){
            this.setTransparent();
        }
    }

    public OpticPane(BorderLayout borderLayout) {
        super(borderLayout);
        this.setDoubleBuffered(true);
        this.drawImage = true;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        this.setTransparent();
    }
    
    public OpticPane(BorderLayout borderLayout, boolean transparent) {
        super(borderLayout);
        this.setDoubleBuffered(true);
        this.drawImage = true;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        if(transparent){
            this.setTransparent();
        }
    }
    
    
    @Override
    public void setTransparent(){
        this.transparent = true;
        this.setOpaque(false);
    }
    
    @Override
    public void setOpaque(){
        this.transparent = false;
        this.setOpaque(true);
    }
    
    private Image getBackgroundImage() {
        File imgFile = new File("graphic/LogoCut.png");
        if(!imgFile.exists()){
            File no = new File((new File(".")).getParent());
            System.err.println("Image not found in "+no.getAbsolutePath());
            return null;
        }
         
        Image image = null;
        try {
            image = ImageIO.read(imgFile);
//            System.out.println("Image set.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
     
    @Override
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!this.transparent && this.drawImage){
            g.drawImage(this.getBackgroundImage(), 0, 0, null);
        }
        
    }

    @Override
    public void drawBackgroundImaage(boolean draw) {
        this.drawImage = draw;
    }
    
}