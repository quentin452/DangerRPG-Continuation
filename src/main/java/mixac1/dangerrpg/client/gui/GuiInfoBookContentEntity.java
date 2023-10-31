package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.util.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentEntity extends GuiInfoBookContent
{
    public static final ResourceLocation TEXTURE;
    public static int imageW;
    public static int imageH;
    public static int offset;
    public static int sizeContent;
    public static int butX;
    public static int butY;
    public static int butU;
    public static int butV;
    public static int butW;
    public static int butH;
    public static int titleW;
    public static int titleH;
    public static int titleX;
    public static int titleY;
    public static int infoX;
    public static int infoY;
    public static int infoI;
    public static int infoW;
    private int currIndex;

    public GuiInfoBookContentEntity(final Minecraft mc, final int width, final int height, final int top, final int size, final int left, final GuiInfoBook parent) {
        super(mc, width, height, top, size - GuiInfoBookContentEntity.imageH - GuiInfoBookContentEntity.offset, left, GuiInfoBookContentEntity.sizeContent, parent);
        this.currIndex = -1;
    }

    public void init() {
        super.init();
        for (final LvlEAProvider attr : this.parent.attributes) {
            this.list.add(new ContentItem(attr));
        }
    }

    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        super.drawScreen(mouseX, mouseY, par3);
        if (this.currIndex >= 0) {
            final int offsetX = this.left;
            final int offsetY = this.bottom + GuiInfoBookContentEntity.offset;
            final int indent = GuiInfoBookContentEntity.infoI + this.mc.fontRenderer.FONT_HEIGHT;
            final LvlEAProvider lvlProv = this.parent.attributes.get(this.currIndex);
            this.mc.getTextureManager().bindTexture(GuiInfoBookContentEntity.TEXTURE);
            this.parent.drawTexturedModalRect(offsetX, offsetY, 0, 0, GuiInfoBookContentEntity.imageW, GuiInfoBookContentEntity.imageH);
            String s = this.mc.fontRenderer.trimStringToWidth(lvlProv.attr.getDisplayName().toUpperCase(), GuiInfoBookContentEntity.titleW);
            this.mc.fontRenderer.drawStringWithShadow(s, offsetX + (GuiInfoBookContentEntity.titleW - this.mc.fontRenderer.getStringWidth(s) + 4) / 2, offsetY + (GuiInfoBookContentEntity.titleH - this.mc.fontRenderer.FONT_HEIGHT + 4) / 2, 16777215);
            int k = 0;
            s = Utils.toString(DangerRPG.trans("ia.lvl"), ": ", lvlProv.getLvl(this.parent.target));
            if (!lvlProv.isMaxLvl(this.parent.target)) {
                s = Utils.toString(s, "   (", lvlProv.maxLvl, ")");
            }
            this.mc.fontRenderer.drawStringWithShadow(s, offsetX + GuiInfoBookContentEntity.infoX, offsetY + GuiInfoBookContentEntity.infoY, 16777215);
            k += indent;
            final String s2 = lvlProv.attr.getDisplayValue(this.parent.target);
            if (s2 != null) {
                s = Utils.toString(DangerRPG.trans("rpgstr.value"), ": ", s2);
                this.mc.fontRenderer.drawStringWithShadow(s, offsetX + GuiInfoBookContentEntity.infoX, offsetY + GuiInfoBookContentEntity.infoY + k, 16777215);
                k += indent;
            }
            s = lvlProv.attr.getInfo();
            final List list = this.mc.fontRenderer.listFormattedStringToWidth(s, GuiInfoBookContentEntity.infoW);
            for (int i = 0; i < list.size(); ++i) {
                this.mc.fontRenderer.drawStringWithShadow(list.get(i).toString(), offsetX + GuiInfoBookContentEntity.infoX, offsetY + GuiInfoBookContentEntity.infoY + k, 16777215);
                k += indent;
            }
            if (!lvlProv.isMaxLvl(this.parent.target)) {
                final int value = lvlProv.getExpUp(this.parent.target);
                s = String.format("%d/%d", this.parent.player.experienceLevel, value);
                final int tmp = offsetX + (GuiInfoBookContentEntity.infoW - this.mc.fontRenderer.getStringWidth(s)) / 2;
                this.mc.fontRenderer.drawStringWithShadow(s, offsetX + (GuiInfoBookContentEntity.infoW - this.mc.fontRenderer.getStringWidth(s)) / 2, offsetY + 73, lvlProv.canUp(this.parent.target, this.parent.player) ? 48896 : 12517376);
            }
            else {
                s = this.mc.fontRenderer.trimStringToWidth(DangerRPG.trans("rpgstr.max"), GuiInfoBookContentEntity.infoW);
                this.mc.fontRenderer.drawStringWithShadow(s, offsetX + (GuiInfoBookContentEntity.infoW - this.mc.fontRenderer.getStringWidth(s)) / 2, offsetY + 73, 15724288);
            }
        }
        String s = DangerRPG.trans("rpgstr.stats");
        this.mc.fontRenderer.drawStringWithShadow(s, this.left + (this.listWidth - this.mc.fontRenderer.getStringWidth(s)) / 2, this.top - this.mc.fontRenderer.FONT_HEIGHT - 4, 16777215);
    }

    protected void elementClicked(final int index, final boolean doubleClick) {
        if (this.currIndex == index) {
            this.currIndex = -1;
        }
        else {
            this.currIndex = index;
        }
    }

    protected boolean isSelected(final int index) {
        return index == this.currIndex;
    }

    protected void drawBackground() {
    }

    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        ((ContentItem)this.list.get(var1)).draw(this.left, var3, 16777215, 48896);
    }

    static {
        TEXTURE = new ResourceLocation("DangerRPG:textures/gui/info_book_player_content.png");
        GuiInfoBookContentEntity.imageW = 176;
        GuiInfoBookContentEntity.imageH = 84;
        GuiInfoBookContentEntity.offset = 5;
        GuiInfoBookContentEntity.sizeContent = 20;
        GuiInfoBookContentEntity.butX = 111;
        GuiInfoBookContentEntity.butY = 1;
        GuiInfoBookContentEntity.butU = 176;
        GuiInfoBookContentEntity.butV = 0;
        GuiInfoBookContentEntity.butW = 32;
        GuiInfoBookContentEntity.butH = 15;
        GuiInfoBookContentEntity.titleW = 109;
        GuiInfoBookContentEntity.titleH = 15;
        GuiInfoBookContentEntity.titleX = 1;
        GuiInfoBookContentEntity.titleY = 1;
        GuiInfoBookContentEntity.infoX = 5;
        GuiInfoBookContentEntity.infoY = 20;
        GuiInfoBookContentEntity.infoI = 2;
        GuiInfoBookContentEntity.infoW = GuiInfoBookContentEntity.imageW - GuiInfoBookContentEntity.infoX * 2;
    }

    public class ContentItem
    {
        private LvlEAProvider attr;

        public ContentItem(final LvlEAProvider attr) {
            this.attr = attr;
        }

        public void draw(final int x, final int y, final int color1, final int color2) {
            String s = GuiInfoBookContentEntity.this.mc.fontRenderer.trimStringToWidth(this.attr.attr.getDisplayName(), GuiInfoBookContentEntity.this.listWidth - 20);
            GuiInfoBookContentEntity.this.mc.fontRenderer.drawStringWithShadow(s, x + (GuiInfoBookContentEntity.this.listWidth - GuiInfoBookContentEntity.this.mc.fontRenderer.getStringWidth(s)) / 2, y + (GuiInfoBookContentEntity.sizeContent - GuiInfoBookContentEntity.this.mc.fontRenderer.FONT_HEIGHT) / 2, 16777215);
            s = String.valueOf(this.attr.getLvl(GuiInfoBookContentEntity.this.parent.target));
            final int color3 = this.attr.isMaxLvl(GuiInfoBookContentEntity.this.parent.target) ? 15724288 : (this.attr.canUp(GuiInfoBookContentEntity.this.parent.target, GuiInfoBookContentEntity.this.parent.player) ? 48896 : 16777215);
            GuiInfoBookContentEntity.this.mc.fontRenderer.drawStringWithShadow(s, x + 5, y + (GuiInfoBookContentEntity.sizeContent - GuiInfoBookContentEntity.this.mc.fontRenderer.FONT_HEIGHT) / 2, color3);
        }
    }

    public static class LevelUpButton extends GuiButton
    {
        private GuiInfoBookContentEntity parent;
        private boolean isUp;

        public LevelUpButton(final int id, final boolean isUp, final int x, final int y, final GuiInfoBookContentEntity parent) {
            super(id, x, y, GuiInfoBookContentEntity.butW, GuiInfoBookContentEntity.butH, isUp ? "+" : "-");
            this.parent = parent;
            this.isUp = isUp;
        }

        public void drawButton(final Minecraft mc, final int par1, final int par2) {
            GL11.glDisable(2896);
            if (this.visible && this.parent.currIndex >= 0) {
                final boolean flag = par1 >= this.xPosition && par2 >= this.yPosition && par1 < this.xPosition + this.width && par2 < this.yPosition + this.height;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                mc.getTextureManager().bindTexture(GuiInfoBookContentEntity.TEXTURE);
                this.enabled = (this.isUp ? this.parent.parent.attributes.get(this.parent.currIndex).canUp(this.parent.parent.target, this.parent.parent.player) : this.parent.parent.attributes.get(this.parent.currIndex).canDown(this.parent.parent.target, this.parent.parent.player));
                int color = 9539985;
                if (this.enabled) {
                    if (flag) {
                        if (!Mouse.isButtonDown(0)) {
                            this.drawTexturedModalRect(this.xPosition, this.yPosition, GuiInfoBookContentEntity.butU, GuiInfoBookContentEntity.butV + GuiInfoBookContentEntity.butH, this.width, this.height);
                            color = 15724288;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(this.xPosition, this.yPosition, GuiInfoBookContentEntity.butU, GuiInfoBookContentEntity.butV, this.width, this.height);
                        color = 15724288;
                    }
                }
                mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + (this.width - mc.fontRenderer.getStringWidth(this.displayString)) / 2, this.yPosition + (this.height - mc.fontRenderer.FONT_HEIGHT) / 2, color);
            }
        }

        public boolean mousePressed(final Minecraft mc, final int x, final int y) {
            return super.mousePressed(mc, x, y) && this.parent.parent.attributes.get(this.parent.currIndex).tryUp(this.parent.parent.target, this.parent.parent.player, this.isUp);
        }
    }
}
