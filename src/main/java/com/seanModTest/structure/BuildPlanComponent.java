package com.seanModTest.structure;

import java.util.Random;

import com.seanModTest.templates.BuildPlan;
import com.seanModTest.templates.BuildTheme;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class BuildPlanComponent extends StructureComponent {
	private BuildPlan buildPlan;
	private BuildTheme buildTheme;

	public BuildPlanComponent() {
		// TODO Auto-generated constructor stub
	}

	public BuildPlanComponent(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		// TODO Auto-generated method stub
		return false;
	}

}
