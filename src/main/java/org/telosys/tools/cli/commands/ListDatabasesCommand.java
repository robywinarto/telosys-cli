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

import java.util.List;

import org.telosys.tools.api.TelosysProject;
import org.telosys.tools.cli.Command;
import org.telosys.tools.cli.Environment;
import org.telosys.tools.commons.TelosysToolsException;
import org.telosys.tools.commons.dbcfg.yaml.DatabaseDefinition;
import org.telosys.tools.commons.dbcfg.yaml.DatabaseDefinitions;

import jline.console.ConsoleReader;

public class ListDatabasesCommand extends Command {
	
	/**
	 * Constructor
	 * @param out
	 */
	public ListDatabasesCommand(ConsoleReader consoleReader, Environment environment) {
		super(consoleReader, environment);
	}

	@Override
	public String getName() {
		return "ldb";
	}

	@Override
	public String getShortDescription() {
		return "List Databases" ;
	}

	@Override
	public String getDescription() {
		return "List the databases";
	}
	
	@Override
	public String getUsage() {
		return "ldb [id]";
	}

	@Override
	public String execute(String[] args) {
		if ( checkHomeDirectoryDefined() && checkArguments(args, 0, 1 ) ) {
			if ( args.length > 1 ) {
				// database-id
//				String argId = args[1];
//				Integer id = StrUtil.getIntegerObject(argId);
//				if ( id != null ) {
//					return listDatabases(id);
//					
//				}
//				else {
//					print("Invalid database id '" + argId + "'");					
//				}
				return listDatabases(args[1]);
			}
			else {
				// no database-id
				return listDatabases(null);
			}
		}
		return null ;
	}

	private String listDatabases(String id) {
		TelosysProject telosysProject = getTelosysProject();
		try {
			StringBuilder sb = new StringBuilder();
			if ( id != null ) {
				// DatabaseConfiguration dbConfig = telosysProject.getDatabaseConfiguration(id);
				DatabaseDefinition dbConfig = telosysProject.getDatabaseDefinition(id);
				if ( dbConfig != null ) {
					printDbConfig(sb, dbConfig);
				}
				else {
					appendLine(sb, "Database " + id + " is not defined."  );
				}
			}
			else {
				DatabaseDefinitions databasesConfigurations = telosysProject.getDatabaseDefinitions();
				List<DatabaseDefinition> databases = databasesConfigurations.getDatabases();
				if ( databases.isEmpty() ) {
					appendLine(sb, "No database defined." );
				}
				else {
					appendLine(sb, databasesConfigurations.getDatabases().size()+ " database(s) defined" );
					for ( DatabaseDefinition dbConfig : databases ) {
						printDbConfig(sb, dbConfig);
					}
				}
			}
			return sb.toString();
		} catch (TelosysToolsException e) {
			printError(e);
		}
		return null ;
	}

	private void printDbConfig(StringBuilder sb, DatabaseDefinition dbConfig) {
		appendLine(sb, " ");
		appendLine(sb, "Database '" + dbConfig.getId() + "' : "  );
		appendLine(sb, " . Name          : " + dbConfig.getName() );
		appendLine(sb, " . Type          : " + dbConfig.getType() );
		appendLine(sb, " . JDBC URL      : " + dbConfig.getUrl()  );
		appendLine(sb, " . Driver class  : " + dbConfig.getDriver() );
		appendLine(sb, " . User          : " + dbConfig.getUser() );
		appendLine(sb, " . Password      : " + dbConfig.getPassword() );
		
		appendLine(sb, " . Catalog       : " + dbConfig.getCatalog() );
		appendLine(sb, " . Schema        : " + dbConfig.getSchema() );
		appendLine(sb, " . Table filters : " );
		appendLine(sb, "   - pattern : " + dbConfig.getTableNamePattern() );
		appendLine(sb, "   - types   : " + dbConfig.getTableTypes() );
		appendLine(sb, "   - exclude : " + dbConfig.getTableNameExclude() );
		appendLine(sb, "   - include : " + dbConfig.getTableNameInclude() );
	}
}
