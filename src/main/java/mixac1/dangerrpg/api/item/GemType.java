package mixac1.dangerrpg.api.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class GemType
{
    public final String name;
    public final int hash;

    public GemType(String name)
    {
        this.name = "gt.".concat(name);
        hash = name.hashCode();

        RPGCapability.mapIntToGemType.put(hash, this);
    }

    public boolean hasIt(ItemStack stack)
    {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
               && RPGCapability.rpgItemRegistr.get(stack.getItem()).gems.containsKey(this);
    }

    public void checkIt(ItemStack stack)
    {
        RPGItemHelper.checkNBT(stack);
        if (!stack.stackTagCompound.hasKey(name)) {
            attach(stack, Collections.EMPTY_LIST);
        }
    }

    public boolean isTrueGem(Gem gem, ItemStack stack)
    {
        return this == gem.getGemType() && (gem.itemTypes.isEmpty() || gem.itemTypes.contains(RPGCapability.rpgItemRegistr.get(stack.getItem()).itemType));
    }

    public void attach(ItemStack dest, ItemStack... src)
    {
        attach(dest, Arrays.asList(src));
    }

    public void attach(ItemStack dest, List<ItemStack> src)
    {
        NBTTagList tagList = new NBTTagList();
        int max = RPGCapability.rpgItemRegistr.get(dest.getItem()).gems.get(this).value1;
        for (int i = 0; i < src.size() && i < max; ++i) {
            ItemStack stack = src.get(i);
            if (stack != null && stack.getItem() instanceof Gem && isTrueGem((Gem) stack.getItem(), dest)) {
                NBTTagCompound nbt = new NBTTagCompound();
                stack.writeToNBT(nbt);
                tagList.appendTag(nbt);
            }
        }
        dest.stackTagCompound.setTag(name, tagList);
    }

    public List<ItemStack> detach(ItemStack dest)
    {
        List<ItemStack> stacks = get(dest);
        attach(dest, Collections.EMPTY_LIST);
        return stacks;
    }

    public List<ItemStack> get(ItemStack stack)
    {
        if (hasIt(stack)) {
            List<ItemStack> stacks = getRaw(stack);

            for(Iterator<ItemStack> it = stacks.iterator(); it.hasNext();) {
                ItemStack itStack = it.next();
                if (itStack == null || !(itStack.getItem() instanceof Gem) || !isTrueGem((Gem) itStack.getItem(), stack)) {
                    it.remove();
                }
            }
            return stacks;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ItemStack> getRaw(ItemStack stack)
    {
        NBTTagList nbtList = stack.stackTagCompound.getTagList(name, 10);
        List<ItemStack> stacks = new ArrayList<ItemStack>(nbtList.tagCount());
        int max = RPGCapability.rpgItemRegistr.get(stack.getItem()).gems.get(this).value1;
        for (int i = 0; i < nbtList.tagCount() && i < max; ++i) {
            stacks.add(i, ItemStack.loadItemStackFromNBT(nbtList.getCompoundTagAt(i)));
        }
        return stacks;
    }

    public abstract void activate1(ItemStack stack, EntityPlayer player, Object... meta);

    public abstract void activate2(ItemStack stack, EntityPlayer player, Object... meta);

    public void activate1All(ItemStack stack, EntityPlayer player, Object... meta)
    {
        List<ItemStack> stacks = get(stack);
        for (ItemStack it : stacks) {
            activate1(it, player, meta);
        }
    }

    public void activate2All(ItemStack stack, EntityPlayer player, Object... meta)
    {
        List<ItemStack> stacks = get(stack);
        for (ItemStack it : stacks) {
            activate2(it, player, meta);
        }
    }

    public String getDispayName()
    {
        return DangerRPG.trans(name);
    }

    @Override
    public final int hashCode()
    {
        return hash;
    }

    public boolean isConfigurable()
    {
        return true;
    }
}
