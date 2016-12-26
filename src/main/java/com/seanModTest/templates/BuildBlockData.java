package com.seanModTest.templates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ResourceLocation;

public class BuildBlockData {

	public static final String DEFAULT_BLOCK_NAME = "stone";
	public static final int DEFAULT_META = 0;
	public static final String[] DEFAULT_TILE_DATA = {""};
	
	//perhaps change to use specific data tags instead of meta
	private final String blockName;
	private final int meta;
	private final String[] tileEntityData;
	public BuildBlockData(String uniqueBlockName, int metaValue, String[] tileData) {
		blockName = uniqueBlockName;
		meta = metaValue;
		tileEntityData = tileData;
	}
	
	public BuildBlockData(String uniqueBlockName, int metaValue) {
		blockName = uniqueBlockName;
		meta = metaValue;
		tileEntityData = DEFAULT_TILE_DATA;
	}
	
	public BuildBlockData(String uniqueBlockName) {
		blockName = uniqueBlockName;
		meta = DEFAULT_META;
		tileEntityData = DEFAULT_TILE_DATA;
	}
	
	public BuildBlockData() {
		blockName = DEFAULT_BLOCK_NAME;
		meta = DEFAULT_META;
		tileEntityData = DEFAULT_TILE_DATA;
	}
	
	public String getBlockName() {
		return blockName;
	}
	
	public int getMetaValue() {
		return meta;
	}
	
	public String[] getUnprocessedTileEntityData(){
		return tileEntityData;
	}
	
	public IBlockState getBlockState(){
		Block block = Block.getBlockFromName(blockName);
		if(block != null){
			return block.getStateFromMeta(meta);
		}
		else return null;
	}
	

}
