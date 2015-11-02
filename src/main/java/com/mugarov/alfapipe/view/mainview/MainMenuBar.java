/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview;

import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.control.listeners.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
        
        this.main = new JMenu(Pool.MENU_MAIN);
        this.add(main);
        
        this.exit = new JMenuItem(Pool.MENU_EXIT);
        this.exit.setActionCommand(Pool.MENU_EXIT);
        this.listener = Pool.LISTENER_MENU;
        this.exit.addActionListener(this.listener);
        this.main.add(exit);
        
                
        
    }
    
    

    
}
