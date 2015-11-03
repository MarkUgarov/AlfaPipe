/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import javax.swing.JTabbedPane;

/**
 *
 * @author Mark
 */
public class DataTabbedPane extends JTabbedPane{
    
    public DataTabbedPane(){
        super(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        this.setDoubleBuffered(true);
    }
    
    public Tab newTab(String id, TabListenerBag bag){
//        System.out.println("Adding Set(DataTabbedPane ready)");
        Tab ret = new Tab(id, bag);
        this.add(id,ret);
        this.setSelectedComponent(this.getComponent(this.getComponentCount()-1));
//        this.updateUI();
        return ret;
        
    }
    
    public void renameTab(String id, String newName){
        System.out.println("Try to rename "+id+"  with tab count "+this.getTabCount());
        for(int t=0;t< this.getTabCount(); t++){
            System.out.println("Checking position "+t);
            if ((((Tab)this.getTabComponentAt(t)).getID()).equals(id)){
                this.setTitleAt(t, newName);
            }
        }
    }
    
}
