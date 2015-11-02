/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Mark
 */
public class OutputDirectoryChooser extends JFileChooser{
    
    public OutputDirectoryChooser(){
        this.setMultiSelectionEnabled(false);
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    
    @Override
    public File getSelectedFile(){
        return super.getSelectedFile();
    }
    
}
