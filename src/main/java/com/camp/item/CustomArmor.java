package com.camp.item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.example.examplemod.cm;

public class CustomArmor extends ItemArmor{
	public final String name;
	public CustomArmor (ArmorMaterial material, int renderIndex, int armorType, String name) {
		super(material, renderIndex, armorType);
		this.name = name;
		this.setUnlocalizedName(cm.MODID + "_" + name);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
	if(slot == 2){
		return "cm:textures/models/armor/custom_armor_layer_2.png";
	}
	return "cm:textures/models/armor/custom_armor_layer_1.png";
	}
}