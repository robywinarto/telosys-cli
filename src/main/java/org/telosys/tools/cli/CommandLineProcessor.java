/**
 *  Copyright (C) 2015-2019 Telosys project org. ( http://www.telosys.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.telosys.tools.cli;

import java.io.PrintWriter;

/**
 * CLI command line processor 
 * 
 * @author Laurent GUERIN
 *
 */
public class CommandLineProcessor {
	
	private final PrintWriter     out ;
	private final CommandProvider commandProvider ;
	
	/**
	 * Constructor
	 * @param output  
	 * @param commandProvider 
	 */
	public CommandLineProcessor(PrintWriter output, CommandProvider commandProvider) {
		super();
		this.out = output ;
		this.commandProvider = commandProvider;
	}

	/**
	 * Processes the given line (try to interpret and execute the command) 
	 * @param line
	 */
	public boolean processLine( String line ) {
		debug("(line='"+line+"')");
		if ( line.trim().length() == 0 ) {
			return false ;
		}
		String result ;
		String[] args = CommandLineUtil.toArgs(line);
		try {
			debug("(args length = " + args.length +")");
			String commandName = args[0];
			debug("(commandName = '" + commandName +"')");
			// is it a comment ?
			if ( commandName.startsWith("//") ) {
				return true; // OK (nothing to do)
			}
			// try to find a command 
			Command command = commandProvider.getCommand(commandName);
			if ( command != null ) {
				try {
					result = command.execute(args);
					print(result);
					return true; // OK
				} catch (CancelCommandException cancelCommandEx ) {
					// The command has been canceled, for example due to invalid arguments 
					// => just print the messsage and continue
					print(cancelCommandEx.getMessage());
				}
			}
			else {
				print("Invalid command '" + commandName + "'");
			}
		} catch (Exception e) {
			printException(e);
		}
		return false ;
	}
	
	private void debug(String message) {
		if ( Trace.DEBUG ) {
			out.println("[DEBUG] "+message);
			out.flush();
		}
	}
	private void print(String message) {
		if ( message != null ) {
			out.println(message);
			out.flush();
		}
	}
	private void printException(Exception e) {
		out.println("ERROR - Unexpected exception : " + e.getClass().getSimpleName() );
		out.println("  Message : " + e.getMessage() );
		e.printStackTrace(out);
		out.flush();
	}
}
