
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.control.listeners.tabrelated.singlefile;

import com.mugarov.alfapipe.model.datatypes.InputFile;
import com.mugarov.alfapipe.model.Pool;
import com.mugarov.alfapipe.model.datatypes.SetOfFiles;
import com.mugarov.alfapipe.model.programparse.datatypes.Parseable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Mark
 */
public class SingleFileListener implements ActionListener, ItemListener{

    private InputFile file;
    private final SetOfFiles parentSet;
    
    public SingleFileListener(InputFile file, SetOfFiles set){
        this.file = file;
        this.parentSet = set;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
//        System.out.println(ae.getActionCommand()+" was performed in SingleFileListener for file " +this.file.getAbsolutePath());
        if(ae.getActionCommand().equals(Pool.BUTTON_DELETE_FILE_COMMAND)){
//            System.out.println("Listener tries to delete.");
            this.parentSet.deleteFile(this.file.getAbsolutePath());
        }
    }
    
    public void setFile(InputFile file){
        this.file = file;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
       JCheckBox box =((JCheckBox) ie.getItemSelectable());
//       System.out.println(ie.getStateChange() == ItemEvent.SELECTED ? "was selected" : "was unselected");
       Parseable tool= Pool.GENERTATOR_TOOLS.get(box.getText());
       
       if(ie.getStateChange() == ItemEvent.SELECTED){
//           System.out.println(box.getText()+" was selected;");
           this.file.selectTool(tool);
       }
       else{
//           System.out.println(box.getText()+" was unselected;");
           this.file.unselectTool(tool);
       }
    }
    
}
