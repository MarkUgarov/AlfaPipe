/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mark
 */
public class OptionPanel extends JPanel{
    
    private final JLabel programLabel;
    private final ArrayList<SingleProgramPanel> programPanels;
    
    private final JLabel clusterLabel;
    private final ClusterPanel clusterPanel;
    
    private final TabListenerBag bagOfListeners;

    private final JLabel toolLabel;
    private final JPanel toolOptionsPanel;
//    private ProgramParameterPanel assemblerParamPanel;
    
    private ArrayList<ProgramParameterPanel> toolParamPanels;
  
    public OptionPanel(int required, TabListenerBag bagOfListeners){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.programPanels = new ArrayList<>(required);
        this.bagOfListeners = bagOfListeners;
        
        this.toolParamPanels = new ArrayList<>();
        
        this.add(this.getDistinguishBar());
        
        this.clusterLabel = new JLabel(ParameterPool.LABEL_CLUSTER);
        this.clusterLabel.setPreferredSize(ParameterPool.LABEL_DIMENSION);
        this.clusterLabel.setForeground(ParameterPool.LABEL_IMPORTANCE_COLOR);
        this.add(this.clusterLabel);
        
        this.clusterPanel = new ClusterPanel();
        this.add(this.clusterPanel);
        
        this.add(this.getDistinguishBar());
        
        this.programLabel = new JLabel(ParameterPool.LABEL_PROGRAMS);
        this.programLabel.setPreferredSize(ParameterPool.LABEL_DIMENSION);
        this.programLabel.setForeground(ParameterPool.LABEL_IMPORTANCE_COLOR);
        this.add(this.programLabel);
        
        for(int i=0; i<required; i++){
            this.programPanels.add(new SingleProgramPanel(i));
            this.add(this.programPanels.get(i));
        }
        

        
        this.add(this.getDistinguishBar());
        this.toolLabel = new JLabel(ParameterPool.LABEL_TOOLS);
        this.toolLabel.setForeground(ParameterPool.LABEL_IMPORTANCE_COLOR);
        this.toolLabel.setPreferredSize(ParameterPool.LABEL_DIMENSION);
        this.add(this.toolLabel);
        
        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new BoxLayout(toolOptionsPanel, BoxLayout.Y_AXIS));
        
        this.add(this.toolOptionsPanel);
        
        this.add(this.getDistinguishBar());

    }
    
    private JPanel getDistinguishBar(){
        JPanel bar = new JPanel();
        bar.setPreferredSize(ParameterPool.DISTINGUISH_BAR_DIMENSION);
        bar.setBackground(ParameterPool.DISTINGUISH_BAR_COLOR);
        return bar;
    }
    
    public void initCluster(ParameterListener listener){
        this.clusterPanel.setParameters(listener.getInputParameters(), listener);
    }
    
    public void initSelection(int index, String name, ProgramListener listener){
        if(index < 0 || index >= this.programPanels.size()){
            System.err.println("Index out of range on "+OptionPanel.class);
        }
        else{
            this.programPanels.get(index).initSelection(name, listener);
            this.programPanels.get(index).initClusterSelection(this.bagOfListeners.getClusterSelectionListener());
            this.updateUI();
        }
    }
    
    
    /**
     * 
     * @param selectionIndex is the "row" of the selection
     * @param programName is the name of the program in that row
     * @param parameters are the parameters required for this program
     * @param listener is the listener required for setting those parameters
     * @param updateUI if the UI (graphics) should be updated 
     */
    public void selectProgram(int selectionIndex, String programName, ArrayList<InputParameter> parameters, ParameterListener listener, boolean updateUI){
        if(selectionIndex < 0 || selectionIndex >= this.programPanels.size()){
            System.err.println("Index out of range on "+OptionPanel.class);
        }
        else{
            this.programPanels.get(selectionIndex).setParameters(programName, parameters, listener);
            if(updateUI){
                this.updateUI();
            }
        }
    }


    
    
    public void addTool(String name, ArrayList<InputParameter> parameters, ParameterListener listener){
        ProgramParameterPanel tool = new ProgramParameterPanel(name, parameters, listener);
        this.toolParamPanels.add(tool);
        this.toolOptionsPanel.add(tool);
        this.updateUI();
    }

    public void disableEditing() {
        for(SingleProgramPanel progPan:this.programPanels){
            progPan.disableEditing();
        }
        for(ProgramParameterPanel tool:this.toolParamPanels){
            tool.disableEditing();
        }
    }
    
    
    
}
