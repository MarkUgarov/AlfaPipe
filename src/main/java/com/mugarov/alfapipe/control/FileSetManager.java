/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control
;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.datatypes.ProgramSet;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.fabrics.ClusterParameterFabric;
import com.mugarov.alfapipe.view.mainview.MainJFrame;
import com.mugarov.alfapipe.view.mainview.tab.Tab;
import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class FileSetManager {
    
    private final ArrayList<SetOfFiles> sets;
    private int lastIndex;
    private final MainJFrame frame;
    private ArrayList<Thread> startedThreads;
    
    public FileSetManager(MainJFrame frame){
        this.sets = new ArrayList<>();
        this.lastIndex = 0;
        this.frame = frame;
        this.startedThreads = new ArrayList<>();
        ComponentPool.LISTENER_BUTTON.setFileManager(this);
        this.add();
    }
    
    public SetOfFiles get(int index){
        return this.sets.get(index);
    }
    
    public void add(){
        // determine the name
        String name = "data"+lastIndex;
        int i = this.sets.size()-1;
        while (i>=0&& !this.sets.isEmpty()){
            
            if(this.sets.get(i).getID().equals(name) || this.sets.get(i).getName().equals(name) ){
               // name was not valid
                lastIndex++;
                name = "data"+lastIndex;
                i = this.sets.size()-1;
            }
            else{
                i--;
            }
        }
        ProgramSet clusterSet = (new ClusterParameterFabric()).getSet();
        TabListenerBag bag = new TabListenerBag(clusterSet);
        
        Tab tab = this.frame.getTabPane().newTab(name,bag);
        SetOfFiles set = new SetOfFiles(name, tab, clusterSet);
        bag.setFileSet(set);
        bag.setFileManager(this);
        this.sets.add(set);

    }
    
    public void remove(String id){
        int i = 0;
        boolean foundSet = false;
        boolean foundTab = false;
        while ((!foundSet || !foundTab) && i<this.sets.size()){
            if(this.sets.get(i).getID().equals(id)){
                this.sets.remove(i);
                foundSet = true;
            }
            if(((Tab)this.frame.getTabPane().getComponentAt(i)).getID().equals(id)){
                this.frame.getTabPane().remove(i);
                foundTab= true;
            }
            i++;
        }

        if(this.sets.isEmpty()){
            this.add();
        }
    }
    
    public void rename(String id){
//        System.out.println("Try to rename "+id+"  with tab count ");
        boolean foundSet = false;
        boolean foundTab = false;
        int i = 0;
        while ((!foundSet || !foundTab) && i<this.sets.size()){
            if(this.sets.get(i).getID().equals(id)){
//                System.out.println("Rename set "+id);
                this.sets.get(i).applyName();
                foundSet = true;
            }
            if(((Tab)this.frame.getTabPane().getComponentAt(i)).getID().equals(id)){
                
                String name = ((Tab)this.frame.getTabPane().getComponentAt(i)).getCustumName();
//                System.out.println("Rename tab "+id+ " to "+name);
                this.frame.getTabPane().setTitleAt(i, name);
                foundTab= true;
            }
            i++;
        }
    }
    
    public void run(){
        System.out.println("STARTED with "+this.sets.size()+" sets.");
        for (SetOfFiles sof: this.sets){
            if(!sof.runs()){
                this.startedThreads.add( new Thread(sof, sof.getID()));
                this.startedThreads.get(this.startedThreads.size()-1).start();
            }
        }
    }
    
    public void cancel(String ID){
        Thread t;
        int i = 0;
        for(SetOfFiles sof:this.sets){
            if(sof.getID().equals(ID)){
                sof.interrupt();
            }
        }
        while(i<this.startedThreads.size()){
            t= this.startedThreads.get(i);
            if(t.isDaemon() || t.isInterrupted()){
                this.startedThreads.remove(i);
            }
            else if(t.getName().equals(ID)){
                t.interrupt();
                this.startedThreads.remove(i);
            }
            else{
                i++;
            }
        }
    }
    
    public void cancelAll(){
        for(SetOfFiles sof: this.sets){
            sof.interrupt();
        }
        for(Thread t: this.startedThreads){
            t.interrupt();
        }
        this.startedThreads = new ArrayList<>();
    }
    
}
