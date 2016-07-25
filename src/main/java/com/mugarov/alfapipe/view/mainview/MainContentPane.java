/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview;

import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.view.ColorExplanation;
import com.mugarov.alfapipe.view.mainview.tab.DataTabbedPane;
import com.mugarov.alfapipe.view.optics.OpticButton;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.optics.OpticSplitPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;


/**
 *
 * @author Mark
 */
public class MainContentPane extends JPanel{
    
    private final DataTabbedPane  tabs;
    
    private OpticSplitPane split;
    private final OpticPane head;
    private final OpticPane southPanel;
    private final OpticPane foot;
    private final OpticButton addSet;
    private final OpticButton start;
    private final OpticButton cancelAll;
    private final OpticButton qancellorButton;
    
    
    
    public MainContentPane(){
        
        super(new BorderLayout());
        
        
        this.tabs = new DataTabbedPane();
        this.head = new OpticPane(new BorderLayout());
        this.southPanel = new OpticPane(new BorderLayout());
        this.foot = new OpticPane(new FlowLayout());
       
        this.addSet = ComponentPool.MAIN_BUTTON_POOL.getNewSetButton();
        this.start = ComponentPool.MAIN_BUTTON_POOL.getStartButton();
        this.cancelAll = ComponentPool.MAIN_BUTTON_POOL.getCancelButton();
        this.qancellorButton = ComponentPool.MAIN_BUTTON_POOL.getQancellorButton();
        
        this.init();

    }
    
    private void init(){
        this.head.add(this.tabs, BorderLayout.CENTER);
        this.southPanel.add(this.addSet.inSurroundingPanel(), BorderLayout.WEST);
        this.southPanel.add(this.start.inSurroundingPanel(), BorderLayout.EAST);
        OpticPane southCenterPane = new OpticPane(new FlowLayout());
        southCenterPane.add(this.qancellorButton.inSurroundingPanel());
        southCenterPane.add(this.cancelAll.inSurroundingPanel());
        this.southPanel.add(southCenterPane, BorderLayout.CENTER);
        
        
        this.head.add(this.southPanel, BorderLayout.SOUTH);
        
        this.foot.add(new ColorExplanation());
        
        this.split = new OpticSplitPane(this.head, this.foot);
        this.add(this.split);
        this.split.setResizeWeight(1 );
    }
    
    public DataTabbedPane getTabs(){
        return this.tabs;
    }
    
    
    
}
