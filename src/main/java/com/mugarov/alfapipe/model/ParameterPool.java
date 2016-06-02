/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.alfapipe.model;


import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Mark
 */
public abstract class ParameterPool {
    
    // cluster
    public static final boolean CLUSTER_ENABLE = true;
    public static final int CLUSTER_WAITING_TIME = 5000;
    
    // general optical parameters
    public static final String TITLE = "Alfa - Pipe: A likely functional assembler Pipe.";
    public static final boolean FULLSCREAN = true;
    public static final Dimension FRAMESIZE = new Dimension(400,600);
    public static final Color COLOR_BACKGROUND_STANDARD = new Color(128, 255 , 0);
    public static final Color COLOR_BACKGROUND_SECOND = new Color(160,160,160,125);
    public static final Color COLOR_BACKGROUND_CLUSTER = new Color(0, 0, 255, 125);
    public static final Color COLOR_BACKGROUND_DISABLED = Color.yellow;
    public static final Color COLOR_BACKGROUND_MOUSEOVER = new Color(160,160,160,150);
    
    // text for menu
    public static final String MENU_MAIN = "Menu";
    public static final String MENU_EXIT = "Close";
    
    //texts for main content
    public static final String BUTTON_START_TEXT = "Start";
    public static final String BUTTON_START_COMMAND = "Start";
    
    public static final String BUTTON_ADD_DATA_SET_TEXT = "add set";
    public static final String BUTTON_ADD_DATA_SET_COMMAND = "addDataSet";
    
    public static final String BUTTON_CANCEL_ALL_TEXT = "cancel all sets";
    public static final String BUTTON_CANCEL_ALL_COMMAND = "cancelAll";
    
    // texts for tabs content in the main content
    public static final String BUTTON_CHOOSE_INPUT_FILE_TEXT = "Add files";
    public static final String BUTTON_CHOOSE_INPUT_FILE_COMMAND = "chooseFile";
    
    public static final String BUTTON_DELETE_SET_TEXT = "remove this set";
    public static final String BUTTON_DELETE_SET_COMMAND = "removeSet";
    
    public static final String BUTTON_CHOOOSE_OUTPUT_TEXT = "choose output";
    public static final String BUTTON_CHOOSE_OUTPUT_COMMAND = "outputChoose";
    
    public static final String BUTTON_CANCEL_SET_TEXT = "Cancel set";
    public static final String BUTTON_CANCEL_SET_COMMAND = "cancelSet";
    
    public static final String BUTTON_QANCELLOR_TEXT = "Qancellor";
    public static final String BUTTON_QANCELLOR_COMMAND = "qancellor";
            
    
    public static final int PARAMETERS_IN_ONE_ROW = 3;
    
    // texts for the SingleProgramPanels in the tabs
    public static final Color LABEL_IMPORTANCE_COLOR = Color.MAGENTA;
    public static final String LABEL_CLUSTER = "Cluster settings";
    public static final String LABEL_PROGRAMS = "Programs";
        // labels for the default programs
    public static final String LABEL_PREPROCESSING = "Preprocessing";
    public static final String LABEL_PROCESSING = "Processing";
    public static final String LABEL_ASSEMBLER = "Assembler";
    public static final String LABEL_COMPARISON = "Comparison";
    public static final String LABEL_ANNOTATION = "Gene Annotation";
    public static final String LABEL_TOOLS = "Tools";
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
    public static final Color DISTINGUISH_PROGRAM_COLOR_1 = new Color(51,255,255,125);
    public static final Color DISTINGUISH_PROGRAM_COLOR_0 = new Color(160,160,160,125);
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
    public static final String TOOLTIP_CLUSTER_CHECKBOX_SELECTED = "The selected program will run on the cluster.";
    public static final String TOOLTIP_CLUSTER_CHECKBOX_UNSELECTED = "The selected program will run on your local machine.";
    public static final String TOOLTIP_DISABLED = "Cluster is disabled. The selected program will run on your local machine.";
    public static final String TOOLTIP_PARAMETER_DEFAULT = "No information about this parameter.";
    
    // available Tools and Programs
    
    public static final String CONFIG_PREFIX = "/vol/ampipe/data/";
    public static final String CONFIG_INFIX = "CONFIG";
    
    public static final String NAME_CLUSTER_FILE = "Cluster.yaml";
    public static final String PATH_CLUSTER = "CLUSTER/"+NAME_CLUSTER_FILE;
      
    public static final String PATH_PREFIX_OBLIGATORY_PROGRAMS = "PROGRAMS";
    
