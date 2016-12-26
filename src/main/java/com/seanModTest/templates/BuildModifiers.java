package com.seanModTest.templates;

import java.util.HashSet;
import java.util.Set;

public class BuildModifiers {

	/* Build can be rotated along the y axis*/
	public static final String CAN_ROTATE = "can_rotate";
	/* Build can be stretched along the x axis*/
	public static final String CAN_STRETCH_X = "can_stretch_x";
	/* Build can be repeated along the y axis */
	public static final String CAN_STRETCH_Y = "can_stretch_y";
	/* Build can be repeated along the z axis */
	public static final String CAN_STRETCH_Z = "can_stretch_z";
	/* Build can be repeated along the x axis */
	public static final String CAN_REPEAT_X = "can_repeat_x";
	/* Build can be repeated along the y axis */
	public static final String CAN_REPEAT_Y = "can_repeat_y";
	/* Build can be repeated along the z axis */
	public static final String CAN_REPEAT_Z = "can_repeat_z";
	
	private final Set<String> modifiers = new HashSet<String>();
	
	public BuildModifiers() {
	}
	
	public boolean addModifier(String mod){
		return modifiers.add(mod);
	}
	
	public Set<String> getModifiers(){
		return modifiers;
	}

	//later in development, add some static applyX() methods that can be called later
}
