/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control
;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.view.mainview.MainJFrame;
import com.mugarov.alfapipe.view.mainview.tab.Tab;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Mark
 */
public class FileSetManager {
    
    private ArrayList<SetOfFiles> sets;
    private int lastIndex;
    private final MainJFrame frame;
    
    public FileSetManager(MainJFrame frame){
        this.sets = new ArrayList<>();
        this.lastIndex = 0;
        this.frame = frame;
        Pool.LISTENER_BUTTON.setFileManager(this);
        this.add();
    }
    
    public SetOfFiles get(int index){
        return this.sets.get(index);
    }
    
    public void add(){
//        System.out.println("Adding Set(FileSetManger ready)");
        // looking for a name
        String name = "data"+lastIndex;
        int i = this.sets.size()-1;
        while (i>=0&& !this.sets.isEmpty()){
//            System.out.println("\t Searching "+name +" found on index "+i);
            
            if(this.sets.get(i).getID().equals(name) || this.sets.get(i).getName().equals(name) ){
//                System.out.println("\t"+name+ " was not valid.");
                lastIndex++;
                name = "data"+lastIndex;
                i = this.sets.size()-1;
            }
            else{
                i--;
            }
        }
        
        TabListenerBag bag = new TabListenerBag();
        Tab tab = this.frame.getTabPane().newTab(name,bag);
        SetOfFiles set = new SetOfFiles(name, tab);
        bag.setFileSet(set);
        bag.setFileManager(this);
        this.sets.add(set);
//        System.out.println("Available sets now: ");
//        for(SetOfFiles s:this.sets){
//            System.out.println("\t Set "+s.getID()+" with name "+s.getName());
//        }
//        System.out.println("Available Tabs now: ");
//        for (int j = 0; i<this.frame.getTabPane().getComponents().length; i++){
//            System.out.println("\t Tab "+ ((Tab)this.frame.getTabPane().getComponentAt(j)).getID());
//        }
    }
    
    public void remove(String id){
        int i = 0;
        boolean foundSet = false;
        boolean foundTab = false;
        while ((!foundSet || !foundTab) && i<this.sets.size()){
            if(this.sets.get(i).getID().equals(id)){
//                System.out.println("Remove set "+id);
                this.sets.remove(i);
                foundSet = true;
            }
            if(((Tab)this.frame.getTabPane().getComponentAt(i)).getID().equals(id)){
//                System.out.println("Remove tab "+id);
                this.frame.getTabPane().remove(i);
                foundTab= true;
            }
            i++;
        }
//        for(SetOfFiles s:this.sets){
//            System.out.println("\t Set "+s.getID()+" with name "+s.getName());
//        }
        if(this.sets.isEmpty()){
            this.add();
        }
    }
    
    public void rename(String id){
        System.out.println("Try to rename "+id+"  with tab count ");
        boolean foundSet = false;
        boolean foundTab = false;
        int i = 0;
        while ((!foundSet || !foundTab) && i<this.sets.size()){
            if(this.sets.get(i).getID().equals(id)){
                System.out.println("Rename set "+id);
                this.sets.get(i).applyName();
                foundSet = true;
            }
            if(((Tab)this.frame.getTabPane().getComponentAt(i)).getID().equals(id)){
                
                String name = ((Tab)this.frame.getTabPane().getComponentAt(i)).getCustumName();
                System.out.println("Rename tab "+id+ " to "+name);
                this.frame.getTabPane().setTitleAt(i, name);
                foundTab= true;
            }
            i++;
        }
    }
    
    public void run(){
        System.out.println("STARTED with "+this.sets.size()+" sets.");
        /*
        TODO: 
        execute instead of just writing
        */
        for (SetOfFiles sof: this.sets){
            (new Thread(sof)).start();
            System.out.println(sof.getPreprocessingCommand(sof.getOutputPath()));
            System.out.println(sof.getProcessingCommand(sof.getOutputPath()));
            System.out.println(sof.getAssemblerCommand(sof.getOutputPath()));
            System.out.println(sof.getReadsVsContigsCommand(sof.getOutputPath()));
            System.out.println(sof.getProdigalCommand(sof.getOutputPath()));
            for(String s:sof.getToolCommands(sof.getOutputPath())){
                System.out.println(s);
            }
        }

    }
    
    
    
    
    
    
    
}
