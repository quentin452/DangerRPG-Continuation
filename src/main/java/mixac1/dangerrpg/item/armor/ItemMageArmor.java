package mixac1.dangerrpg.item.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.client.model.ModelMageArmor;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemMageArmor extends ItemRPGArmor implements IColorArmor {
    @SideOnly(Side.CLIENT)
    private IIcon customOverlayIcon;
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

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String tmp = "dangerrpg".concat(":armors/");
        this.itemIcon = iconRegister.registerIcon(Utils.toString(tmp, this.armorComponent.name, ARMOR_TYPES[this.armorType]));
        this.customOverlayIcon = iconRegister.registerIcon(Utils.toString(tmp, this.getUnlocalizedName(), "_overlay"));
    }
    @SideOnly(Side.CLIENT)
    public IIcon getCustomOverlayIcon() {
        return customOverlayIcon;
    }
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        final ModelMageArmor model = (slot == 2) ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        if (type != null) {
            return Utils.toString(this.modelTexture, (slot == 2) ? 2 : 1, "_", type, ".png");
        }
        return Utils.toString("DangerRPG:textures/models/armors/", this.armorComponent.name, "_layer_", (slot == 2) ? 2 : 1, ".png");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            return this.getCustomOverlayIcon();
        } else {
            return super.getIcon(stack, pass);
        }
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
