package mixac1.dangerrpg.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import mixac1.dangerrpg.client.gui.GuiInfoBookContentEntity.LevelUpButton;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiInfoBook extends GuiScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/info_book.png");

    public EntityPlayer player;
    public EntityLivingBase target;
    public boolean isTargetPlayer;
    public List<LvlEAProvider> attributes;
    private ItemStack[] stacks;
    public static int currContent = 0;

    private GuiInfoBook.SelectContentButton[] butContent;
    public GuiInfoBookContent[] content = new GuiInfoBookContent[6];

    private int offsetX;
    private int offsetY;

    public static final int bookImageW = 186;
    public static final int bookImageH = 254;
    public static final int titleHeight = 16;

    public static final int butContentX = 31;
    public static final int butContentY = 16;
    public static final int butContentU = 186;
    public static final int butContentV = 0;
    public static final int butContentI = 1;
    public static final int butContentS = 20;

    public static final int emptyIconU = 206;
    public static final int emptyIconV = 0;

    public static final int contentX = 5;
    public static final int contentY = 55;
    public static final int contentS = 190;

    private LevelUpButton buttonUp;
    private LevelUpButton buttonDown;

    public GuiInfoBook(EntityPlayer player) {
        this.player = player;

        MovingObjectPosition mop = RPGHelper.getMouseOver(0, 10);
        if (mop != null && mop.entityHit != null && mop.entityHit instanceof EntityPlayer && RPGEntityHelper.isRPGable((EntityLivingBase) mop.entityHit)) {
            target = (EntityLivingBase) mop.entityHit;
            isTargetPlayer = true;
        } else {
            target = player;
            isTargetPlayer = true;
        }

        attributes = RPGEntityProperties.get(target).getLvlProviders();
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);

        if (target != mc.thePlayer) {
            currContent = 0;
        }

        if (isTargetPlayer) {
            EntityPlayer tmp = (EntityPlayer) target;
            stacks = new ItemStack[] {
                tmp.getCurrentEquippedItem(),
                tmp.getCurrentArmor(3),
                tmp.getCurrentArmor(2),
                tmp.getCurrentArmor(1),
                tmp.getCurrentArmor(0)
            };
        } else {
            stacks = new ItemStack[5];
        }

        offsetX = (width - bookImageW) / 2;
        offsetY = (height - bookImageH) / 2;

        content[0] = new GuiInfoBookContentEntity(mc, bookImageW - contentX * 2, 0, offsetY + contentY, contentS, offsetX + contentX, this);
        for (int i = 1; i < content.length; ++i) {
            content[i] = new GuiInfoBookContentStack(mc, bookImageW - contentX * 2, 0, offsetY + contentY, contentS, offsetX + contentX, this, stacks[i - 1]);
        }
        buttonList.add(buttonDown = new LevelUpButton(100, false, offsetX + contentX + GuiInfoBookContentEntity.butX, offsetY + contentY + contentS + GuiInfoBookContentEntity.butY - GuiInfoBookContentEntity.imageH, (GuiInfoBookContentEntity) content[0]));
        buttonList.add(buttonUp = new LevelUpButton(101, true, offsetX + contentX + GuiInfoBookContentEntity.butX + GuiInfoBookContentEntity.butW, offsetY + contentY + contentS + GuiInfoBookContentEntity.butY - GuiInfoBookContentEntity.imageH, (GuiInfoBookContentEntity) content[0]));

        content[currContent].init();
    }

    @Override
    public void initGui() {
        buttonList.clear();

        offsetX = (width - bookImageW) / 2;
        offsetY = (height - bookImageH) / 2;

        int i = 0;
        butContent = new GuiInfoBook.SelectContentButton[6];
        for (int k = 0; k < 6; ++k) {
            buttonList.add(butContent[k] = new GuiInfoBook.SelectContentButton(i++, offsetX + butContentX + (butContentS + butContentI) * k, offsetY + butContentY));
        }
    }

    @Override
    protected void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == RPGKeyBinds.infoBookKey.getKeyCode()) {
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        GL11.glPushMatrix();

        offsetX = (width - bookImageW) / 2;
        offsetY = (height - bookImageH) / 2;

        for (int k = 0; k < stacks.length; ++k) {
            if (stacks[k] != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GuiScreen.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stacks[k], butContent[k + 1].xPosition + 2, butContent[k + 1].yPosition + 2);
                // Disabling this line fix https://github.com/quentin452/DangerRPG-Continuation/issues/34
                // GuiScreen.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), stacks[k], butContent[k + 1].xPosition + 2, butContent[k + 1].yPosition + 2);
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, bookImageW, bookImageH);

        String title = Utils.toString(DangerRPG.trans("rpgstr.info_about"), " ", target.getCommandSenderName());
        fontRendererObj.drawStringWithShadow(title, offsetX + (bookImageW - fontRendererObj.getStringWidth(title)) / 2, offsetY + (titleHeight - fontRendererObj.FONT_HEIGHT) / 2 + 2, 0xffffff);

        content[currContent].drawScreen(mouseX, mouseY, par3);

        super.drawScreen(mouseX, mouseY, par3);

        GL11.glPopMatrix();
    }

    class SelectContentButton extends GuiButton {
        public SelectContentButton(int id, int x, int y) {
            super(id, x, y, butContentS, butContentS, "");
        }

        @Override
        public void drawButton(Minecraft mc, int par1, int par2) {
            if (this.visible) {
                boolean flag = par1 >= xPosition && par2 >= yPosition && par1 < xPosition + width && par2 < yPosition + height;

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);

                if (id == currContent) {
                    this.drawTexturedModalRect(xPosition, yPosition, butContentU, butContentV + butContentS, butContentS, butContentS);
                } else if (flag && this.enabled) {
                    this.drawTexturedModalRect(xPosition, yPosition, butContentU, butContentV, butContentS, butContentS);
                }

                if (!(id != 0 && stacks[id - 1] != null)) {
                    drawTexturedModalRect(xPosition + 2, yPosition + 2, emptyIconU + (isTargetPlayer ? 0 : 16), emptyIconV + id * 16, 16, 16);
                }
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int x, int y) {
            if (super.mousePressed(mc, x, y)) {
                currContent = id;
                content[id].init();
                buttonUp.visible = id == 0;
                buttonDown.visible = id == 0;
                return true;
            }
            return false;
        }
    }
}
