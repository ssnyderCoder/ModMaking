package com.seanModTest.gen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import no.geosoft.cc.geometry.Geometry;

public class WorldGenTestCubes implements IWorldGenerator{
	private final int range = 4;
	private static final IBlockState BRIDGE_BLOCKS[] = {Blocks.DIRT.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(),
			Blocks.MOSSY_COBBLESTONE.getDefaultState(), Blocks.WOOL.getDefaultState(), Blocks.NETHERRACK.getDefaultState(),
			Blocks.PRISMARINE.getDefaultState(), Blocks.DIAMOND_ORE.getDefaultState(), Blocks.PURPUR_BLOCK.getDefaultState(),
			Blocks.STAINED_HARDENED_CLAY.getDefaultState(), Blocks.GLOWSTONE.getDefaultState()}; 
	
	@SubscribeEvent
	public void beforeVanillaGen(DecorateBiomeEvent.Pre preGen){
		this.generate(preGen.getRand(), preGen.getPos().getX() >> 4, preGen.getPos().getZ() >> 4,
				preGen.getWorld(), null, preGen.getWorld().getChunkProvider());
	}

	//spawns cubes at random locations, and nearby cubes connect to eachother
	public WorldGenTestCubes() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		spawnCubeIfValid(world, chunkX, chunkZ);
		spawnBridges(world, chunkX, chunkZ);
	}

	private void spawnBridges(World world, int chunkX, int chunkZ) {
		Random rand = new Random(world.getSeed());
		long l1 = rand.nextLong();
		long l2 = rand.nextLong();
		//get boundaries for all cubes within range
		ArrayList<StructureBoundingBox> boundlist = new ArrayList<StructureBoundingBox>();
		for(int i = -range; i <= range; i++)
			for(int j = -range; j <= range; j++){
				long lX = (long)(chunkX+i) * l1;
				long lZ = (long)(chunkZ+j) * l2;
				rand.setSeed(lX ^ lZ ^ world.getSeed());
				StructureBoundingBox bb = getBounds(rand, chunkX + i, chunkZ + j, world );
				if(bb != null) boundlist.add(bb);
			}
		//get all pairs of cubes with a bridge between them that enter this chunk and generate the bridges
		for(int si = 0; si < boundlist.size() - 1; si++){
			StructureBoundingBox bb1 = boundlist.get(si);
			int sj = si + 1;
			while(sj < boundlist.size()){
				StructureBoundingBox bb2 = boundlist.get(sj);
				//get line from center of both cubes to each other
				int cx1 = bb1.minX + bb1.getXSize() / 2;
				int cz1 = bb1.minZ + bb1.getZSize() / 2;
				int cx2 = bb2.minX + bb2.getXSize() / 2;
				int cz2 = bb2.minZ + bb2.getZSize() / 2;
				int chunkCoordX = chunkX * 16;
				int chunkCoordZ = chunkZ * 16;
				int chunkDistX = Math.abs((bb1.minX >> 4) - (bb2.minX >> 4));
				int chunkDistZ = Math.abs((bb1.minZ >> 4) - (bb2.minZ >> 4));
				if(chunkDistX <= range && chunkDistZ <= range && Geometry.isLineIntersectingRectangle(cx1, cz1, cx2, cz2, chunkCoordX, chunkCoordZ, chunkCoordX + 16, chunkCoordZ + 16))
					generateBridge(world, bb1, bb2, chunkCoordX, chunkCoordZ);
				sj++;
			}
		}
	}

	//can make more efficient later
	private void generateBridge(World world, StructureBoundingBox bb1, StructureBoundingBox bb2,
			int chunkCoordX, int chunkCoordZ) {
		//trace along the center line and generate blocks when it is within chunk, but not within bounding boxes
		int cx1 = bb1.minX + bb1.getXSize() / 2;
		int cz1 = bb1.minZ + bb1.getZSize() / 2;
		int cx2 = bb2.minX + bb2.getXSize() / 2;
		int cz2 = bb2.minZ + bb2.getZSize() / 2;
		int distX = Math.abs(cx1 - cx2);
		int distZ = Math.abs(cz1 - cz2);
		double skipSize = distX > distZ ? 1.0D / distX : 1.0D / distZ;
		final IBlockState block = getBridgeBlock(world, bb1);
		skipSize *= 0.5;
		int multiplier = 0;
		System.out.println("Bridge spawned between " + cx1 + " " + cz1 + " and " + cx2 + " " + cz2 +" skip: " + skipSize);
		while(multiplier * skipSize <= 1){
			double point[] = Geometry.computePointOnLine(cx1, cz1, cx2, cz2, skipSize * multiplier);
			int px = (int) point[0];
			int pz = (int) point[1];
			if(Geometry.isPointInsideRectangle(chunkCoordX, chunkCoordZ, chunkCoordX+16, chunkCoordZ+16, px, pz)
					&& !Geometry.isPointInsideRectangle(bb1.minX, bb1.minZ, bb1.maxX+1, bb1.maxZ+1, px, pz)
					&& !Geometry.isPointInsideRectangle(bb2.minX, bb2.minZ, bb2.maxX+1, bb2.maxZ+1, px, pz)){
				double cy1 = bb1.minY + bb1.getYSize() / 2;
				double cy2 = bb2.minY + bb2.getYSize() / 2;
				int py = (int) (cy1 + (skipSize * multiplier) * (cy2 - cy1));
				world.setBlockState(new BlockPos(px, py, pz), block, 2);
			}
			multiplier++;
		}
		
	}

	private IBlockState getBridgeBlock(World world, StructureBoundingBox bb) {
		Random rand = new Random(world.getSeed());
		rand.nextLong();
		rand.nextLong();
		long l1 = rand.nextLong();
		long l2 = rand.nextLong();
		long lX = (long)(bb.minX / 16) * l1;
		long lZ = (long)(bb.minZ / 16) * l2;
		rand.setSeed(lX ^ lZ ^ world.getSeed());
		return BRIDGE_BLOCKS[rand.nextInt(BRIDGE_BLOCKS.length)];
		
	}

	private void spawnCubeIfValid(World world, int chunkX, int chunkZ) {
				//Check if valid spawning location for cube and spawn it if so
				Random rand = new Random(world.getSeed());
				long l1 = rand.nextLong();
				long l2 = rand.nextLong();
				
				long lX = (long)chunkX * l1;
				long lZ = (long)chunkZ * l2;
				rand.setSeed(lX ^ lZ ^ world.getSeed());
				StructureBoundingBox bounds = getBounds(rand, chunkX, chunkZ, world);
				if(bounds != null){
					System.out.println("Cube created at " + bounds.minX + " " + bounds.minZ);
					for(int i = bounds.minX; i <= bounds.maxX; i++)
						for(int j = bounds.minZ; j <= bounds.maxZ; j++)
							for(int k = bounds.minY; k <= bounds.maxY; k++){
								world.setBlockState(new BlockPos(i, k, j), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
							}
				}
		
	}

	private StructureBoundingBox getBounds(Random random, int chunkX, int chunkZ, World world) {
		if(cubeSpawnsHere(random)){
			int xCoord = chunkX * 16 + random.nextInt(4);
			int yCoord = 70 + random.nextInt(4);
			int zCoord = chunkZ * 16 + random.nextInt(4);
			int size = 4 + random.nextInt(6);
			return new StructureBoundingBox(xCoord, yCoord, zCoord, xCoord + size, yCoord + size, zCoord + size);
		}
		else return null;
	}

	private boolean cubeSpawnsHere(Random random) {
		return random.nextFloat() < 0.04;
	}

}
