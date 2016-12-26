package com.seanModTest.templates;

import java.util.ArrayList;
import java.util.Random;

import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BuildCubeConcrete extends BuildCube {

	private final BuildBlockData[] blocks;
	public BuildCubeConcrete(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds) {
		super(tagDataProvider, theBounds);
		blocks = new BuildBlockData[theBounds.getXSize()*theBounds.getYSize()*theBounds.getZSize()];
	}
	
	public boolean setBlockData(int x, int y, int z, BuildBlockData block){
		if(x < 0 || y < 0 || z < 0 || x > bounds.maxX || y > bounds.maxY ||  z > bounds.maxZ){
			return false;
		}
		blocks[x + y*bounds.getXSize() + z*bounds.getYSize()*bounds.getXSize()] = block;
		return true;
	}
	
	public BuildBlockData getBlockData(int x, int y, int z){
		return blocks[x + y*bounds.getXSize() + z*bounds.getYSize()*bounds.getXSize()];
	}
	
	public void clearAllBlocks(){
		int max  = this.bounds.getXSize()*this.bounds.getYSize()*this.bounds.getZSize();
		for(int i = 0; i < max; i++){
			blocks[i] = null;
		}
	}
	//TODO Test adding block data, generating, and generating with rotation/stretch/repeat

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme,
			BuildModifiers modifiers) {
		final int xSize = bounds.getXSize();
		final int ySize = bounds.getYSize();
		final int zSize = bounds.getZSize();
		for(int i = 0; i < xSize; i++)
			for(int j = 0; j < ySize; j++)
				for(int k = 0; k < zSize; k++){
					BuildBlockData block = blocks[i + j*xSize + k*xSize*ySize];
					if(block != null){
						setBlock(world, startPos, i, j, k, block);
					}
				}
	}

	protected void setBlock(World world, BlockPos startPos, int i, int j, int k, BuildBlockData block) {
		world.setBlockState(startPos.add(i, j, k), block.getBlockState());
	}

}
