package mixac1.dangerrpg.item.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public class ItemRPGArmor extends ItemArmor implements IRPGItem.IRPGItemArmor, IHasBooksInfo {

    public static String[] ARMOR_TYPES;
    public RPGArmorMaterial armorMaterial;
    public RPGItemComponent.RPGArmorComponent armorComponent;
    public String name;
    public String modelTexture;

    public ItemRPGArmor(final RPGArmorMaterial armorMaterial, final RPGItemComponent.RPGArmorComponent armorComponent,
        final int renderIndex, final int armorType) {
        super(armorMaterial.material, renderIndex, armorType);
        this.armorMaterial = armorMaterial;
        this.armorComponent = armorComponent;
        this.name = RPGItems.getRPGName(armorComponent, armorMaterial);
        this.modelTexture = Utils.toString("DangerRPG:textures/models/armors/", this.name, "_layer_");
        this.setUnlocalizedName(this.name.concat(ItemRPGArmor.ARMOR_TYPES[armorType]));
        this.setTextureName(Utils.toString("dangerrpg", ":armors/", getUnlocalizedName()));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
    }

    public static ItemRPGArmor[] createFullSet(final RPGArmorMaterial armorMaterial,
        final RPGItemComponent.RPGArmorComponent armorComponent) {
        return new ItemRPGArmor[] { new ItemRPGArmor(armorMaterial, armorComponent, 0, 0),
            new ItemRPGArmor(armorMaterial, armorComponent, 0, 1),
            new ItemRPGArmor(armorMaterial, armorComponent, 0, 2),
            new ItemRPGArmor(armorMaterial, armorComponent, 0, 3) };
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(this.getIconString());
    }

    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }

    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemArmor(item, map);
    }

    public RPGItemComponent.RPGArmorComponent getItemComponent(final Item item) {
        return this.armorComponent;
    }

    public RPGArmorMaterial getArmorMaterial(final Item item) {
        return this.armorMaterial;
    }

    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return Utils.toString(this.modelTexture, (slot == 2) ? 2 : 1, ".png");
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack,
        final int armorSlot) {
        return null;
    }

    static {
        ItemRPGArmor.ARMOR_TYPES = new String[] { "_helmet", "_chestplate", "_leggings", "_boots" };
    }
}
