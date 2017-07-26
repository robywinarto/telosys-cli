package org.telosys.tools.cli;

import java.io.IOException;
import java.io.PrintWriter;

import jline.console.ConsoleReader;

import org.telosys.tools.api.TelosysProject;
import org.telosys.tools.commons.StrUtil;

public abstract class Command {

	private final ConsoleReader consoleReader ;
	private final PrintWriter out ;
	private final Environment environment ;
	
	public Command(ConsoleReader consoleReader, Environment environment ) {
		super();
		//this.out = out;
		this.consoleReader = consoleReader ;
		this.out = new PrintWriter(consoleReader.getOutput()) ;
		this.environment = environment ;
	}
	
	public abstract String getName();
	
	public abstract String getShortName();
	
//	public abstract String execute(Environment environment, String[] args);
	public abstract String execute(String[] args);
	
	public abstract String getDescription();
	
	protected Environment getEnvironment() {
		return environment ;
	}

	protected void appendLine(StringBuffer sb, String s) {
		sb.append(s);
		sb.append(Environment.LINE_SEPARATOR);
	}
	
	protected void appendEndOfLine(StringBuffer sb) {
		sb.append(Environment.LINE_SEPARATOR);
	}

//	protected String getLastErrorMessage() {
//		return lastErrorMessage;
//	}

	protected String invalidUsage(String message) {
		return "Invalid usage : " + message ;
	}

	protected void print(String message) {
		out.println(message);
		out.flush();
	}
	protected void printError(Exception ex) {
		LastError.setError(ex);
		out.println("[ERROR] Exception : "+ ex.getMessage());
		out.flush();
	}
	protected void printDebug(String message) {
		if ( Trace.DEBUG ) {
			out.println("[DEBUG] "+message);
			out.flush();
		}
	}

	protected String getCurrentDirectory() {
		return environment.getCurrentDirectory();
	}
	//-------------------------------------------------------------------------
	// Home directory 
	//-------------------------------------------------------------------------
	protected void setCurrentHome() {
		environment.setHomeDirectory();
		updatePrompt();
	}
	
	/**
	 * @param directory
	 */
	protected void setCurrentHome(String directory) {
		environment.setHomeDirectory(directory);
		updatePrompt();
	}
	
	/**
	 * Returns the current home directory
	 * @return
	 */
	protected String getCurrentHome() {
		return environment.getHomeDirectory();
	}

	//-------------------------------------------------------------------------
	// Model
	//-------------------------------------------------------------------------
	/**
	 * Set the current model name in the current environment
	 * @param modelName
	 */
	protected void setCurrentModel(String modelName) {
		environment.setCurrentModel(modelName);
		updatePrompt();
	}
	
	/**
	 * Returns the current model in the environment
	 */
	protected String getCurrentModel() {
		return environment.getCurrentModel();
	}
	
	protected String getCurrentGitHubStore() {
		return environment.getCurrentGitHubStore();
	}
	
	
	/**
	 * Returns the current home directory
	 * @return
	 */
	protected String getHomeDirectory() {
		return environment.getHomeDirectory();
	}

	protected void updatePrompt() {
		String prompt = Const.PROMPT_TEXT ;
		if ( environment.getHomeDirectory() != null ) {
			prompt = prompt + "#" ;
		}
		if ( environment.getCurrentModel() != null ) {
			prompt = prompt + "(" + environment.getCurrentModel() + ")" ;
		}
		prompt = prompt + Const.PROMPT_CHAR ;
		consoleReader.setPrompt(Color.colorize(prompt, Const.PROMPT_COLOR));
	}
	
	protected String launchEditor(Environment environment, String fileFullPath) {
		String editorCommand = environment.getEditorCommand();
		String fullCommand = editorCommand ;
		if ( ! StrUtil.nullOrVoid(fileFullPath) ) {
			fullCommand = editorCommand + " " + fileFullPath;
		}
		launchSystemCommand(fullCommand);
		return fullCommand ;
	}
	
	protected void launchSystemCommand(String fullCommand) {
		try {
			Runtime.getRuntime().exec(fullCommand);
		} catch (IOException e) {
			print( "ERROR : IOException : " + e.getMessage() );
		}
	}

	/**
	 * Returns the current Telosys project, or null if HOME is not defined
	 * @param environment
	 * @return
	 */
	protected TelosysProject getTelosysProject() {
		if ( environment.getHomeDirectory() == null ) {
			print( "Home directory must be set before using this command!" ) ;
			return null ;
		}
		
		String projectFullPath = environment.getHomeDirectory();
		TelosysProject telosysProject = new TelosysProject(projectFullPath);
		return telosysProject ;
	}
}   
