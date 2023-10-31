package mixac1.dangerrpg.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.item.gem.*;

public class InventoryLvlupTable implements IInventory {

    public static final String NAME = "lvlup_table";
    public ItemStack[] inv;
    private ContainerLvlupTable eventHandler;

    public InventoryLvlupTable(final ContainerLvlupTable eventHandler) {
        this.inv = new ItemStack[4];
        this.eventHandler = eventHandler;
    }

    public int getSizeInventory() {
        return this.inv.length;
    }

    public ItemStack getStackInSlot(final int index) {
        return (index < 0 || index >= this.getSizeInventory()) ? null : this.inv[index];
    }

    public ItemStack decrStackSize(final int index, final int count) {
        ItemStack stack = this.getStackInSlot(index);
        if (stack != null) {
            ItemStack itemstack;
            if (stack.stackSize <= count) {
                itemstack = stack;
                this.setInventorySlotContents(index, null);
            } else {
                itemstack = stack.splitStack(count);
                if (stack.stackSize == 0) {
                    stack = null;
                }
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(final int index) {
        final ItemStack stack = this.getStackInSlot(index);
        if (stack != null) {
            final ItemStack itemstack = stack;
            this.setInventorySlotContents(index, null);
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(final int index, final ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory()) {
            return;
        }
        this.inv[index] = stack;
        this.eventHandler.onCraftMatrixChanged((IInventory) this);
    }

    public String getInventoryName() {
        return "lvlup_table";
    }

    public boolean hasCustomInventoryName() {
        return true;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void markDirty() {}

    public boolean isUseableByPlayer(final EntityPlayer player) {
        return true;
    }

    public void openInventory() {}

    public void closeInventory() {}

    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        if (stack != null) {
            return (index == 0 && RPGItemHelper.isRPGable(stack))
                || (this.inv[0] != null && this.inv[0].getItem() instanceof Gem
                    && Gem.areGemsEqual(this.inv[0], stack));
        }
        if (index != 0) {
            return true;
        }
        for (int i = 1; i < this.inv.length; ++i) {
            if (this.inv[i] != null) {
                return false;
            }
        }
        return true;
    }
}
