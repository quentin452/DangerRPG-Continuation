package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiRPGWorkbench extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/container/gui_rpg_workbench.png");

    public static int textureW = 176;
    public static int textureH = 227;

    public static int nameStrY = 5;

    public static int invStrX = 8;
    public static int invStrY = 133;

    public GuiRPGWorkbench(InventoryPlayer inventory, World world, int x, int y, int z)
    {
        super(new ContainerRPGWorkbench(inventory, world, x, y, z));

        xSize = textureW;
        ySize = textureH;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s1 = StatCollector.translateToLocal(RPGBlocks.rpgWorkbench.getLocalizedName());
        String s2 = StatCollector.translateToLocal("key.inventory");
        fontRendererObj.drawString(s1, (xSize - fontRendererObj.getStringWidth(s1)) / 2, nameStrY, 0x404040);
        fontRendererObj.drawString(s2, invStrX, invStrY, 0x404040);
    }
}
