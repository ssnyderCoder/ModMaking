package com.seanModTest.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class GiantPitComponent extends StructureComponent {

	private int pitRadius;
	private int pitDepth;
	public GiantPitComponent() {
		pitRadius = 0;
		pitDepth = 0;
		}
	

	public GiantPitComponent(Random rand, int x, int y, int z, int radius, int depth) {
		super(0);
		pitRadius = radius;
		pitDepth = depth;
		this.boundingBox = new StructureBoundingBox(x, y - depth, z, x + radius * 2, y, z  + radius * 2);
		this.setCoordBaseMode(EnumFacing.random(rand));
		System.out.println("Pit component created at " + x + " " + z);
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("Radius", pitRadius);
        tagCompound.setInteger("Depth", pitDepth);

	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound) {
        pitRadius = tagCompound.getInteger("Radius");
        pitDepth = tagCompound.getInteger("Depth");

	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		this.fillWithAirCircle(worldIn, structureBoundingBoxIn, randomIn, 0, 0, 0, pitRadius * 2, pitDepth, pitRadius * 2);
		System.out.println("Pit spawned at " + structureBoundingBoxIn.minX + " " + structureBoundingBoxIn.maxY + " " + structureBoundingBoxIn.minZ);
		return true;
	}
	//make it ignore water, perhaps clear all blocks above this area up to 128, or find way to make it occur before other decorative world gens.
	protected void fillWithAirCircle(World worldIn, StructureBoundingBox structurebb, Random randomIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        for (int i = minY; i <= maxY; ++i)
        {
            for (int j = minX; j <= maxX; ++j)
            {
                for (int k = minZ; k <= maxZ; ++k)
                {
                	int radi = (int) ((this.pitRadius*1.0F) * ((i*1.0F) / (maxY*1.0F)));
                	//check if xz within radius
                	int centerX = minX + ((maxX - minX) / 2);
                	int centerZ = minZ + ((maxZ - minZ) / 2);
                	int distX = j - centerX;
                	int distZ = k - centerZ;
                	int distSq = (distX * distX) + (distZ * distZ);
                	int radSq = radi*radi;
                	if(distSq - radSq <= -3){
                		this.clearBlock(worldIn, j, i, k, structurebb);
                		if(i == maxY - 1) this.clearAboveBlocks(worldIn, j, i, k, structurebb);
                	}
                	else if (distSq - radSq <= 0 && randomIn.nextFloat() < 0.6){
                		this.clearBlock(worldIn, j, i, k, structurebb);
                	}
                	else if (distSq - radSq <= 8){
                		this.putOre(worldIn, randomIn, j, i, k, structurebb);
                	}
                }
            }
        }
    }


	private void clearAboveBlocks(World worldIn, int j, int i, int k, StructureBoundingBox structurebb) {
		int yCoord = this.getYWithOffset(i);
		for(int offset = 0; yCoord + offset < 128; offset++){
			BlockPos blockpos = new BlockPos(this.getXWithOffset(j, k), this.getYWithOffset(i + offset), this.getZWithOffset(j, k));
			worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
		}
	}


	private void clearBlock(World worldIn, int j, int i, int k,
			StructureBoundingBox structurebb) {
		Block block = getBlockStateFromPos(worldIn, i, j, k, structurebb).getBlock();
		if(block instanceof BlockLiquid || block == Blocks.BEDROCK)
			return;
		
		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), j, i, k, structurebb);
	}


	private void putOre(World worldIn, Random randomIn, int j, int i, int k,
			StructureBoundingBox structurebb) {
		if(getBlockStateFromPos(worldIn, j, i, k, structurebb).getBlock() == Blocks.STONE){
			float chance = randomIn.nextFloat();
			int yCoord = this.getYWithOffset(i);
			if(yCoord <= 30 && chance < 0.01) this.setBlockState(worldIn, Blocks.DIAMOND_ORE.getDefaultState(), j, i, k, structurebb);
			else if(yCoord <= 30 && chance < 0.07) this.setBlockState(worldIn, Blocks.REDSTONE_ORE.getDefaultState(), j, i, k, structurebb);
			else if(yCoord <= 35 && chance < 0.09) this.setBlockState(worldIn, Blocks.LAPIS_ORE.getDefaultState(), j, i, k, structurebb);
			else if(yCoord <= 40 && chance < 0.1) this.setBlockState(worldIn, Blocks.EMERALD_ORE.getDefaultState(), j, i, k, structurebb);
			else if(yCoord <= 45 && chance < 0.13) this.setBlockState(worldIn, Blocks.GOLD_ORE.getDefaultState(), j, i, k, structurebb);
			else if(yCoord <= 60 && chance < 0.19) this.setBlockState(worldIn, Blocks.IRON_ORE.getDefaultState(), j, i, k, structurebb);
			else if(chance < 0.3) this.setBlockState(worldIn, Blocks.COAL_ORE.getDefaultState(), j, i, k, structurebb);
		}
		
	}

}
