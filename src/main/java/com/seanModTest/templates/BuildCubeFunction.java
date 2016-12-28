package com.seanModTest.templates;

import java.util.Random;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BuildCubeFunction extends BuildCube {

	public BuildCubeFunction(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds) throws Exception {
		super(tagDataProvider, theBounds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme,
			BuildModifiers modifiers) {
		// TODO Auto-generated method stub

	}

}
