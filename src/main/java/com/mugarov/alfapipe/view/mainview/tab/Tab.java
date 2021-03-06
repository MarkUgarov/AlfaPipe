/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.view.optics.OpticSplitPane;
import com.mugarov.alfapipe.view.mainview.tab.selection.OptionPanel;
import com.mugarov.alfapipe.view.mainview.tab.tabular.FileSetPanel;
import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramPanelListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.optics.OpticButton;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.optics.OpticScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Mark
 */
public class Tab extends OpticPane{
    private final OpticButton fileChooser;
    private final OpticButton deleteSet;
    private final OpticButton choosePath;
    private final OpticButton cancelSet;
    private final OpticPane outputPanel;
    
    private final OpticPane namePanel;
    private final JLabel nameLabel;
    private final JTextField nameField;
    private final OpticButton applyName;
    private final OptionPanel optionPanel;
    
    private final BorderLayout layout;
    
    private final OpticPane northPanel;
    private final OpticPane configPanel;
    private final OpticScrollPane configScrollPane;
    private final OpticPane centerPanel;
    private final FileSetPanel fileScrollPanel;
    private final OpticPane southPanel;
    
    private final String id;
    private final TabListenerBag listenerBag;
    
    private final JLabel outputPath;
    private OpticSplitPane mainPanel;
    
    
    public Tab(String id, TabListenerBag bag){
        super.setOpaque();
        super.drawBackgroundImage(true);
        this.id = id;
        this.listenerBag = bag;
        this.layout = new BorderLayout();
        this.setLayout(this.layout);
        this.fileChooser = new OpticButton(ParameterPool.BUTTON_CHOOSE_INPUT_FILE_TEXT, ParameterPool.BUTTON_CHOOSE_INPUT_FILE_COMMAND);
        this.deleteSet = new OpticButton(ParameterPool.BUTTON_DELETE_SET_TEXT, ParameterPool.BUTTON_DELETE_SET_COMMAND);
        this.choosePath = new OpticButton(ParameterPool.BUTTON_CHOOOSE_OUTPUT_TEXT, ParameterPool.BUTTON_CHOOSE_OUTPUT_COMMAND);
        this.cancelSet = new OpticButton(ParameterPool.BUTTON_CANCEL_SET_TEXT, ParameterPool.BUTTON_CANCEL_SET_COMMAND);
        
        this.namePanel = new OpticPane(new BorderLayout());
        this.nameLabel = new JLabel("Name:");
        this.nameField = new JTextField(this.id);
        this.applyName = new OpticButton(ParameterPool.BUTTON_APPLY_NAME_TEXT, ParameterPool.BUTTON_APPLY_NAME_COMMAND);
        
        ArrayList<ProgramPanelListener> programListeners = this.listenerBag.getProgramListeners();
        this.optionPanel = new OptionPanel(programListeners.size(), this.listenerBag);
        for (ProgramPanelListener pl:programListeners){
            this.optionPanel.initSelection(pl.getIndex(), pl.getName(), pl);
        }

        this.optionPanel.initCluster(this.listenerBag.getClusterParameterListener(), this.listenerBag.getClusterSelectionListener());
        
        this.outputPath = new JLabel();
        this.outputPanel = new OpticPane();
        
        this.northPanel = new OpticPane(new BorderLayout());

        this.configPanel = new OpticPane(new BorderLayout());
        this.configScrollPane = new OpticScrollPane(this.configPanel);
        this.centerPanel = new OpticPane(new BorderLayout());
        this.fileScrollPanel = new FileSetPanel(this.listenerBag);
        this.southPanel = new OpticPane(new BorderLayout());
        
        
        this.init();
        
    }
    
