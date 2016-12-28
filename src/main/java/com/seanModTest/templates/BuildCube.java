package com.seanModTest.templates;

import java.util.Random;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public abstract class BuildCube {

	protected final TaggedDataProvider<BuildPlanTemplate> dataProvider;
	protected final StructureBoundingBox bounds; //bounds should map from 0,0,0 to sizeX, sizeY, sizeZ
	
	public BuildCube(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds) throws Exception {
		// TODO Auto-generated constructor stub
		dataProvider = tagDataProvider;
		bounds = theBounds;
		if(bounds.minX < 0 || bounds.minY < 0 || bounds.minZ < 0) throw new Exception("Bounds cannot be below 0");
	}

	public abstract void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme, BuildModifiers modifiers);
	
	public StructureBoundingBox getBounds(){
		return bounds;
	}
	

	public boolean shouldRunLast(){
		return false;
	}
	
	
}
