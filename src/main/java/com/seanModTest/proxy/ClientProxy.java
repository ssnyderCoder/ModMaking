package com.seanModTest.proxy;

import com.seanModTest.tutorial.TestMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
		 ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(TestMod.modId + ":" + id, "inventory"));
	}
}
