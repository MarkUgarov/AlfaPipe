/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.mainview.tab.DataTabbedPane;
import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JButton;

/**
 *
 * @author Mark
 */
public class MainContentPane extends Panel{
    
    private final BorderLayout layout;
    private final DataTabbedPane  tabs;
    
    private final Panel southPanel;
    private final JButton addSet;
    private final JButton start;
    
    
    
    public MainContentPane(){
        
        this.layout = new BorderLayout();
        this.setLayout(layout);
        
        
        this.tabs = new DataTabbedPane();
        
        this.southPanel = new Panel(new BorderLayout());
       
        this.addSet = ComponentPool.MAIN_BUTTON_POOL.getNewSetButton();
        this.start = ComponentPool.MAIN_BUTTON_POOL.getStartButton();
        
        this.init();

    }
    
    private void init(){
        
        this.add(this.tabs, BorderLayout.CENTER);
        this.southPanel.add(this.addSet, BorderLayout.WEST);
        this.southPanel.add(this.start, BorderLayout.EAST);
        this.add(this.southPanel, BorderLayout.SOUTH);
        
        
    }
    
    public DataTabbedPane getTabs(){
        return this.tabs;
    }
    
    
    
}
