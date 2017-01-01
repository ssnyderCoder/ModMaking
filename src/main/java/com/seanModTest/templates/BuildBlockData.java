package com.seanModTest.templates;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ResourceLocation;

public class BuildBlockData {

	public static final String DEFAULT_BLOCK_NAME = "stone";
	public static final int DEFAULT_META = 0;
	public static final String DEFAULT_TILE_JSON = "";
	
	//perhaps change to use specific data tags instead of meta
	private final String blockName;
	private final int meta;
	private final String tileEntityJSON;
	public BuildBlockData(String uniqueBlockName, int metaValue, String tileJSON) {
		blockName = uniqueBlockName;
		meta = metaValue;
		tileEntityJSON = tileJSON;
	}
	
	public BuildBlockData(String uniqueBlockName, int metaValue) {
		blockName = uniqueBlockName;
		meta = metaValue;
		tileEntityJSON = DEFAULT_TILE_JSON;
	}
	
	public BuildBlockData(String uniqueBlockName) {
		blockName = uniqueBlockName;
		meta = DEFAULT_META;
		tileEntityJSON = DEFAULT_TILE_JSON;
	}
	
	public BuildBlockData() {
		blockName = DEFAULT_BLOCK_NAME;
		meta = DEFAULT_META;
		tileEntityJSON = DEFAULT_TILE_JSON;
	}
	
	public String getBlockName() {
		return blockName;
	}
	
	public int getMetaValue() {
		return meta;
	}
	
	public String getTileEntityJSON(){
		return tileEntityJSON;
	}
	
	@Override
	public String toString(){
		return blockName + "___" + meta + "___" + tileEntityJSON;
	}
	
	public IBlockState getBlockState(){
		ResourceLocation resourcelocation = new ResourceLocation(blockName);
		Block block = (Block)Block.REGISTRY.getObject(resourcelocation);
		if(block != null){
			return block.getStateFromMeta(meta);
		}
		else return null;
	}
	

}
