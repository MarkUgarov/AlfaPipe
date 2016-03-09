/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.view.mainview.tab.selection.OptionPanel;
import com.mugarov.alfapipe.view.mainview.tab.tabular.FileSetPanel;
import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.Button;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mark
 */
public class Tab extends JPanel{
    private final JButton fileChooser;
    private final JButton deleteSet;
    private final JButton choosePath;
    private final JPanel outputPanel;
    
    private final JPanel namePanel;
    private final JLabel nameLabel;
    private final JTextField nameField;
    private final JButton applyName;
    private final OptionPanel optionPanel;
    
    private final BorderLayout layout;
    
    private final JPanel northPanel;
    private final JPanel centerPanel;
    private final FileSetPanel centerScrollPanel;
    private final JPanel southPanel;
    
    private final String id;
    private final TabListenerBag listenerBag;
    
    private final JLabel outputPath;
    
    
    
    public Tab(String id, TabListenerBag bag){
        
        this.id = id;
        this.listenerBag = bag;
        this.layout = new BorderLayout();
        this.setLayout(this.layout);
        this.fileChooser = new Button(ParameterPool.BUTTON_CHOOSE_RAW_FILE_TEXT, ParameterPool.BUTTON_CHOOSE_RAW_FILE_COMMAND);
        this.deleteSet = new Button(ParameterPool.BUTTON_DELETE_SET_TEXT, ParameterPool.BUTTON_DELETE_SET_COMMAND);
        this.choosePath = new Button(ParameterPool.BUTTON_CHOOOSE_OUTPUT_TEXT, ParameterPool.BUTTON_CHOOSE_OUTPUT_COMMAND);

        
        this.namePanel = new JPanel(new BorderLayout());
        this.nameLabel = new JLabel("Name:");
        this.nameField = new JTextField(this.id);
        this.applyName = new Button(ParameterPool.BUTTON_APPLY_NAME_TEXT, ParameterPool.BUTTON_APPLY_NAME_COMMAND);
        /**
         * TODO: rewrite as soon as ListenerBag is rewritten
         */
        this.optionPanel = new OptionPanel(5, this.listenerBag);
        this.optionPanel.initSelection(0, ParameterPool.LABEL_PREPROCESSING, this.listenerBag.getPreprocessingRadioButtonListener());
        this.optionPanel.initSelection(1, ParameterPool.LABEL_PROCESSING, this.listenerBag.getProcessingRadioButtonListener());
        this.optionPanel.initSelection(2, ParameterPool.LABEL_ASSEMBLER, this.listenerBag.getAssemblerRadioListener());
        this.optionPanel.initSelection(3, ParameterPool.LABEL_COMPARISON, this.listenerBag.getReadsVsContigsListener());
        this.optionPanel.initSelection(4, ParameterPool.LABEL_ANNOTATION, this.listenerBag.getProdigalListener());
        /**
         * end rewrite
         */
        this.optionPanel.initCluster(this.listenerBag.getClusterParameterListener());
        
        this.outputPath = new JLabel();
        this.outputPanel = new JPanel();
        
        this.northPanel = new JPanel(new BorderLayout());
        this.centerPanel = new JPanel(new BorderLayout());
        this.centerScrollPanel = new FileSetPanel(this.listenerBag);
        this.southPanel = new JPanel(new BorderLayout());
        
        
        this.init();
        
    }
    
    private void init(){
        this.deleteSet.addActionListener(this.listenerBag.getButtonListener());
        this.fileChooser.addActionListener(this.listenerBag.getButtonListener());
        this.choosePath.addActionListener(this.listenerBag.getButtonListener());
        this.applyName.addActionListener(this.listenerBag.getButtonListener());
        
        this.outputPanel.setLayout(new BorderLayout());
        this.outputPanel.add(this.choosePath, BorderLayout.WEST);
        this.outputPanel.add(this.outputPath, BorderLayout.CENTER);
        
        this.namePanel.add(this.nameLabel, BorderLayout.WEST);
        this.namePanel.add(this.nameField, BorderLayout.CENTER);
        this.namePanel.add(this.applyName, BorderLayout.EAST);
        
        this.northPanel.add(this.outputPanel, BorderLayout.NORTH);
        this.northPanel.add(this.fileChooser, BorderLayout.WEST);
        this.northPanel.add(this.namePanel, BorderLayout.CENTER);
        this.northPanel.add(this.deleteSet, BorderLayout.EAST);
        this.northPanel.add(this.optionPanel, BorderLayout.SOUTH);
        
        this.centerPanel.add(this.centerScrollPanel, BorderLayout.CENTER);
        
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.centerPanel, BorderLayout.CENTER);
        this.add(this.southPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public String getID(){
        return this.id;
    }
    
    public String getCustumName(){
        return this.nameField.getText();
    }
    
    
    public void setPreprocessing(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.optionPanel.selectProgram(0, name, parameters, listener, true);
    }
    
    public void setProcessing(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
//        this.optionPanel.setProcessing(name, parameters, listener);
        this.optionPanel.selectProgram(1, name, parameters, listener, true);
    }
    
    public void setAssembler(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.optionPanel.selectProgram(2, name, parameters, listener, true);
    }
    
    public void setReadsVsContigs(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
//        this.optionPanel.setReadsVsContigs(name, parameters, listener);
        this.optionPanel.selectProgram(3, name, parameters, listener, true);
    }
    
    public void setProdigal(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
//        this.optionPanel.setProdigal(name, parameters, listener);
        this.optionPanel.selectProgram(4, name, parameters, listener, true);
    }
    
    
    public void addTool(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.optionPanel.addTool(name, parameters,listener);
    }
    
    public void addFile(String id, String name, SingleFileListener listener){
        this.centerScrollPanel.addFile(id, name, listener);
    }
    
    public void addPaired(String mainFileID, String pairedName){
        this.centerScrollPanel.addPaired(mainFileID, pairedName);
    }
    
    public void deleteFile(String id){
        this.centerScrollPanel.deleteFile(id);
    }
    
    public void selectToolForAll(String toolName){
        this.centerScrollPanel.selectToolForAll(toolName);
    }
    
    public void unselectToolForAll(String toolName){
        this.centerScrollPanel.unselectToolForAll(toolName);
    }
    
    public void setOutputPath(String path){
        this.outputPath.setText(path);
    }
    
    public void setValidation(String id, boolean fileValid, ArrayList<String> validTools){
        this.centerScrollPanel.setValidation(id, fileValid, validTools);
    }

    public void disableEditing() {
        this.optionPanel.disableEditing();
        this.centerScrollPanel.disableEditing();
        this.fileChooser.setEnabled(false);
        this.applyName.setEnabled(false);
        this.choosePath.setEnabled(false);
        this.deleteSet.setEnabled(false);
        this.updateUI();
    }
    
    public void reenableSetRemoval(){
        this.deleteSet.setEnabled(true);
    }
    
    public void setFileProgressed(String id, boolean success){
        this.centerScrollPanel.setProgressed(id, success);
    }

    public void setToolProgressed(String fileID, ArrayList<String> toolIDs, ArrayList<Boolean> toolSuccess){
        this.centerScrollPanel.setToolProgressed(fileID, toolIDs, toolSuccess);
    }
    
    public void setAllProgressed(boolean success){
        this.centerScrollPanel.setAllProgressed(success);
    }
    
}
