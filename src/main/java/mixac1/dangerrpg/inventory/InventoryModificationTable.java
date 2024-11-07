package mixac1.dangerrpg.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.util.Tuple.Pair;
import mixac1.dangerrpg.util.Tuple.Stub;

public class InventoryModificationTable implements IInventory {

    public static final String NAME = "modification_table";

    public ItemStack main;
    public ItemStack[][] inv = new ItemStack[0][];
    private ContainerModificationTable eventHandler;

    public InventoryModificationTable(ContainerModificationTable eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public int getSizeInventory() {
        int size = 1;
        for (ItemStack[] it : inv) {
            size += it.length;
        }
        return size;
    }

    public int[] getSizes() {
        int[] tmp = new int[inv.length];
        for (int i = 0; i < inv.length; ++i) {
            tmp[i] = inv[i].length;
        }
        return tmp;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return main;
        } else {
            --index;
        }

        for (ItemStack[] it : inv) {
            if (index >= it.length) {
                index -= it.length;
            } else {
                return it[index];
            }
        }
        return null;
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
        if (index == 0) {
            if (main == null) {
                if (stack != null && RPGItemHelper.isRPGable(stack)) {
                    detachGems(stack);
                }
            } else {
                if (stack == null && RPGItemHelper.isRPGable(main)) {
                    attachGems(main);
                }
            }
            main = stack;

            eventHandler.onMainSlotChanged(this);
            return;
        }

        --index;
        for (ItemStack[] it : inv) {
            if (index >= it.length) {
                index -= it.length;
            } else {
                it[index] = stack;
                return;
            }
        }
    }

    private void detachGems(ItemStack stack) {
        HashMap<GemType, Stub<Integer>> map = (HashMap<GemType, Stub<Integer>>) RPGCapability.rpgItemRegistr
            .get(stack.getItem()).gems;
        inv = new ItemStack[map.size()][];

        int i = 0;
        for (Entry<GemType, Stub<Integer>> entry : map.entrySet()) {
            List<ItemStack> list = entry.getKey()
                .detach(stack);

            inv[i++] = list.toArray(new ItemStack[entry.getValue().value1]);
        }
    }

    private void attachGems(ItemStack stack) {
        int i = 0;
        for (GemType gemType : RPGItemHelper.getGemTypes(stack)) {
            gemType.attach(stack, inv[i]);
            ++i;
        }
        inv = new ItemStack[0][];
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

    private Pair<Integer, Integer> getRowColumn(int index) {
        int row = 0;
        for (ItemStack[] it : inv) {
            if (index >= it.length) {
                index -= it.length;
                ++row;
            } else {
                return new Pair<>(row, index);
            }
        }
        return null;
    }

    private GemType getGemType(int row) {
        int i = 0;
        for (GemType it : RPGItemHelper.getGemTypes(main)) {
            if (i++ == row) {
                return it;
            }
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null) {
            return true;
        }

        if (index == 0 && RPGItemHelper.isRPGable(stack)) {
            return main == null;
        }

        if (main != null && stack.getItem() instanceof Gem) {
            GemType gemType = getGemTypeSlot(index);
            if (gemType != null) {
                return gemType.isTrueGem((Gem) stack.getItem(), main)
                    && ItemAttributes.LEVEL.get(main) >= ItemAttributes.LEVEL.get(stack);
            }
        }
        return false;
    }

    public GemType getGemTypeSlot(int index) {
        if (index > 0 && RPGItemHelper.isRPGable(main)) {
            Pair<Integer, Integer> coords = getRowColumn(index - 1);
            if (coords != null) {
                return getGemType(coords.value1);
            }
        }
        return null;
    }
}
