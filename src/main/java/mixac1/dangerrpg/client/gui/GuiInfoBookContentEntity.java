package mixac1.dangerrpg.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentEntity extends GuiInfoBookContent
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/info_book_player_content.png");

    public static int imageW  = 176;
    public static int imageH  = 84;

    public static int offset = 5;
    public static int sizeContent = 20;

    public static int butX = 111;
    public static int butY = 1;
    public static int butU = 176;
    public static int butV = 0;
    public static int butW = 32;
    public static int butH = 15;

    public static int titleW = 109;
    public static int titleH = 15;
    public static int titleX = 1;
    public static int titleY = 1;

    public static int infoX = 5;
    public static int infoY = 20;
    public static int infoI = 2;
    public static int infoW = imageW - infoX * 2;

    private int currIndex = -1;

    public GuiInfoBookContentEntity(Minecraft mc, int width, int height, int top, int size, int left, GuiInfoBook parent)
    {
        super(mc, width, height, top, size - imageH - offset, left, sizeContent, parent);
    }

    @Override
    public void init()
    {
        super.init();
        for (LvlEAProvider attr : parent.attributes) {
            list.add(new ContentItem(attr));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        String s, s1;
        if (currIndex >= 0) {
            int offsetX = left;
            int offsetY = bottom + offset;
            int indent = infoI + mc.fontRenderer.FONT_HEIGHT;
            LvlEAProvider lvlProv = parent.attributes.get(currIndex);

            mc.getTextureManager().bindTexture(TEXTURE);
            parent.drawTexturedModalRect(offsetX, offsetY, 0, 0, imageW, imageH);

            s = mc.fontRenderer.trimStringToWidth(lvlProv.attr.getDisplayName().toUpperCase(), titleW);
            mc.fontRenderer.drawStringWithShadow(s, offsetX + (titleW - mc.fontRenderer.getStringWidth(s) + 4) / 2, offsetY + (titleH - mc.fontRenderer.FONT_HEIGHT + 4) / 2, 0xffffff);

            int k = 0;
            s = Utils.toString(DangerRPG.trans("ia.lvl"), ": ", lvlProv.getLvl(parent.target));
            if (!lvlProv.isMaxLvl(parent.target)) {
                s = Utils.toString(s, "   (", lvlProv.maxLvl, ")");
            }
            mc.fontRenderer.drawStringWithShadow(s, offsetX + infoX, offsetY + infoY, 0xffffff);
            k += indent;

            s1 = lvlProv.attr.getDisplayValue(parent.target);
            if (s1 != null) {
                s = Utils.toString(DangerRPG.trans("rpgstr.value"), ": ", s1);
                mc.fontRenderer.drawStringWithShadow(s, offsetX + infoX, offsetY + infoY + k, 0xffffff);
                k += indent;
            }

            s = lvlProv.attr.getInfo();
            List list = mc.fontRenderer.listFormattedStringToWidth(s, infoW);
            for (int i = 0; i < list.size(); ++i) {
                mc.fontRenderer.drawStringWithShadow(list.get(i).toString(), offsetX + infoX, offsetY + infoY + k, 0xffffff);
                k += indent;
            }


            if (!lvlProv.isMaxLvl(parent.target)) {
                int value = lvlProv.getExpUp(parent.target);
                s = String.format("%d/%d", parent.player.experienceLevel, value);
                int tmp = offsetX + (infoW - mc.fontRenderer.getStringWidth(s)) / 2;
                mc.fontRenderer.drawStringWithShadow(s, offsetX + (infoW - mc.fontRenderer.getStringWidth(s)) / 2, offsetY + 73, lvlProv.canUp(parent.target, parent.player) ? 0x00bf00 : 0xbf0000);
            }
            else {
                s = mc.fontRenderer.trimStringToWidth(DangerRPG.trans("rpgstr.max"), infoW);
                mc.fontRenderer.drawStringWithShadow(s, offsetX + (infoW - mc.fontRenderer.getStringWidth(s)) / 2, offsetY + 73, 0xefef00);
            }
        }

        s = DangerRPG.trans("rpgstr.stats");
        mc.fontRenderer.drawStringWithShadow(s, left + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, top - mc.fontRenderer.FONT_HEIGHT - 4, 0xffffff);
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick)
    {
        if (currIndex == index) {
            currIndex = -1;
        }
        else {
            currIndex = index;
        }
    }

    @Override
    protected boolean isSelected(int index)
    {
        return index == currIndex;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
    {
        ((ContentItem) list.get(var1)).draw(left, var3, 0xffffff, 0x00bf00);
    }

    public class ContentItem
    {
        private LvlEAProvider attr;

        public ContentItem(LvlEAProvider attr)
        {
            this.attr = attr;
        }

        public void draw(int x, int y, int color1, int color2)
        {
            String s = mc.fontRenderer.trimStringToWidth(attr.attr.getDisplayName(), listWidth - 20);
            mc.fontRenderer.drawStringWithShadow(s, x + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, y + (sizeContent - mc.fontRenderer.FONT_HEIGHT) / 2, 0xffffff);

            s = String.valueOf(attr.getLvl(parent.target));
            int color = attr.isMaxLvl(parent.target) ? 0xefef00 : attr.canUp(parent.target, parent.player) ? 0x00bf00 : 0xffffff;
            mc.fontRenderer.drawStringWithShadow(s, x + 5, y + (sizeContent - mc.fontRenderer.FONT_HEIGHT) / 2, color);
        }
    }

    public static class LevelUpButton extends GuiButton
    {
        private GuiInfoBookContentEntity parent;
        private boolean isUp;


        public LevelUpButton(int id, boolean isUp, int x, int y, GuiInfoBookContentEntity parent)
        {
            super(id, x, y, butW, butH, isUp ? "+" : "-");
            this.parent = parent;
            this.isUp = isUp;
        }

        @Override
        public void drawButton(Minecraft mc, int par1, int par2)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            if (visible && parent.currIndex >= 0) {
                boolean flag = par1 >= xPosition && par2 >= yPosition && par1 < xPosition + width && par2 < yPosition + height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);

                enabled = isUp ?
                        parent.parent.attributes.get(parent.currIndex).canUp(parent.parent.target, parent.parent.player) :
                        parent.parent.attributes.get(parent.currIndex).canDown(parent.parent.target, parent.parent.player);
                int color = 0x919191;

                if (enabled) {
                    if (flag) {
                        if (!Mouse.isButtonDown(0)) {
                            this.drawTexturedModalRect(xPosition, yPosition, butU, butV + butH, width, height);
                            color = 0xefef00;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(xPosition, yPosition, butU, butV, width, height);
                        color = 0xefef00;
                    }
                }

                mc.fontRenderer.drawStringWithShadow(displayString, xPosition + (width - mc.fontRenderer.getStringWidth(displayString)) / 2, yPosition + (height - mc.fontRenderer.FONT_HEIGHT) / 2, color);
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int x, int y)
        {
            if (super.mousePressed(mc, x, y) && parent.parent.attributes.get(parent.currIndex).tryUp(parent.parent.target, parent.parent.player, isUp)) {
                return true;
            }
            return false;
        }
    }
}
