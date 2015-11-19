/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Mark
 */
public class MultiFileChooser extends JFileChooser{
    
    private InputFilter filter;
    
    public MultiFileChooser(){
        this.setMultiSelectionEnabled(true);
        this.filter = new InputFilter();
        this.setFileFilter(filter);
    }
    
    @Override
    public File[] getSelectedFiles(){
        if(super.getSelectedFiles().length != 0 && super.getSelectedFile().getParentFile().exists()){
             this.setCurrentDirectory(super.getSelectedFile().getParentFile());
        }
        return super.getSelectedFiles();
    }
    
    public void setInputFilter(String name, String[] valids){
//        System.out.println("Set input filter for "+name+" with ");
//        for(String v:valids){
//            System.out.println("\t"+v);
//        }
        this.filter.setName(name);
        this.filter.setValid(valids);
    }

    private static class InputFilter extends FileFilter {
        private static String name;
        private static String[] valid;
        
        public InputFilter() {
        }

        public void setName(String description){
            name = description;
        }
        
        public void setValid(String[] valids){
            valid = valids;
        }
        
        @Override
        public boolean accept(File f) {
            if(f.isDirectory()||valid ==null){
                return true;
            }
            for(String v: valid){
                if(f.getName().endsWith(v)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return name;
        }
    }
    
}
