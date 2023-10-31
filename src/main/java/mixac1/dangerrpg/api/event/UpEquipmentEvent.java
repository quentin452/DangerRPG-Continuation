package mixac1.dangerrpg.api.event;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import cpw.mods.fml.common.eventhandler.*;

public class UpEquipmentEvent extends Event {

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
