package mixac1.dangerrpg.item;

import net.minecraft.item.EnumRarity;

public interface IMaterialSpecial
{
    public boolean hasSpecialColor();

    public int getSpecialColor();

    public EnumRarity getItemRarity();
}
