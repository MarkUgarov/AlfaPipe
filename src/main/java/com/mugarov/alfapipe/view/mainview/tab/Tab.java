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
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramPanelListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.Button;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    private final JPanel configPanel;
    private final JScrollPane configScrollPane;
    private final JPanel centerPanel;
    private final FileSetPanel fileScrollPanel;
    private final JPanel southPanel;
    
    private final String id;
    private final TabListenerBag listenerBag;
    
    private final JLabel outputPath;
    private SplittedPane mainPanel;
    
    
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
        
        ArrayList<ProgramPanelListener> programListeners = this.listenerBag.getProgramListeners();
        this.optionPanel = new OptionPanel(programListeners.size(), this.listenerBag);
        for (ProgramPanelListener pl:programListeners){
            this.optionPanel.initSelection(pl.getIndex(), pl.getName(), pl);
        }

        this.optionPanel.initCluster(this.listenerBag.getClusterParameterListener());
        
        this.outputPath = new JLabel();
        this.outputPanel = new JPanel();
        
        this.northPanel = new JPanel(new BorderLayout());

        this.configPanel = new JPanel(new BorderLayout());
        this.configScrollPane = new JScrollPane(this.configPanel);
        this.centerPanel = new JPanel(new BorderLayout());
        this.fileScrollPanel = new FileSetPanel(this.listenerBag);
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
        
        this.configPanel.add(this.optionPanel, BorderLayout.SOUTH);
        
        this.mainPanel = new SplittedPane(this.configScrollPane, this.fileScrollPanel);
        
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
        this.updateUI();
    }
    
    public void reenableSetRemoval(){
        this.deleteSet.setEnabled(true);
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
    
}
