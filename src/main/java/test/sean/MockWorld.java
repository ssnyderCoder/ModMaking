package test.sean;

import java.util.HashMap;
import java.util.Map;

import com.seanModTest.templates.BuildBlockData;

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
	
	private final Map<Long, BuildBlockData> blocks = new HashMap<Long, BuildBlockData>();

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
	
	public boolean setBlockData(BlockPos pos, BuildBlockData state){
		blocks.put(pos.toLong(), state);
		return true;
	}
	
	public BuildBlockData getBlockData(BlockPos pos){
		return blocks.get(pos.toLong());
	}
	
	public void clearBlocks(){
		blocks.clear();
	}
	

}
