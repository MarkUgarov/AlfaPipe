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
        Tab ret = new Tab(id, bag);
        this.add(id,ret);
        this.setSelectedComponent(this.getComponent(this.getComponentCount()-1));
        return ret;
    }
    
    public void renameTab(String id, String newName){
        for(int t=0;t< this.getTabCount(); t++){
            if ((((Tab)this.getTabComponentAt(t)).getID()).equals(id)){
                this.setTitleAt(t, newName);
            }
        }
    }
    
}
