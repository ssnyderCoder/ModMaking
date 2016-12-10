package com.seanModTest.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenForestBeets implements IWorldGenerator {
	private final Random genRand;
    private BlockBeetroot beets;
    private IBlockState state;
	
	public WorldGenForestBeets(){
		genRand = new Random();
		beets = (BlockBeetroot) Blocks.BEETROOTS;
		state = beets.withAge(3);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		//make sure that biome is a forest
		final BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		final Biome currentBiome = world.getBiomeProvider().getBiomeGenerator(new BlockPos(chunkPos.getX() + 8, 0, chunkPos.getZ() + 8));
		if(BiomeDictionary.isBiomeOfType(currentBiome, BiomeDictionary.Type.FOREST)){
			//check if valid region to spawn the beets
			int regX = chunkX >> 5;
			int regZ = chunkZ >> 5;
			long newSeed = (long)regX * 341873128712L + (long)regZ * 132897987541L + world.getSeed() + 14357617L;
			genRand.setSeed(newSeed);
			float spawnChance = regX > 8 || regZ > 8 ? 0.25F : 0.125F; // spawns more further out
			if(genRand.nextFloat() > spawnChance) return;
			
			//spawn the beets
			genBeets(world, random, chunkPos.add(random.nextInt(16), 60 + random.nextInt(20), random.nextInt(16)));
		}
	}

	private void genBeets(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255) && isValidSoil(worldIn, blockpos))
            {
            	worldIn.setBlockState(blockpos.down(), Blocks.FARMLAND.getDefaultState(), 2);
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }
	}

	private boolean isValidSoil(World worldIn, BlockPos blockpos) {
		IBlockState soil = worldIn.getBlockState(blockpos.down());
		return (worldIn.getLight(blockpos) >= 8 || worldIn.canSeeSky(blockpos)) && soil.getBlock().canSustainPlant(soil, worldIn, blockpos.down(), net.minecraft.util.EnumFacing.UP, Blocks.RED_FLOWER);
	}

}
