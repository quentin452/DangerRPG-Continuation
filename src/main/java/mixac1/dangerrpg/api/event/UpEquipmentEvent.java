package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class UpEquipmentEvent extends Event
{
    public EntityPlayer player;
    public EntityLivingBase target;
    public ItemStack stack;
    public float points;
    public boolean[] needUp;
    
    public UpEquipmentEvent(final EntityPlayer player, final ItemStack stack, final float points) {
        this.needUp = new boolean[] { true, true, true, true, true };
        this.player = player;
        this.stack = stack;
        this.points = points;
    }
}
