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
public abstract class ParameterPool {
    
    
    
    // general optical parameters
    public static final String TITLE = "Alfa - Pipe: A likely functional assembler Pipe.";
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
    
    // texts for the SingleProgramPanels in the tabs
    public static final Color LABEL_IMPORTANCE_COLOR = Color.MAGENTA;
    public static final String LABEL_CLUSTER = "Cluster settings";
    public static final String LABEL_PROGRAMS = "Programs:";
    public static final String LABEL_PREPROCESSING = "Preprocessing";
    public static final String LABEL_PROCESSING = "Processing";
    public static final String LABEL_ASSEMBLER = "Assembler";
    public static final String LABEL_COMPARISON = "Comparison";
    public static final String LABEL_ANNOTATION = "Gene Annotation";
    public static final String LABEL_TOOLS = "Tools:";
    public static final Dimension LABEL_OFFSET = new Dimension(
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*0.03),
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*0.02)
                );
    public static final Dimension LABEL_DIMENSION = new Dimension(
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*0.1),
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*0.02)
                );
    public static final Dimension DISTINGUISH_BAR_DIMENSION = new Dimension(
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*0.05),
                (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*0.005)
                );
    public static final Color DISTINGUISH_PROGRAM_COLOR_1 = Color.LIGHT_GRAY;
    public static final Color DISTINGUISH_PROGRAM_COLOR_0 = Color.WHITE;
    public static final Color DISTINGUISH_BAR_COLOR = Color.gray;
    
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
    
    
    // standard Tooltips
    public static final String TOOLTIP_CLUSTER_CHECKBOX_SELECTED = "The selected program will run on the Cluster.";
    public static final String TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED = "The selected program will run on you local machine.";
    
    // available Tools and Assemblers
    
        public static final String CONFIG_PREFIX = "/vol/ampipe/data/";
    public static final String CONFIG_PATH = "CONFIG";
    
    public static final String OBLIGATORY_PREPROCESSING = "Preprocessing.yaml";
    public static final String OBLIGATORY_PROCESSING = "Processing.yaml";
    public static final String OBLIGATORY_ASSEMBLY = "Assembly.yaml";
    public static final String OBLIGATORY_COMPARISON = "Comparison.yaml";
    public static final String OBLIGATORY_ANNOTATION = "Annotation.yaml";
    public static final String[] CONFIG_OBLIGATORIES = {"OBLIGATORY_PREPROCESSING", "OBLIGATORY_PROCESSING", "OBLIGATORY_ASSEMBLY","OBLIGATORY_COMPARISON","OBLIGATORY_ANNOTATION"};
    
    public static final Generator GENERATOR_PREPROCESSING = new PreprocessingGenerator();
    public static final String PATH_PREPROCESSING_LIST = "CONFIG/Preprocessing.yaml";
    
    public static final Generator GENERATOR_PROCESSING = new ProcessingGenerator();
    public static final String PATH_PROCESSING_LIST = "CONFIG/Processing.yaml";
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
    
    public static final String PROGRAM_DIRECTORY_VALUE = "//.";
    public static final String PROGRAM_EMPTY_PARAMETER_VALUE = "/empty";
    public static final String PROGRAM_PATH_VALUE = "[path]";
    public static final String PROGRAM_NAME_VALUE = "[name]";
    public static final String PROGRAM_INPUT_PATH_SET_PARAMETER_NAME = "inputPathCommand";
    public static final String PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME = "outputPathCommand";
    public static final String PROGRAM_PAIRED_PARAMETER_NAME = "pairedCommand";
    
     
    
    // file management
    public static final String FILE_ORIGIN_DEFAULT = "/vol/ampipe/data/"+System.getProperty("user.name")+"/output";
    public static final String FILE_LOGFILE_NAME = "log.txt";
    public static final String WORKING_DIRECTORY = "/vol/ampipe/data";
    
    //standard messegas
    public static final String MESSAGE_PREFIX = "echo ";
    public static final String MESSAGE_PREPROCESSING_IS_NULL ="Null preprocessing was selected.";
    public static final String MESSAGE_PROCESSING_IS_NULL ="Null processing was selected.";
    public static final String MESSAGE_ASSEMBLER_IS_NULL ="Null assembler was selected.";
    public static final String MESSAGE_READSVSCONTIGS_IS_NULL ="Null reads vs contigs was selected";
    public static final String MESSAGE_PRODIGAL_IS_NULL ="Null prodigal was selected";
    public static final String MESSAGE_TOOL_IS_NULL ="A tool with command null was selected";
            
    
    
    //Logfile standard strings
    public static final String LOG_LINE_PREFIX = "<>< ";
    public static final String LOG_LINE_POSTFIX = "|°|";
    public static final String LOG_COMMAND_PREFIX = "EXECUTE: ";
    public static final String LOG_WARNING = "ATTENTION: ";
    public static final String LOG_SOURCE_HINT = "  <- Message from ";
    public static final String LOG_OVERWRITTEN_HINT = "Old logfile was overwritten";
    public static final String LOG_CHANGE_TO = "Trying to change directory to ";
    public static final String LOG_CHANGED_FROM = "Changed to this logfile from ";
    
    // Standard substitutions
    public static final String[] REPLACE_REGEX = {"-", "/", ":"};
    public static final String REPLACE_REPLACEMENT = "_";
    
}
