package com.camp.world;

import com.camp.Logger;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Random;

/**
 * Allows scattered features (jungle/desert temples, witch huts) to spawn in mod biomes equivalent to the vanilla biomes,
 * i.e. any biome registered as JUNGLE, SANDY or SWAMP
 * <p>
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2471489-jungle-and-desert-temple-spawn-biome
 */
public class MapGenScatteredFeatureModBiomes extends MapGenScatteredFeature {
	public final List<BiomeDictionary.Type> biomeTypes = ImmutableList.of(BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.SWAMP);

	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		// These fields are private in the super class and always constant for non-flat worlds so inline them here
		// Flat worlds don't fire InitMapGenEvent, so this class will never be used in a flat world
		final int minDistanceBetweenScatteredFeatures = 8;
		final int maxDistanceBetweenScatteredFeatures = 32;

		if (chunkX < 0) {
			chunkX -= maxDistanceBetweenScatteredFeatures - 1;
		}

		if (chunkZ < 0) {
			chunkZ -= maxDistanceBetweenScatteredFeatures - 1;
		}

		int i1 = chunkX / maxDistanceBetweenScatteredFeatures;
		int j1 = chunkZ / maxDistanceBetweenScatteredFeatures;
		Random random = this.worldObj.setRandomSeed(i1, j1, 14357617);
		i1 *= maxDistanceBetweenScatteredFeatures;
		j1 *= maxDistanceBetweenScatteredFeatures;
		i1 += random.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);
		j1 += random.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);

		if (chunkX == i1 && chunkZ == j1) {
			BiomeGenBase biomegenbase = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8));

			if (biomegenbase != null) {
				for (BiomeDictionary.Type type : biomeTypes) {
					if (BiomeDictionary.isBiomeOfType(biomegenbase, type)) {
						return true;
					}
				}
			}
		}

		return super.canSpawnStructureAtCoords(chunkX, chunkZ);
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(worldObj, rand, chunkX, chunkZ);
	}

	public static class Start extends MapGenScatteredFeature.Start {
		public Start() {}

		@SuppressWarnings("unchecked")
		public Start(World worldIn, Random random, int chunkX, int chunkZ) {
			super(worldIn, random, chunkX, chunkZ);

			Logger.info("Scattered feature at %d, %d", chunkX * 16, chunkZ * 16);

			this.components.clear();

			BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8));

			if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.SANDY)) {
				ComponentScatteredFeaturePieces.DesertPyramid desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(random, chunkX * 16, chunkZ * 16);
				this.components.add(desertpyramid);
				
			}

			if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.JUNGLE)) {
				ComponentScatteredFeaturePieces.JunglePyramid junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(random, chunkX * 16, chunkZ * 16);
				this.components.add(junglepyramid);
			}

			if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.SWAMP)) {
				ComponentScatteredFeaturePieces.SwampHut swamphut = new ComponentScatteredFeaturePieces.SwampHut(random, chunkX * 16, chunkZ * 16);
				this.components.add(swamphut);
			}

			this.updateBoundingBox();
		}
	}
}