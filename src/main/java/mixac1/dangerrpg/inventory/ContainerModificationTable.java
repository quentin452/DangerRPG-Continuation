package mixac1.dangerrpg.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerModificationTable extends Container {

    public static int playerInvX = 8;
    public static int playerInvY = 145;

    public static int fastInvX = 8;
    public static int fastInvY = 203;

    public static int mainX = 80;
    public static int mainY = 19;

    public static int dynamicY = 42;

    private World worldObj;
    private InventoryModificationTable invTable;
    protected int posX;
    protected int posY;
    protected int posZ;

    public int staticSize;

    public ContainerModificationTable(IInventory playerInv, World world, int x, int y, int z) {
        worldObj = world;
        posX = x;
        posY = y;
        posZ = z;

        invTable = new InventoryModificationTable(this);

        // Player inventory: 0 - 27
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, playerInvX + j * 18, playerInvY + i * 18));
            }
        }

        // Player Inventory, Slot 27 - 36
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInv, i, fastInvX + i * 18, fastInvY));
        }

        staticSize = inventorySlots.size();

        addSlotToContainer(new SlotE(invTable, 0, staticSize, mainX, mainY));

        onCraftMatrixChanged(invTable);
    }

    protected Slot setSlotToContainer(int index, Slot slot) {
        if (index >= inventorySlots.size()) {
            return addSlotToContainer(slot);
        } else {
            slot.slotNumber = index;
            inventorySlots.set(index, slot);
            inventoryItemStacks.set(index, (Object) null);
            return slot;
        }
    }

    protected void popSlotFromContainer() {
        int index = inventorySlots.size() - 1;
        inventorySlots.remove(index);
        inventoryItemStacks.remove(index);
    }

    public void onMainSlotChanged(InventoryModificationTable inv) {
        int size = inv.getSizeInventory();
        int[] sizes = inv.getSizes();

        int tmp = inventorySlots.size() - staticSize - size;
        for (int i = 0; i < tmp; ++i) {
            popSlotFromContainer();
        }

        for (int i = 0, k = 0; i < sizes.length; ++i) {
            int dynamicX = mainX - (sizes[i] - 1) * 9;
            for (int j = 0; j < sizes[i]; ++j, ++k) {
                setSlotToContainer(
                    k + staticSize + 1,
                    new SlotE(invTable, k + 1, staticSize, dynamicX + j * 18, dynamicY + i * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return player.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if (fromSlot >= 0 && fromSlot < 27) {
                if (tryTransfer(staticSize, inventoryItemStacks.size(), slot)) {
                    return null;
                } else if (!mergeItemStack(stack1, 27, 36, false)) {
                    return null;
                }
            } else if (fromSlot >= 27 && fromSlot < 36) {
                if (tryTransfer(staticSize, inventoryItemStacks.size(), slot)) {
                    return null;
                } else if (!mergeItemStack(stack1, 0, 27, false)) {
                    return null;
                }
            } else if (fromSlot >= staticSize && fromSlot < inventoryItemStacks.size()) {
                if (tryTransfer(0, 36, slot)) {
                    return null;
                }
            } else {
                return null;
            }

            if (stack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack1.stackSize == stack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, stack1);
        }
        return stack;
    }

    public boolean tryTransfer(int from, int to, Slot slot) {
        for (int i = from; i < to; ++i) {
            Slot tmp = getSlot(i);
            if (tmp.getStack() == null && tmp.isItemValid(slot.getStack())) {
                tmp.putStack(slot.getStack());
                slot.putStack(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!worldObj.isRemote) {
            if (invTable.main != null) {
                ItemStack stack = invTable.main;
                invTable.setInventorySlotContents(0, null);
                player.dropPlayerItemWithRandomChoice(stack, false);
            }
        }
    }

    @Override
    public ItemStack slotClick(int index, int par2, int par3, EntityPlayer player) {
        if (index >= staticSize) {
            if (!invTable.isItemValidForSlot(index - staticSize, null)) {
                return null;
            }
        }
        return super.slotClick(index, par2, par3, player);
    }
}
