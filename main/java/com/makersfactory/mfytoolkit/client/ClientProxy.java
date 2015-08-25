package com.makersfactory.mfytoolkit.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.makersfactory.mfytoolkit.CommonProxy;
import com.makersfactory.mfytoolkit.GuiGPS;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void doRegistry() {

		// INITIALIZE EVENT LISTENERS
		MinecraftForge.EVENT_BUS.register(new GuiGPS(Minecraft.getMinecraft()));
	}
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.addNewArmourRendererPrefix("gpsMaterial");
	}

}
