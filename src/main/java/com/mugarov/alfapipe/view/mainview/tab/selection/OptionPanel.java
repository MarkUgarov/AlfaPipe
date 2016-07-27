/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.selection;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.control.listeners.tabrelated.cluster.ClusterSelectionListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.parameters.ParameterListener;
import com.mugarov.alfapipe.control.listeners.tabrelated.radiobuttons.ProgramListener;
import com.mugarov.alfapipe.model.ParameterPool;
import com.mugarov.alfapipe.model.datatypes.InputParameter;
import com.mugarov.alfapipe.view.optics.OpticPane;
import com.mugarov.alfapipe.view.mainview.tab.parameters.ProgramParameterPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Mark
 */
public class OptionPanel extends OpticPane{
    

    private final ArrayList<SingleProgramPanel> programPanels;
    
    private final ClusterPanel clusterPanel;
    
    private final TabListenerBag bagOfListeners;


    private final OpticPane toolOptionsPanel;
    
    private ArrayList<ProgramParameterPanel> toolParamPanels;
  
    public OptionPanel(int required, TabListenerBag bagOfListeners){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setTransparent();
        this.programPanels = new ArrayList<>(required);
        this.bagOfListeners = bagOfListeners;
        this.toolParamPanels = new ArrayList<>();
        

        if(ParameterPool.CLUSTER_ENABLE){
            this.add(this.getDistinguishBar());
            this.add(this.getDistinguishLabelInPanel(ParameterPool.LABEL_CLUSTER));

            this.clusterPanel = new ClusterPanel();
            this.add(this.clusterPanel);
        }
        
        this.add(this.getDistinguishBar());
        this.add(this.getDistinguishLabelInPanel(ParameterPool.LABEL_PROGRAMS));
        
        for(int i=0; i<required; i++){
            this.programPanels.add(new SingleProgramPanel(i));
            this.add(this.programPanels.get(i));
        }

        this.add(this.getDistinguishBar());
        this.add(this.getDistinguishLabelInPanel(ParameterPool.LABEL_TOOLS));
        
        this.toolOptionsPanel = new OpticPane();
        this.toolOptionsPanel.setLayout(new BoxLayout(toolOptionsPanel, BoxLayout.Y_AXIS));
        
        this.add(this.toolOptionsPanel);
        
        this.add(this.getDistinguishBar());

    }
    
    private OpticPane getDistinguishLabelInPanel(String labeltext){
        JLabel label = new JLabel(labeltext, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setPreferredSize(ParameterPool.LABEL_DIMENSION);
        label.setForeground(ParameterPool.LABEL_IMPORTANCE_COLOR);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        label.setBorder( BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
        
        OpticPane ret = new OpticPane();
        ret.add(label);
        return ret;
    }
    
    private OpticPane getDistinguishBar(){
        OpticPane bar = new OpticPane(false);
        bar.drawBackgroundImage(false);
        bar.setPreferredSize(ParameterPool.DISTINGUISH_BAR_DIMENSION);
        bar.setBackground(ParameterPool.DISTINGUISH_BAR_COLOR);
        return bar;
    }
    
    public void initCluster(ParameterListener parameterListener, ClusterSelectionListener selectionListener){
        this.clusterPanel.setParameters(parameterListener.getInputParameters(), parameterListener, selectionListener);
    }
    
    public void initSelection(int index, String name, ProgramListener listener){
        if(index < 0 || index >= this.programPanels.size()){
            System.err.println("Index out of range on "+OptionPanel.class);
        }
        else{
            this.programPanels.get(index).initSelection(name, listener);
            this.programPanels.get(index).initClusterSelection(this.bagOfListeners.getClusterSelectionListener());
        }
        this.updateUI();
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
        tool.addClusterBox(this.bagOfListeners.getClusterSelectionListener(), this.toolParamPanels.size()-1, false);
        this.updateUI();
    }

    public void disableEditing() {
        if(this.clusterPanel != null){
            this.clusterPanel.disableEditing();
        }
        
        for(SingleProgramPanel progPan:this.programPanels){
            progPan.disableEditing();
        }
        for(ProgramParameterPanel tool:this.toolParamPanels){
            tool.disableEditing();
        }
    }

    public void disableCluster(int index, boolean isTool) {
        if(isTool && index<this.toolParamPanels.size()){
            this.toolParamPanels.get(index).disableClusterByPresettings();
        }
        else if(index<this.programPanels.size()){
            this.programPanels.get(index).disableClusterByPresettings();
        }
    }
    
    public void reenableCluster(int index, boolean isTool){
        if(isTool && index<this.toolParamPanels.size()){
            this.toolParamPanels.get(index).reenableCluster();
        }
        else if(index<this.programPanels.size()){
            this.programPanels.get(index).reenableCluster();
        }
    }
    
    public void selectAllClusterBoxes(boolean selected){
        for(SingleProgramPanel prog:this.programPanels){
            prog.setClusterSelected(selected);
        }
        for(ProgramParameterPanel par:this.toolParamPanels){
            par.setClusterBoxSelected(selected);
        }
    }
    
    
    
}
