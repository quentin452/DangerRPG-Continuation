package mixac1.dangerrpg.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public abstract class GuiInfoBookContent extends GuiScrollingList
{
    protected Minecraft mc;
    protected ArrayList list;
    protected GuiInfoBook parent;

    public GuiInfoBookContent(Minecraft mc, int width, int height, int top, int size, int left, int entryHeight, GuiInfoBook parent)
    {
        super(mc, width, height, top, top + size, left, entryHeight);
        this.mc = mc;
        this.parent = parent;
        init();
    }

    @Override
    protected final int getSize()
    {
        return list.size();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);

        int offsetX = (parent.width  - GuiInfoBook.bookImageW)  / 2;
        int offsetY = (parent.height - GuiInfoBook.bookImageH) / 2;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GuiInfoBook.TEXTURE);
        parent.drawTexturedModalRect(left, top - slotHeight, left - offsetX, top - offsetY - slotHeight, listWidth, slotHeight);
        parent.drawTexturedModalRect(left, bottom, left - offsetX, bottom - offsetY, listWidth, slotHeight);
    }

    public void init()
    {
        list = new ArrayList();
    }
}
