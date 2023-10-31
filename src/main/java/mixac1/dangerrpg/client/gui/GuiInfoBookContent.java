package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.client.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;

@SideOnly(Side.CLIENT)
public abstract class GuiInfoBookContent extends GuiScrollingList
{
    protected Minecraft mc;
    protected ArrayList list;
    protected GuiInfoBook parent;
    
    public GuiInfoBookContent(final Minecraft mc, final int width, final int height, final int top, final int size, final int left, final int entryHeight, final GuiInfoBook parent) {
        super(mc, width, height, top, top + size, left, entryHeight);
        this.mc = mc;
        this.parent = parent;
        this.init();
    }
    
    protected final int getSize() {
        return this.list.size();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        super.drawScreen(mouseX, mouseY, par3);
        final int width = this.parent.width;
        final GuiInfoBook parent = this.parent;
        final int offsetX = (width - GuiInfoBook.bookImageW) / 2;
        final int height = this.parent.height;
        final GuiInfoBook parent2 = this.parent;
        final int offsetY = (height - GuiInfoBook.bookImageH) / 2;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final TextureManager getTextureManager = this.mc.getTextureManager();
        final GuiInfoBook parent3 = this.parent;
        getTextureManager.bindTexture(GuiInfoBook.TEXTURE);
        this.parent.drawTexturedModalRect(this.left, this.top - this.slotHeight, this.left - offsetX, this.top - offsetY - this.slotHeight, this.listWidth, this.slotHeight);
        this.parent.drawTexturedModalRect(this.left, this.bottom, this.left - offsetX, this.bottom - offsetY, this.listWidth, this.slotHeight);
    }
    
    public void init() {
        this.list = new ArrayList();
    }
}
