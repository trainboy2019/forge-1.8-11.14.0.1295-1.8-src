package com.camp.block;

import com.example.examplemod.cm;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class DeathBlock extends Block{
	public static final String name = "DeathBlock";
	protected DeathBlock() {
		super(Material.fire);
		// TODO Auto-generated constructor stub
		this.setUnlocalizedName(this.name);
		this.setCreativeTab(cm.tabIke);
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
		entityIn.attackEntityFrom(DamageSource.generic, 100.0F);
		entityIn.attackEntityFrom(DamageSource.magic, 10.0f);
        entityIn.attackEntityFrom(DamageSource.wither, 100.0F);
    }


}
