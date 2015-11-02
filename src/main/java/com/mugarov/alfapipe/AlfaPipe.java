/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe;

import com.mugarov.alfapipe.control.FileSetManager;
import com.mugarov.alfapipe.view.mainview.MainJFrame;

/**
 *
 * @author Mark
 */
public class AlfaPipe {
    
    public static void main(String args[]){
        MainJFrame frame = new MainJFrame();
        FileSetManager manager = new FileSetManager(frame);
    }
    
}
