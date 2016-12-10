package com.seanModTest.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenGiantPit extends MapGenStructure {

	public MapGenGiantPit() {
		}

	@Override
	public String getStructureName() {
		return "Giant Pit";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return (chunkX % 2) == 0 && (chunkZ % 2) == 0;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		System.out.println("Pit structure start created at " + chunkX + " " + chunkZ);
		return new MapGenGiantPit.Start(this.worldObj, this.rand, chunkX, chunkZ);
	}
	
	 public static class Start extends StructureStart
     {
         public Start()
         {
         }

         public Start(World worldIn, Random random, int chunkX, int chunkZ)
         {
             super(chunkX, chunkZ);

             int coordX = chunkX * 16 + random.nextInt(16);
             int coordY = 60 + random.nextInt(30);
             int coordZ = chunkZ * 16 + random.nextInt(16);
             int radius = 6 + random.nextInt(22);
             int depth = 8 + random.nextInt(20);
             this.components.add(new GiantPitComponent(random, coordX, coordY, coordZ, radius, depth));
             this.updateBoundingBox();
         }
     }

}
