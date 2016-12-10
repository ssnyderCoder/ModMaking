package com.seanModTest.gen;

import com.seanModTest.structure.GiantPitComponent;
import com.seanModTest.structure.MapGenGiantPit;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModMapGen {

	public static void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new WorldGenForestBeets(), 75);
		MinecraftForge.EVENT_BUS.register(new WorldGenGiantPit());
		MinecraftForge.EVENT_BUS.register(new WorldGenTestCubes());
		//GameRegistry.registerWorldGenerator(new WorldGenGiantPit(), -1); //done by subscribe event instead
		//MapGenStructureIO.registerStructure(MapGenGiantPit.Start.class, "SeanMod:GiantPit");
		//MapGenStructureIO.registerStructureComponent(GiantPitComponent.class, "SeanMod:GiantPitComp");
	}
}
