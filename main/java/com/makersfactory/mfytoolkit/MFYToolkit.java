package com.makersfactory.mfytoolkit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

import com.makersfactory.mfytoolkit.GuiGPS;

@Mod(modid="MFYToolkit", name="MFYToolkit", version="1.0")
public class MFYToolkit {

	@Instance(value = "1")
	public static MFYToolkit instance;
	
	@SidedProxy(clientSide="com.makersfactory.mfytoolkit.client.ClientProxy", serverSide="com.makersfactory.mfytoolkit.CommonProxy")
	public static CommonProxy proxy;
	
	public static Item handheldGPS = new Item()
		.setUnlocalizedName("handheldGPS")
		.setCreativeTab(CreativeTabs.tabTools)
		.setTextureName("mfytoolkit:handheldGPS");
	public static ItemStopwatch stopwatch = new ItemStopwatch();
	public static ArmorMaterial gpsMaterial = EnumHelper.addArmorMaterial("gpsMaterial", 15, new int[]{0,0,0,0}, 9);
	public static ItemGPSHeadset gpsHeadset = new ItemGPSHeadset(gpsMaterial, 5);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new ToolkitEventsFML());
		GameRegistry.registerItem(handheldGPS, "handheldGPS");
		GameRegistry.registerItem(gpsHeadset, "gpsHeadset");
		GameRegistry.registerItem(stopwatch, "stopwatch");
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		proxy.doRegistry();
		proxy.registerRenderers();

		ItemStack oneIron = new ItemStack(Items.iron_ingot);
		ItemStack oneCompass = new ItemStack(Items.compass);
		ItemStack oneMap = new ItemStack(Items.map);
		ItemStack oneRedstone = new ItemStack(Items.redstone);
		ItemStack oneGPS = new ItemStack(handheldGPS);
		ItemStack oneGlass = new ItemStack(Blocks.glass_pane);
		ItemStack oneGPSHeadset = new ItemStack(gpsHeadset);
		
		GameRegistry.addRecipe(oneGPS, "ici", "iri", "imi", 'i', oneIron, 'c', oneCompass, 'r', oneRedstone, 'm', oneMap);
		GameRegistry.addRecipe(oneGPSHeadset, "igi", " s ", 'i', oneIron, 'g', oneGPS, 's', oneGlass);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
