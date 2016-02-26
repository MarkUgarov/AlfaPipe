/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.view.mainview.tab.tabular;

import com.mugarov.alfapipe.control.listeners.tabrelated.singlefile.SingleFileListener;
import com.mugarov.alfapipe.model.ParameterPool;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.BorderFactory;

/**
 *
 * @author Mark
 */
    public class SingleFilePanel extends JPanel{
        
        private final SingleFileListener fileListener;
        private final String id;
        private final String name;
        
        private JPanel leftPanel;
        private JButton delete;
        private JLabel filename;
        private JPanel filePanel;
        
        
        private JPanel rightPanel;
        private final BorderLayout  layout;
        
        private final ArrayList<JCheckBox> tools;
        
        /**
         * 
         * @param id should be the path
         * @param name should be the filename
         */
        
        public SingleFilePanel(String id, String name,  SingleFileListener listener){
            super();
            this.setDoubleBuffered(true);
            this.setAlignmentY(TOP_ALIGNMENT);
            this.id = id;
            this.name = name;
            this.fileListener= listener;
            this.layout = new BorderLayout();
            this.setLayout(this.layout);
            this.setBorder(BorderFactory.createLineBorder(Color.black));
           
            
            this.createLeftPanel();
            
            this.tools = new ArrayList<>();
            this.creatRightPanel();
            
            this.add(this.leftPanel, BorderLayout.WEST); 
            this.add(this.rightPanel, BorderLayout.EAST);  
            
            this.setPreferredSize(ParameterPool.SINGLEFIlE_DIMENSION);
            this.setMaximumSize(this.getPreferredSize());
            
            
        }
        
        public String getID(){
            return this.id;
        }
        

        @Override
        public String getName(){
            return this.name;
        }
        
        private void createLeftPanel(){
            this.leftPanel = new JPanel();
            
            this.delete = new JButton();
            this.delete.setText(ParameterPool.BUTTON_DELETE_FILE_TEXT);
            this.delete.setActionCommand(ParameterPool.BUTTON_DELETE_FILE_COMMAND);
            this.delete.addActionListener(this.fileListener);
            this.leftPanel.add(delete);
            
            this.filePanel = new JPanel();
            this.filePanel.setLayout(new BorderLayout());
            
            this.filename = new JLabel(name);
            this.filePanel.add(this.filename, BorderLayout.NORTH);
            this.leftPanel.add(this.filePanel);
        }
        
        private void creatRightPanel(){
            this.rightPanel = new JPanel();
            
            for(String tool: ParameterPool.GENERTATOR_TOOLS.getAvailableNames()){
                JCheckBox toolBox= new JCheckBox(tool);
                toolBox.addItemListener(this.fileListener);
                toolBox.setSelected(true);
                this.tools.add(toolBox);
                this.rightPanel.add(toolBox);
            }
//            for(Component c:this.rightPanel.getComponents()){
//                System.out.println("Component: "+c.toString());
//            }
            
        }
        
        public void addPaired(String filename){
            this.filePanel.add(new JLabel(filename), BorderLayout.SOUTH);
        }
        
        public void selectTool(String toolName){
            for(JCheckBox box:this.tools){
                if(box.getText().equals(toolName)){
                    box.setSelected(true);
                }
            }
        }
        
        public void unselectTool(String toolName){
            for(JCheckBox box:this.tools){
                if(box.getText().equals(toolName)){
                    box.setSelected(false);
                }
            }
        }
        
        public void setValidation(boolean fileValid, ArrayList<String> validTools){

            this.leftPanel.setBackground(fileValid?ParameterPool.COLOR_VALID:ParameterPool.COLOR_INVALID);

            for(JCheckBox box:this.tools){
                if(validTools.contains(box.getText()) == true){
                    box.setBackground(ParameterPool.COLOR_VALID);
                }
                else{
                    box.setBackground(ParameterPool.COLOR_INVALID);
                }
            }
        }

    public void disableEditing() {
        this.delete.setEnabled(false);
        for(JCheckBox box: this.tools){
            box.setEnabled(false);
        }
    }

    public void setProgressed(boolean success) {
        this.setBackground(success?ParameterPool.COLOR_SUCCESS:ParameterPool.COLOR_FAILURE);
    }
    
    public void setToolsProgressed(ArrayList<String> tools, ArrayList<Boolean> success){
        for(int i=0; i<tools.size(); i++){
            for(JCheckBox box:this.tools){
                if(box.getText().equals(tools.get(i))){
                    box.setBackground(success.get(i) ? ParameterPool.COLOR_SUCCESS:ParameterPool.COLOR_FAILURE);
                }
            }
        }
    }
        
}
