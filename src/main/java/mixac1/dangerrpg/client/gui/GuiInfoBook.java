package mixac1.dangerrpg.client.gui;

import java.util.*;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

@SideOnly(Side.CLIENT)
public class GuiInfoBook extends GuiScreen {

    public static final ResourceLocation TEXTURE;
    public EntityPlayer player;
    public EntityLivingBase target;
    public boolean isTargetPlayer;
    public List<LvlEAProvider> attributes;
    private ItemStack[] stacks;
    public static int currContent;
    private SelectContentButton[] butContent;
    public GuiInfoBookContent[] content;
    private int offsetX;
    private int offsetY;
    public static int bookImageW;
    public static int bookImageH;
    public static int titleHeight;
    public static int butContentX;
    public static int butContentY;
    public static int butContentU;
    public static int butContentV;
    public static int butContentI;
    public static int butContentS;
    public static int emptyIconU;
    public static int emptyIconV;
    public static int contentX;
    public static int contentY;
    public static int contentS;
    private GuiInfoBookContentEntity.LevelUpButton buttonUp;
    private GuiInfoBookContentEntity.LevelUpButton buttonDown;

    public GuiInfoBook(final EntityPlayer player) {
        this.content = new GuiInfoBookContent[6];
        this.player = player;
        final MovingObjectPosition mop = RPGHelper.getMouseOver(0.0f, 10.0f);
        if (mop != null && mop.entityHit != null
            && mop.entityHit instanceof EntityPlayer
            && RPGEntityHelper.isRPGable((EntityLivingBase) mop.entityHit)) {
            this.target = (EntityLivingBase) mop.entityHit;
            this.isTargetPlayer = true;
        } else {
            this.target = (EntityLivingBase) player;
            this.isTargetPlayer = true;
        }
        this.attributes = (List<LvlEAProvider>) RPGEntityProperties.get(this.target)
            .getLvlProviders();
    }

    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        super.setWorldAndResolution(mc, width, height);
        if (this.target != mc.thePlayer) {
            GuiInfoBook.currContent = 0;
        }
        if (this.isTargetPlayer) {
            final EntityPlayer tmp = (EntityPlayer) this.target;
            this.stacks = new ItemStack[] { tmp.getCurrentEquippedItem(), tmp.getCurrentArmor(3),
                tmp.getCurrentArmor(2), tmp.getCurrentArmor(1), tmp.getCurrentArmor(0) };
        } else {
            this.stacks = new ItemStack[5];
        }
        this.offsetX = (width - GuiInfoBook.bookImageW) / 2;
        this.offsetY = (height - GuiInfoBook.bookImageH) / 2;
        this.content[0] = new GuiInfoBookContentEntity(
            mc,
            GuiInfoBook.bookImageW - GuiInfoBook.contentX * 2,
            0,
            this.offsetY + GuiInfoBook.contentY,
            GuiInfoBook.contentS,
            this.offsetX + GuiInfoBook.contentX,
            this);
        for (int i = 1; i < this.content.length; ++i) {
            this.content[i] = new GuiInfoBookContentStack(
                mc,
                GuiInfoBook.bookImageW - GuiInfoBook.contentX * 2,
                0,
                this.offsetY + GuiInfoBook.contentY,
                GuiInfoBook.contentS,
                this.offsetX + GuiInfoBook.contentX,
                this,
                this.stacks[i - 1]);
        }
        this.buttonList.add(
            this.buttonDown = new GuiInfoBookContentEntity.LevelUpButton(
                100,
                false,
                this.offsetX + GuiInfoBook.contentX + GuiInfoBookContentEntity.butX,
                this.offsetY + GuiInfoBook.contentY
                    + GuiInfoBook.contentS
                    + GuiInfoBookContentEntity.butY
                    - GuiInfoBookContentEntity.imageH,
                (GuiInfoBookContentEntity) this.content[0]));
        this.buttonList.add(
            this.buttonUp = new GuiInfoBookContentEntity.LevelUpButton(
                101,
                true,
                this.offsetX + GuiInfoBook.contentX + GuiInfoBookContentEntity.butX + GuiInfoBookContentEntity.butW,
                this.offsetY + GuiInfoBook.contentY
                    + GuiInfoBook.contentS
                    + GuiInfoBookContentEntity.butY
                    - GuiInfoBookContentEntity.imageH,
                (GuiInfoBookContentEntity) this.content[0]));
        this.content[GuiInfoBook.currContent].init();
    }

    public void initGui() {
        this.buttonList.clear();
        this.offsetX = (this.width - GuiInfoBook.bookImageW) / 2;
        this.offsetY = (this.height - GuiInfoBook.bookImageH) / 2;
        int i = 0;
        this.butContent = new SelectContentButton[6];
        for (int k = 0; k < 6; ++k) {
            this.buttonList.add(
                this.butContent[k] = new SelectContentButton(
                    i++,
                    this.offsetX + GuiInfoBook.butContentX + (GuiInfoBook.butContentS + GuiInfoBook.butContentI) * k,
                    this.offsetY + GuiInfoBook.butContentY));
        }
    }

    protected void keyTyped(final char c, final int keyCode) {
        super.keyTyped(c, keyCode);
        if (keyCode == 1 || keyCode == 18 || keyCode == RPGKeyBinds.infoBookKey.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawScreen(final int mouseX, final int mouseY, final float par3) {
        GL11.glPushMatrix();
        this.offsetX = (this.width - GuiInfoBook.bookImageW) / 2;
        this.offsetY = (this.height - GuiInfoBook.bookImageH) / 2;
        for (int k = 0; k < this.stacks.length; ++k) {
            if (this.stacks[k] != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GuiScreen.itemRender.renderItemAndEffectIntoGUI(
                    this.mc.fontRenderer,
                    this.mc.getTextureManager(),
                    this.stacks[k],
                    this.butContent[k + 1].xPosition + 2,
                    this.butContent[k + 1].yPosition + 2);
                GuiScreen.itemRender.renderItemOverlayIntoGUI(
                    this.mc.fontRenderer,
                    this.mc.getTextureManager(),
                    this.stacks[k],
                    this.butContent[k + 1].xPosition + 2,
                    this.butContent[k + 1].yPosition + 2);
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager()
            .bindTexture(GuiInfoBook.TEXTURE);
        this.drawTexturedModalRect(this.offsetX, this.offsetY, 0, 0, GuiInfoBook.bookImageW, GuiInfoBook.bookImageH);
        final String title = Utils
            .toString(DangerRPG.trans("rpgstr.info_about"), " ", this.target.getCommandSenderName());
        this.fontRendererObj.drawStringWithShadow(
            title,
            this.offsetX + (GuiInfoBook.bookImageW - this.fontRendererObj.getStringWidth(title)) / 2,
            this.offsetY + (GuiInfoBook.titleHeight - this.fontRendererObj.FONT_HEIGHT) / 2 + 2,
            16777215);
        this.content[GuiInfoBook.currContent].drawScreen(mouseX, mouseY, par3);
        super.drawScreen(mouseX, mouseY, par3);
        GL11.glPopMatrix();
    }

    static {
        TEXTURE = new ResourceLocation("dangerrpg", "textures/gui/info_book.png");
        GuiInfoBook.currContent = 0;
        GuiInfoBook.bookImageW = 186;
        GuiInfoBook.bookImageH = 254;
        GuiInfoBook.titleHeight = 16;
        GuiInfoBook.butContentX = 31;
        GuiInfoBook.butContentY = 16;
        GuiInfoBook.butContentU = 186;
        GuiInfoBook.butContentV = 0;
        GuiInfoBook.butContentI = 1;
        GuiInfoBook.butContentS = 20;
        GuiInfoBook.emptyIconU = 206;
        GuiInfoBook.emptyIconV = 0;
        GuiInfoBook.contentX = 5;
        GuiInfoBook.contentY = 55;
        GuiInfoBook.contentS = 190;
    }

    class SelectContentButton extends GuiButton {

        public SelectContentButton(final int id, final int x, final int y) {
            super(id, x, y, GuiInfoBook.butContentS, GuiInfoBook.butContentS, "");
        }

        public void drawButton(final Minecraft mc, final int par1, final int par2) {
            if (this.visible) {
                final boolean flag = par1 >= this.xPosition && par2 >= this.yPosition
                    && par1 < this.xPosition + this.width
                    && par2 < this.yPosition + this.height;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                mc.getTextureManager()
                    .bindTexture(GuiInfoBook.TEXTURE);
                if (this.id == GuiInfoBook.currContent) {
                    this.drawTexturedModalRect(
                        this.xPosition,
                        this.yPosition,
                        GuiInfoBook.butContentU,
                        GuiInfoBook.butContentV + GuiInfoBook.butContentS,
                        GuiInfoBook.butContentS,
                        GuiInfoBook.butContentS);
                } else if (flag && this.enabled) {
                    this.drawTexturedModalRect(
                        this.xPosition,
                        this.yPosition,
                        GuiInfoBook.butContentU,
                        GuiInfoBook.butContentV,
                        GuiInfoBook.butContentS,
                        GuiInfoBook.butContentS);
                }
                if (this.id == 0 || GuiInfoBook.this.stacks[this.id - 1] == null) {
                    this.drawTexturedModalRect(
                        this.xPosition + 2,
                        this.yPosition + 2,
                        GuiInfoBook.emptyIconU + (GuiInfoBook.this.isTargetPlayer ? 0 : 16),
                        GuiInfoBook.emptyIconV + this.id * 16,
                        16,
                        16);
                }
            }
        }

        public boolean mousePressed(final Minecraft mc, final int x, final int y) {
            if (super.mousePressed(mc, x, y)) {
                GuiInfoBook.currContent = this.id;
                GuiInfoBook.this.content[this.id].init();
                GuiInfoBook.this.buttonUp.visible = (this.id == 0);
                GuiInfoBook.this.buttonDown.visible = (this.id == 0);
                return true;
            }
            return false;
        }
    }
}
