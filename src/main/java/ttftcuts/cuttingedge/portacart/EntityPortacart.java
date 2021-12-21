package ttftcuts.cuttingedge.portacart;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.Optional;
import mods.railcraft.api.carts.ILinkableCart;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Optional.Interface(iface = "mods.railcraft.api.carts.ILinkableCart", modid = "RailcraftAPI|carts")
public class EntityPortacart extends EntityMinecart implements ILinkableCart {

	public EntityPortacart(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityPortacart(World world) {
		super(world);
	}

	@Override
	public int getMinecartType() {
		return 0;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)riddenByEntity;
			
			for(int slotIndex : ModulePortacart.cartSlots) {
				ItemStack cart = BaublesApi.getBaubles(player).getStackInSlot(slotIndex);
				if (cart == null || cart.getItem() instanceof ItemPortacart) {
					return;
				}
			}
		}
		setDead();
	}

	@Override
	protected void func_145821_a(int x, int y, int z, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_) {
		if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase) {
			EntityLivingBase ent = ((EntityLivingBase)riddenByEntity);
			float forward = ent.moveForward;

			double dx = -Math.sin((double)(riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
			double dz = Math.cos((double)(riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));

			if (forward > 0.0) {
				motionX += dx * 0.0025;
				motionZ += dz * 0.0025;
			} else if (forward < 0.0) {
				motionX *= 0.93;
				motionZ *= 0.93;
			}
		}

		super.func_145821_a(x, y, z, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
	}

	/* RAILCRAFT METHODS */
	
	@Override
	public boolean isLinkable() {
		return false;
	}

	@Override
	public boolean canLinkWithCart(EntityMinecart cart) {
		return false;
	}

	@Override
	public boolean hasTwoLinks() {
		return false;
	}

	@Override
	public float getLinkageDistance(EntityMinecart cart) {
		return 0;
	}

	@Override
	public float getOptimalDistance(EntityMinecart cart) {
		return 0;
	}

	@Override
	public boolean canBeAdjusted(EntityMinecart cart) {
		return false;
	}

	@Override
	public void onLinkCreated(EntityMinecart cart) {}

	@Override
	public void onLinkBroken(EntityMinecart cart) {}
	
	/* END RAILCRAFT METHODS */
}
