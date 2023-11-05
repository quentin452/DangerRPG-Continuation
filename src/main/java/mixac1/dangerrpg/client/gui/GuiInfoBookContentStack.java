package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemType;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentStack extends GuiInfoBookContent {

    private final ItemStack stack;
    private EntityPlayer player;

    public GuiInfoBookContentStack(Minecraft mc, int width, int height, int top, int size, int left, GuiInfoBook parent,
        ItemStack stack) {
        super(mc, width, height, top, size, left, mc.fontRenderer.FONT_HEIGHT + 2, parent);
        this.stack = stack;
        if (parent.target instanceof EntityPlayer) {
            player = (EntityPlayer) parent.target;
        }
    }

    @Override
    public void init() {
        super.init();

        if (stack == null) {
            addCenteredString(DangerRPG.trans("rpgstr.no_item"));
            return;
        }

        if (player == null) {
            return;
        }

        Item item = stack.getItem();

        addCenteredString(
            stack.getDisplayName()
                .toUpperCase());
        addString("");

        boolean isRPGable = RPGItemHelper.isRPGable(stack);
        if (isRPGable) {

            addString(
                String.format("%s: %d\n", ItemAttributes.LEVEL.getDispayName(), (int) ItemAttributes.LEVEL.get(stack)));

            if (ItemAttributes.LEVEL.isMax(stack)) {
                addString(DangerRPG.trans("rpgstr.max"));
            } else {
                if (ItemAttributes.MAX_EXP.hasIt(stack)) {
                    addString(
                        String.format(
                            "%s: %d/%d",
                            ItemAttributes.CURR_EXP.getDispayName(),
                            (int) ItemAttributes.CURR_EXP.get(stack),
                            (int) ItemAttributes.MAX_EXP.get(stack)));
                }
            }

            if (ItemAttributes.MAX_DURABILITY.hasIt(stack)) {
                if (!stack.isItemStackDamageable()) {
                    addString(
                        String.format(
                            "%s: %s",
                            ItemAttributes.DURABILITY.getDispayName(),
                            DangerRPG.trans("rpgstr.unbreakable")));
                } else {
                    addString(
                        String.format(
                            "%s: %s/%s",
                            ItemAttributes.DURABILITY.getDispayName(),
                            ItemAttributes.DURABILITY.getDispayValue(stack, player),
                            ItemAttributes.MAX_DURABILITY.getDispayValue(stack, player)));
                }
            }
            addString("");
        }

        if (stack.getItem() instanceof IHasBooksInfo) {
            String s = ((IHasBooksInfo) stack.getItem()).getInformationToInfoBook(stack, player);
            if (s != null) {
                addCenteredString(
                    DangerRPG.trans("rpgstr.description")
                        .toUpperCase());
                addString("");
                addString(s);
                addString("");
            }
        }

        if (item instanceof Gem) {
            Gem gem = (Gem) item;
            addString(Utils.toString(DangerRPG.trans("rpgstr.target_it"), ":"));
            if (!gem.itemTypes.isEmpty()) {
                for (ItemType it : gem.itemTypes) {
                    addString(Utils.toString("- ", it.getDisplayName()));
                }
            } else {
                addString(Utils.toString("- ", ItemType.getDisplayNameAll()));
            }
            addString("");
        }

        if (isRPGable) {
            if (!RPGCapability.rpgItemRegistr.get(stack.getItem()).isSupported) {
                addString(DangerRPG.trans("rpgstr.item_not_supported"));
                addString("");
            }

            Set<ItemAttribute> attrs = new LinkedHashSet<ItemAttribute>(RPGItemHelper.getItemAttributes(stack));
            attrs.remove(ItemAttributes.LEVEL);
            attrs.remove(ItemAttributes.MAX_EXP);
            attrs.remove(ItemAttributes.DURABILITY);
            attrs.remove(ItemAttributes.MAX_DURABILITY);
            if (!attrs.isEmpty()) {
                addCenteredString(
                    DangerRPG.trans("rpgstr.parametres")
                        .toUpperCase());
                addString("");
                boolean flag = false;

                flag |= addAttribute(ItemAttributes.PHYSIC_ARMOR, attrs);
                flag |= addAttribute(ItemAttributes.MAGIC_ARMOR, attrs);
                flag |= addAttribute(ItemAttributes.MELEE_DAMAGE, attrs);
                flag |= addAttribute(ItemAttributes.SHOT_DAMAGE, attrs);
                flag |= addAttribute(ItemAttributes.SHOT_POWER, attrs);
                flag |= addAttribute(ItemAttributes.MELEE_SPEED, attrs);
                flag |= addAttribute(ItemAttributes.SHOT_SPEED, attrs);
                flag |= addAttribute(ItemAttributes.MANA_COST, attrs);
                flag |= addAttribute(ItemAttributes.REACH, attrs);
                flag |= addAttribute(ItemAttributes.KNOCKBACK, attrs);

                if (flag) {
                    addString("");
                    flag = false;
                }

                flag |= addAttribute(ItemAttributes.STR_MUL, attrs);
                flag |= addAttribute(ItemAttributes.AGI_MUL, attrs);
                flag |= addAttribute(ItemAttributes.INT_MUL, attrs);
                flag |= addAttribute(ItemAttributes.KNBACK_MUL, attrs);

                if (flag) {
                    addString("");
                    flag = false;
                }

                flag |= addAttribute(ItemAttributes.EFFICIENCY, attrs);
                flag |= addAttribute(ItemAttributes.ENCHANTABILITY, attrs);
                for (ItemAttribute iter : attrs) {
                    if (iter.isVisibleInInfoBook(stack)) {
                        addString(String.format("%s : %s", iter.getDispayName(), iter.getDispayValue(stack, player)));
                    }
                }
                addString("");
            }

            Set<GemType> set = RPGCapability.rpgItemRegistr.get(item).gems.keySet();
            for (GemType gemType : set) {
                List<ItemStack> list = gemType.get(stack);
                for (ItemStack it : list) {
                    initGem(it, gemType);
                }
            }
        } else if (!(item instanceof ItemBlock)) {
            if (stack.isItemStackDamageable()) {
                addString(
                    String.format(
                        "%s: %d/%d",
                        ItemAttributes.DURABILITY.getDispayName(),
                        stack.getMaxDamage() - stack.getItemDamage(),
                        stack.getMaxDamage()));
            } else {
                addString(
                    String.format(
                        "%s: %s",
                        ItemAttributes.DURABILITY.getDispayName(),
                        DangerRPG.trans("rpgstr.unbreakable")));
            }
            addString("");
        }
    }

    private void initGem(ItemStack stack, GemType gemType) {
        if (stack == null || !(stack.getItem() instanceof Gem) || !RPGItemHelper.isRPGable(stack)) {
            return;
        }

        Gem gem = (Gem) stack.getItem();
        String tmp;

        addCenteredString("----------------------------------------------------------------");
        addCenteredString(
            DangerRPG.trans("rpgstr.gem")
                .toUpperCase());
        addString("");
        addString(Utils.toString(DangerRPG.trans("rpgstr.name"), ": ", stack.getDisplayName()));
        addString(Utils.toString(DangerRPG.trans("rpgstr.type"), ": ", gemType.getDispayName()));
        addString("");

        tmp = ItemAttributes.LEVEL.isMax(stack) ? Utils.toString(" (", DangerRPG.trans("rpgstr.max"), ")") : "";
        addString(
            Utils.toString(ItemAttributes.LEVEL.getDispayName(), ": ", (int) ItemAttributes.LEVEL.get(stack), tmp));
        addString("");

        if (stack.getItem() instanceof IHasBooksInfo) {
            String s = ((IHasBooksInfo) stack.getItem()).getInformationToInfoBook(stack, player);
            if (s != null) {
                addCenteredString(
                    DangerRPG.trans("rpgstr.description")
                        .toUpperCase());
                addString("");
                addString(s);
                addString("");
            }
        }

        Set<ItemAttribute> attrs = new LinkedHashSet<ItemAttribute>(RPGItemHelper.getItemAttributes(stack));
        attrs.remove(ItemAttributes.LEVEL);
        if (!attrs.isEmpty()) {
            addCenteredString(
                DangerRPG.trans("rpgstr.parametres")
                    .toUpperCase());
            addString("");

            for (ItemAttribute iter : attrs) {
                if (iter.isVisibleInInfoBook(stack)) {
                    addString(String.format("%s : %s", iter.getDispayName(), iter.getDispayValue(stack, player)));
                }
            }
            addString("");
        }
    }

    private void addString(String str) {
        list.addAll(mc.fontRenderer.listFormattedStringToWidth(str, listWidth - 15));
    }

    private void addCenteredString(String str) {
        list.add(new CenteredString(str));
    }

    private boolean addAttribute(ItemAttribute attr, Set<ItemAttribute> set) {
        if (attr.hasIt(stack) && attr.isVisibleInInfoBook(stack)) {
            addString(String.format("%s : %s", attr.getDispayName(), attr.getDispayValue(stack, player)));
            set.remove(attr);

            return true;
        }
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        super.drawScreen(mouseX, mouseY, par3);

        String s = DangerRPG.trans("rpgstr.item_info");
        mc.fontRenderer.drawStringWithShadow(
            s,
            left + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2,
            top - mc.fontRenderer.FONT_HEIGHT - 4,
            0xffffff);
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {}

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        Object obj = list.get(var1);
        if (obj instanceof CenteredString) {
            ((CenteredString) obj).draw(left, var3, 0xffffff);
        } else {
            mc.fontRenderer.drawString(obj.toString(), left + 5, var3, 0xffffff);
        }
    }

    public class CenteredString {

        String str;

        public CenteredString(String str) {
            this.str = str;
        }

        public void draw(int x, int y, int color) {
            String s = mc.fontRenderer.trimStringToWidth(str, listWidth);
            mc.fontRenderer.drawString(s, x + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, y, color);
        }
    }
}
