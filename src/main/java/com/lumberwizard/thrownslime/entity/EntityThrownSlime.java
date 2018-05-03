package com.lumberwizard.thrownslime.entity;

import com.lumberwizard.thrownslime.Config;
import com.lumberwizard.thrownslime.ModThrownSlime;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrownSlime extends EntitySnowball {

	public EntityThrownSlime(World worldIn) {
		super(worldIn);
	}

	public EntityThrownSlime(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityThrownSlime(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                world.spawnParticle(EnumParticleTypes.SLIME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null && !(result.entityHit instanceof EntitySlime))
        {
            int i = 0;

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
            if (result.entityHit instanceof EntityLivingBase && !world.isRemote && (this.getThrower() == null || !this.getThrower().isEntityEqual(result.entityHit))) {
            	((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(Potion.getPotionById(2), Config.slownessTime * 20, Config.level - 1));
            }
        }

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }

}
