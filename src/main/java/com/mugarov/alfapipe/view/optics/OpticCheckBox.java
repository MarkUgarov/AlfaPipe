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
    private JPanel surroundingPanel;
    private Color enabledColor;
    private final Color disabledColor = ParameterPool.COLOR_BACKGROUND_DISABLED;
    
    public OpticCheckBox(String content){
        super(content);
        this.setDoubleBuffered(true);
        this.enabledColor = super.getBackground();
        this.setTransparent();
        this.drawBackground = false;
        this.setRolloverEnabled(false);
    }
    
    public OpticCheckBox(){
        this("");
    }

    @Override
    public void setTransparent() {
        super.setOpaque(false);
        this.transparent = true;
    }

    @Override
    public void setOpaque() {
        super.setOpaque(true);
        this.transparent = false;
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
    public JPanel inTransparentPanel(){
        if(this.surroundingPanel == null){
            this.surroundingPanel = new JPanel();
            this.surroundingPanel.setOpaque(false);
            this.surroundingPanel.setDoubleBuffered(true);
            this.surroundingPanel.add(this);
        }
        
        return this.surroundingPanel;
    }
    
    @Override
    public void setBackground(Color bg){
        super.setBackground(bg);
        this.enabledColor = bg;
    }
    
    public void disableByPresetting(){
        this.setEnabled(false);
        this.setSelected(false);
        super.setBackground(this.disabledColor);
        if(this.surroundingPanel != null){
            this.surroundingPanel.setBackground(this.disabledColor);
        }
        
        
    }
    
    public void reenable(){
        if(this.isEnabled()){
            return;
        }
        this.setEnabled(true);
        this.setSelected(false);
        super.setBackground(this.enabledColor);
        if(this.surroundingPanel != null){
            this.surroundingPanel.setBackground(this.enabledColor);
        }
        
    }
    
    
}
