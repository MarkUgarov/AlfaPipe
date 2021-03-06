/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.tabular;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.optics.OpticScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

/**
 *
 * @author Mark
 */
public class FileSetPanel extends OpticScrollPane{
    
    private final OpticPane top = new OpticPane();
    private TopPanel topLine;
    private final OpticPane scrollable;
    private final JTextArea text = new JTextArea(6,30);
    private final Component glue;
    private final ArrayList<SingleFilePanel> files;
      
    private final TabListenerBag listenerBag;
    
    
    public FileSetPanel(TabListenerBag bag){
        super();
        this.listenerBag = bag;
        this.setColumnHeaderView(this.top);
        this.getColumnHeader().setBackground(ParameterPool.COLOR_BACKGROUND_SECOND);
        this.scrollable = new OpticPane();
        this.setViewportView(scrollable);
        BoxLayout boxLayout =new BoxLayout(this.scrollable,BoxLayout.PAGE_AXIS);
        
        this.scrollable.setLayout(boxLayout);
        this.glue = Box.createVerticalGlue();
        this.scrollable.add(glue);
        this.files = new ArrayList<>();
        this.topLine = null;
                
        this.initTop();
//        this.addFile("Test","Test", new SingleFileListener());
        this.setVisible(true);
    }
    
    /**
     *
     * @param id should be the filepath
     * @param name should be the filename
     * @param listener should be an individual Listener for a single file
     */
    public void addFile(String id, String name, SingleFileListener listener){
        SingleFilePanel pan = new SingleFilePanel(id, name, listener);
        this.files.add(pan);
        this.scrollable.add(pan);
        this.updateUI();
    }
    
    public void addPaired(String mainFileID, String pairedFileName){
        SingleFilePanel p = null;
        for(int i = 0;i<this.scrollable.getComponentCount();i++){
            if(!this.scrollable.getComponent(i).equals(this.glue)){
                p= (SingleFilePanel)this.scrollable.getComponent(i);
                if(mainFileID.equals(p.getID())){
                    p.addPaired(pairedFileName);
                }
            }
        }
        this.updateUI();
    }
    
    private void initTop(){
        this.topLine = new TopPanel(this.listenerBag);
        this.top.add(this.topLine);
    }
    
    public void deleteFile(String id){
        SingleFilePanel p = null;
        for(int i = 0;i<this.scrollable.getComponentCount();i++){
            if(!this.scrollable.getComponent(i).equals(this.glue)){
                p= (SingleFilePanel)this.scrollable.getComponent(i);
                if(id.equals(p.getID())){
                    this.scrollable.remove(i);
                }
            }
        }
        this.updateUI();
    }
    
   public void selectToolForAll(String toolName){
       SingleFilePanel p = null;
        for(int i = 0;i<this.scrollable.getComponentCount();i++){
            if(!this.scrollable.getComponent(i).equals(this.glue)){
                ((SingleFilePanel)this.scrollable.getComponent(i)).selectTool(toolName);
            }
        }
   }
   public void unselectToolForAll(String toolName){
       SingleFilePanel p = null;
        for(int i = 0;i<this.scrollable.getComponentCount();i++){
            if(!this.scrollable.getComponent(i).equals(this.glue)){
                ((SingleFilePanel)this.scrollable.getComponent(i)).unselectTool(toolName);
            }
        }
   }
   
   public void setValidation(String id, boolean valid, ArrayList<String> validTools){
        for(SingleFilePanel p: this.files){
            if(id.equals(p.getID())){
                    p.setValidation(valid, validTools);
            }
        }
        this.updateUI();
   }
   
   private Image getBackgroundImage() {
        File imgFile = new File("graphic/LogoCut.png");
        if(!imgFile.exists()){
            File no = new File((new File(".")).getParent());
            System.err.println("Image not found in "+no.getAbsolutePath());
            return null;
        }
         
        Image image = null;
        try {
            image = ImageIO.read(imgFile);
//            System.out.println("Image set.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void disableEditing() {
        this.topLine.disableEditing();
        SingleFilePanel p;
        for(int i = 0;i<this.scrollable.getComponentCount();i++){
            if(!this.scrollable.getComponent(i).equals(this.glue)){
                p= (SingleFilePanel)this.scrollable.getComponent(i);
                p.disableEditing();
            }
        }
    }

    public void setProgressed(String id, boolean success) {
        for(SingleFilePanel file:this.files){
            if(file.getID().equals(id)){
                file.setProgressed(success);
            }
        }
    }
    
    public void setToolProgressed(String fileID, ArrayList<String> toolIDs, ArrayList<Boolean> toolSuccess){
       for(SingleFilePanel file:this.files){
            if(file.getID().equals(fileID)){
                file.setToolsProgressed(toolIDs, toolSuccess);
            }
        }
    }

    public void setAllProgressed(boolean success) {
        Color c = (success?ParameterPool.COLOR_SUCCESS:ParameterPool.COLOR_FAILURE);
        this.top.setBackground(c);
        this.scrollable.setBackground(c);
        this.updateUI();
    }
   
}
