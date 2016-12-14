package com.seanModTest.templates;

import java.util.HashSet;
import java.util.Set;

import com.sean.utility.TaggedDataProvider;

public class BuildPlanTemplate {

	private final Set<String> tags = new HashSet<String>();
	private final Set<String> modifiers = new HashSet<String>(); // allow a template to be rotated, stretched, repeated, etc
	private final String name;
	private final int xSize;
	private final int ySize;
	private final int zSize;
	
	/*add BuildCube class that can be 1 of 2 things:
	 * > Abstract: Has measurements(xyz), placement location(xyz), and tags
	 * >>eg. 1x5x5 Wall placed at 0,0,0 in template with "wall, !hasWindows" tags
	 * > Concrete: Has placement location, a 3d array of IDs, and ID identifiers
	 * >>eg. (2x3x2) Wall placed at 0,0,0 with idArray[[0,1][0,1][0,1]][[1,0][1,0][1,0]]
	 * >> with IDs: id0=theme.primary, id1=theme.secondary 
	*/
	
	public BuildPlanTemplate(String uniqueName, int sizeX, int sizeY, int sizeZ) {
		name = uniqueName;
		xSize = sizeX;
		ySize = sizeY;
		zSize = sizeZ;
	}
	
	public boolean addTag(String tag){
		return tags.add(tag);
	}
	
	public boolean addModifier(String modifier){
		return modifiers.add(modifier);
	}
	
	public boolean hasTag(String tag){
		return tags.contains(tag);
	}
	
	public boolean hasModifier(String modifier){
		return modifiers.contains(modifier);
	}
	
	public String getName(){
		return name;
	}
	
	public BuildPlan generateBuildPlan(TaggedDataProvider<BuildPlanTemplate> dataProvider){
		return null;
	}

}
