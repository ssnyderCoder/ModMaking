package test.sean;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class MockWorld extends World {
	
	private final Map<Long, IBlockState> blocks = new HashMap<Long, IBlockState>();

	public MockWorld() {
		super(null, null, new WorldProviderSurface(), null, false);
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state){
		blocks.put(pos.toLong(), state);
		return true;
	}
	
	@Override
	public IBlockState getBlockState(BlockPos pos){
		return blocks.get(pos.toLong());
	}
	
	public void clearBlocks(){
		blocks.clear();
	}
	

}
