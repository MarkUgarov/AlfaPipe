/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.optics;

import java.awt.Graphics;

/**
 *
 * @author mugarov
 */
public interface Optic {


    public abstract void setTransparent();
    
    public abstract void setOpaque(); 

    public abstract void drawBackgroundImage(boolean draw);

    public abstract void mouseEntered();

    public abstract void mouseExit();
    
}
