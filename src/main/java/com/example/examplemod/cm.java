package com.example.examplemod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.camp.IkeTab;
import com.camp.biome.CustomBiome;
import com.camp.block.BlockManager;
import com.camp.entity.CustomMob;
import com.camp.entity.ModelJoe;
import com.camp.entity.RenderCustomBiped;
import com.camp.init.ModBiomes;
import com.camp.item.ItemManager;
import com.camp.world.CustomDungeon;
import com.camp.world.CustomDungeonGen;
import com.camp.world.CustomHouse;
import com.camp.world.CustomHouseGen;
import com.camp.world.CustomTeleporter;
import com.camp.world.CustomVillagePiece;
import com.camp.world.CustomWorldProvider;
import com.camp.world.MapGenScatteredFeatureModBiomes;
import com.camp.world.ModMapGen;
import com.camp.world.TestGen;
//import com.camp.world.CustomHome.House4Garden;
@Mod(modid = cm.MODID, version = cm.VERSION)
public class cm
{
    public static final String MODID = "cm";
    public static final String VERSION = "1.0";
    public static final IkeTab tabIke = new IkeTab("tabIke");
    public static ToolMaterial customToolMaterial;
    public static int lastBiomeID = 0;
    public static BiomeGenBase customBiome;
    public static CustomTeleporter teleporterSurface;
    public static CustomTeleporter teleporterCustom;
     
