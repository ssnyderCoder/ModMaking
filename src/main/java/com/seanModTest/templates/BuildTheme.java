package com.seanModTest.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Blocks;

public class BuildTheme {
	private static final String PRIMARY = "primary";
	private static final String SECONDARY = "secondary";
	private static final String DAMAGED_PRIMARY = "d_primary";
	private static final String DAMAGED_SECONDARY = "d_secondary";
	private static final String WINDOW = "window";
	private static final String FURNITURE = "furniture";
	private static final String STAIRS = "stairs";
	private static final String SLAB = "slab";
	private static final String FENCE = "fence";
	private static final String DOOR = "door";
	private static final String OTHER = "other";

	//can get damaged variant of any block by prefacing new type with "d_"
	//chance of block being damaged determined by BuildTheme
	
	private static final BuildTheme DEFAULT_BUILD_THEME = new BuildTheme();
	static{
		DEFAULT_BUILD_THEME.addBBData(PRIMARY, "minecraft:stonebrick");
		DEFAULT_BUILD_THEME.addBBData(SECONDARY, "minecraft:cobblestone");
		DEFAULT_BUILD_THEME.addBBData(DAMAGED_PRIMARY, new BuildBlockData("minecraft:stonebrick", 2)); //cracked
		DEFAULT_BUILD_THEME.addBBData(DAMAGED_SECONDARY, "minecraft:mossy_cobblestone");
		DEFAULT_BUILD_THEME.addBBData(WINDOW, "minecraft:glass");
		DEFAULT_BUILD_THEME.addBBData(FURNITURE, "minecraft:jukebox");
		DEFAULT_BUILD_THEME.addBBData(STAIRS, "minecraft:stone_brick_stairs");
		DEFAULT_BUILD_THEME.addBBData(SLAB, "minecraft:stone_slab");
		DEFAULT_BUILD_THEME.addBBData(FENCE, "minecraft:cobblestone_wall");
		DEFAULT_BUILD_THEME.addBBData(DOOR, "minecraft:wooden_door");
		DEFAULT_BUILD_THEME.addBBData(OTHER, "minecraft:dirt");
	}
	//use block names to designate blocks
	private final Map<String, ArrayList<BuildBlockData>> typeToBBData= new HashMap<String, ArrayList<BuildBlockData>>();
	private final Map<String, Float> damagedBlockChances = new HashMap<String, Float>();
	
	public BuildTheme() {
	}
	
	public boolean addBBData(String type, BuildBlockData bbd){
		ArrayList<BuildBlockData> bbdList = typeToBBData.get(type);
		if(bbdList == null){
			bbdList = new ArrayList<BuildBlockData>();
			typeToBBData.put(type, bbdList);
		}
		return bbdList.add(bbd);
	}
	
	public boolean hasBBDataForType(String type){
		return typeToBBData.containsKey(type);
	}
	
	protected Map<String, ArrayList<BuildBlockData>> getTypeToBBData(){
		return typeToBBData;
	}
	
	public boolean addBBData(String type, String blockName){
		return addBBData(type, new BuildBlockData(blockName));
	}
	
	public BuildBlockData getRandomBBData(Random rand, String type){
		ArrayList<BuildBlockData> bbdList = typeToBBData.get(type);
		if(bbdList == null){
			bbdList = DEFAULT_BUILD_THEME.getTypeToBBData().get(type);
			if(bbdList == null) bbdList = DEFAULT_BUILD_THEME.getTypeToBBData().get(OTHER);
		}
		return bbdList.get(rand.nextInt(bbdList.size()));
	}
	
	public boolean addDamageChance(String type, float chance){
		if(typeToBBData.containsKey(type) || DEFAULT_BUILD_THEME.hasBBDataForType(type)){
			damagedBlockChances.put(type, chance);
			return true;
		}
		else
			return false;
	}
	
	public boolean hasDamagedVariant(String type){
		return damagedBlockChances.containsKey(type);
	}
	
	public BuildBlockData getFineOrDamagedBBD(Random rand, BuildBlockData bbd, String type){
		if(!damagedBlockChances.containsKey(type)) return bbd;
		else{
			float damageChance = damagedBlockChances.get(type);
			float f = rand.nextFloat();
			if(f < damageChance) return getRandomBBData(rand, "d_" + type);
			else return bbd;
		}
	}
}
