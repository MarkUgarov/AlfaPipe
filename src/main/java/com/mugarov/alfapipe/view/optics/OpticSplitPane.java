/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author mugarov
 */
public class OpticSplitPane extends JSplitPane implements Optic{
    
    private final Component north;
    private final Component south;
    private boolean transparent;
    private boolean drawImage;
    private OpticerWrap surroundingPanel;
    
    public OpticSplitPane(Component north, Component south){
        super(JSplitPane.VERTICAL_SPLIT, north, south);
        this.setDoubleBuffered(true);
        this.north = north;
        this.south = south;
        super.setOneTouchExpandable(true);
        this.setTransparent();
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

    /**
     * Not functional yet. Overdraw the paint-method to draw background picture.
     * @param draw 
     */
    @Override
    public void drawBackgroundImage(boolean draw) {
        this.drawImage=draw;
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
