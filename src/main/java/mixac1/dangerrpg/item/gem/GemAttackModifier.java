package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.*;
import java.util.*;

public abstract class GemAttackModifier extends Gem
{
    public GemAttackModifier(final String name) {
        super(name);
    }
    
    public GemType getGemType() {
        return (GemType)GemTypes.AM;
    }
    
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        super.registerAttributes(item, map);
    }
    
    public abstract void onEntityHit(final ItemStack p0, final EntityPlayer p1, final EntityLivingBase p2, final Tuple.Stub<Float> p3, final HashSet<Class<? extends GemAttackModifier>> p4);
    
    public abstract void onDealtDamage(final ItemStack p0, final EntityPlayer p1, final EntityLivingBase p2, final Tuple.Stub<Float> p3, final HashSet<Class<? extends GemAttackModifier>> p4);
    
    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return DangerRPG.trans(this.unlocalizedName.concat(".info"));
    }
    
    protected static boolean checkDisabling(final HashSet<Class<? extends GemAttackModifier>> set, final Class<? extends GemAttackModifier> obj) {
        for (final Class<? extends GemAttackModifier> it : set) {
            if (it.isAssignableFrom(obj)) {
                return true;
            }
        }
        return false;
    }
}
