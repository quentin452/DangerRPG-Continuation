package mixac1.dangerrpg.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class SlotE extends Slot
{
    int offset;
    
    public SlotE(final IInventory inv, final int index, final int offset, final int x, final int y) {
        super(inv, index, x, y);
        this.offset = offset;
    }
    
    public int getSlotIndex() {
        return this.slotNumber - this.offset;
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public void putStack(final ItemStack stack) {
        this.inventory.setInventorySlotContents(this.getSlotIndex(), stack);
    }
    
    public boolean isItemValid(final ItemStack stack) {
        return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
    }
}
