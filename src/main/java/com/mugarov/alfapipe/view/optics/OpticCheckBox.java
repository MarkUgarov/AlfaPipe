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
        super.setOpaque(false);

    }

    @Override
    public void setOpaque() {
        this.transparent = false;
        super.setOpaque(true);

    }
    
    @Override
    public void setOpaque(boolean opaque) {
        this.transparent = opaque;
        super.setOpaque(opaque);
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
    
    
    
    public void disableByPresetting(){
        this.setEnabled(false);
        this.setSelected(false);
        super.setBackground(this.disabledColor);

        
        
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
