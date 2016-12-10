package com.seanModTest.gen;

import java.util.Random;

import com.seanModTest.structure.GiantPitComponent;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGenGiantPit implements IWorldGenerator {

	public WorldGenGiantPit() {
	}
	
	@SubscribeEvent
	public void beforeVanillaGen(DecorateBiomeEvent.Pre preGen){
		this.generate(preGen.getRand(), preGen.getPos().getX() >> 4, preGen.getPos().getZ() >> 4,
				preGen.getWorld(), null, preGen.getWorld().getChunkProvider());
	}

	protected boolean canSpawnStructureAtCoords(Random random, int chunkX, int chunkZ) {
		return (chunkX % 3) == 0 && (chunkZ % 3) == 0 && random.nextFloat() < 0.01;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if(!canSpawnStructureAtCoords(random, chunkX, chunkZ)) return;
		int coordX = chunkX * 16 + random.nextInt(16);
        int coordZ = chunkZ * 16 + random.nextInt(16);
        int coordY = world.getTopSolidOrLiquidBlock(new BlockPos(coordX, 60, coordZ)).getY() + 1;
        int radius = 4 + random.nextInt(13);
        int depth = 8 + random.nextInt(40);
        //uses component for convenience methods
        GiantPitComponent pitComp = new GiantPitComponent(random, coordX, coordY, coordZ, radius, depth);
        pitComp.addComponentParts(world, random, pitComp.getBoundingBox());
	}

}
