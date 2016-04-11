/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mark
 */
public class OpticButton extends JButton implements Optic{
    
    private boolean transparent;
    private boolean drawImage;
    private final int width;
    private final int height;
    int marginWidth=15;
    int marginHeight=15;
    
    public OpticButton(String text, String command){
        super(text);
        this.setDoubleBuffered(true);
        this.setOpaque();
        this.setActionCommand(command);
        
        this.setBackground(ParameterPool.COLOR_BACKGROUND_SECOND);
        this.setBorder(null);
        
        this.width = (int) this.getPreferredSize().getWidth()+marginWidth;
        this.height = (int) this.getPreferredSize().getHeight()+marginHeight;
        
        this.setPreferredSize(new Dimension(this.width, this.height));
        
    }
    


    @Override
    public void setTransparent() {
        this.transparent = true;
        this.setOpaque(false);
    }

    @Override
    public void setOpaque() {
       this.transparent = false;
       this.setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g){
// Take advantage of Graphics2D to position string 
        Graphics2D g2d = (Graphics2D)g; 
// Make it grey #DDDDDD, and make it round with // 1px black border. 
// Use an HTML color guide to find a desired color. 
// Last color is alpha, with max 0xFF to make 
// completely opaque. 
        g2d.setColor(ParameterPool.COLOR_BACKGROUND_SECOND); 
// Draw rectangle with rounded corners on top of 
// button 
        g2d.fillRoundRect(0,0,width,height,18,18); 
// I'm just drawing a border 
        g2d.setColor(Color.darkGray); 
        g2d.drawRoundRect(0,0,width, height,18,18); 
// Finding size of text so can position in center.
        FontRenderContext frc = new FontRenderContext(null, false, false); 
        Rectangle2D r = getFont().getStringBounds(getText(), frc);
        float xMargin = (float)(width-r.getWidth())/2; 
        float yMargin = (float)(height-getFont().getSize())/2; 
// Draw the text in the center 
        g2d.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin); 
        this.setSize(width, height);
    }

    /**
     * Currently no picture will be drawn. Overwrite the paint-method to 
     * draw a picture.
     * @param draw 
     */
    @Override
    public void drawBackgroundImaage(boolean draw) {
        this.drawImage = draw;
    }
    
    public JPanel inTransparentPanel(){
        JPanel ret = new JPanel();
        ret.setOpaque(false);
        ret.setDoubleBuffered(true);
        ret.add(this);
        return ret;
    }
    
 

}
