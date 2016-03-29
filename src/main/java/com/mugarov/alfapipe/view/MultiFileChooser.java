/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view;

import com.mugarov.alfapipe.model.ParameterPool;
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
    

    /**
     * Sets a Filter with the chosen name which the chosen file endings.
     * @param name can be any String (should not be null) which will be shown in a drop-down-menu
     * @param validInputEndings can be an Array of any Strings, can be null so every file will be accepted
     */
    public void setInputfilter(String name, String[] validInputEndings) {
        if(validInputEndings == null){
            this.filter = new InputFilter();
            this.setFileFilter(filter);
            this.filter.setName(name);
        }
        else if(validInputEndings.length == 1 && validInputEndings[0].equals(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
            this.filter.setName(name);
            this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        else{
            this.filter.setName(name);
            boolean dirFound=false;
            for(String v:validInputEndings){
                if(v.equals(ParameterPool.PROGRAM_DIRECTORY_VALUE)){
                    dirFound=true;
                }
            }
            if(dirFound){
                this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            }    
            else{
                this.setFileSelectionMode(JFileChooser.FILES_ONLY);
            }
            this.filter.setValid(validInputEndings);
        }
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
