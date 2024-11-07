package mixac1.dangerrpg.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.item.gem.Gem;

public class InventoryLvlupTable implements IInventory {

    public static final String NAME = "lvlup_table";

    public ItemStack[] inv = new ItemStack[4];

    private ContainerLvlupTable eventHandler;

    public InventoryLvlupTable(ContainerLvlupTable eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index < 0 || index >= getSizeInventory() ? null : inv[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = getStackInSlot(index);
        if (stack != null) {
            ItemStack itemstack;

            if (stack.stackSize <= count) {
                itemstack = stack;
                setInventorySlotContents(index, null);
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

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = getStackInSlot(index);
        if (stack != null) {
            ItemStack itemstack = stack;
            setInventorySlotContents(index, null);
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= getSizeInventory()) {
            return;
        }

        inv[index] = stack;
        eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public String getInventoryName() {
        return NAME;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null) {
            if (index != 0) {
                return true;
            } else {
                for (int i = 1; i < inv.length; ++i) {
                    if (inv[i] != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        if (index == 0 && RPGItemHelper.isRPGable(stack)) {
            return true;
        }

        if (inv[0] != null && inv[0].getItem() instanceof Gem && Gem.areGemsEqual(inv[0], stack)) {
            return true;
        }

        return false;
    }
}
