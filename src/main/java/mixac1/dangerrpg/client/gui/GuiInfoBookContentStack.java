package mixac1.dangerrpg.client.gui;

import java.util.*;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.util.*;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentStack extends GuiInfoBookContent {

    private final ItemStack stack;
    private EntityPlayer player;

    public GuiInfoBookContentStack(final Minecraft mc, final int width, final int height, final int top, final int size,
        final int left, final GuiInfoBook parent, final ItemStack stack) {
        super(mc, width, height, top, size, left, mc.fontRenderer.FONT_HEIGHT + 2, parent);
        this.stack = stack;
        if (parent.target instanceof EntityPlayer) {
            this.player = (EntityPlayer) parent.target;
        }
    }

    public void init() {
        super.init();
        if (this.stack == null) {
            this.addCenteredString(DangerRPG.trans("rpgstr.no_item"));
            return;
        }
        if (this.player == null) {
            return;
        }
        final Item item = this.stack.getItem();
        this.addCenteredString(
            this.stack.getDisplayName()
                .toUpperCase());
        this.addString("");
        final boolean isRPGable = RPGItemHelper.isRPGable(this.stack);
        if (isRPGable) {
            this.addString(
                String.format(
                    "%s: %d\n",
                    ItemAttributes.LEVEL.getDispayName(),
                    (int) ItemAttributes.LEVEL.get(this.stack)));
            if (ItemAttributes.LEVEL.isMax(this.stack)) {
                this.addString(DangerRPG.trans("rpgstr.max"));
            } else if (ItemAttributes.MAX_EXP.hasIt(this.stack)) {
                this.addString(
                    String.format(
                        "%s: %d/%d",
                        ItemAttributes.CURR_EXP.getDispayName(),
                        (int) ItemAttributes.CURR_EXP.get(this.stack),
                        (int) ItemAttributes.MAX_EXP.get(this.stack)));
            }
            if (ItemAttributes.MAX_DURABILITY.hasIt(this.stack)) {
                if (!this.stack.isItemStackDamageable()) {
                    this.addString(
                        String.format(
                            "%s: %s",
                            ItemAttributes.DURABILITY.getDispayName(),
                            DangerRPG.trans("rpgstr.unbreakable")));
                } else {
                    this.addString(
                        String.format(
                            "%s: %s/%s",
                            ItemAttributes.DURABILITY.getDispayName(),
                            ItemAttributes.DURABILITY.getDispayValue(this.stack, this.player),
                            ItemAttributes.MAX_DURABILITY.getDispayValue(this.stack, this.player)));
                }
            }
            this.addString("");
        }
        if (this.stack.getItem() instanceof IHasBooksInfo) {
            final String s = ((IHasBooksInfo) this.stack.getItem()).getInformationToInfoBook(this.stack, this.player);
            if (s != null) {
                this.addCenteredString(
                    DangerRPG.trans("rpgstr.description")
                        .toUpperCase());
                this.addString("");
                this.addString(s);
                this.addString("");
            }
        }
        if (item instanceof Gem) {
            final Gem gem = (Gem) item;
            this.addString(Utils.toString(DangerRPG.trans("rpgstr.target_it"), ":"));
            if (!gem.itemTypes.isEmpty()) {
                for (final RPGItemRegister.ItemType it : gem.itemTypes) {
                    this.addString(Utils.toString("- ", it.getDisplayName()));
                }
            } else {
                this.addString(Utils.toString("- ", RPGItemRegister.ItemType.getDisplayNameAll()));
            }
            this.addString("");
        }
        if (isRPGable) {
            if (!RPGCapability.rpgItemRegistr
                .get(this.stack.getItem()).isSupported) {
                this.addString(DangerRPG.trans("rpgstr.item_not_supported"));
                this.addString("");
            }
            final Set<ItemAttribute> attrs = new LinkedHashSet<>(
                RPGItemHelper.getItemAttributes(this.stack));
            attrs.remove(ItemAttributes.LEVEL);
            attrs.remove(ItemAttributes.MAX_EXP);
            attrs.remove(ItemAttributes.DURABILITY);
            attrs.remove(ItemAttributes.MAX_DURABILITY);
            if (!attrs.isEmpty()) {
                this.addCenteredString(
                    DangerRPG.trans("rpgstr.parametres")
                        .toUpperCase());
                this.addString("");
                boolean flag = false;
                flag |= this.addAttribute(ItemAttributes.PHYSIC_ARMOR, attrs);
                flag |= this.addAttribute(ItemAttributes.MAGIC_ARMOR, attrs);
                flag |= this.addAttribute(ItemAttributes.MELEE_DAMAGE, attrs);
                flag |= this.addAttribute(ItemAttributes.SHOT_DAMAGE, attrs);
                flag |= this.addAttribute(ItemAttributes.SHOT_POWER, attrs);
                flag |= this.addAttribute(ItemAttributes.MELEE_SPEED, attrs);
                flag |= this.addAttribute(ItemAttributes.SHOT_SPEED, attrs);
                flag |= this.addAttribute(ItemAttributes.MANA_COST, attrs);
                flag |= this.addAttribute(ItemAttributes.REACH, attrs);
                flag |= this.addAttribute(ItemAttributes.KNOCKBACK, attrs);
                if (flag) {
                    this.addString("");
                    flag = false;
                }
                flag |= this.addAttribute(ItemAttributes.STR_MUL, attrs);
                flag |= this.addAttribute(ItemAttributes.AGI_MUL, attrs);
                flag |= this.addAttribute(ItemAttributes.INT_MUL, attrs);
                flag |= this.addAttribute(ItemAttributes.KNBACK_MUL, attrs);
                if (flag) {
                    this.addString("");
                    flag = false;
                }
                flag |= this.addAttribute(ItemAttributes.EFFICIENCY, attrs);
                flag |= this.addAttribute(ItemAttributes.ENCHANTABILITY, attrs);
                for (final ItemAttribute iter : attrs) {
                    if (iter.isVisibleInInfoBook(this.stack)) {
                        this.addString(
                            String
                                .format("%s : %s", iter.getDispayName(), iter.getDispayValue(this.stack, this.player)));
                    }
                }
                this.addString("");
            }
            final Set<GemType> set = RPGCapability.rpgItemRegistr
                .get(item).gems.keySet();
            for (final GemType gemType : set) {
                final List<ItemStack> list = gemType.get(this.stack);
                for (final ItemStack it2 : list) {
                    this.initGem(it2, gemType);
                }
            }
        } else if (!(item instanceof ItemBlock)) {
            if (this.stack.isItemStackDamageable()) {
                this.addString(
                    String.format(
                        "%s: %d/%d",
                        ItemAttributes.DURABILITY.getDispayName(),
                        this.stack.getItemDamage(),
                        this.stack.getMaxDamage()));
            } else {
                this.addString(
                    String.format(
                        "%s: %s",
                        ItemAttributes.DURABILITY.getDispayName(),
                        DangerRPG.trans("rpgstr.unbreakable")));
            }
            this.addString("");
        }
    }

    private void initGem(final ItemStack stack, final GemType gemType) {
        if (stack == null || !(stack.getItem() instanceof Gem) || !RPGItemHelper.isRPGable(stack)) {
            return;
        }
        final Gem gem = (Gem) stack.getItem();
        this.addCenteredString("----------------------------------------------------------------");
        this.addCenteredString(
            DangerRPG.trans("rpgstr.gem")
                .toUpperCase());
        this.addString("");
        this.addString(Utils.toString(DangerRPG.trans("rpgstr.name"), ": ", stack.getDisplayName()));
        this.addString(Utils.toString(DangerRPG.trans("rpgstr.type"), ": ", gemType.getDisplayName()));
        this.addString("");
        final String tmp = ItemAttributes.LEVEL.isMax(stack) ? Utils.toString(" (", DangerRPG.trans("rpgstr.max"), ")")
            : "";
        this.addString(
            Utils.toString(ItemAttributes.LEVEL.getDispayName(), ": ", (int) ItemAttributes.LEVEL.get(stack), tmp));
        this.addString("");
        if (stack.getItem() instanceof IHasBooksInfo) {
            final String s = ((IHasBooksInfo) stack.getItem()).getInformationToInfoBook(stack, this.player);
            if (s != null) {
                this.addCenteredString(
                    DangerRPG.trans("rpgstr.description")
                        .toUpperCase());
                this.addString("");
                this.addString(s);
                this.addString("");
            }
        }
        final Set<ItemAttribute> attrs = new LinkedHashSet<ItemAttribute>(RPGItemHelper.getItemAttributes(stack));
        attrs.remove(ItemAttributes.LEVEL);
        if (!attrs.isEmpty()) {
            this.addCenteredString(
                DangerRPG.trans("rpgstr.parametres")
                    .toUpperCase());
            this.addString("");
            for (final ItemAttribute iter : attrs) {
                if (iter.isVisibleInInfoBook(stack)) {
                    this.addString(
                        String.format("%s : %s", iter.getDispayName(), iter.getDispayValue(stack, this.player)));
                }
            }
            this.addString("");
        }
    }

    private void addString(final String str) {
        this.list.addAll(this.mc.fontRenderer.listFormattedStringToWidth(str, this.listWidth - 15));
    }

    private void addCenteredString(final String str) {
        this.list.add(new CenteredString(str));
    }

    private boolean addAttribute(final ItemAttribute attr, final Set<ItemAttribute> set) {
        if (attr.hasIt(this.stack) && attr.isVisibleInInfoBook(this.stack)) {
            this.addString(
                String.format("%s : %s", attr.getDispayName(), attr.getDispayValue(this.stack, this.player)));
            set.remove(attr);
            return true;
        }
        return false;
    }

    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        super.drawScreen(mouseX, mouseY, par3);
        final String s = DangerRPG.trans("rpgstr.item_info");
        this.mc.fontRenderer.drawStringWithShadow(
            s,
            this.left + (this.listWidth - this.mc.fontRenderer.getStringWidth(s)) / 2,
            this.top - this.mc.fontRenderer.FONT_HEIGHT - 4,
            16777215);
    }

    protected void elementClicked(final int index, final boolean doubleClick) {}

    protected boolean isSelected(final int index) {
        return false;
    }

    protected void drawBackground() {}

    protected void drawSlot(final int var1, final int var2, final int var3, final int var4, final Tessellator var5) {
        final Object obj = this.list.get(var1);
        if (obj instanceof CenteredString) {
            ((CenteredString) obj).draw(this.left, var3, 16777215);
        } else {
            this.mc.fontRenderer.drawString(obj.toString(), this.left + 5, var3, 16777215);
        }
    }

    public class CenteredString {

        String str;

        public CenteredString(final String str) {
            this.str = str;
        }

        public void draw(final int x, final int y, final int color) {
            final String s = GuiInfoBookContentStack.this.mc.fontRenderer
                .trimStringToWidth(this.str, GuiInfoBookContentStack.this.listWidth);
            GuiInfoBookContentStack.this.mc.fontRenderer.drawString(
                s,
                x + (GuiInfoBookContentStack.this.listWidth
                    - GuiInfoBookContentStack.this.mc.fontRenderer.getStringWidth(s)) / 2,
                y,
                color);
        }
    }
}
