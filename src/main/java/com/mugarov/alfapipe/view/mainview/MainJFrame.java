/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.view.mainview.tab.DataTabbedPane;
import com.mugarov.alfapipe.view.mainview.tab.Tab;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Mark
 */
public class MainJFrame extends JFrame{
    
    private MainMenuBar menu;
    private MainContentPane content;
    
    public MainJFrame(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(Pool.TITLE);
        
//        this.menu=new MainMenuBar();
        this.setJMenuBar(menu);
        this.content = new MainContentPane();
        this.setContentPane(content);
        this.setSize(Pool.FRAMESIZE);
        if(Pool.FULLSCREAN){
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        this.setVisible(true);
        
    }
    
    public DataTabbedPane getTabPane(){
        return this.content.getTabs();
        
    }
    
    
    
}
