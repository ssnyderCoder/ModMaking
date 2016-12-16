package com.seanModTest.templates;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public interface IBuildCube {

	public void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme);
	public StructureBoundingBox getBounds();
	
	//implement with abstract buildtemplate version and concrete build theme version
}
