package com.seanModTest.templates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BuildPlanTemplate {

	private final Set<String> tags = new HashSet<String>();
	private final Set<String> modifiers = new HashSet<String>(); // allow a template to be rotated, stretched, repeated, etc
	private final List<BuildCube> buildCubes = new ArrayList<BuildCube>();
	private final TaggedDataProvider<BuildPlanTemplate> dataProvider;
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
	
	public BuildPlanTemplate(String uniqueName, int sizeX, int sizeY, int sizeZ, TaggedDataProvider<BuildPlanTemplate> templateProvider) {
		name = uniqueName;
		xSize = sizeX;
		ySize = sizeY;
		zSize = sizeZ;
		dataProvider = templateProvider;
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
	
	public void addBuildCube(BuildCube bc){
		buildCubes.add(bc);
	}
	/*
	 * world Current World
	 * rand Current random number generator
	 * chunkX, chunkZ Chunk coordinates
	 * startPos Position that this template is supposed to generate at
	 * theme the Theme to apply
	 * (Later on also pass rotational, stretch, copy data)
	 */
	public void generateInChunk(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme){
		StructureBoundingBox chunkBounds = new StructureBoundingBox(chunkX * 16, chunkZ * 16, chunkX*16 + 15, chunkZ*16 + 15);
		for(BuildCube bc : buildCubes){
			StructureBoundingBox cubeBounds = new StructureBoundingBox(bc.getBounds());
			cubeBounds.offset(startPos.getX(), startPos.getY(), startPos.getZ());
			if(chunkBounds.intersectsWith(cubeBounds)) bc.generate(world, rand, chunkX, chunkZ, startPos, theme);
		}
	}

}
