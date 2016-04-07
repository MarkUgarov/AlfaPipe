/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.tabular;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.view.optics.OpticPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 *
 * @author Mark
 */
public class TopPanel extends OpticPane{
    
    private final TabListenerBag listenerBag;

    private OpticPane leftPanel ;
    private JLabel filename;
    private OpticPane rightPanel;
    private final BorderLayout  layout;
    private final ArrayList<JCheckBox> boxes;
    
    
    public TopPanel(TabListenerBag bag){
        super();
        this.setDoubleBuffered(true);
        this.listenerBag = bag;
        double width = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width)*0.8;
        this.setPreferredSize( new Dimension( (int) width, 40));
         
        this.layout = new BorderLayout();
        this.setLayout(this.layout);
        
        this.boxes = new ArrayList<>();

        this.createLeftPanel();
        this.creatRightPanel();
        
        this.add(this.leftPanel, BorderLayout.WEST); 
        this.add(this.rightPanel, BorderLayout.EAST);
    }
    
    private void createLeftPanel(){
            this.leftPanel = new OpticPane();
            this.leftPanel.setLayout(new BorderLayout());
            this.filename = new JLabel("Selected Files:");
            this.leftPanel.add(filename, BorderLayout.EAST);
        }
    
     private void creatRightPanel(){
            this.rightPanel = new OpticPane();
             for(String tool: ComponentPool.GENERTATOR_TOOLS.getAvailableNames()){
                JCheckBox toolBox= new JCheckBox(tool);
                toolBox.addItemListener(this.listenerBag.getToolListener());
                toolBox.setSelected(true);
                this.boxes.add(toolBox);
                this.rightPanel.add(toolBox);
            }
        }
    
     public void disableEditing(){
        for(JCheckBox box:this.boxes){
            box.setEnabled(false);
        }
     }
    
}
