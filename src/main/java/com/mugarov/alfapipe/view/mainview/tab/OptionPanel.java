/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab;

import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
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
    
    private final JPanel assemblerOptions;
    private final JPanel toolOptions;
    private ProgramParameterPanel assemblerParamPanel;
    private ArrayList<ProgramParameterPanel> toolParamPanels;
  
    public OptionPanel(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.assemblerOptions = new JPanel();
        this.toolOptions = new JPanel();
        this.add(this.assemblerOptions);
        this.add(this.toolOptions);
        this.assemblerOptions.setLayout(new BoxLayout(assemblerOptions, BoxLayout.Y_AXIS));
        this.toolOptions.setLayout(new BoxLayout(toolOptions, BoxLayout.Y_AXIS));
        this.assemblerParamPanel = null;
        this.toolParamPanels = new ArrayList<>();
    }
    
    public void setAssembler(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        this.assemblerParamPanel = new ProgramParameterPanel(name, parameters, listener);
        this.assemblerOptions.removeAll();
        this.assemblerOptions.add(this.assemblerParamPanel);
        this.updateUI();
        
    }
    
    public void addProgram(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        ProgramParameterPanel tool = new ProgramParameterPanel(name, parameters, listener);
        this.toolParamPanels.add(tool);
        this.toolOptions.add(tool);
        this.updateUI();
    }

    void disableEditing() {
        this.assemblerParamPanel.disableEditing();
        for(ProgramParameterPanel tool:this.toolParamPanels){
            tool.disableEditing();
        }
    }
    
    
    
}
