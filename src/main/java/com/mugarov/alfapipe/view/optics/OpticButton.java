/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.control.listeners.MouseOver;
import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
    
    private final Color onNotMouseOver = ParameterPool.COLOR_BACKGROUND_STANDARD;
    private final Color onMouseOver = ParameterPool.COLOR_BACKGROUND_MOUSEOVER;
    private boolean isMouseOver;
    
    
    public OpticButton(String text, String command){
        super(text);
        this.setActionCommand(command);
        
        this.setDoubleBuffered(true);
        
        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        this.setTransparent();
        
        this.setBackground(this.onNotMouseOver);
        this.isMouseOver = false;
        
        this.addMouseListener(new MouseOver(this));
        
        this.width = (int) this.getPreferredSize().getWidth()+marginWidth;
        this.height = (int) this.getPreferredSize().getHeight()+marginHeight;
        
        this.setPreferredSize(new Dimension(this.width, this.height));
        
    }
    


    @Override
    public void setTransparent() {
        this.transparent = true;
        super.setOpaque(false);
    }

    @Override
    public void setOpaque() {
       this.transparent = false;
       super.setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        // draw rectangle with rounded corners on top of button 
        g.setColor(super.getBackground());
        g.fillRoundRect(0,0,width,height,this.marginWidth, this.marginHeight); 
        // draw a border 
        g.setColor(super.getForeground());
        g.drawRoundRect(0,0,width, height, this.marginWidth, this.marginHeight); 
        if(!this.isMouseOver){
            // find size text to position in center.
            FontRenderContext frc = new FontRenderContext(null, false, false); 
            Rectangle2D r = getFont().getStringBounds(this.getText(), frc);
            int xMargin = (int)(this.width-r.getWidth())/2; 
            int yMargin = (int)(this.height-this.getFont().getSize())/2; 
            // draw text in the center 
            g.drawString(this.getText(), xMargin, this.getFont().getSize() + yMargin); 
        }

    }

    /**
     * Currently no picture will be drawn. Overwrite the paint-method to 
     * draw a picture.
     * @param draw 
     */
    @Override
    public void drawBackgroundImage(boolean draw) {
        this.drawImage = draw;
    }
    
    @Override
    public OpticerWrap inTransparentPanel(){
        JPanel ret = new JPanel();
        ret.setOpaque(false);
        ret.setDoubleBuffered(true);
        ret.add(this);
        return new OpticerWrap(ret);
    }
    
    @Override
    public void mouseEntered(){
        this.setBackground(onMouseOver);
        this.isMouseOver = true;
    }
    
    @Override
    public void mouseExit(){
        this.setBackground(onNotMouseOver);
        this.isMouseOver = false;
    }
 

}
