/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class OpticCheckBox extends JCheckBox implements Optic{
    
    private boolean transparent;
    private boolean drawBackground;
    
    public OpticCheckBox(String content){
        super(content);
        this.setDoubleBuffered(true);
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
        JPanel ret = new JPanel();
        ret.setOpaque(false);
        ret.setDoubleBuffered(true);
        ret.add(this);
        return ret;
    }
    
}
