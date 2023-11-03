package mixac1.dangerrpg.item.gem;

import java.util.HashSet;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.GemTypes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.util.Tuple.Stub;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class GemAttackModifier extends Gem
{
    public GemAttackModifier(String name)
    {
        super(name);
    }

    @Override
    public GemType getGemType()
    {
        return GemTypes.AM;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        super.registerAttributes(item, map);
    }

    public abstract void onEntityHit(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage, HashSet<Class<? extends GemAttackModifier>> disableSet);

    public abstract void onDealtDamage(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage, HashSet<Class<? extends GemAttackModifier>> disableSet);

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans(unlocalizedName.concat(".info"));
    }

    protected static boolean checkDisabling(HashSet<Class<? extends GemAttackModifier>> set, Class<? extends GemAttackModifier> obj)
    {
        for (Class<? extends GemAttackModifier> it : set) {
            if (it.isAssignableFrom(obj)) {
                return true;
            }
        }
        return false;
    }
}
