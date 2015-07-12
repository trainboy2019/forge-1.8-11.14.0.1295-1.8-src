package com.camp.item;
 
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class ItemManager {
 
    public static CustomItem customItem;
    public static CustomPickaxe customPickaxe;
    public static CustomAxe customAxe;
    public static CustomHoe customHoe;
    public static CustomShovel customShovel;
    public static CustomSword customSword;
    public static LightningOrb lightningOrb;
    public static CustomArmor customHelmet;
    public static CustomArmor customChestplate;
    public static CustomArmor customLeggings;
    public static CustomArmor customBoots;
     
    public static void mainRegistry() {
        initializeItem();
        registerItem();
    }
 
    public static void initializeItem() {
        customItem = new CustomItem();
        customPickaxe = new CustomPickaxe(ToolMaterial.EMERALD);
        customAxe = new CustomAxe(ToolMaterial.EMERALD);
        customHoe = new CustomHoe(ToolMaterial.EMERALD);
        customShovel = new CustomShovel(ToolMaterial.EMERALD);
        customSword = new CustomSword(ToolMaterial.EMERALD);
        lightningOrb= new LightningOrb();
        customHelmet = new CustomArmor(ArmorMaterial.IRON, 0, 0, "CustomHelmet");
        customChestplate = new CustomArmor(ArmorMaterial.IRON, 0, 1, "CustomChestplate");
        customLeggings = new CustomArmor(ArmorMaterial.IRON, 0, 2, "CustomLeggings");
        customBoots = new CustomArmor(ArmorMaterial.IRON, 0, 3, "CustomBoots");

    }
 
    public static void registerItem() {
        GameRegistry.registerItem(customItem, customItem.name);
        GameRegistry.registerItem(customPickaxe, customPickaxe.name);
        GameRegistry.registerItem(customAxe, customAxe.name);
        GameRegistry.registerItem(customHoe, customHoe.name);
        GameRegistry.registerItem(customShovel, customShovel.name);
        GameRegistry.registerItem(customSword, customSword.name);
        GameRegistry.registerItem(lightningOrb, lightningOrb.name);
        GameRegistry.registerItem(customHelmet, customHelmet.name);
        GameRegistry.registerItem(customChestplate, customChestplate.name);
        GameRegistry.registerItem(customLeggings, customLeggings.name);
        GameRegistry.registerItem(customBoots, customBoots.name);
    }

	
 
}