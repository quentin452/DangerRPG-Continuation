package mixac1.dangerrpg.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerLvlupTable extends Container {

    public static int playerInvX = 8;
    public static int playerInvY = 129;

    public static int fastInvX = 8;
    public static int fastInvY = 187;

    public static int mainX = 80;
    public static int mainY = 44;

    public static int extX = 62;
    public static int extY = 62;

    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private boolean firstUse = true;
    public int expToUp;

    public InventoryLvlupTable invTable;

    public int staticSize;

    public ContainerLvlupTable(IInventory playerInv, World world, int x, int y, int z) {
        worldPointer = world;
        posX = x;
        posY = y;
        posZ = z;

        invTable = new InventoryLvlupTable(this);

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

        for (int i = 1; i < invTable.inv.length; ++i) {
            addSlotToContainer(new SlotE(invTable, i, staticSize, extX + (i - 1) * 18, extY));
        }

        onCraftMatrixChanged(invTable);
    }

    @Override
    public void addCraftingToCrafters(ICrafting craft) {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, expToUp);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); ++i) {
            ICrafting iCrafting = (ICrafting) crafters.get(i);
            iCrafting.sendProgressBarUpdate(this, 0, expToUp);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            expToUp = par2;
        } else {
            super.updateProgressBar(par1, par2);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (firstUse) {
            RPGHelper.rebuildPlayerExp(player);
            firstUse = false;
        }
        return player.getDistance(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
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

        if (!worldPointer.isRemote) {
            for (ItemStack stack : invTable.inv) {
                if (stack != null) {
                    player.dropPlayerItemWithRandomChoice(stack, false);
                }
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        if (inventory == invTable) {
            ItemStack stack = inventory.getStackInSlot(0);
            boolean b1 = stack != null && RPGItemHelper.isRPGable(stack);
            boolean b2 = b1 && stack.getItem() instanceof Gem;

            for (int i = 1; i < invTable.inv.length; ++i) {
                Slot slot = (Slot) inventorySlots.get(staticSize + i);
                slot.xDisplayPosition = b2 ? extX + (i - 1) * 18 : 1000;
            }

            if (b1) {
                if (!worldPointer.isRemote) {
                    if (!ItemAttributes.LEVEL.isMax(stack)) {
                        if (stack.getItem() instanceof Gem) {
                            expToUp = (int) ItemAttributes.LEVEL.get(stack);
                            detectAndSendChanges();
                            return;
                        } else {
                            float currExp = ItemAttributes.CURR_EXP.get(stack);
                            float maxExp = ItemAttributes.MAX_EXP.get(stack);
                            expToUp = Math.round(maxExp - currExp) + 1;
                            detectAndSendChanges();
                            return;
                        }
                    }
                }
            }
            expToUp = -1;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int flag) {
        ItemStack stack = invTable.getStackInSlot(0);
        if (stack != null && flag == 0 && expToUp >= 0) {
            if (stack.getItem() instanceof Gem) {
                for (int i = 1; i < invTable.inv.length; ++i) {
                    if (invTable.inv[i] == null || !Gem.areGemsEqual(stack, invTable.getStackInSlot(i))) {
                        return false;
                    }
                }
            }

            if (player.capabilities.isCreativeMode) {
                if (!worldPointer.isRemote) {
                    if (stack.getItem() instanceof Gem) {
                        burnItems();
                    }
                    RPGItemHelper.instantLvlUp(stack);
                    onCraftMatrixChanged(invTable);
                }
                return true;
            } else {
                RPGHelper.rebuildPlayerExp(player);

                if (stack.getItem() instanceof Gem) {
                    if (player.experienceLevel >= expToUp) {
                        if (!worldPointer.isRemote) {
                            player.addExperienceLevel(-expToUp);
                            RPGHelper.rebuildPlayerExp(player);
                            RPGItemHelper.instantLvlUp(stack);
                            burnItems();
                            onCraftMatrixChanged(invTable);
                        }
                        return true;
                    }
                } else {
                    if (player.experienceTotal >= expToUp) {
                        if (!worldPointer.isRemote) {
                            player.addExperience(-expToUp);
                            RPGHelper.rebuildPlayerLvl(player);
                            RPGItemHelper.instantLvlUp(stack);
                            onCraftMatrixChanged(invTable);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void burnItems() {
        for (int i = 1; i < invTable.inv.length; ++i) {
            invTable.inv[i] = null;
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
