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
import com.mugarov.alfapipe.model.Pool;
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
    
    private final SingleProgramPanel preprocessingPanel;
    private final SingleProgramPanel processingPanel;
    private final SingleProgramPanel assemblerPanel;
    private final SingleProgramPanel readsVsContigsPanel;
    private final SingleProgramPanel prodigalPanel;
    
    
    private final JPanel toolOptionsPanel;
//    private ProgramParameterPanel assemblerParamPanel;
    
    private ArrayList<ProgramParameterPanel> toolParamPanels;
  
    public OptionPanel(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        

        this.toolParamPanels = new ArrayList<>();
        
        this.preprocessingPanel = new SingleProgramPanel();
        this.add(this.preprocessingPanel);
        
        this.processingPanel = new SingleProgramPanel();
        this.add(this.processingPanel);
        
        this.assemblerPanel = new SingleProgramPanel();
        this.add(this.assemblerPanel);
        
        this.readsVsContigsPanel = new SingleProgramPanel();
        this.add(this.readsVsContigsPanel);
        
        this.prodigalPanel = new SingleProgramPanel();
        this.add(this.prodigalPanel);
        
        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new BoxLayout(toolOptionsPanel, BoxLayout.Y_AXIS));
        this.add(this.toolOptionsPanel);

    }
    
    public void initPreprocessingSelection(PreprocessingListener listener){
       this.preprocessingPanel.initSelection(Pool.LABEL_PREPROCESSING, listener);
       this.updateUI();
    }
    
    public void initProcessingSelection(ProcessingListener listener){
       this.processingPanel.initSelection(Pool.LABEL_PROCESSING, listener);
       this.updateUI();
    }
    
    public void initAssemblerSelection(AssemblerListener listener){
       this.assemblerPanel.initSelection(Pool.LABEL_ASSEMBLER, listener);
       this.updateUI();
    }
    
    public void initReadsVsContigsSelection(ReadsVsContigsListener listener){
       this.readsVsContigsPanel.initSelection(Pool.LABEL_READS_VS_CONTIGS, listener);
       this.updateUI();
    }
    
    public void initProdigalSelection(ProdigalListener listener){
       this.prodigalPanel.initSelection(Pool.LABEL_PRODIGAL, listener);
       this.updateUI();
    }

    public void setPreprocessing(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.preprocessingPanel.setParameters(name, parameters, listener);
        this.updateUI();
    }
    
    public void setProcessing(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.preprocessingPanel.setParameters(name, parameters, listener);
        this.updateUI();
    }
    
    public void setAssembler(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.assemblerPanel.setParameters(name, parameters, listener);
        this.updateUI();
    }
    
    public void setReadsVsContigs(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.readsVsContigsPanel.setParameters(name, parameters, listener);
        this.updateUI();
    }
    public void setProdigal(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.prodigalPanel.setParameters(name, parameters, listener);
        this.updateUI();
    }
    
    
    public void addProgram(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        ProgramParameterPanel tool = new ProgramParameterPanel(name, parameters, listener);
        this.toolParamPanels.add(tool);
        this.toolOptionsPanel.add(tool);
        this.updateUI();
    }

    void disableEditing() {
        this.preprocessingPanel.disableEditing();
        this.processingPanel.disableEditing();
        this.assemblerPanel.disableEditing();
        this.readsVsContigsPanel.disableEditing();
        this.prodigalPanel.disableEditing();
        for(ProgramParameterPanel tool:this.toolParamPanels){
            tool.disableEditing();
        }
    }
    
    
    
}
