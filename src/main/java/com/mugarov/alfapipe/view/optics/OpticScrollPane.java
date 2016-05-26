/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author mugarov
 */
public class OpticScrollPane extends JScrollPane implements Optic{
    
    private boolean transparent;
    private OpticerWrap surroundingPanel;
    private boolean drawImage;
    
    public OpticScrollPane(){
        this.setDoubleBuffered(true);
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        this.drawImage = true;
        this.setTransparent();
    }

    public OpticScrollPane(OpticPane panel) {
        super(panel);
        this.setDoubleBuffered(true);
        this.drawImage = true;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        this.setTransparent();
    }
    
    @Override
     public void setTransparent(){
        this.transparent = true;
        this.drawImage = true;
         if(this.surroundingPanel == null){
            super.setOpaque(false);
            this.getViewport().setOpaque(false);
        }
        else{
            this.surroundingPanel.setOpaque(false);
        }
    }
    
    @Override
    public void setOpaque(){
        this.transparent = false;
        this.drawImage = true;
        if(this.surroundingPanel == null){
            super.setOpaque(true);
            this.getViewport().setOpaque(true);
        }
        else{
            this.surroundingPanel.setOpaque(true);
        }
    }

    @Override
    public void setBackground(Color bg){
         if(this.surroundingPanel == null){
            super.setBackground(bg);
        }
        else{
            this.surroundingPanel.setBackground(bg);
        }
    }
    
     @Override 
    public Color getBackground(){
        if(this.surroundingPanel == null){
            return super.getBackground();
        }
        else{
            return this.surroundingPanel.getBackground();
        }
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
    public void drawBackgroundImage(boolean draw) {
        this.drawImage = draw;
    }

    @Override
    public void mouseEntered() {
        // do nothing
    }

    @Override
    public void mouseExit() {
        // do nothing
    }

    public OpticerWrap inTransparentPanel(){
        if(this.surroundingPanel == null){
            this.surroundingPanel = new OpticerWrap(this);
            this.surroundingPanel.setBackground(super.getBackground());
        }
        return this.surroundingPanel;
    }
    
}
