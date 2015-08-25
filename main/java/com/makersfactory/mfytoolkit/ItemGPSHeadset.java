package com.makersfactory.mfytoolkit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGPSHeadset extends ItemArmor {

	public ItemGPSHeadset(ArmorMaterial mat, int id) {
		super(mat, id, 0);
		this.setUnlocalizedName("gpsHeadset");
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setTextureName("mfytoolkit:gpsHeadsetIcon");
		this.maxStackSize = 1;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "mfytoolkit:textures/models/armor/gpsHeadset.png";
	}

}