    public static final String OBLIGATORY_PREPROCESSING = "Preprocessing.yaml";
    public static final String OBLIGATORY_PROCESSING = "Processing.yaml";
    public static final String OBLIGATORY_ASSEMBLY = "Assembly.yaml";
    public static final String OBLIGATORY_COMPARISON = "Comparison.yaml";
    public static final String OBLIGATORY_ANNOTATION = "Annotation.yaml";
    public static final String[] CONFIG_OBLIGATORIES = {OBLIGATORY_PREPROCESSING, OBLIGATORY_PROCESSING, OBLIGATORY_ASSEMBLY,OBLIGATORY_COMPARISON,OBLIGATORY_ANNOTATION};
  
    
    public static final String NAME_TOOLS_LIST = "Tools.yaml";
    public static final String PATH_OBLIGATORY_TOOLS = "TOOLS/"+NAME_TOOLS_LIST;
    
    public static final String PROGRAM_DIRECTORY_VALUE = "//.";
    public static final String PROGRAM_FILE_VALUE = "//this";
    public static final String PROGRAM_EMPTY_PARAMETER_VALUE = "/empty";
    public static final String PROGRAM_PATH_VALUE = "PATH"; // value of the directory (parent if file is not a directory itself)
    public static final String PROGRAM_NAME_VALUE = "NAME"; // clear name of the file
    public static final String PROGRAM_USER_VALUE = "USER"; // the user name
    public static final String PROGRAM_PROGRAM_NAME_VALUE = "PROGRAM"; // the name of the program
    public static final String PROGRAM_INPUT_PATH_SET_PARAMETER_NAME = "inputPathCommand";
    public static final String PROGRAM_OUTPUT_PATH_SET_PARAMETER_NAME = "outputPathCommand";
    public static final String PROGRAM_PAIRED_PARAMETER_NAME = "pairedCommand";
    
    public static final String[] ENDINGS_FASTA = {".fna", ".fa", ".fasta"};
    
    
    
    // more complex parameters
    public static final String PROGRAM_BINARAY_NAME = "Binary";
    public static final String PROGRAM_BINARY_COMMAND = "-b";
    public static final String PROGRAM_BINARY_CONFIRM = "y";
    public static final int PROGRAM_BINARY_POSITION = 0;
    public static final boolean PROGRAM_BINARY_OPTIONAL = false;
    public static final String PROGRAM_BINARY_DESCRIPTION = "Is a binary (not a shell script).";
            
    
     
    
    // file management
    public static final String FILE_ORIGIN_DEFAULT = "/vol/ampipe/data/"+System.getProperty("user.name")+"/output";
    public static final String FILE_LOGFILE_NAME = "log.txt";
    public static final String WORKING_DIRECTORY = "/vol/ampipe/data";
    public static final String FILE_SCRIPT_PATH = "/vol/ampipe/data/SCRIPTS/";
    
    //standard messegas
    public static final String MESSAGE_PREFIX = "echo ";
    public static final String MESSAGE_OUT_OF_INDEX = "Tried to get a command with index out of bounds.";
    public static final String MESSAGE_PROGRAM_EMPTY = "Tried to execute an empty command";
    public static final String MESSAGE_PROGRAM_COMMAND_NULL = "Program command was null.";
    public static final String MESSAGE_PREPROCESSING_IS_NULL ="Null preprocessing was selected.";
    public static final String MESSAGE_PROCESSING_IS_NULL ="Null processing was selected.";
    public static final String MESSAGE_ASSEMBLER_IS_NULL ="Null assembler was selected.";
    public static final String MESSAGE_READSVSCONTIGS_IS_NULL ="Null reads vs contigs was selected";
    public static final String MESSAGE_PRODIGAL_IS_NULL ="Null prodigal was selected";
    public static final String MESSAGE_TOOL_IS_NULL ="A tool with command null was selected";
            
    
    
    //Logfile standard strings
    public static final String LOG_LINE_PREFIX = "<>< ";
    public static final String LOG_LINE_POSTFIX = "|:|";
    public static final String LOG_COMMAND_PREFIX = "EXECUTE: ";
    public static final String LOG_WARNING = "ATTENTION: ";
    public static final String LOG_SOURCE_HINT = "  <- Message from ";
    public static final String LOG_RENAMED_HINT = "Old logfile was renamed.";
    public static final String LOG_CHANGE_TO = "Trying to change directory to ";
    public static final String LOG_CHANGED_FROM = "Changed to this logfile from ";
    
    // Standard substitutions
    public static final String[] REPLACE_REGEX = {"\\s+","-", "/", ":"};
    public static final String REPLACE_REPLACEMENT = "_";
    
}