    private void init(){
        this.deleteSet.addActionListener(this.listenerBag.getButtonListener());
        this.fileChooser.addActionListener(this.listenerBag.getButtonListener());
        this.choosePath.addActionListener(this.listenerBag.getButtonListener());
        this.cancelSet.addActionListener(this.listenerBag.getButtonListener());
        this.applyName.addActionListener(this.listenerBag.getButtonListener());
        
        this.outputPanel.setLayout(new BorderLayout());
        this.outputPanel.add(this.choosePath.inSurroundingPanel(), BorderLayout.WEST);
        this.outputPanel.add(this.outputPath, BorderLayout.CENTER);
        
        this.namePanel.add(this.nameLabel, BorderLayout.WEST);
        this.namePanel.add(this.nameField, BorderLayout.CENTER);
        this.namePanel.add(this.applyName.inSurroundingPanel(), BorderLayout.EAST);
        
        this.northPanel.add(this.outputPanel, BorderLayout.SOUTH);
        this.northPanel.add(this.fileChooser.inSurroundingPanel(), BorderLayout.WEST);
        this.northPanel.add(this.namePanel, BorderLayout.CENTER);
        
        OpticPane northEastPane = new OpticPane(new BorderLayout());
        northEastPane.add(this.deleteSet.inSurroundingPanel(), BorderLayout.EAST);
        northEastPane.add(this.cancelSet.inSurroundingPanel(), BorderLayout.CENTER);
        this.cancelSet.setVisible(false);
        this.northPanel.add(northEastPane, BorderLayout.EAST);
        
        this.configPanel.add(this.optionPanel, BorderLayout.SOUTH);
        
        this.mainPanel = new OpticSplitPane(this.configScrollPane, this.fileScrollPanel);
//        this.mainPanel = new SplittedPane(new OpticScrollPane(), new OpticScrollPane());
        
        this.centerPanel.add(this.mainPanel, BorderLayout.CENTER);
        
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.mainPanel, BorderLayout.CENTER);
        this.add(this.southPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public String getID(){
        return this.id;
    }
    
    public String getCustumName(){
        return this.nameField.getText();
    }
    
    public void selectProgram(int index, String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.optionPanel.selectProgram(index, name, parameters, listener, true);
    }
    
    public void disableCluster(int index, boolean isTool) {
        this.optionPanel.disableCluster(index, isTool);
    }
    
    public void reenableCluster(int inded, boolean isTool){
        this.optionPanel.reenableCluster(inded, isTool);
    }
    
    
    public void addTool(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.optionPanel.addTool(name, parameters,listener);
    }
    
    public void addFile(String id, String name, SingleFileListener listener){
        this.fileScrollPanel.addFile(id, name, listener);
    }
    
    public void addPaired(String mainFileID, String pairedName){
        this.fileScrollPanel.addPaired(mainFileID, pairedName);
    }
    
    public void deleteFile(String id){
        this.fileScrollPanel.deleteFile(id);
    }
    
    public void selectToolForAll(String toolName){
        this.fileScrollPanel.selectToolForAll(toolName);
    }
    
    public void unselectToolForAll(String toolName){
        this.fileScrollPanel.unselectToolForAll(toolName);
    }
    
    public void setOutputPath(String path){
        this.outputPath.setText(path);
    }
    
    public void setValidation(String id, boolean fileValid, ArrayList<String> validTools){
        this.fileScrollPanel.setValidation(id, fileValid, validTools);
    }

    public void disableEditing() {
        this.optionPanel.disableEditing();
        this.fileScrollPanel.disableEditing();
        this.fileChooser.setEnabled(false);
        this.applyName.setEnabled(false);
        this.choosePath.setEnabled(false);
        this.deleteSet.setEnabled(false);
        this.cancelSet.setVisible(true);
        this.updateUI();
    }
    
    public void reenableSetRemoval(){
        this.deleteSet.setEnabled(true);
        this.cancelSet.setVisible(false);
        this.updateUI();
    }
    
    public void reenablePartially(){
        this.fileChooser.setEnabled(true);
        this.deleteSet.setEnabled(true);
        this.cancelSet.setVisible(false);
    }
    
    public void setFileProgressed(String id, boolean success){
        this.fileScrollPanel.setProgressed(id, success);
    }

    public void setToolProgressed(String fileID, ArrayList<String> toolIDs, ArrayList<Boolean> toolSuccess){
        this.fileScrollPanel.setToolProgressed(fileID, toolIDs, toolSuccess);
    }
    
    public void setAllProgressed(boolean success){
        this.fileScrollPanel.setAllProgressed(success);
    }
    
    public void recalculateSize(){
        this.mainPanel.resetToPreferredSizes();
    }

    public void selectAllClusterBoxes(boolean selected){
        this.optionPanel.selectAllClusterBoxes(selected);
    }
     
    
}
