package com.makersfactory.mfytoolkit;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiGPS extends Gui {

	private Minecraft mc;
	public static int GUI_ID = 20;

	public GuiGPS(Minecraft mc) {
		super();
		this.mc = mc;
	}

	static final ResourceLocation guiTexture = new ResourceLocation("mfytoolkit:textures/gui/gps-background.png");
	static final int LS = 10;
	static final int TOP = 5;
	static final int LEFT = 5;
	static final double AL = 3.0;
	static final int BG_W = 102;
	static final int BG_H = 55;
	static final int PAD_LEFT = 8;
	static final int PAD_TOP = 6;
	static final int LT_EDGE = 16;
	
	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent e) {
		if(e.isCancelable() || e.type != ElementType.CROSSHAIRS)
	    {      
	      return;
	    }
		ItemStack currItem = this.mc.thePlayer.getCurrentEquippedItem();
		ItemStack currHelmet = this.mc.thePlayer.getCurrentArmor(3);
		if (	(currItem != null && currItem.getItem() == MFYToolkit.handheldGPS) || 
				(currHelmet != null && currHelmet.getItem() == MFYToolkit.gpsHeadset)) {
			// Draw Background
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);      
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(LEFT, TOP, 0, 0, BG_W, BG_H);
			
			// Calculate coordinates and direction
			String l1 = "X: " + (int)Math.floor(this.mc.thePlayer.posX);
			String l2 = "Y: " + (int)Math.floor(this.mc.thePlayer.posY);
			String l3 = "Z: " + (int)Math.floor(this.mc.thePlayer.posZ);
			String l4 = "Heading: ";
			
			double a = mc.thePlayer.rotationYaw;
			while (a < 0) a += 360.0;
			a = a % 360;
			String angleWord = cardinal(a);		
			boolean aligned = (a < AL) || (a > 360.0-AL) || 
							   (a > 90-AL && a < 90+AL) ||
							   (a > 180-AL && a < 180+AL) ||
							   (a > 270-AL && a < 270+AL);
			//direction = (per == 100 ? "Exactly " : (per > 95 ? "Mostly " : "Somewhat ")) + direction;
			
			// Draw coordinates and direction
			this.drawString(mc.fontRenderer, l1, LEFT+PAD_LEFT, TOP+PAD_TOP, 0xff7070);
			this.drawString(mc.fontRenderer, l2, LEFT+PAD_LEFT, TOP+PAD_TOP+LS, 0x3dff87);
			this.drawString(mc.fontRenderer, l3, LEFT+PAD_LEFT, TOP+PAD_TOP+(2*LS), 0x758aff);
			
			this.drawString(mc.fontRenderer, "Aligned: ", LEFT+PAD_LEFT, TOP+PAD_TOP+(int)(3.5*LS)-2, 0xffffff);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(LEFT+BG_W-PAD_LEFT-LT_EDGE+2, TOP+BG_H-PAD_TOP-LT_EDGE+1, 149, (aligned ? 0 : 34), 16, 16);
			
			//this.drawString(mc.fontRenderer, angleWord, LEFT+60, TOP+LS, 0xFFFFFF);
			//drawCardinalCentered(angleWord, LEFT+73, TOP+PAD+2);
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			this.drawString(mc.fontRenderer, angleWord, (int)((LEFT+BG_W)/2)-(6*angleWord.length())-3, (int)((TOP+(LS/2))/2)+1, 0xffffff);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}
		else if (currItem != null && currItem.getItem() == MFYToolkit.stopwatch) {
			// Background
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);      
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(LEFT, TOP, 0, 0, BG_W, BG_H);
			
			// Damage
			String line1 = "Time: "+currItem.getTagCompound().getLong("diff");
			this.drawString(mc.fontRenderer, line1, LEFT+PAD_LEFT, TOP+PAD_TOP, 0xff7070);
		}
	}
	
	private void drawCardinalCentered(String dir, int x, int y) {
		int w = 0;
		for (char c : dir.toCharArray()) {
			switch (c) {
			case 'N':
			case 'n': w+= 22; break;
			case 'S':
			case 's': w+= 21; break;
			case 'E':
			case 'e': w+= 20; break;
			case 'W':
			case 'w': w+= 26; break;
			default: break;
			}
		}
		int xc = x - (w/2);
		for (char c : dir.toCharArray()) {
			this.mc.renderEngine.bindTexture(guiTexture);
			switch(c) {
			case 'N':
			case 'n':
				this.drawTexturedModalRect(xc, y, 1, 69, 22, 27);
				xc += 22;
				break;
			case 'S':
			case 's':
				this.drawTexturedModalRect(xc, y, 29, 69, 21, 27);
				xc += 21;
				break;
			case 'E':
			case 'e':
				this.drawTexturedModalRect(xc, y, 57, 69, 20, 27);
				xc += 20;
				break;
			case 'W':
			case 'w':
				this.drawTexturedModalRect(xc, y, 85, 69, 26, 27);
				xc += 26;
				break;
			}
		}
	}
	
	private String cardinal(double a) {
		if (a < 11.25) return "S";
		if (a < 33.75) return "SSW";
		if (a < 56.25) return "SW";
		if (a < 78.75) return "WSW";
		if (a < 101.25) return "W";
		if (a < 123.75) return "WNW";
		if (a < 146.25) return "NW";
		if (a < 168.75) return "NNW";
		if (a < 191.25) return "N";
		if (a < 213.75) return "NNE";
		if (a < 236.25) return "NE";
		if (a < 258.75) return "ENE";
		if (a < 281.25) return "E";
		if (a < 303.75) return "ESE";
		if (a < 326.25) return "SE";
		if (a < 348.75) return "SSE";
		return "S";
	}

}
