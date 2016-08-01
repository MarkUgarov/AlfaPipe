/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
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
    private OpticerWrap surroundingPanel;
    private Color background;
    private Image backgroundImage;
    private boolean failedToReadBackgroundImage;
    private float imageOpacity =  0.5f;
    private float imageScale = 0.5f;
    
    
     public OpticPane(){
        super();
        this.setDoubleBuffered(true);
        this.drawImage = false;
        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
        this.failedToReadBackgroundImage = false;
        this.setTransparent();
    }
    
    public OpticPane(boolean transparent){
        this();
        if(!transparent){
            this.setOpaque();
        }
    }

    public OpticPane(LayoutManager layout) {
        this(layout, true);
    }
    
    public OpticPane(LayoutManager layout, boolean transparent) {
        this();
        this.setLayout(layout);
        if(!transparent){
            this.setOpaque();
        }
    }
    

    
    
    
    @Override
    public void setTransparent() {
        this.transparent = true;
        if(this.surroundingPanel == null){
            super.setOpaque(false);
        }
        else{
            this.surroundingPanel.setOpaque(false);
        }
    }

    @Override
    public void setOpaque() {
        this.transparent = false;
        if(this.surroundingPanel == null){
            super.setOpaque(true);
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
        File imgFile = new File("/vol/ampipe/AlfaPipe/graphic/LogoCut.png");
        if(!imgFile.exists()){
            File no = new File((new File(".")).getParent());
            System.err.println("Image not found in "+no.getAbsolutePath());
            this.failedToReadBackgroundImage = true;
            return null;
        }
         
        Image image = null;
        try {
            image = ImageIO.read(imgFile);
//            System.out.println("Image set.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.failedToReadBackgroundImage = false;
        return image;
    }
    
    public BufferedImage toBufferedImage(Image img){
        int imageHeight = (int) Math.min(this.getHeight(),(Math.max(this.getHeight(), this.getWidth())*this.imageScale));
        int imageWidth = (int)  Math.min(this.getWidth(),(Math.max(this.getHeight(), this.getWidth())*this.imageScale));
        BufferedImage buffImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = buffImage.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.imageOpacity));
        g.drawImage(img, 0, 0, imageWidth, imageHeight, null);
        g.dispose();
        return buffImage;
    }
     
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!this.transparent && this.drawImage && !this.failedToReadBackgroundImage){
            
            if(this.backgroundImage == null){
                this.backgroundImage = this.getBackgroundImage();
            }
            if(!this.failedToReadBackgroundImage){
                g.drawImage(this.toBufferedImage(this.backgroundImage), 0, 0, null);
            }
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