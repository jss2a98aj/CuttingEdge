package ttftcuts.cuttingedge.portacart;

import java.util.List;

import org.lwjgl.input.Keyboard;

import baubles.api.BaubleType;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ttftcuts.cuttingedge.CuttingEdge;

public class ItemPortacart extends Item implements IBaubleExpanded {
	
	public ItemPortacart() {
		this.setUnlocalizedName("portacart");
		this.setTextureName(CuttingEdge.MOD_ID + ":portacart/portacart");
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabTransport);
	}
	
	public static boolean placeCart(EntityPlayer player, World world, int x, int y, int z) {
		double dist = player.getDistance(x + 0.5D, y + 0.5D, z + 0.5D);
		if (dist <= 1.5D && BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
			if (!world.isRemote) {
				player.setSneaking(false);

				EntityMinecart entityminecart = new EntityPortacart(world, x + 0.5D, y + 0.5D, z + 0.5D);

				world.spawnEntityInWorld(entityminecart);

				player.mountEntity(entityminecart);
			}
			return true;
		} else {
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean debug) {
		if(GuiScreen.isShiftKeyDown()) {
			tooltip.add(StatCollector.translateToLocal("tooltip.compatibleslots"));
			tooltip.add(StatCollector.translateToLocal("slot." + ModulePortacart.cartType));

			String key = GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
			String action = StatCollector.translateToLocal(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyDescription());

			if(key != null && !key.equals("NONE")) {
				tooltip.add(StatCollector.translateToLocal("item.portacart.desc").replaceAll("%action%", action).replaceAll("%key%", key));
			}
		} else {
			tooltip.add(StatCollector.translateToLocal("tooltip.shiftprompt"));
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return null;
	}

	@Override
	public String[] getBaubleTypes(ItemStack itemstack) {
		return new String[] {ModulePortacart.cartType};
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		if (player.ridingEntity != null && player.ridingEntity instanceof EntityPortacart) {
			player.dismountEntity(player.ridingEntity);
			player.ridingEntity.setDead();
		}
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

}
