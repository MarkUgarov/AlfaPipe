/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control;

import com.mugarov.qancellor.control.QancellorHead;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author mugarov
 */
public class QancellorControl {
    private QancellorHead qancellorHead;
    private boolean opened;
    
    public QancellorControl(){
        this.opened = false;
        this.qancellorHead = new QancellorHead(new QancellWindowListener(), false);
        
    }
    
    public void openQancellor(){
        
        if(!opened){
            qancellorHead.setFrameVisible(true);
            opened = true;
        }
    }
    
    

    private class QancellWindowListener implements WindowListener {

        public QancellWindowListener() {
        }

        @Override
        public void windowOpened(WindowEvent e) {
           opened = true;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            qancellorHead.setFrameVisible(false);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            opened = false;
        }

        @Override
        public void windowIconified(WindowEvent e) {
            opened = false;
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            opened = true;
        }

        @Override
        public void windowActivated(WindowEvent e) {
            opened = true;
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            opened = false;
        }
    }
    
}
