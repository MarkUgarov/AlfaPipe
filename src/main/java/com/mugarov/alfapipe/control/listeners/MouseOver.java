/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners;

import com.mugarov.alfapipe.view.optics.Optic;
import com.mugarov.alfapipe.view.optics.OpticButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author mugarov
 */
public class MouseOver implements MouseListener{
    
    private final Optic button;
    
    public MouseOver(Optic button){
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.button.mouseEntered();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.button.mouseExit();
    }
    
}
