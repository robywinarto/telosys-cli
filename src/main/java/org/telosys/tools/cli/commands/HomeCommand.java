package org.telosys.tools.cli.commands;

import jline.console.ConsoleReader;

import org.telosys.tools.cli.Command;
import org.telosys.tools.cli.Environment;

public class HomeCommand extends Command {
	
	/**
	 * Constructor
	 * @param out
	 */
	public HomeCommand(ConsoleReader consoleReader, Environment environment) {
		super(consoleReader, environment);
	}
	
	@Override
	public String getName() {
		return "h";
	}

	@Override
	public String getShortDescription() {
		return "Home" ;
	}
	
	@Override
	public String getDescription() {
		return "Set/print the HOME directory ('h [dir]')";
	}
	
	@Override
	public String execute(String[] args) {
//		environment.setHomeDirectory();
//		updatePrompt(environment);
		if ( args.length > 1 ) {
			return tryToSetHome(args[1]);
		}
		else {
			//return invalidUsage("model-name argument expected");
			return undefinedIfNull(getCurrentHome());
		}
		
//		setCurrentHome();
////		return "Home set ('" + environment.getHomeDirectory() + "')" ;	
//		return "Home set ('" + getHomeDirectory() + "')" ;	
	}

	private String tryToSetHome(String dir) {
		if ( ".".equals(dir) ) {
			setCurrentHome();
			return "Home set ('" + getCurrentHome() + "')" ;	
		}
		else {
			return "Invalid directory";
		}
	}
}