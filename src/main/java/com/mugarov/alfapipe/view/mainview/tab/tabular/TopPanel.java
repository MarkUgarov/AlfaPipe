/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.tabular;

import com.mugarov.alfapipe.control.listeners.tabrelated.TabListenerBag;
import com.mugarov.alfapipe.model.ComponentPool;
import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mark
 */
public class TopPanel extends JPanel{
    
    private final TabListenerBag listenerBag;

    private JPanel leftPanel ;
    private JLabel filename;
    private JPanel rightPanel;
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
            this.leftPanel = new JPanel();
            this.leftPanel.setLayout(new BorderLayout());
            this.filename = new JLabel("Selected Files:");
            this.leftPanel.add(filename, BorderLayout.EAST);
        }
    
     private void creatRightPanel(){
            this.rightPanel = new JPanel();
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