    @EventHandler
        public void serverStart(FMLServerStartedEvent event){
            this.teleporterSurface = new CustomTeleporter(MinecraftServer.getServer().worldServerForDimension(0));
            this.teleporterCustom = new CustomTeleporter(MinecraftServer.getServer().worldServerForDimension(2));
    }
        
   
    public static int getEmptyBiomeID(){
        int i;
        BiomeGenBase[] array = BiomeGenBase.getBiomeGenArray();
        //loop through biome array to find an empty id.
        for(i = lastBiomeID; i < array.length; i++){
            if( array[i] == null){
                // Found an empty spot
                // Set lastBiomeID then return index.
                lastBiomeID = i;
                return i;
            }
        }
        // didn't find an empty spot
        return -1;
    
    
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    
    
    {
    	customToolMaterial = EnumHelper.addToolMaterial("Custom", 3, 100, 4, 50, 0);
    	ItemManager.mainRegistry();
    	BlockManager.mainRegistry();
    	ModBiomes.registerBiomes();
    	
    	ModMapGen.registerMapGen();
    	
    	createEntity(CustomMob.class, "CustomMob", 0x00FF00, 0xFF0000);
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
  {
    	
    	
    	GameRegistry.registerWorldGenerator(new CustomDungeonGen(), 10000);
    	
    	// Crafting Recipes
    	 
        //Shaped Recipes
        GameRegistry.addShapedRecipe(new ItemStack(Items.book, 1), "xy", "yx", 'x', Blocks.stone, 'y', Blocks.gravel);
        GameRegistry.addShapedRecipe(new ItemStack(Items.carrot, 1), "xyx", "y y", "xyx", 'x', Blocks.stone, 'y', Blocks.gravel);
        GameRegistry.addRecipe(new ItemStack(Items.cooked_chicken), "xxx", "xyx", "xxx", 'x', Items.stick, 'y', Items.chicken);
    	GameRegistry.addRecipe(new ItemStack(BlockManager.customBlock, 1), "xxx", "xyx", "xxx", 'x', Blocks.glass, 'y', Blocks.glowstone);
 
        //Shapeless Recipes
        GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 64), Blocks.dirt);
        
      //End Portal
      		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.anvil,1),ItemManager.customItem);
      		//diamond block crafting
          	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.diamond_block,500000000),Blocks.dragon_egg, Blocks.anvil);
          	//lava crafting
          	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.lava,1),Blocks.dirt, Blocks.cobblestone);
         
        ItemStack dirtStack = new ItemStack(Blocks.dirt);
        ItemStack sandStack = new ItemStack(Blocks.tnt);
        sandStack.setItemDamage(1);
 
        GameRegistry.addShapelessRecipe((sandStack), dirtStack, dirtStack, dirtStack, dirtStack, Items.acacia_door, Items.cake);
        
        //Smelting Recipies
         GameRegistry.addSmelting(new ItemStack(BlockManager.customBlock), new ItemStack(ItemManager.customItem, 4), 0.1f);
         	ItemStack potionPoisonStack = new ItemStack(Items.potionitem, 1, 8292);
    	GameRegistry.addSmelting(Items.poisonous_potato, potionPoisonStack, 0.1f);
    	GameRegistry.addSmelting(Items.rotten_flesh, new ItemStack(Items.leather), 0.1f);
    	//stone brick smelting
    	ItemStack stoneStackCrack = new ItemStack(Blocks.stonebrick);
    	stoneStackCrack.setItemDamage(2);
    	GameRegistry.addSmelting(Blocks.stonebrick, stoneStackCrack, 0.1f);
    	GameRegistry.addSmelting(Blocks.stone, new ItemStack(Blocks.stonebrick), 0.1f);
    	//carpet smelting
    	ItemStack carpetStackWhite = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackOrange = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackMagenta = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackLightBlue = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackYellow = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackLime = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackPink = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackGrey = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackLightGrey = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackCyan = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackPurple = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackBlue = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackBrown = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackGreen = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackRed = new ItemStack(Blocks.carpet);
    	ItemStack carpetStackBlack = new ItemStack(Blocks.carpet);
    	carpetStackWhite.setItemDamage(0);
    	carpetStackOrange.setItemDamage(1);
    	carpetStackMagenta.setItemDamage(2);
    	carpetStackLightBlue.setItemDamage(3);
    	carpetStackYellow.setItemDamage(4);
    	carpetStackLime.setItemDamage(5);
    	carpetStackPink.setItemDamage(6);
    	carpetStackGrey.setItemDamage(7);
    	carpetStackLightGrey.setItemDamage(8);
    	carpetStackCyan.setItemDamage(9);
    	carpetStackPurple.setItemDamage(10);
    	carpetStackBlue.setItemDamage(11);
    	carpetStackBrown.setItemDamage(12);
    	carpetStackGreen.setItemDamage(13);
    	carpetStackRed.setItemDamage(14);
    	carpetStackBlack.setItemDamage(15);
    	GameRegistry.addSmelting(carpetStackWhite, carpetStackOrange, 0.1f);
    	GameRegistry.addSmelting(carpetStackOrange, carpetStackMagenta, 0.1f);
    	GameRegistry.addSmelting(carpetStackMagenta, carpetStackLightBlue, 0.1f);
    	GameRegistry.addSmelting(carpetStackLightBlue, carpetStackYellow, 0.1f);
    	GameRegistry.addSmelting(carpetStackYellow, carpetStackLime, 0.1f);
    	GameRegistry.addSmelting(carpetStackLime, carpetStackPink, 0.1f);
    	GameRegistry.addSmelting(carpetStackPink, carpetStackGrey, 0.1f);
    	GameRegistry.addSmelting(carpetStackGrey, carpetStackLightGrey, 0.1f);
    	GameRegistry.addSmelting(carpetStackLightGrey, carpetStackCyan, 0.1f);
    	GameRegistry.addSmelting(carpetStackCyan, carpetStackPurple, 0.1f);
    	GameRegistry.addSmelting(carpetStackPurple, carpetStackBlue, 0.1f);
    	GameRegistry.addSmelting(carpetStackBlue, carpetStackBrown, 0.1f);
    	GameRegistry.addSmelting(carpetStackBrown, carpetStackGreen, 0.1f);
    	GameRegistry.addSmelting(carpetStackGreen, carpetStackRed, 0.1f);
    	GameRegistry.addSmelting(carpetStackRed, carpetStackBlack, 0.1f);
    	GameRegistry.addSmelting(carpetStackBlack, carpetStackWhite, 0.1f);
    //wool smelting
    	ItemStack woolStackWhite = new ItemStack(Blocks.wool);
    	ItemStack woolStackOrange = new ItemStack(Blocks.wool);
    	ItemStack woolStackMagenta = new ItemStack(Blocks.wool);
    	ItemStack woolStackLightBlue = new ItemStack(Blocks.wool);
    	ItemStack woolStackYellow = new ItemStack(Blocks.wool);
    	ItemStack woolStackLime = new ItemStack(Blocks.wool);
    	ItemStack woolStackPink = new ItemStack(Blocks.wool);
    	ItemStack woolStackGrey = new ItemStack(Blocks.wool);
    	ItemStack woolStackLightGrey = new ItemStack(Blocks.wool);
    	ItemStack woolStackCyan = new ItemStack(Blocks.wool);
    	ItemStack woolStackPurple = new ItemStack(Blocks.wool);
    	ItemStack woolStackBlue = new ItemStack(Blocks.wool);
    	ItemStack woolStackBrown = new ItemStack(Blocks.wool);
    	ItemStack woolStackGreen = new ItemStack(Blocks.wool);
    	ItemStack woolStackRed = new ItemStack(Blocks.wool);
    	ItemStack woolStackBlack = new ItemStack(Blocks.wool);
    	woolStackWhite.setItemDamage(0);
    	woolStackOrange.setItemDamage(1);
    	woolStackMagenta.setItemDamage(2);
    	woolStackLightBlue.setItemDamage(3);
    	woolStackYellow.setItemDamage(4);
    	woolStackLime.setItemDamage(5);
    	woolStackPink.setItemDamage(6);
    	woolStackGrey.setItemDamage(7);
    	woolStackLightGrey.setItemDamage(8);
    	woolStackCyan.setItemDamage(9);
    	woolStackPurple.setItemDamage(10);
    	woolStackBlue.setItemDamage(11);
    	woolStackBrown.setItemDamage(12);
    	woolStackGreen.setItemDamage(13);
    	woolStackRed.setItemDamage(14);
    	woolStackBlack.setItemDamage(15);
    	GameRegistry.addSmelting(woolStackWhite, woolStackOrange, 0.1f);
    	GameRegistry.addSmelting(woolStackOrange, woolStackMagenta, 0.1f);
    	GameRegistry.addSmelting(woolStackMagenta, woolStackLightBlue, 0.1f);
    	GameRegistry.addSmelting(woolStackLightBlue, woolStackYellow, 0.1f);
    	GameRegistry.addSmelting(woolStackYellow, woolStackLime, 0.1f);
    	GameRegistry.addSmelting(woolStackLime, woolStackPink, 0.1f);
    	GameRegistry.addSmelting(woolStackPink, woolStackGrey, 0.1f);
    	GameRegistry.addSmelting(woolStackGrey, woolStackLightGrey, 0.1f);
    	GameRegistry.addSmelting(woolStackLightGrey, woolStackCyan, 0.1f);
    	GameRegistry.addSmelting(woolStackCyan, woolStackPurple, 0.1f);
    	GameRegistry.addSmelting(woolStackPurple, woolStackBlue, 0.1f);
    	GameRegistry.addSmelting(woolStackBlue, woolStackBrown, 0.1f);
    	GameRegistry.addSmelting(woolStackBrown, woolStackGreen, 0.1f);
    	GameRegistry.addSmelting(woolStackGreen, woolStackRed, 0.1f);
    	GameRegistry.addSmelting(woolStackRed, woolStackBlack, 0.1f);
    	GameRegistry.addSmelting(woolStackBlack, woolStackWhite, 0.1f);
    	//horse armor rotation
    	GameRegistry.addSmelting(Items.golden_horse_armor, new ItemStack(Items.iron_horse_armor), 10.0f);
    	GameRegistry.addSmelting(Items.iron_horse_armor, new ItemStack(Items.diamond_horse_armor), 10.0f);
    	GameRegistry.addSmelting(Items.diamond_horse_armor, new ItemStack(Items.golden_horse_armor), 10.0f);
    	
    	
    	
    	
    	
    	RenderingRegistry.registerEntityRenderingHandler(CustomMob.class, new RenderCustomBiped(new ModelJoe(), 0.5f));
    	/*customOreGenerator = new CustomWorldGenerator();
        GameRegistry.registerWorldGenerator(customOreGenerator, 2);*/
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        
        if(event.getSide() == Side.CLIENT)
        {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
 
            renderItem.getItemModelMesher().register(ItemManager.customItem, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customItem.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customHelmet, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customHelmet.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customChestplate, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customChestplate.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customLeggings, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customLeggings.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customBoots, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customBoots.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.lightningOrb, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.lightningOrb.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customPickaxe, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customPickaxe.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customAxe, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customAxe.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customHoe, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customHoe.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customShovel, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customShovel.name, "inventory"));
            renderItem.getItemModelMesher().register(ItemManager.customSword, 0, new ModelResourceLocation(this.MODID + ":" + ItemManager.customSword.name, "inventory"));
            renderItem.getItemModelMesher().register(Item.getItemFromBlock(BlockManager.customBlock), 0, new ModelResourceLocation(this.MODID + ":" + BlockManager.customBlock.name, "inventory"));
            renderItem.getItemModelMesher().register(Item.getItemFromBlock(BlockManager.smashBlock), 0, new ModelResourceLocation(this.MODID + ":" + BlockManager.smashBlock.name, "inventory"));
            renderItem.getItemModelMesher().register(Item.getItemFromBlock(BlockManager.customPortalBlock), 0, new ModelResourceLocation(this.MODID + ":" + BlockManager.customPortalBlock.name, "inventory"));
            renderItem.getItemModelMesher().register(Item.getItemFromBlock(BlockManager.deathBlock), 0, new ModelResourceLocation(this.MODID + ":" + BlockManager.deathBlock.name, "inventory"));
          //  renderItem.getItemModelMesher().register(Item.getItemFromBlock(BlockManager.bluestoneWire), 0, new ModelResourceLocation(this.MODID + ":" + BlockManager.bluestoneWire.name, "inventory"));
            
            
            
            
            DimensionManager.registerProviderType(2,CustomWorldProvider.class, false);
            DimensionManager.registerDimension(2,2);
        }
        
        
        /*int biomeId = getEmptyBiomeID();
        customBiome = new CustomBiome(biomeId, true).setBiomeName("Firestone").setHeight(new Height(0.1f, 0.2f));
        BiomeEntry customEntry = new BiomeEntry(customBiome, 1500);
        BiomeManager.addBiome(BiomeType.WARM, customEntry);
        BiomeManager.addSpawnBiome(customBiome);*/
     // Register village creation classes
        VillagerRegistry.instance().registerVillageCreationHandler(new CustomVillagePiece());
        MapGenStructureIO.registerStructureComponent(TestGen.class, "cm:testgen");
        
        
        VillagerRegistry.instance().registerVillageCreationHandler(new CustomHouseGen());
        MapGenStructureIO.registerStructureComponent(CustomHouse.class, "cm:customhouse");
        
        
        
        addSpawn(CustomMob.class, 10, 30, 50, EnumCreatureType.MONSTER);
  //      VillagerRegistry.instance().registerVillageCreationHandler(new CustomVillagePiece2());
    //    MapGenStructureIO.registerStructureComponent(CustomOceanMonument.class, "cm:oceanmonument");
    }
    
    public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor) {
        int entityId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityId);
        EntityList.entityEggs.put(Integer.valueOf(entityId), new EntityList.EntityEggInfo(entityId, solidColor, spotColor));
    }
    
    public static void addSpawn(Class entityClass, int probability, int min, int max, EnumCreatureType type) {
        for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
            if (BiomeGenBase.getBiomeGenArray()[i] != null) {
                EntityRegistry.addSpawn(entityClass, probability, min, max, type, BiomeGenBase.getBiomeGenArray()[i]);
            }
        }
    }

}