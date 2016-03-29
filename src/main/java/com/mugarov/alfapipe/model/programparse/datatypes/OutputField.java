/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model.programparse.datatypes;

/**
 *
 * @author mugarov
 */
public class OutputField {
    private boolean directory;
    private boolean make;

    public OutputField(){
        this.directory = false;
        this.make =false;
    }
    
    /**
     * @return if an directory has to be set as an output
     */
    public boolean isDirectory() {
        return directory;
    }

    /**
     * @param if an directory has to be set as an output
     */
    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    /**
     * @return if the directory has to be made in advance
     */
    public boolean isMakeDirectory() {
        return make;
    }

    /**
     * @param if the directory has to be made in advance
     */
    public void setMakeDirectory(boolean makeDir) {
        this.make = makeDir;
    }
}
