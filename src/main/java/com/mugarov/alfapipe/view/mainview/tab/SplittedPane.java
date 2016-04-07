/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JSplitPane;

/**
 *
 * @author mugarov
 */
public class SplittedPane extends JSplitPane{
    
    private final Component north;
    private final Component south;
    
    public SplittedPane(Component north, Component south){
        super(JSplitPane.VERTICAL_SPLIT, north, south);
        this.north = north;
        this.south = south;
        super.setOneTouchExpandable(true);
        this.setOpaque(false);
//        this.north.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
//        this.south.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
//        this.setBackground(ParameterPool.COLOR_BACKGROUND_STANDARD);
    }

    
}
