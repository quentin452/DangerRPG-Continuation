package mixac1.dangerrpg.api.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * It is fires whenever needs up equipment {@link ItemStack} stack by the points.<br>
 * You can spend this points for your own needs,
 * remainder will be shared equally on all equipment stacks that need it.<br>
 * <br>
 * boolean needUp[5]<br>
 * 0 - equipment itemstack<br>
 * 1 - equipment boots<br>
 * 2 - equipment leggings<br>
 * 3 - equipment chestplate<br>
 * 4 - equipment helmet<br>
 */
public class UpEquipmentEvent extends Event {

    public EntityPlayer player;
    public EntityLivingBase target;
    public ItemStack stack;
    public float points;

    public boolean[] needUp = new boolean[] { true, true, true, true, true };

    public UpEquipmentEvent(EntityPlayer player, ItemStack stack, float points) {
        this.player = player;
        this.stack = stack;
        this.points = points;
    }
}
