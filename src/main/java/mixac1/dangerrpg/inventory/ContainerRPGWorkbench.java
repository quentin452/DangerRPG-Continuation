package mixac1.dangerrpg.inventory;

import net.minecraft.world.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ContainerRPGWorkbench extends Container
{
    public static int craftSize;
    public static int playerInvX;
    public static int playerInvY;
    public static int fastInvX;
    public static int fastInvY;
    public static int craftX;
    public static int craftY;
    public static int craftResX;
    public static int craftResY;
    public InventoryRPGCrafting craftMatrix;
    public IInventory craftResult;
    protected World worldObj;
    protected int posX;
    protected int posY;
    protected int posZ;
    
    public ContainerRPGWorkbench(final InventoryPlayer inv, final World world, final int x, final int y, final int z) {
        this.craftMatrix = new InventoryRPGCrafting(this, ContainerRPGWorkbench.craftSize, ContainerRPGWorkbench.craftSize);
        this.craftResult = (IInventory)new InventoryCraftResult();
        this.worldObj = world;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        for (int m = 0; m < 3; ++m) {
            for (int n = 0; n < 9; ++n) {
                this.addSlotToContainer(new Slot((IInventory)inv, m * 9 + n + 9, ContainerRPGWorkbench.playerInvX + n * 18, ContainerRPGWorkbench.playerInvY + m * 18));
            }
        }
        for (int m = 0; m < 9; ++m) {
            this.addSlotToContainer(new Slot((IInventory)inv, m, ContainerRPGWorkbench.fastInvX + m * 18, ContainerRPGWorkbench.fastInvY));
        }
        for (int m = 0; m < ContainerRPGWorkbench.craftSize; ++m) {
            for (int n = 0; n < ContainerRPGWorkbench.craftSize; ++n) {
                this.addSlotToContainer(new Slot((IInventory)this.craftMatrix, ContainerRPGWorkbench.craftSize * m + n, ContainerRPGWorkbench.craftX + n * 18, ContainerRPGWorkbench.craftY + m * 18));
            }
        }
        this.addSlotToContainer((Slot)new SlotCrafting(inv.player, (IInventory)this.craftMatrix, this.craftResult, 0, ContainerRPGWorkbench.craftResX, ContainerRPGWorkbench.craftResY));
        this.onCraftMatrixChanged((IInventory)this.craftMatrix);
    }
    
    public void onCraftMatrixChanged(final IInventory inv) {
        ItemStack stack = RPGRecipes.ownFindMatchingRecipe((InventoryCrafting)this.craftMatrix, this.worldObj, ContainerRPGWorkbench.craftSize, ContainerRPGWorkbench.craftSize);
        this.craftResult.setInventorySlotContents(0, stack);
        if (stack != null) {
            return;
        }
        for (int i = 0; i < ContainerRPGWorkbench.craftSize - 2; ++i) {
            for (int j = 0; j < ContainerRPGWorkbench.craftSize - 2; ++j) {
                if (this.craftMatrix.isValidCrafting(j, i)) {
                    stack = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix.getCrafting(j, i), this.worldObj);
                    this.craftResult.setInventorySlotContents(0, stack);
                    return;
                }
            }
        }
    }
    
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < ContainerRPGWorkbench.craftSize * ContainerRPGWorkbench.craftSize; ++i) {
                final ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return player.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player, final int index) {
        ItemStack stack = null;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack newStack = slot.getStack();
            stack = newStack.copy();
            if (index == 61) {
                if (!this.mergeItemStack(newStack, 0, 36, true)) {
                    return null;
                }
                slot.onSlotChange(newStack, stack);
            }
            else if (index >= 0 && index < 27) {
                if (!this.mergeItemStack(newStack, 27, 36, false)) {
                    return null;
                }
            }
            else if (index >= 27 && index < 36) {
                if (!this.mergeItemStack(newStack, 0, 27, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(newStack, 0, 36, false)) {
                return null;
            }
            if (newStack.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (newStack.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, newStack);
        }
        return stack;
    }
    
    public boolean func_94530_a(final ItemStack stack, final Slot slot) {
        return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
    }
    
    static {
        ContainerRPGWorkbench.craftSize = 5;
        ContainerRPGWorkbench.playerInvX = 8;
        ContainerRPGWorkbench.playerInvY = 145;
        ContainerRPGWorkbench.fastInvX = 8;
        ContainerRPGWorkbench.fastInvY = 203;
        ContainerRPGWorkbench.craftX = 13;
        ContainerRPGWorkbench.craftY = 17;
        ContainerRPGWorkbench.craftResX = 143;
        ContainerRPGWorkbench.craftResY = 53;
    }
}
