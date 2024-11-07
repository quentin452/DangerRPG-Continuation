package mixac1.dangerrpg.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.inventory.ContainerLvlupTable;
import mixac1.dangerrpg.item.gem.Gem;

@SideOnly(Side.CLIENT)
public class GuiLvlupTable extends GuiContainer {

    public static final ResourceLocation TEXTURE = new ResourceLocation(
        DangerRPG.MODID,
        "textures/gui/container/gui_lvlup_table.png");

    public static int invStrX = 8;
    public static int invStrY = 117;

    public static int butX = 115;
    public static int butY = 110;
    public static int butW = 54;
    public static int butH = 15;
    public static int butU = 176;
    public static int butV = 18;

    public static int slotU = 176;
    public static int slotV = 0;

    private LevelUpButton but;

    public GuiLvlupTable(InventoryPlayer inventory, World world, int x, int y, int z) {
        super(new ContainerLvlupTable(inventory, world, x, y, z));
        xSize = 176;
        ySize = 211;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);
        buttonList.add(but = new LevelUpButton(0, (width - xSize) / 2 + butX, (height - ySize) / 2 + butY));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager()
            .bindTexture(TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        ContainerLvlupTable clt = (ContainerLvlupTable) inventorySlots;
        for (int i = clt.staticSize; i < clt.inventorySlots.size(); ++i) {
            Slot slot = (Slot) clt.inventorySlots.get(i);
            drawTexturedModalRect(
                guiLeft + slot.xDisplayPosition - 1,
                guiTop + slot.yDisplayPosition - 1,
                slotU,
                slotV,
                18,
                18);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s1 = StatCollector.translateToLocal(RPGBlocks.lvlupTable.getLocalizedName());
        String s2 = StatCollector.translateToLocal("key.inventory");
        fontRendererObj.drawString(s1, (xSize - fontRendererObj.getStringWidth(s1)) / 2, 5, 0x404040);
        fontRendererObj.drawString(s2, invStrX, invStrY, 0x404040);
    }

    public class LevelUpButton extends GuiButton {

        public LevelUpButton(int id, int x, int y) {
            super(id, x, y, butW, butH, DangerRPG.trans("rpgstr.upgrade"));
        }

        @Override
        public void drawButton(Minecraft mc, int par1, int par2) {
            GL11.glDisable(GL11.GL_LIGHTING);
            if (visible) {
                boolean flag = par1 >= xPosition && par2 >= yPosition
                    && par1 < xPosition + width
                    && par2 < yPosition + height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager()
                    .bindTexture(TEXTURE);

                int color = 0x919191;

                enabled = inventorySlots.enchantItem(mc.thePlayer, 0);

                if (enabled) {
                    if (flag) {
                        if (!Mouse.isButtonDown(0)) {
                            this.drawTexturedModalRect(xPosition, yPosition, butU, butV + butH, width, height);
                            color = 0xefef00;
                        }
                    } else {
                        this.drawTexturedModalRect(xPosition, yPosition, butU, butV, width, height);
                        color = 0xefef00;
                    }
                }

                mc.fontRenderer.drawStringWithShadow(
                    displayString,
                    xPosition + (width - mc.fontRenderer.getStringWidth(displayString)) / 2,
                    yPosition + (height - mc.fontRenderer.FONT_HEIGHT) / 2,
                    color);

                if (!mc.thePlayer.capabilities.isCreativeMode) {
                    ItemStack stack = ((ContainerLvlupTable) inventorySlots).invTable.inv[0];
                    if (stack != null && ((ContainerLvlupTable) inventorySlots).expToUp >= 0) {
                        String str = "";
                        if (stack.getItem() instanceof Gem) {
                            str = String.format(
                                "%d/%d",
                                mc.thePlayer.experienceLevel,
                                ((ContainerLvlupTable) inventorySlots).expToUp);
                        } else {
                            str = String.format(
                                "%d/%d",
                                mc.thePlayer.experienceTotal,
                                ((ContainerLvlupTable) inventorySlots).expToUp);
                        }

                        int strW = mc.fontRenderer.getStringWidth(str);
                        mc.fontRenderer.drawStringWithShadow(
                            str,
                            strW > butW ? xPosition + width - strW : xPosition + (width - strW) / 2,
                            yPosition + (height - mc.fontRenderer.FONT_HEIGHT) / 2 - 15,
                            color);
                    }
                }
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int x, int y) {
            if (super.mousePressed(mc, x, y)) {
                mc.playerController.sendEnchantPacket(inventorySlots.windowId, 0);
                return true;
            }
            return false;
        }
    }
}
