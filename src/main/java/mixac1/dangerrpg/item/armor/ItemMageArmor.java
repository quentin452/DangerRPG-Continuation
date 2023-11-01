package mixac1.dangerrpg.item.armor;

import mixac1.dangerrpg.DangerRPG;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.client.*;
import mixac1.dangerrpg.client.model.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public class ItemMageArmor extends ItemRPGArmor implements IColorArmor {

    protected int DEFAULT_COLOR;

    public ItemMageArmor(final RPGArmorMaterial armorMaterial, final RPGItemComponent.RPGArmorComponent armorComponent,
        final int armorType) {
        super(armorMaterial, armorComponent, 0, armorType);
        this.DEFAULT_COLOR = 3371492;
    }

    public static ItemMageArmor[] createFullSet(final RPGArmorMaterial armorMaterial,
        final RPGItemComponent.RPGArmorComponent armorComponent) {
        return new ItemMageArmor[] { new ItemMageArmor(armorMaterial, armorComponent, 0),
            new ItemMageArmor(armorMaterial, armorComponent, 1), new ItemMageArmor(armorMaterial, armorComponent, 2),
            new ItemMageArmor(armorMaterial, armorComponent, 3) };
    }
    // todo fix crash when enabling this.overlayIcon
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String tmp = "dangerrpg".concat(":armors/");
        this.itemIcon = iconRegister.registerIcon(Utils.toString(tmp, this.armorComponent.name, ARMOR_TYPES[this.armorType]));
      // this.overlayIcon = iconRegister.registerIcon(Utils.toString(tmp, this.getUnlocalizedName(), "_overlay"));
    }
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }

    @Override
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        final ModelMageArmor model = (slot == 2) ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        if (type != null) {
            return Utils.toString(this.modelTexture, (slot == 2) ? 2 : 1, "_", type, ".png");
        }
        return Utils.toString(
            "DangerRPG:textures/models/armors/",
            this.armorComponent.name,
            "_layer_",
            (slot == 2) ? 2 : 1,
            ".png");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(final EntityLivingBase entity, final ItemStack stack, final int slot) {
        final ModelBiped tmp = RPGRenderHelper.modelBipedInit(
            entity,
            (slot == 2) ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR,
            slot);
        return tmp;
    }

    public boolean hasColor(final ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound()
            .hasKey("display", 10)
            && stack.getTagCompound()
                .getCompoundTag("display")
                .hasKey("color", 3);
    }

    public int getColor(final ItemStack stack) {
        final NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            return this.DEFAULT_COLOR;
        }
        final NBTTagCompound nbtColor = nbt.getCompoundTag("display");
        return (nbtColor == null) ? this.DEFAULT_COLOR
            : (nbtColor.hasKey("color", 3) ? nbtColor.getInteger("color") : this.DEFAULT_COLOR);
    }

    public void removeColor(final ItemStack stack) {
        final NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            final NBTTagCompound nbtColor = nbt.getCompoundTag("display");
            if (nbtColor.hasKey("color")) {
                nbtColor.removeTag("color");
            }
        }
    }

    public void func_82813_b(final ItemStack stack, final int color) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        final NBTTagCompound nbtColor = nbt.getCompoundTag("display");
        if (!nbt.hasKey("display", 10)) {
            nbt.setTag("display", (NBTBase) nbtColor);
        }
        nbtColor.setInteger("color", color);
    }
}