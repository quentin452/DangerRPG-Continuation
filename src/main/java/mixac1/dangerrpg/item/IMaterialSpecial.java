package mixac1.dangerrpg.item;

import net.minecraft.item.*;

public interface IMaterialSpecial
{
    boolean hasSpecialColor();
    
    int getSpecialColor();
    
    EnumRarity getItemRarity();
}
