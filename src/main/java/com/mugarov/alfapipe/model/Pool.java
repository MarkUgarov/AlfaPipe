/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;

import com.mugarov.alfapipe.view.MainViewButtonPool;
import com.mugarov.alfapipe.control.listeners.MainViewButtonListener;
import com.mugarov.alfapipe.control.listeners.MenuListener;
import com.mugarov.alfapipe.model.programparse.generators.AssemblerGenerator;
import com.mugarov.alfapipe.model.programparse.generators.Generator;
import com.mugarov.alfapipe.model.programparse.generators.PreprocessingGenerator;
import com.mugarov.alfapipe.model.programparse.generators.ProcessingGenerator;
import com.mugarov.alfapipe.model.programparse.generators.ProdigalGenerator;
import com.mugarov.alfapipe.model.programparse.generators.ReadsVsContigsGenerator;
import com.mugarov.alfapipe.model.programparse.generators.ToolGenerator;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Mark
 */
public abstract class Pool {
    // general optical parameters
    public static final String TITLE = "Alfa - Pipe: A likely functionally assembler Pipe.";
    public static final boolean FULLSCREAN = true;
    public static final Dimension FRAMESIZE = new Dimension(400,600);
    // text for menu
    public static final String MENU_MAIN = "Menu";
    public static final String MENU_EXIT = "Close";
    
    //texts for main content
    public static final String BUTTON_START_TEXT = "Start";
    public static final String BUTTON_START_COMMAND = "Start";
    
    // texts for tabs content in the main content
    public static final String BUTTON_CHOOSE_RAW_FILE_TEXT = "Add files";
    public static final String BUTTON_CHOOSE_RAW_FILE_COMMAND = "chooseFile";
    
    public static final String BUTTON_ADD_DATA_SET_TEXT = "add set";
    public static final String BUTTON_ADD_DATA_SET_COMMAND = "addDataSet";
    
    public static final String BUTTON_DELETE_SET_TEXT = "remove this set";
    public static final String BUTTON_DELETE_SET_COMMAND = "removeSet";
    
    public static final String BUTTON_CHOOOSE_OUTPUT_TEXT = "choose output";
    public static final String BUTTON_CHOOSE_OUTPUT_COMMAND = "outputChoose";
    
    public static final int PARAMETERS_IN_ONE_ROW = 3;
    
    // texts and parameters for the SingleFilePanels in the tabs
    public static final String BUTTON_DELETE_FILE_TEXT = "delete";
    public static final String BUTTON_DELETE_FILE_COMMAND = "delFile";
    
    public static final String BUTTON_APPLY_NAME_TEXT = "Apply";
    public static final String BUTTON_APPLY_NAME_COMMAND = "applyName";
    
    public static final Dimension SINGLEFIlE_DIMENSION = new Dimension(
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*0.8),
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*0.05)
                );
    
    public static final Color COLOR_VALID = Color.GREEN;
    public static final Color COLOR_INVALID = Color.RED;
    public static final Color COLOR_SUCCESS = Color.CYAN;
    public static final Color COLOR_FAILURE = Color.darkGray;
    
    // available Tools and Assemblers
    public static final Generator GENERATOR_PREPROCESSING = new PreprocessingGenerator();
    public static final String PATH_PREPROCESSING_LIST ="CONFIG/Preprocessing.yaml";
    
    public static final Generator GENERATOR_PROCESSING = new ProcessingGenerator();
    public static final String PATH_PROCESSING_LIST ="CONFIG/Processing.yaml";
    public static final String NAME_DEFAULT_PROCESSING ="defaultProcessing";
    
    public static final Generator GENERATOR_ASSEMBLER= new AssemblerGenerator();
    public static final String PATH_ASSEMBLER_LIST = "CONFIG/Assemblers.yaml";
    
    public static final Generator GENERATOR_READS_VS_CONTIGS = new ReadsVsContigsGenerator();
    public static final String PATH_READS_VS_CONTIGS_LIST = "CONFIG/ReadsVsContigs.yaml";
    public static final String NAME_DEFAULT_READS_VS_CONTIGS = "defaultReadsVsContigs";
    
    public static final Generator GENERATOR_PRODIGAL = new ProdigalGenerator();
    public static final String PATH_PRODIGAL_LIST = "CONFIG/Prodigal.yaml";
    public static final String NAME_DEFAULT_PRODIGAL = "defaultProdigal";
    
    public static final Generator GENERTATOR_TOOLS = new ToolGenerator();
    public static final String PATH_TOOLS_LIST = "CONFIG/Tools.yaml";
    
    public static final String PROGRAM_EMPTY_PARAMETER_VALUE = "/empty";
    public static final String PROGRAM_INPUT_PATH_SET_PARAMETER_NAME = "inputPathCommand";
    public static final String PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME = "outputPathCommand";
    
     // listeners for the main content - the components (Button, Menu....) will add them by themselves 
    public static final MainViewButtonListener LISTENER_BUTTON = new MainViewButtonListener();
    public static final MenuListener LISTENER_MENU = new MenuListener();
    
    // file management
    public static final String FILE_ORIGIN_DEFAULT = "output";
    public static final String FILE_LOGFILE_NAME = "log.txt";
    
    public static final int FILE_PAIR_DISTINGUISHER_POSITION = -2;
    public static final String FILE_PAIR_DISTIGNUISHER_REGEX = "_";
    
    //standard messegas
    public static final String MESSAGE_PREFIX = "echo ";
    public static final String MESSAGE_PREPROCESSING_IS_NULL ="Null preprocessing was selected.";
    public static final String MESSAGE_PROCESSING_IS_NULL ="Null processing was selected.";
    public static final String MESSAGE_ASSEMBLER_IS_NULL ="Null assembler was selected.";
    public static final String MESSAGE_READSVSCONTIGS_IS_NULL ="Null reads vs contigs was selected";
    public static final String MESSAGE_PRODIGAL_IS_NULL ="Null prodigal was selected";
    public static final String MESSAGE_TOOL_IS_NULL ="A tool with command null was selected";
            
    //static components
    public static MainViewButtonPool MAIN_BUTTON_POOL = new MainViewButtonPool();
   
    
}
