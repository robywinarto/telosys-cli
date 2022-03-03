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

import org.telosys.tools.cli.CommandWithModel;
import org.telosys.tools.cli.Environment;
import org.telosys.tools.cli.commons.CriteriaUtil;
import org.telosys.tools.dsl.DslModelUtil;

import jline.console.ConsoleReader;

public class ListEntitiesCommand extends CommandWithModel {

	/**
	 * Constructor
	 * @param out
	 */
	public ListEntitiesCommand(ConsoleReader consoleReader, Environment environment) {
		super(consoleReader, environment);
	}
	
	@Override
	public String getName() {
		return "le";
	}

	@Override
	public String getShortDescription() {
		return "List Entities" ;
	}

	@Override
	public String getDescription() {
		return "List the entities defined in the current model";
	}
	
	@Override
	public String getUsage() {
		return "le [*|pattern|pattern1,pattern2,...]";
	}
	
	@Override
	public String execute(String[] args) {
		
		if ( checkArguments(args, 0, 1) && checkHomeDirectoryDefined() && checkModelDefined() ) {
			listEntities(args);
		}
		return null ;		
	}
	
	private void listEntities(String[] args) {
		// Get all entities for the current model
//		File modelFile = getCurrentModelFile();
//		List<String> entities ;
//		if ( isDslModelFile(modelFile) ) {
//			entities = getDslModelEntities(modelFile);
//		}
//		else {
//			entities = getDbModelEntities(modelFile);
//		}
		List<String> entities = DslModelUtil.getEntityNames(getCurrentModelFolder());
		// Apply filter with criteria
		List<String> criteria = CriteriaUtil.buildCriteriaFromArg(args.length > 1 ? args[1] : null) ;
		List<String> result = CriteriaUtil.selectAndSort(entities, criteria);
		if ( result.isEmpty() ) {
			print("No entity");
		}
		else {
			printList( CriteriaUtil.selectAndSort(entities, criteria), " . " );
		}
	}
	
//	private List<String> getDslModelEntities(File modelFile) {
//		List<String> entities = new LinkedList<>();
//		for ( String fileName : DslModelUtil.getEntitiesSimpleFileNames(modelFile) ) {
//			entities.add( StrUtil.removeEnd(fileName, ".entity") ); // TODO : constant
//		}
//		return entities;
//	}
	
//	private List<String> getDbModelEntities(File modelFile) {
//		List<String> entities = new LinkedList<>();
//		Model model = loadModel(modelFile);
//		if ( model != null ) {
//			for ( Entity e : model.getEntities() ) {
//				entities.add(e.getClassName());
//			}
//		}
//		return entities;
//	}
	
	protected void printList(List<String> list, String prefix) {
		for ( String s : list) {
			print(prefix + s );
		}
	}
}
