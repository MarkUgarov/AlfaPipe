/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview;

import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.control.listeners.MenuListener;
import com.mugarov.alfapipe.model.ComponentPool;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Mark
 */
public class MainMenuBar extends JMenuBar{
    
    private MenuListener listener;
    
    private JMenu main;
    private JMenuItem exit;
    
    
    public MainMenuBar(){
             
        this.init();
        this.setVisible(true);
    }
    
    private void init(){
        
        
        this.main = new JMenu(ParameterPool.MENU_MAIN);
        this.add(main);
        
        this.exit = new JMenuItem(ParameterPool.MENU_EXIT);
        this.exit.setActionCommand(ParameterPool.MENU_EXIT);
        this.listener = ComponentPool.LISTENER_MENU;
        this.exit.addActionListener(this.listener);
        this.main.add(exit);
        
                
        
    }
    
    

    
}
