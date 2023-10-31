package mixac1.dangerrpg.inventory;

import net.minecraft.world.*;
import net.minecraft.inventory.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.util.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.capability.*;

public class ContainerLvlupTable extends Container
{
    public static int playerInvX;
    public static int playerInvY;
    public static int fastInvX;
    public static int fastInvY;
    public static int mainX;
    public static int mainY;
    public static int extX;
    public static int extY;
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private boolean firstUse;
    public int expToUp;
    public InventoryLvlupTable invTable;
    public int staticSize;

    public ContainerLvlupTable(final IInventory playerInv, final World world, final int x, final int y, final int z) {
        this.firstUse = true;
        this.worldPointer = world;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.invTable = new InventoryLvlupTable(this);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, ContainerLvlupTable.playerInvX + j * 18, ContainerLvlupTable.playerInvY + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, ContainerLvlupTable.fastInvX + i * 18, ContainerLvlupTable.fastInvY));
        }
        this.staticSize = this.inventorySlots.size();
        this.addSlotToContainer((Slot)new SlotE((IInventory)this.invTable, 0, this.staticSize, ContainerLvlupTable.mainX, ContainerLvlupTable.mainY));
        for (int i = 1; i < this.invTable.inv.length; ++i) {
            this.addSlotToContainer((Slot)new SlotE((IInventory)this.invTable, i, this.staticSize, ContainerLvlupTable.extX + (i - 1) * 18, ContainerLvlupTable.extY));
        }
        this.onCraftMatrixChanged((IInventory)this.invTable);
    }

    public void addCraftingToCrafters(final ICrafting craft) {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate((Container)this, 0, this.expToUp);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            final ICrafting iCrafting = (ICrafting) this.crafters.get(i);
            iCrafting.sendProgressBarUpdate((Container)this, 0, this.expToUp);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int par1, final int par2) {
        if (par1 == 0) {
            this.expToUp = par2;
        }
        else {
            super.updateProgressBar(par1, par2);
        }
    }

    public boolean canInteractWith(final EntityPlayer player) {
        if (this.firstUse) {
            RPGHelper.rebuildPlayerExp(player);
            this.firstUse = false;
        }
        return player.getDistance(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }

    public ItemStack transferStackInSlot(final EntityPlayer player, final int fromSlot) {
        ItemStack stack = null;
        final Slot slot = (Slot) this.inventorySlots.get(fromSlot);
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
        if (!this.worldPointer.isRemote) {
            for (final ItemStack stack : this.invTable.inv) {
                if (stack != null) {
                    player.dropPlayerItemWithRandomChoice(stack, false);
                }
            }
        }
    }

    public void onCraftMatrixChanged(final IInventory inventory) {
        if (inventory == this.invTable) {
            final ItemStack stack = inventory.getStackInSlot(0);
            final boolean b1 = stack != null && RPGItemHelper.isRPGable(stack);
            final boolean b2 = b1 && stack.getItem() instanceof Gem;
            for (int i = 1; i < this.invTable.inv.length; ++i) {
                final Slot slot = (Slot) this.inventorySlots.get(this.staticSize + i);
                slot.xDisplayPosition = (b2 ? (ContainerLvlupTable.extX + (i - 1) * 18) : 1000);
            }
            if (b1 && !this.worldPointer.isRemote && !ItemAttributes.LEVEL.isMax(stack)) {
                if (stack.getItem() instanceof Gem) {
                    this.expToUp = (int)ItemAttributes.LEVEL.get(stack);
                    this.detectAndSendChanges();
                    return;
                }
                final float currExp = ItemAttributes.CURR_EXP.get(stack);
                final float maxExp = ItemAttributes.MAX_EXP.get(stack);
                this.expToUp = Math.round(maxExp - currExp) + 1;
                this.detectAndSendChanges();
            }
            else {
                this.expToUp = -1;
            }
        }
    }

    public boolean enchantItem(final EntityPlayer player, final int flag) {
        final ItemStack stack = this.invTable.getStackInSlot(0);
        if (stack != null && flag == 0 && this.expToUp >= 0) {
            if (stack.getItem() instanceof Gem) {
                for (int i = 1; i < this.invTable.inv.length; ++i) {
                    if (this.invTable.inv[i] == null || !Gem.areGemsEqual(stack, this.invTable.getStackInSlot(i))) {
                        return false;
                    }
                }
            }
            if (player.capabilities.isCreativeMode) {
                if (!this.worldPointer.isRemote) {
                    if (stack.getItem() instanceof Gem) {
                        this.burnItems();
                    }
                    RPGItemHelper.instantLvlUp(stack);
                    this.onCraftMatrixChanged((IInventory)this.invTable);
                }
                return true;
            }
            RPGHelper.rebuildPlayerExp(player);
            if (stack.getItem() instanceof Gem) {
                if (player.experienceLevel >= this.expToUp) {
                    if (!this.worldPointer.isRemote) {
                        player.addExperienceLevel(-this.expToUp);
                        RPGHelper.rebuildPlayerExp(player);
                        RPGItemHelper.instantLvlUp(stack);
                        this.burnItems();
                        this.onCraftMatrixChanged((IInventory)this.invTable);
                    }
                    return true;
                }
            }
            else if (player.experienceTotal >= this.expToUp) {
                if (!this.worldPointer.isRemote) {
                    player.addExperience(-this.expToUp);
                    RPGHelper.rebuildPlayerLvl(player);
                    RPGItemHelper.instantLvlUp(stack);
                    this.onCraftMatrixChanged((IInventory)this.invTable);
                }
                return true;
            }
        }
        return false;
    }

    private void burnItems() {
        for (int i = 1; i < this.invTable.inv.length; ++i) {
            this.invTable.inv[i] = null;
        }
    }

    public ItemStack slotClick(final int index, final int par2, final int par3, final EntityPlayer player) {
        if (index >= this.staticSize && !this.invTable.isItemValidForSlot(index - this.staticSize, null)) {
            return null;
        }
        return super.slotClick(index, par2, par3, player);
    }

    static {
        ContainerLvlupTable.playerInvX = 8;
        ContainerLvlupTable.playerInvY = 129;
        ContainerLvlupTable.fastInvX = 8;
        ContainerLvlupTable.fastInvY = 187;
        ContainerLvlupTable.mainX = 80;
        ContainerLvlupTable.mainY = 44;
        ContainerLvlupTable.extX = 62;
        ContainerLvlupTable.extY = 62;
    }
}
