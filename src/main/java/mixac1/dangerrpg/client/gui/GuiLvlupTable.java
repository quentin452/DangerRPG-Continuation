package mixac1.dangerrpg.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.inventory.*;
import mixac1.dangerrpg.item.gem.*;

@SideOnly(Side.CLIENT)
public class GuiLvlupTable extends GuiContainer {

    public static final ResourceLocation TEXTURE;
    public static int invStrX;
    public static int invStrY;
    public static int butX;
    public static int butY;
    public static int butW;
    public static int butH;
    public static int butU;
    public static int butV;
    public static int slotU;
    public static int slotV;
    private LevelUpButton but;

    public GuiLvlupTable(final InventoryPlayer inventory, final World world, final int x, final int y, final int z) {
        super((Container) new ContainerLvlupTable((IInventory) inventory, world, x, y, z));
        this.xSize = 176;
        this.ySize = 211;
    }

    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        super.setWorldAndResolution(mc, width, height);
        this.buttonList.add(
            this.but = new LevelUpButton(
                0,
                (width - this.xSize) / 2 + GuiLvlupTable.butX,
                (height - this.ySize) / 2 + GuiLvlupTable.butY));
    }

    public void updateScreen() {
        super.updateScreen();
    }

    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        this.mc.getTextureManager()
            .bindTexture(GuiLvlupTable.TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final ContainerLvlupTable clt = (ContainerLvlupTable) this.inventorySlots;
        for (int i = clt.staticSize; i < clt.inventorySlots.size(); ++i) {
            final Slot slot = (Slot) clt.inventorySlots.get(i);
            this.drawTexturedModalRect(
                this.guiLeft + slot.xDisplayPosition - 1,
                this.guiTop + slot.yDisplayPosition - 1,
                GuiLvlupTable.slotU,
                GuiLvlupTable.slotV,
                18,
                18);
        }
    }

    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String s1 = StatCollector.translateToLocal(RPGBlocks.lvlupTable.getLocalizedName());
        final String s2 = StatCollector.translateToLocal("key.inventory");
        this.fontRendererObj.drawString(s1, (this.xSize - this.fontRendererObj.getStringWidth(s1)) / 2, 5, 4210752);
        this.fontRendererObj.drawString(s2, GuiLvlupTable.invStrX, GuiLvlupTable.invStrY, 4210752);
    }

    static {
        TEXTURE = new ResourceLocation("dangerrpg", "textures/gui/container/gui_lvlup_table.png");
        GuiLvlupTable.invStrX = 8;
        GuiLvlupTable.invStrY = 117;
        GuiLvlupTable.butX = 115;
        GuiLvlupTable.butY = 110;
        GuiLvlupTable.butW = 54;
        GuiLvlupTable.butH = 15;
        GuiLvlupTable.butU = 176;
        GuiLvlupTable.butV = 18;
        GuiLvlupTable.slotU = 176;
        GuiLvlupTable.slotV = 0;
    }

    public class LevelUpButton extends GuiButton {

        public LevelUpButton(final int id, final int x, final int y) {
            super(id, x, y, GuiLvlupTable.butW, GuiLvlupTable.butH, DangerRPG.trans("rpgstr.upgrade"));
        }

        public void drawButton(final Minecraft mc, final int par1, final int par2) {
            GL11.glDisable(2896);
            if (this.visible) {
                final boolean flag = par1 >= this.xPosition && par2 >= this.yPosition
                    && par1 < this.xPosition + this.width
                    && par2 < this.yPosition + this.height;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                mc.getTextureManager()
                    .bindTexture(GuiLvlupTable.TEXTURE);
                int color = 9539985;
                this.enabled = GuiLvlupTable.this.inventorySlots.enchantItem((EntityPlayer) mc.thePlayer, 0);
                if (this.enabled) {
                    if (flag) {
                        if (!Mouse.isButtonDown(0)) {
                            this.drawTexturedModalRect(
                                this.xPosition,
                                this.yPosition,
                                GuiLvlupTable.butU,
                                GuiLvlupTable.butV + GuiLvlupTable.butH,
                                this.width,
                                this.height);
                            color = 15724288;
                        }
                    } else {
                        this.drawTexturedModalRect(
                            this.xPosition,
                            this.yPosition,
                            GuiLvlupTable.butU,
                            GuiLvlupTable.butV,
                            this.width,
                            this.height);
                        color = 15724288;
                    }
                }
                mc.fontRenderer.drawStringWithShadow(
                    this.displayString,
                    this.xPosition + (this.width - mc.fontRenderer.getStringWidth(this.displayString)) / 2,
                    this.yPosition + (this.height - mc.fontRenderer.FONT_HEIGHT) / 2,
                    color);
                if (!mc.thePlayer.capabilities.isCreativeMode) {
                    final ItemStack stack = ((ContainerLvlupTable) GuiLvlupTable.this.inventorySlots).invTable.inv[0];
                    if (stack != null && ((ContainerLvlupTable) GuiLvlupTable.this.inventorySlots).expToUp >= 0) {
                        String str = "";
                        if (stack.getItem() instanceof Gem) {
                            str = String.format(
                                "%d/%d",
                                mc.thePlayer.experienceLevel,
                                ((ContainerLvlupTable) GuiLvlupTable.this.inventorySlots).expToUp);
                        } else {
                            str = String.format(
                                "%d/%d",
                                mc.thePlayer.experienceTotal,
                                ((ContainerLvlupTable) GuiLvlupTable.this.inventorySlots).expToUp);
                        }
                        final int strW = mc.fontRenderer.getStringWidth(str);
                        mc.fontRenderer.drawStringWithShadow(
                            str,
                            (strW > GuiLvlupTable.butW) ? (this.xPosition + this.width - strW)
                                : (this.xPosition + (this.width - strW) / 2),
                            this.yPosition + (this.height - mc.fontRenderer.FONT_HEIGHT) / 2 - 15,
                            color);
                    }
                }
            }
        }

        public boolean mousePressed(final Minecraft mc, final int x, final int y) {
            if (super.mousePressed(mc, x, y)) {
                mc.playerController.sendEnchantPacket(GuiLvlupTable.this.inventorySlots.windowId, 0);
                return true;
            }
            return false;
        }
    }
}
