package mixac1.dangerrpg.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotE extends Slot {

    int offset;

    public SlotE(IInventory inv, int index, int offset, int x, int y) {
        super(inv, index, x, y);
        this.offset = offset;
    }

    @Override
    public int getSlotIndex() {
        return slotNumber - offset;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public void putStack(ItemStack stack) {
        inventory.setInventorySlotContents(getSlotIndex(), stack);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(getSlotIndex(), stack);
    }
}
