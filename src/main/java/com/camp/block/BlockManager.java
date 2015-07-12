package com.camp.block;
 
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockDragonEgg;
 
 
public class BlockManager {
 
    public static CustomBlock customBlock;
    public static SmashBlock smashBlock;
    public static CustomPortalBlock customPortalBlock;
    public static BlockCommandBlock commandBlock;
    public static CustomBarrier customBarrier;
    public static BlockDragonEgg dragonEgg;
    public static DeathBlock deathBlock;
    
   // public static BluestoneWire bluestoneWire;
     
    public static void mainRegistry() {
        initializeBlock();
        registerBlock();
    }
 
    public static void initializeBlock() {
    	
        customBlock = new CustomBlock();
        smashBlock = new SmashBlock();
        customPortalBlock = new CustomPortalBlock();
        commandBlock = (BlockCommandBlock) new BlockCommandBlock().setCreativeTab(CreativeTabs.tabRedstone);
        customBarrier = new CustomBarrier();
        dragonEgg = (BlockDragonEgg) new BlockDragonEgg().setCreativeTab(CreativeTabs.tabMisc);
        deathBlock = new DeathBlock();
       // bluestoneWire= new BluestoneWire();
    }
 
    public static void registerBlock() {
    	
        GameRegistry.registerBlock(customBlock, customBlock.name);
        GameRegistry.registerBlock(smashBlock, smashBlock.name);
        GameRegistry.registerBlock(customPortalBlock, customPortalBlock.name);
        GameRegistry.registerBlock(deathBlock, deathBlock.name);
       // GameRegistry.registerBlock(bluestoneWire, bluestoneWire.name);
   //     GameRegistry.registerBlock(commandBlock, "CommandBlock");
     //   GameRegistry.registerBlock(customBarrier, "CustomBarrier");
       // GameRegistry.registerBlock(dragonEgg, "DragonEgg");
        
    }
 
}