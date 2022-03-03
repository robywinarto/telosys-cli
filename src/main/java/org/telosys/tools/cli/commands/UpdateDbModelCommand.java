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
package org.telosys.tools.cli.commands;

import org.telosys.tools.api.TelosysProject;
import org.telosys.tools.cli.CommandWithModel;
import org.telosys.tools.cli.Environment;
import org.telosys.tools.commons.StrUtil;
import org.telosys.tools.commons.TelosysToolsException;

import jline.console.ConsoleReader;

public class UpdateDbModelCommand extends CommandWithModel {

	/**
	 * Constructor
	 * @param out
	 */
	public UpdateDbModelCommand(ConsoleReader consoleReader, Environment environment) {
		super(consoleReader, environment);
	}
	
	@Override
	public String getName() {
		return "udbm";
	}

	@Override
	public String getShortDescription() {
		return "Update Database Model" ;
	}

	@Override
	public String getDescription() {
		return "Update a model for the current/given database";
	}
	
	@Override
	public String getUsage() {
		return "udbm [database-id] [-y]";
	}

	@Override
	public String execute(String[] args) {
		
		if ( checkHomeDirectoryDefined() ) {
			// Check arguments :
			// 0 : udbm 
			// 1 : udbm -y | udbm DbId
			// 2 : udbm DbId -y
			if ( checkArguments(args, 0, 1, 2 ) && checkOptions(args, "-y") ) {
				String[] newArgs = registerAndRemoveYesOption(args);
				// newArgs = args without '-y' if any
				execute2(newArgs);				
			}
		}
		return null;
	}

	public void execute2(String[] args) {
		if ( args.length > 1 ) {
			// udbm database-id
			String argId = args[1];
			Integer id = StrUtil.getIntegerObject(argId);
			if ( id != null ) {
				updateDatabaseModel(id);
			}
			else {
				print("Invalid database id '" + argId + "'");					
			}
		}
		else {
			// udbm (no db id)
			updateDatabaseModel(null);
		}			
	}
		
	private void updateDatabaseModel(Integer id) {
//		if ( confirm("Do you really want to update your model from the database ?") ) {
//			try {
//				TelosysProject telosysProject = getTelosysProject();
//				telosysProject.updateDbModel(id) ;
//				print("Model updated.");
//			} catch (TelosysToolsException e) {
//				printError(e);
//			}
//		}
		print("This command is no longer usable since version 3.4.0.");
	}
	
}
