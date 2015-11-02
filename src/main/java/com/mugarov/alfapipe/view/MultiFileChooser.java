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
public class MultiFileChooser extends JFileChooser{
    
    public MultiFileChooser(){
        this.setMultiSelectionEnabled(true);
    }
    
    @Override
    public File[] getSelectedFiles(){
        if(super.getSelectedFiles().length != 0 && super.getSelectedFile().getParentFile().exists()){
             this.setCurrentDirectory(super.getSelectedFile().getParentFile());
        }
        return super.getSelectedFiles();
    }
    
}
