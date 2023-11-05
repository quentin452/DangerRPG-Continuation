package mixac1.dangerrpg.item.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.client.model.ModelMageArmor;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent.RPGArmorComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMageArmor extends ItemRPGArmor implements IColorArmor {

    protected int DEFAULT_COLOR = 0x3371e4;

    public ItemMageArmor(RPGArmorMaterial armorMaterial, RPGArmorComponent armorComponent, int armorType) {
        super(armorMaterial, armorComponent, 0, armorType);
    }

    public static ItemMageArmor[] createFullSet(RPGArmorMaterial armorMaterial, RPGArmorComponent armorComponent) {
        return new ItemMageArmor[] { new ItemMageArmor(armorMaterial, armorComponent, 0),
            new ItemMageArmor(armorMaterial, armorComponent, 1), new ItemMageArmor(armorMaterial, armorComponent, 2),
            new ItemMageArmor(armorMaterial, armorComponent, 3) };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String tmp = DangerRPG.MODID.concat(":armors/");
        itemIcon = iconRegister.registerIcon(Utils.toString(tmp, armorComponent.name, ARMOR_TYPES[armorType]));
        overlayIcon = iconRegister.registerIcon(Utils.toString(tmp, unlocalizedName, "_overlay"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        ModelMageArmor model = slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        if (type != null) {
            return Utils.toString(modelTexture, slot == 2 ? 2 : 1, "_", type, ".png");
        } else {
            return Utils.toString(
                "DangerRPG:textures/models/armors/",
                armorComponent.name,
                "_layer_",
                slot == 2 ? 2 : 1,
                ".png");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int slot) {
        return RPGRenderHelper
            .modelBipedInit(entity, slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR, slot);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        return !stack.hasTagCompound() ? false
            : (!stack.getTagCompound()
                .hasKey("display", 10) ? false
                    : stack.getTagCompound()
                        .getCompoundTag("display")
                        .hasKey("color", 3));
    }

    @Override
    public int getColor(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null) {
            return DEFAULT_COLOR;
        } else {
            NBTTagCompound nbtColor = nbt.getCompoundTag("display");
            return nbtColor == null ? DEFAULT_COLOR
                : (nbtColor.hasKey("color", 3) ? nbtColor.getInteger("color") : DEFAULT_COLOR);
        }
    }

    @Override
    public void removeColor(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null) {
            NBTTagCompound nbtColor = nbt.getCompoundTag("display");

            if (nbtColor.hasKey("color")) {
                nbtColor.removeTag("color");
            }
        }
    }

    @Override
    public void func_82813_b(ItemStack stack, int color) {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }

        NBTTagCompound nbtColor = nbt.getCompoundTag("display");

        if (!nbt.hasKey("display", 10)) {
            nbt.setTag("display", nbtColor);
        }

        nbtColor.setInteger("color", color);
    }
}
