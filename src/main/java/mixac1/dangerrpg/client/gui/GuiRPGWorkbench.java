package mixac1.dangerrpg.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.inventory.*;

@SideOnly(Side.CLIENT)
public class GuiRPGWorkbench extends GuiContainer {

    public static final ResourceLocation TEXTURE;
    public static int textureW;
    public static int textureH;
    public static int nameStrY;
    public static int invStrX;
    public static int invStrY;

    public GuiRPGWorkbench(final InventoryPlayer inventory, final World world, final int x, final int y, final int z) {
        super((Container) new ContainerRPGWorkbench(inventory, world, x, y, z));
        this.xSize = GuiRPGWorkbench.textureW;
        this.ySize = GuiRPGWorkbench.textureH;
    }

    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        this.mc.getTextureManager()
            .bindTexture(GuiRPGWorkbench.TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String s1 = StatCollector.translateToLocal(RPGBlocks.rpgWorkbench.getLocalizedName());
        final String s2 = StatCollector.translateToLocal("key.inventory");
        this.fontRendererObj.drawString(
            s1,
            (this.xSize - this.fontRendererObj.getStringWidth(s1)) / 2,
            GuiRPGWorkbench.nameStrY,
            4210752);
        this.fontRendererObj.drawString(s2, GuiRPGWorkbench.invStrX, GuiRPGWorkbench.invStrY, 4210752);
    }

    static {
        TEXTURE = new ResourceLocation("dangerrpg", "textures/gui/container/gui_rpg_workbench.png");
        GuiRPGWorkbench.textureW = 176;
        GuiRPGWorkbench.textureH = 227;
        GuiRPGWorkbench.nameStrY = 5;
        GuiRPGWorkbench.invStrX = 8;
        GuiRPGWorkbench.invStrY = 133;
    }
}
