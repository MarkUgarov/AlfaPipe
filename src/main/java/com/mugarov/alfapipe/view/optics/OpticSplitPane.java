/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import java.awt.Component;
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
        this.setOpaque(false);
    }

    @Override
    public void setOpaque() {
        this.transparent = false;
        this.setOpaque(true);
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

    
}
