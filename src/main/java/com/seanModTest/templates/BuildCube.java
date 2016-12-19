package com.seanModTest.templates;

import java.util.Random;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public abstract class BuildCube {

	private final TaggedDataProvider<BuildPlanTemplate> dataProvider;
	private final StructureBoundingBox bounds;
	
	public BuildCube(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds) {
		// TODO Auto-generated constructor stub
		dataProvider = tagDataProvider;
		bounds = theBounds;
	}

	public abstract void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme);
	
	public StructureBoundingBox getBounds(){
		return bounds;
	}
	

	public boolean shouldRunLast(){
		return false;
	}
	
	
}
