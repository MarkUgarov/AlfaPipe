/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.AssemblerListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.PreprocessingListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProcessingListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProdigalListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ReadsVsContigsListener;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Mark
 */
public class OptionPanel extends JPanel{
    
    private final JPanel preprocessingPanel;
    private final JPanel processingPanel;
    private final JPanel assemblerPanel;
    private final JPanel readsVsContigsPanel;
    private final JPanel prodigalPanel;
    
    private SelectionPanel preprocessingSelection;
    private SelectionPanel processingSelection;
    private SelectionPanel assemblySelection;
    private SelectionPanel readsVsContigsSelection;
    private SelectionPanel prodigalSelection;
    
    
    private final JPanel assemblerParameters;
    
    private final JPanel toolOptionsPanel;
    private ProgramParameterPanel assemblerParamPanel;
    
    private ArrayList<ProgramParameterPanel> toolParamPanels;
  
    public OptionPanel(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.preprocessingSelection = null;
        this.processingSelection = null;
        this.assemblySelection = null;
        this.assemblerParamPanel = null;
        this.readsVsContigsSelection = null;
        this.prodigalSelection = null;
        this.toolParamPanels = new ArrayList<>();
        
        this.preprocessingPanel = new JPanel();
        this.preprocessingPanel.setLayout(new BoxLayout(this.preprocessingPanel, BoxLayout.Y_AXIS));
        this.add(this.preprocessingPanel);
        
        this.processingPanel = new JPanel();
        this.processingPanel.setLayout(new BoxLayout(this.processingPanel, BoxLayout.Y_AXIS));
        this.add(this.processingPanel);
        
        this.assemblerPanel = new JPanel();
        this.assemblerPanel.setLayout(new BoxLayout(this.assemblerPanel, BoxLayout.Y_AXIS));
        this.add(this.assemblerPanel);
        this.assemblerParameters = new JPanel();
        this.assemblerParameters.setLayout(new BoxLayout(assemblerParameters, BoxLayout.Y_AXIS));
        this.assemblerPanel.add(this.assemblerParameters);
        
        this.readsVsContigsPanel = new JPanel();
        this.readsVsContigsPanel.setLayout(new BoxLayout(this.readsVsContigsPanel, BoxLayout.Y_AXIS));
        this.add(this.readsVsContigsPanel);
        
        this.prodigalPanel = new JPanel();
        this.prodigalPanel.setLayout(new BoxLayout(this.prodigalPanel, BoxLayout.Y_AXIS));
        this.add(this.prodigalPanel);
        
        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new BoxLayout(toolOptionsPanel, BoxLayout.Y_AXIS));
        this.add(this.toolOptionsPanel);

    }
    
    public void initPreprocessingSelection(PreprocessingListener listener){
       this.preprocessingSelection = new SelectionPanel("Preprocessing",listener,listener.getValidSelections());
       this.preprocessingPanel.removeAll();
       this.preprocessingPanel.add(this.preprocessingSelection);
       this.updateUI();
    }
    
    public void initProcessingSelection(ProcessingListener listener){
       this.processingSelection = new SelectionPanel("Processing",listener,listener.getValidSelections());
       this.processingPanel.removeAll();
       this.processingPanel.add(this.processingSelection);
       this.updateUI();
    }
    
    public void initAssemblerSelection(AssemblerListener listener){
       this.assemblySelection = new SelectionPanel("Assembler",listener,listener.getValidSelections());
       this.assemblerPanel.removeAll();
       this.assemblerPanel.add(this.assemblySelection);
       this.assemblerPanel.add(this.assemblerParameters);
       this.updateUI();
    }
    
    public void initReadsVsContigsSelection(ReadsVsContigsListener listener){
       this.readsVsContigsSelection = new SelectionPanel("ReadsVsContigs",listener,listener.getValidSelections());
       this.readsVsContigsPanel.removeAll();
       this.readsVsContigsPanel.add(this.readsVsContigsSelection);
       this.updateUI();
    }
    
    public void initProdigalSelection(ProdigalListener listener){
       this.prodigalSelection = new SelectionPanel("Prodigal",listener,listener.getValidSelections());
       this.prodigalPanel.removeAll();
       this.prodigalPanel.add(this.prodigalSelection);
       this.updateUI();
    }
    
    
    public void setAssembler(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.assemblerParameters.removeAll();
        this.assemblerParamPanel = new ProgramParameterPanel(name, parameters, listener);
        if(!this.assemblerParamPanel.isEmpty()){
            this.assemblerParameters.add(this.assemblerParamPanel);
        }
        
        this.updateUI();
    }
   
    
    public void addProgram(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        ProgramParameterPanel tool = new ProgramParameterPanel(name, parameters, listener);
        this.toolParamPanels.add(tool);
        this.toolOptionsPanel.add(tool);
        this.updateUI();
    }

    void disableEditing() {
        this.assemblerParamPanel.disableEditing();
        for(ProgramParameterPanel tool:this.toolParamPanels){
            tool.disableEditing();
        }
    }
    
    
    
}
