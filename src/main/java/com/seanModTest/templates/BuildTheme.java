package com.seanModTest.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final BuildTheme DEFAULT_BUILD_THEME = new BuildTheme();
	{
		
	}
	//use block names to designate blocks
	private final Map<String, ArrayList<String>> typeToBlockData= new HashMap<String, ArrayList<String>>();
	public BuildTheme() {

	}

}
