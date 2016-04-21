/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class OpticCheckBox extends JCheckBox implements Optic{
    
    private boolean transparent;
    private boolean drawBackground;
    private OpticerWrap surroundingPanel;
    private Color enabledColor;
    private final Color disabledColor = ParameterPool.COLOR_BACKGROUND_DISABLED;
    
    public OpticCheckBox(String content){
        super(content);
        this.setDoubleBuffered(true);
        this.enabledColor = super.getBackground();
        this.setTransparent();
        this.drawBackground = false;
    }
    
    public OpticCheckBox(){
        this("");
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
    public void setOpaque(boolean opaque) {
        this.transparent = opaque;
        if(this.surroundingPanel == null){
            super.setOpaque(opaque);
        }
        else{
            this.surroundingPanel.setOpaque(opaque);
        }
    }
    
    
    @Override
    public void setBackground(Color bg){
         if(this.surroundingPanel == null){
//            System.out.println("Setting this to "+bg);
            super.setBackground(bg);
        }
        else{
//             System.out.println("Setting surrounding on "+bg);
            this.surroundingPanel.setBackground(bg);
        }
        this.enabledColor = bg;
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

    /**
     * There is no effect yet. 
     * @param draw 
     */
    @Override
    public void drawBackgroundImage(boolean draw) {
        this.drawBackground = draw;
    }

    @Override
    public void mouseEntered() {
        // do nothing
    }

    @Override
    public void mouseExit() {
        // do nothing
    }

    @Override
    public OpticerWrap inTransparentPanel(){
        if(this.surroundingPanel == null){
            this.surroundingPanel = new OpticerWrap(this);
            this.surroundingPanel.setBackground(super.getBackground());
        }
        return this.surroundingPanel;
    }
    
    
    
    public void disableByPresetting(){
        this.setEnabled(false);
        this.setSelected(false);
        if(this.surroundingPanel != null){
            this.surroundingPanel.setBackground(this.disabledColor);
        }
        else{
            super.setBackground(this.disabledColor);
        }
        
        
    }
    
    public void reenable(){
        if(this.isEnabled()){
            return;
        }
        this.setEnabled(true);
        this.setSelected(false);
        this.setBackground(this.enabledColor);
        
    }
    
    
}
