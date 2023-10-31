package mixac1.dangerrpg.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.util.*;
import java.util.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.capability.*;

public class InventoryModificationTable implements IInventory
{
    public static final String NAME = "modification_table";
    public ItemStack main;
    public ItemStack[][] inv;
    private ContainerModificationTable eventHandler;
    
    public InventoryModificationTable(final ContainerModificationTable eventHandler) {
        this.inv = new ItemStack[0][];
        this.eventHandler = eventHandler;
    }
    
    public int getSizeInventory() {
        int size = 1;
        for (final ItemStack[] it : this.inv) {
            size += it.length;
        }
        return size;
    }
    
    public int[] getSizes() {
        final int[] tmp = new int[this.inv.length];
        for (int i = 0; i < this.inv.length; ++i) {
            tmp[i] = this.inv[i].length;
        }
        return tmp;
    }
    
    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return this.main;
        }
        --index;
        for (final ItemStack[] it : this.inv) {
            if (index < it.length) {
                return it[index];
            }
            index -= it.length;
        }
        return null;
    }
    
    public ItemStack decrStackSize(final int index, final int count) {
        ItemStack stack = this.getStackInSlot(index);
        if (stack != null) {
            ItemStack itemstack;
            if (stack.stackSize <= count) {
                itemstack = stack;
                this.setInventorySlotContents(index, null);
            }
            else {
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
    
    public void setInventorySlotContents(int index, final ItemStack stack) {
        if (index == 0) {
            if (this.main == null) {
                if (stack != null && RPGItemHelper.isRPGable(stack)) {
                    this.detachGems(stack);
                }
            }
            else if (stack == null && RPGItemHelper.isRPGable(this.main)) {
                this.attachGems(this.main);
            }
            this.main = stack;
            this.eventHandler.onMainSlotChanged(this);
            return;
        }
        --index;
        for (final ItemStack[] it : this.inv) {
            if (index < it.length) {
                it[index] = stack;
                return;
            }
            index -= it.length;
        }
    }
    
    private void detachGems(final ItemStack stack) {
        final HashMap<GemType, Tuple.Stub<Integer>> map = (HashMap<GemType, Tuple.Stub<Integer>>)((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get((Object)stack.getItem())).gems;
        this.inv = new ItemStack[map.size()][];
        int i = 0;
        for (final Map.Entry<GemType, Tuple.Stub<Integer>> entry : map.entrySet()) {
            final List<ItemStack> list = (List<ItemStack>)entry.getKey().detach(stack);
            this.inv[i++] = list.toArray(new ItemStack[(int)entry.getValue().value1]);
        }
    }
    
    private void attachGems(final ItemStack stack) {
        int i = 0;
        for (final GemType gemType : RPGItemHelper.getGemTypes(stack)) {
            gemType.attach(stack, this.inv[i]);
            ++i;
        }
        this.inv = new ItemStack[0][];
    }
    
    public String getInventoryName() {
        return "modification_table";
    }
    
    public boolean hasCustomInventoryName() {
        return true;
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public void markDirty() {
    }
    
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return true;
    }
    
    public void openInventory() {
    }
    
    public void closeInventory() {
    }
    
    private Tuple.Pair<Integer, Integer> getRowColumn(int index) {
        int row = 0;
        for (final ItemStack[] it : this.inv) {
            if (index < it.length) {
                return new Tuple.Pair<Integer, Integer>(row, index);
            }
            index -= it.length;
            ++row;
        }
        return null;
    }
    
    private GemType getGemType(final int row) {
        int i = 0;
        for (final GemType it : RPGItemHelper.getGemTypes(this.main)) {
            if (i++ == row) {
                return it;
            }
        }
        return null;
    }
    
    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        if (stack == null) {
            return true;
        }
        if (index == 0 && RPGItemHelper.isRPGable(stack)) {
            return this.main == null;
        }
        if (this.main != null && stack.getItem() instanceof Gem) {
            final GemType gemType = this.getGemTypeSlot(index);
            if (gemType != null) {
                return gemType.isTrueGem((Gem)stack.getItem(), this.main) && ItemAttributes.LEVEL.get(this.main) >= ItemAttributes.LEVEL.get(stack);
            }
        }
        return false;
    }
    
    public GemType getGemTypeSlot(final int index) {
        if (index > 0 && this.main != null && RPGItemHelper.isRPGable(this.main)) {
            final Tuple.Pair<Integer, Integer> coords = this.getRowColumn(index - 1);
            if (coords != null) {
                return this.getGemType((int)coords.value1);
            }
        }
        return null;
    }
}
