package com.seanModTest.templates;

import java.util.Random;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BuildCubeTemplate extends BuildCube {
	//has tag requirements

	public BuildCubeTemplate(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds) {
		super(tagDataProvider, theBounds);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme) {
		//get all BuildPlanTemplates that meet tag requirements
		/*search through all that meet size requirements or instead are
		 * Stretchable: Smaller size, but can be stretched along appropriate axises
		 * Repeatable: Smaller size that is factor of required size and that can be tiled along appropriate axises
		 * Rotatable: Can be rotated 90, 180, 270 degrees to fit size requirements
		 */
		//choose template at random
	}

}
