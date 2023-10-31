package mixac1.dangerrpg.inventory;

import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerModificationTable extends Container
{
    public static int playerInvX;
    public static int playerInvY;
    public static int fastInvX;
    public static int fastInvY;
    public static int mainX;
    public static int mainY;
    public static int dynamicY;
    private World worldObj;
    private InventoryModificationTable invTable;
    protected int posX;
    protected int posY;
    protected int posZ;
    public int staticSize;
    
    public ContainerModificationTable(final IInventory playerInv, final World world, final int x, final int y, final int z) {
        this.worldObj = world;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.invTable = new InventoryModificationTable(this);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, ContainerModificationTable.playerInvX + j * 18, ContainerModificationTable.playerInvY + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, ContainerModificationTable.fastInvX + i * 18, ContainerModificationTable.fastInvY));
        }
        this.staticSize = this.inventorySlots.size();
        this.addSlotToContainer((Slot)new SlotE((IInventory)this.invTable, 0, this.staticSize, ContainerModificationTable.mainX, ContainerModificationTable.mainY));
        this.onCraftMatrixChanged((IInventory)this.invTable);
    }
    
    protected Slot setSlotToContainer(final int index, final Slot slot) {
        if (index >= this.inventorySlots.size()) {
            return this.addSlotToContainer(slot);
        }
        slot.slotNumber = index;
        this.inventorySlots.set(index, slot);
        this.inventoryItemStacks.set(index, null);
        return slot;
    }
    
    protected void popSlotFromContainer() {
        final int index = this.inventorySlots.size() - 1;
        this.inventorySlots.remove(index);
        this.inventoryItemStacks.remove(index);
    }
    
    public void onMainSlotChanged(final InventoryModificationTable inv) {
        final int size = inv.getSizeInventory();
        final int[] sizes = inv.getSizes();
        for (int tmp = this.inventorySlots.size() - this.staticSize - size, i = 0; i < tmp; ++i) {
            this.popSlotFromContainer();
        }
        int i = 0;
        int k = 0;
        while (i < sizes.length) {
            final int dynamicX = ContainerModificationTable.mainX - (sizes[i] - 1) * 9;
            for (int j = 0; j < sizes[i]; ++j, ++k) {
                this.setSlotToContainer(k + this.staticSize + 1, new SlotE((IInventory)this.invTable, k + 1, this.staticSize, dynamicX + j * 18, ContainerModificationTable.dynamicY + i * 18));
            }
            ++i;
        }
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return player.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int fromSlot) {
        ItemStack stack = null;
        final Slot slot = this.inventorySlots.get(fromSlot);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack2 = slot.getStack();
            stack = stack2.copy();
            if (fromSlot >= 0 && fromSlot < 27) {
                if (this.tryTransfer(this.staticSize, this.inventoryItemStacks.size(), slot)) {
                    return null;
                }
                if (!this.mergeItemStack(stack2, 27, 36, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 27 && fromSlot < 36) {
                if (this.tryTransfer(this.staticSize, this.inventoryItemStacks.size(), slot)) {
                    return null;
                }
                if (!this.mergeItemStack(stack2, 0, 27, false)) {
                    return null;
                }
            }
            else {
                if (fromSlot < this.staticSize || fromSlot >= this.inventoryItemStacks.size()) {
                    return null;
                }
                if (this.tryTransfer(0, 36, slot)) {
                    return null;
                }
            }
            if (stack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack2.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stack2);
        }
        return stack;
    }
    
    public boolean tryTransfer(final int from, final int to, final Slot slot) {
        for (int i = from; i < to; ++i) {
            final Slot tmp = this.getSlot(i);
            if (tmp.getStack() == null && tmp.isItemValid(slot.getStack())) {
                tmp.putStack(slot.getStack());
                slot.putStack((ItemStack)null);
                return true;
            }
        }
        return false;
    }
    
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        if (!this.worldObj.isRemote && this.invTable.main != null) {
            final ItemStack stack = this.invTable.main;
            this.invTable.setInventorySlotContents(0, null);
            player.dropPlayerItemWithRandomChoice(stack, false);
        }
    }
    
    public ItemStack slotClick(final int index, final int par2, final int par3, final EntityPlayer player) {
        if (index >= this.staticSize && !this.invTable.isItemValidForSlot(index - this.staticSize, null)) {
            return null;
        }
        return super.slotClick(index, par2, par3, player);
    }
    
    static {
        ContainerModificationTable.playerInvX = 8;
        ContainerModificationTable.playerInvY = 145;
        ContainerModificationTable.fastInvX = 8;
        ContainerModificationTable.fastInvY = 203;
        ContainerModificationTable.mainX = 80;
        ContainerModificationTable.mainY = 19;
        ContainerModificationTable.dynamicY = 42;
    }
}
