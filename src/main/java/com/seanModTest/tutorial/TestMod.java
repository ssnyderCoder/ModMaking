package com.seanModTest.tutorial;

import com.seanModTest.gen.ModMapGen;
import com.seanModTest.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TestMod.modId, name = TestMod.name, version = TestMod.version, acceptedMinecraftVersions = "[1.10.2]")
public class TestMod {
	@SidedProxy(serverSide = "com.seanModTest.proxy.CommonProxy", clientSide = "com.seanModTest.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static final String modId = "test";
	public static final String name = "Test Mod";
	public static final String version = "1.0.0";
	
	@Mod.Instance(modId)
	public static TestMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ModMapGen.registerWorldGenerators();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}

