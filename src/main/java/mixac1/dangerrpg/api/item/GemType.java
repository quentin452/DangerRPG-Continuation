package mixac1.dangerrpg.api.item;

import java.util.*;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.util.*;

public abstract class GemType {

    public final String name;
    public final int hash;

    public GemType(final String name) {
        this.name = name;
        this.hash = name.hashCode();
        RPGCapability.mapIntToGemType.put(this.hash, this);
    }

    public boolean hasIt(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && RPGCapability.rpgItemRegistr.get(stack.getItem()).gems.containsKey(this);
    }

    public void checkIt(final ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        if (!stack.stackTagCompound.hasKey(this.name)) {
            this.attach(stack, Collections.EMPTY_LIST);
        }
    }

    public boolean isTrueGem(final Gem gem, final ItemStack stack) {
        return this == gem.getGemType() && (gem.itemTypes.isEmpty()
            || gem.itemTypes.contains(RPGCapability.rpgItemRegistr.get(stack.getItem()).itemType));
    }

    public void attach(final ItemStack dest, final ItemStack... src) {
        this.attach(dest, Arrays.asList(src));
    }

    public void attach(final ItemStack dest, final List<ItemStack> src) {
        final NBTTagList tagList = new NBTTagList();
        for (int max = RPGCapability.rpgItemRegistr.get(dest.getItem()).gems.get(this).value1, i = 0; i < src.size()
            && i < max; ++i) {
            final ItemStack stack = src.get(i);
            if (stack != null && stack.getItem() instanceof Gem && this.isTrueGem((Gem) stack.getItem(), dest)) {
                final NBTTagCompound nbt = new NBTTagCompound();
                stack.writeToNBT(nbt);
                tagList.appendTag((NBTBase) nbt);
            }
        }
        dest.stackTagCompound.setTag(this.name, (NBTBase) tagList);
    }

    public List<ItemStack> detach(final ItemStack dest) {
        final List<ItemStack> stacks = this.get(dest);
        this.attach(dest, Collections.EMPTY_LIST);
        return stacks;
    }

    public List<ItemStack> get(final ItemStack stack) {
        if (this.hasIt(stack)) {
            final List<ItemStack> stacks = this.getRaw(stack);
            final Iterator<ItemStack> it = stacks.iterator();
            while (it.hasNext()) {
                final ItemStack itStack = it.next();
                if (itStack == null || !(itStack.getItem() instanceof Gem)
                    || !this.isTrueGem((Gem) itStack.getItem(), stack)) {
                    it.remove();
                }
            }
            return stacks;
        }
        return (List<ItemStack>) Collections.EMPTY_LIST;
    }

    public List<ItemStack> getRaw(final ItemStack stack) {
        final NBTTagList nbtList = stack.stackTagCompound.getTagList(this.name, 10);
        final List<ItemStack> stacks = new ArrayList<ItemStack>(nbtList.tagCount());
        for (int max = RPGCapability.rpgItemRegistr.get(stack.getItem()).gems.get(this).value1,
            i = 0; i < nbtList.tagCount() && i < max; ++i) {
            stacks.add(i, ItemStack.loadItemStackFromNBT(nbtList.getCompoundTagAt(i)));
        }
        return stacks;
    }

    public abstract void activate1(final ItemStack p0, final EntityPlayer p1, final Object... p2);

    public abstract void activate2(final ItemStack p0, final EntityPlayer p1, final Object... p2);

    public void activate1All(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        final List<ItemStack> stacks = this.get(stack);
        for (final ItemStack it : stacks) {
            this.activate1(it, player, meta);
        }
    }

    public void activate2All(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        final List<ItemStack> stacks = this.get(stack);
        for (final ItemStack it : stacks) {
            this.activate2(it, player, meta);
        }
    }

    public String getDispayName() {
        return DangerRPG.trans(Utils.toString("gt.", this.name));
    }

    @Override
    public final int hashCode() {
        return this.hash;
    }

    public boolean isConfigurable() {
        return true;
    }
}
