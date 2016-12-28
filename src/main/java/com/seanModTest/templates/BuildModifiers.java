package com.seanModTest.templates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.EnumFacing;

public class BuildModifiers {

	
	private EnumFacing facing = EnumFacing.NORTH;
	
	public BuildModifiers() {
		
	}
	
	public void setFacing(EnumFacing face){
		facing = face;
	}
	
	public EnumFacing getFacing(){
		return facing;
	}

	//later in development, add some static applyX() methods that can be called later
}
