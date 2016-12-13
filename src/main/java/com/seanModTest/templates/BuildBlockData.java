package com.seanModTest.templates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BuildBlockData {

	//perhaps change to use specific data tags instead of meta
	private final String blockName;
	private final int meta;
	public BuildBlockData(String uniqueBlockName, int metaValue) {
		blockName = uniqueBlockName;
		meta = metaValue;
	}
	public String getBlockName() {
		return blockName;
	}
	public int getMetaValue() {
		return meta;
	}
	public IBlockState getBlockState(){
		Block block = Block.getBlockFromName(blockName);
		if(block != null){
			block.getStateFromMeta(meta);
		}
		return null;
	}

}
