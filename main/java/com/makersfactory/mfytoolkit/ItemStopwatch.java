package com.makersfactory.mfytoolkit;

import java.util.Calendar;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemStopwatch extends Item {
	
	public ItemStopwatch() {
		super();
		this.setUnlocalizedName("stopwatch");
		this.setCreativeTab(CreativeTabs.tabTools);
		this.maxStackSize = 1;
		this.hasSubtypes = true;
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p) {
		if (!is.hasTagCompound()) is.setTagCompound(initTag());
		is.getTagCompound().setLong("start", System.currentTimeMillis());
		return is;
		/*
		NBTTagCompound oldTag = is.getTagCompound();
		NBTTagCompound newTag = new NBTTagCompound();
		if (oldTag.getBoolean("ticking")) {
			newTag.setBoolean("ticking", false);
			//newtag.setLong("start", Calendar.getInstance().getTimeInMillis());
		}
		else {
			newTag.setLong("start", Calendar.getInstance().getTimeInMillis());
			newTag.setBoolean("ticking", true);
		}
		*/
		
	}
	
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack is) {
		if (!is.hasTagCompound()) is.setTagCompound(initTag());
		NBTTagCompound newTag = new NBTTagCompound();
		newTag.setLong("start", System.currentTimeMillis());
		is.setTagCompound(newTag);
		return false;
	}
	
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.none;
	}
	
	public static NBTTagCompound initTag() {
		NBTTagCompound result = new NBTTagCompound();
		//result.setBoolean("ticking", false);
		result.setLong("start", System.currentTimeMillis());
		//result.setLong("last", Calendar.getInstance().getTimeInMillis());
		return result;
	}
	
}
