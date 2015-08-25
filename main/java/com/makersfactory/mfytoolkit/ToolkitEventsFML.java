package com.makersfactory.mfytoolkit;

import com.ibm.icu.util.Calendar;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ToolkitEventsFML {
	
	public static long elapsed = 0;
	
	public ToolkitEventsFML() {
		elapsed = System.currentTimeMillis();
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent e) {
		ItemStack is = e.entityPlayer.getCurrentEquippedItem();
		if (is != null && is.getItem() == MFYToolkit.stopwatch) {
			e.world.setBlock(e.x, e.y, e.z, Blocks.gold_block);
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if (!e.player.worldObj.isRemote && e.phase == e.phase.END) {
			for (ItemStack is : e.player.inventory.mainInventory) {
				if (is != null) {
					if (is.getItem() == MFYToolkit.stopwatch) {
						if (!is.hasTagCompound()) is.setTagCompound(ItemStopwatch.initTag());
						long diff = System.currentTimeMillis() - is.getTagCompound().getLong("start");
						NBTTagCompound newTag = is.getTagCompound();
						newTag.setLong("diff", diff);
						is.setTagCompound(newTag);
					}
				}
			}
		}
	}
	
}
