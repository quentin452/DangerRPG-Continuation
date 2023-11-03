package mixac1.dangerrpg.client.gui;

import com.google.common.collect.Multimap;
import io.netty.util.AttributeMap;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.hook.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

import java.util.Map;

public class RPGGuiIngame extends Gui {

    public static final RPGGuiIngame INSTANCE;
    public Minecraft mc;
    public FontRenderer fr;
    public static final ResourceLocation TEXTURE;
    private int textureW;
    private int textureH;
    private int part1X;
    private int part1Y;
    private int part1U;
    private int part1V;
    private int part1Width;
    private int part1H;
    private int part2X;
    private int part2Y;
    private int part2U;
    private int part2V;
    private int part2W;
    private int part2H;
    private int part3X;
    private int part3Y;
    private int part3U;
    private int part3V;
    private int part3W;
    private int part3H;
    private int barIconX;
    private int barIconY;
    private int barIconW;
    private int barIconH;
    private int barIconU;
    private int barIconV;
    private int barX;
    private int barY;
    private int barW;
    private int barH;
    private int barU;
    private int barV;
    private int chargeW;
    private int chargeH;
    private int chargeU;
    private int chargeV;
    private GuiMode.GuiModeType mode;
    private boolean isInvert;

    public RPGGuiIngame() {
        this.mc = Minecraft.getMinecraft();
        this.fr = this.mc.fontRenderer;
        this.textureW = 139;
        this.textureH = 59;
        this.isInvert = true;
        this.update(GuiMode.GuiModeType.NORMAL);
        this.update(GuiMode.curr());
    }

    public void update(final GuiMode.GuiModeType type) {
        this.mode = type;
        if (!this.mode.isSimple) {
            this.part1X = 0;
            this.part1Y = 0;
            this.part1U = 0;
            this.part1V = 0;
            this.part1Width = 36;
            this.part1H = 40;
            this.part2X = 38;
            this.part2Y = 2;
            this.part2U = 36;
            this.part2V = 0;
            this.part2W = 101;
            this.part2H = 16;
            this.part3X = 1;
            this.part3Y = 39;
            this.part3U = 36;
            this.part3V = 16;
            this.part3W = 34;
            this.part3H = 13;
            this.barIconX = 41;
            this.barIconY = 20;
            this.barIconW = 10;
            this.barIconH = 10;
            this.barIconU = 165;
            this.barIconV = 0;
            this.barX = this.barIconX + 12;
            this.barY = this.barIconY + 2;
            this.barW = 81;
            this.barH = 5;
            this.barU = 175;
            this.barV = 0;
            this.chargeW = 101;
            this.chargeH = 5;
            this.chargeU = 0;
            this.chargeV = 68;
        } else {
            this.part2X = this.part1X;
            this.part2Y = this.part1Y;
            this.part3X = this.part2W + 1;
            this.part3Y = this.part2Y + 1;
            this.barIconX = this.part1X;
            this.barX = this.barIconX + 12;
            this.barY = this.barIconY + 2;
        }
    }

    public void renderGameOverlay(final ScaledResolution res) {
        this.mc.mcProfiler.startSection("rpgBar");
        GL11.glPushMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        final int width = res.getScaledWidth();
        final int height = res.getScaledHeight();
        this.renderEntityBar(
            (EntityLivingBase) this.mc.thePlayer,
            RPGConfig.ClientConfig.d.guiPlayerHUDOffsetX,
            RPGConfig.ClientConfig.d.guiPlayerHUDOffsetY,
            RPGConfig.ClientConfig.d.guiPlayerHUDIsInvert,
            res);
        this.renderChargeBar(
            RPGConfig.ClientConfig.d.guiChargeIsCentered ? ((width - this.chargeW) / 2)
                : RPGConfig.ClientConfig.d.guiChargeOffsetX,
            height - RPGConfig.ClientConfig.d.guiChargeOffsetY);
        this.renderEnemyBar(
            RPGConfig.ClientConfig.d.guiEnemyHUDOffsetX,
            RPGConfig.ClientConfig.d.guiEnemyHUDOffsetY,
            RPGConfig.ClientConfig.d.guiEnemyHUDIsInvert,
            res);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glPopMatrix();
        this.mc.mcProfiler.endSection();
    }

    private void renderEntityBar(final EntityLivingBase entity, int offsetX, final int offsetY,
        final boolean isInverted, final ScaledResolution res) {
        this.isInvert = isInverted;
        if (isInverted) {
            offsetX = res.getScaledWidth() - offsetX;
        }
        if (!this.mode.isSimple) {
            GuiInventory
                .func_147046_a(offsetX + this.invert(18), offsetY + 37, 16, (float) this.invert(30), 0.0f, entity);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager()
                .bindTexture(RPGGuiIngame.TEXTURE);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.part1X),
                offsetY + this.part1Y,
                this.part1U,
                this.part1V,
                this.part1Width,
                this.part1H,
                isInverted);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.part2X),
                offsetY + this.part2Y,
                this.part2U,
                this.part2V,
                this.part2W,
                this.part2H,
                isInverted);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.part3X),
                offsetY + this.part3Y,
                this.part3U,
                this.part3V,
                this.part3W,
                this.part3H,
                isInverted);
        } else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager()
                .bindTexture(RPGGuiIngame.TEXTURE);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.part2X),
                offsetY + this.part2Y,
                this.part2U,
                this.part2V,
                this.part2W,
                this.part2H,
                isInverted);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.part3X),
                offsetY + this.part3Y,
                this.part3U,
                this.part3V,
                this.part3W,
                this.part3H,
                isInverted);
        }
        int yFal = 0;
        IRPGEntity iRPG = null;
        if (RPGEntityHelper.isRPGable(entity)) {
            final RPGEntityProperties data = RPGEntityProperties.get(entity);
            if (data != null && data.checkValid()) {
                iRPG = RPGCapability.rpgEntityRegistr.get(entity).rpgComponent;
            }
        }
        if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode) {
            final boolean isPlayer = entity instanceof EntityPlayer;
            final boolean hasHealth = true;
            final boolean hasMana = isPlayer;
            final boolean hasArmor = isPlayer;
            final boolean hasMeleeDamage = iRPG != null && (iRPG.getEAMeleeDamage(entity) != null || isPlayer);
            final boolean hasRangeDamage = iRPG != null && iRPG.getEARangeDamage(entity) != null;
            final boolean hasFood = !RPGConfig.ClientConfig.d.guiEnableDefaultFoodBar && entity == this.mc.thePlayer
                && this.mc.thePlayer.getFoodStats()
                    .getFoodLevel() < 20;
            final boolean hasAir = entity == this.mc.thePlayer && this.mc.thePlayer.getAir() < 300;
            int offsetHealth = 0;
            int offsetMana = 0;
            int offsetMeleeDmg = 0;
            int offsetRangeDmg = 0;
            if (hasMana && hasHealth && RPGConfig.ClientConfig.d.guiTwiceHealthManaBar) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barIconX),
                    offsetY + this.barIconY + yFal,
                    this.barIconU,
                    this.barIconV + this.barIconH * 2,
                    this.barIconW,
                    this.barIconH,
                    isInverted);
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY + yFal - 2,
                    this.barU,
                    this.barV,
                    this.barW,
                    this.barH,
                    isInverted);
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY + yFal + 2,
                    this.barU,
                    this.barV,
                    this.barW,
                    this.barH,
                    isInverted);
                this.renderHealthBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                this.renderManaBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                offsetMana = (offsetHealth = offsetY + this.barY + yFal);
                yFal += this.barIconH;
            } else {
                if (hasHealth) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barIconX),
                        offsetY + this.barIconY + yFal,
                        this.barIconU,
                        this.barIconV + this.barIconH * 0,
                        this.barIconW,
                        this.barIconH,
                        isInverted);
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal,
                        this.barU,
                        this.barV,
                        this.barW,
                        this.barH,
                        isInverted);
                    this.renderHealthBar(entity, offsetX, offsetY + yFal, isInverted);
                    offsetHealth = offsetY + this.barY + yFal;
                    yFal += this.barIconH;
                }
                if (hasMana) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barIconX),
                        offsetY + this.barIconY + yFal,
                        this.barIconU,
                        this.barIconV + this.barIconH * 1,
                        this.barIconW,
                        this.barIconH,
                        isInverted);
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal,
                        this.barU,
                        this.barV,
                        this.barW,
                        this.barH,
                        isInverted);
                    this.renderManaBar(entity, offsetX, offsetY + yFal, isInverted);
                    offsetMana = offsetY + this.barY + yFal;
                    yFal += this.barIconH;
                }
            }
            if (hasArmor) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barIconX),
                    offsetY + this.barIconY + yFal,
                    this.barIconU,
                    this.barIconV + this.barIconH * 3,
                    this.barIconW,
                    this.barIconH,
                    isInverted);
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY + yFal - 2,
                    this.barU,
                    this.barV,
                    this.barW,
                    this.barH,
                    isInverted);
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY + yFal + 2,
                    this.barU,
                    this.barV,
                    this.barW,
                    this.barH,
                    isInverted);
                float curr = HookArmorSystem.getTotalPhisicArmor();
                int proc = this.getProcent(curr, 100.0f, this.barW);
                if (proc > 0) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal - 2,
                        this.barU,
                        this.barV + this.barH * 6,
                        proc,
                        this.barH,
                        isInverted);
                } else if (proc < 0) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal - 2,
                        this.barU,
                        this.barV + this.barH * 4,
                        -proc,
                        this.barH,
                        isInverted);
                }
                curr = HookArmorSystem.getTotalMagicArmor();
                proc = this.getProcent(curr, 100.0f, this.barW);
                if (proc > 0) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal + 2,
                        this.barU,
                        this.barV + this.barH * 7,
                        proc,
                        this.barH,
                        isInverted);
                } else if (proc < 0) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX),
                        offsetY + this.barY + yFal - 2,
                        this.barU,
                        this.barV + this.barH * 4,
                        -proc,
                        this.barH,
                        isInverted);
                }
                yFal += this.barIconH;
            }
            if (hasFood && hasAir) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barIconX),
                    offsetY + this.barIconY + yFal,
                    this.barIconU,
                    this.barIconV + this.barIconH * 6,
                    this.barIconW,
                    this.barIconH,
                    isInverted);
                if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barIconX),
                        offsetY + this.barIconY + yFal,
                        this.barIconU,
                        this.barIconV + this.barIconH * 5,
                        this.barIconW,
                        this.barIconH,
                        isInverted);
                } else {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barIconX),
                        offsetY + this.barIconY + yFal,
                        this.barIconU,
                        this.barIconV + this.barIconH * 4,
                        this.barIconW,
                        this.barIconH,
                        isInverted);
                }
                this.renderFoodBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                this.renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                yFal += this.barIconH;
            } else {
                if (hasFood) {
                    if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
                        this.drawTexturedModalRect(
                            offsetX + this.invert(this.barIconX),
                            offsetY + this.barIconY + yFal,
                            this.barIconU,
                            this.barIconV + this.barIconH * 5,
                            this.barIconW,
                            this.barIconH,
                            isInverted);
                    } else {
                        this.drawTexturedModalRect(
                            offsetX + this.invert(this.barIconX),
                            offsetY + this.barIconY + yFal,
                            this.barIconU,
                            this.barIconV + this.barIconH * 4,
                            this.barIconW,
                            this.barIconH,
                            isInverted);
                    }
                    this.renderFoodBar(entity, offsetX, offsetY + yFal, isInverted);
                    yFal += this.barIconH;
                }
                if (hasAir) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barIconX),
                        offsetY + this.barIconY + yFal,
                        this.barIconU,
                        this.barIconV + this.barIconH * 6,
                        this.barIconW,
                        this.barIconH,
                        isInverted);
                    this.renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                    yFal += this.barIconH;
                }
            }
            if (hasMeleeDamage && this.mode.isDigital) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barIconX),
                    offsetY + this.barIconY + yFal,
                    this.barIconU,
                    this.barIconV + this.barIconH * 7,
                    this.barIconW,
                    this.barIconH,
                    isInverted);
                offsetMeleeDmg = offsetY + this.barIconY + yFal;
                yFal += this.barIconH;
            }
            if (hasRangeDamage && this.mode.isDigital) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barIconX),
                    offsetY + this.barIconY + yFal,
                    this.barIconU,
                    this.barIconV + this.barIconH * 8,
                    this.barIconW,
                    this.barIconH,
                    isInverted);
                offsetRangeDmg = offsetY + this.barIconY + yFal;
                yFal += this.barIconH;
            }
            if (this.mode.isDigital) {
                if (hasMana && hasHealth && RPGConfig.ClientConfig.d.guiTwiceHealthManaBar) {
                    final String s = Utils.toString(
                        this.genValueStr(entity.getHealth() + entity.getAbsorptionAmount()),
                        "/",
                        this.genValueStr((float) PlayerAttributes.CURR_MANA.getValue(entity)));
                    this.fr.drawStringWithShadow(
                        s,
                        offsetX + this.getOffsetX(s, this.barX + this.barW + 4, isInverted),
                        offsetHealth + (this.barIconH - this.fr.FONT_HEIGHT) / 2 - 1,
                        16777215);
                } else {
                    if (hasHealth) {
                        final String s = this.genValueStr(entity.getHealth() + entity.getAbsorptionAmount());
                        this.fr.drawStringWithShadow(
                            s,
                            offsetX + this.getOffsetX(s, this.barX + this.barW + 4, isInverted),
                            offsetHealth + (this.barIconH - this.fr.FONT_HEIGHT) / 2 - 1,
                            16777215);
                    }
                    if (hasMana) {
                        final String s = this.genValueStr((float) PlayerAttributes.CURR_MANA.getValue(entity));
                        this.fr.drawStringWithShadow(
                            s,
                            offsetX + this.getOffsetX(s, this.barX + this.barW + 4, isInverted),
                            offsetMana + (this.barIconH - this.fr.FONT_HEIGHT) / 2 - 1,
                            16777215);
                    }
                }
            }
            if (hasMeleeDamage && this.mode.isDigital) {
                ItemStack item = entity.getHeldItem();

                float itemDamage = 0;

                if (item != null) {
                    Multimap itemAttributes = item.getAttributeModifiers();

                    for (Object attribute : itemAttributes.entries()) {
                        if (attribute instanceof Map.Entry) {
                            Map.Entry entry = (Map.Entry) attribute;

                            if (entry.getKey().equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                                itemDamage += ((AttributeModifier) entry.getValue()).getAmount();
                            }
                        }
                    }
                }

                float meleeDamage = iRPG.getEAMeleeDamage(entity).getValue(entity);

                meleeDamage += itemDamage;

                final String s = genValueStr(meleeDamage);
                this.fr.drawStringWithShadow(
                    s,
                    offsetX + this.getOffsetX(s, this.barX, isInverted),
                    offsetMeleeDmg + (this.barIconH - this.fr.FONT_HEIGHT) / 2 + 1,
                    16777215
                );
            }
            if (hasRangeDamage && this.mode.isDigital) {
                final String s = this.genValueStr(
                    (float) iRPG.getEARangeDamage(entity)
                        .getValue(entity));
                this.fr.drawStringWithShadow(
                    s,
                    offsetX + this.getOffsetX(s, this.barX, isInverted),
                    offsetRangeDmg + (this.barIconH - this.fr.FONT_HEIGHT) / 2 + 1,
                    16777215);
            }
        }
        String s = entity.getCommandSenderName();
        this.fr.drawStringWithShadow(
            s,
            offsetX + this.getOffsetX(s, this.part2X + 1, this.part2W - 6, isInverted),
            offsetY + this.part2Y + (this.part2H - this.fr.FONT_HEIGHT) / 2 + 2,
            16777215);
        if (iRPG != null) {
            s = String.valueOf((int) EntityAttributes.LVL.getValue(entity));
            this.fr.drawStringWithShadow(
                s,
                offsetX + this.getOffsetX(s, this.part3X, this.part3W, isInverted),
                offsetY + this.part3Y + (this.part3H - this.fr.FONT_HEIGHT) / 2 + 1,
                16777215);
        }
    }

    private void renderHealthBar(final EntityLivingBase entity, final int offsetX, final int offsetY,
        final boolean isInverted) {
        final float curr = entity.getHealth();
        final float absorbHealth = entity.getAbsorptionAmount();
        final float max = (float) EntityAttributes.HEALTH.getSafe(entity, (entity.getMaxHealth() + absorbHealth));
        if (max > 0.0f) {
            final int proc = this.getProcent(curr, max, this.barW);
            final int procAbsorb = this.getProcent(absorbHealth, max, this.barW);
            if (entity.isPotionActive(Potion.wither)) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY,
                    this.barU,
                    this.barV + this.barH * 4,
                    this.barW,
                    this.barH,
                    isInverted);
            } else {
                if (proc > 0) {
                    if (entity.isPotionActive(Potion.poison)) {
                        this.drawTexturedModalRect(
                            offsetX + this.invert(this.barX),
                            offsetY + this.barY,
                            this.barU,
                            this.barV + this.barH * 3,
                            proc,
                            this.barH,
                            isInverted);
                    } else {
                        this.drawTexturedModalRect(
                            offsetX + this.invert(this.barX),
                            offsetY + this.barY,
                            this.barU,
                            this.barV + this.barH * 1,
                            proc,
                            this.barH,
                            isInverted);
                    }
                }
                if (procAbsorb > 0) {
                    this.drawTexturedModalRect(
                        offsetX + this.invert(this.barX) + proc,
                        offsetY + this.barY,
                        this.barU + proc,
                        this.barV + this.barH * 2,
                        procAbsorb,
                        this.barH,
                        isInverted);
                }
            }
        }
    }

    private void renderManaBar(final EntityLivingBase entity, final int offsetX, final int offsetY,
        final boolean isInverted) {
        final float curr = (float) PlayerAttributes.CURR_MANA.getValue(entity);
        final float max = (float) PlayerAttributes.MANA.getValue(entity);
        if (max > 0.0f) {
            final int proc = this.getProcent(curr, max, this.barW);
            if (proc > 0) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY,
                    this.barU,
                    this.barV + this.barH * 5,
                    proc,
                    this.barH,
                    isInverted);
            }
        }
    }

    private void renderFoodBar(final EntityLivingBase entity, final int offsetX, final int offsetY,
        final boolean isInverted) {
        final float curr = (float) this.mc.thePlayer.getFoodStats()
            .getFoodLevel();
        final int proc = this.getProcent(curr, 20.0f, this.barW);
        if (curr < 20.0f) {
            this.drawTexturedModalRect(
                offsetX + this.invert(this.barX),
                offsetY + this.barY,
                this.barU,
                this.barV,
                this.barW,
                this.barH,
                isInverted);
            if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY,
                    this.barU,
                    this.barV + this.barH * 9,
                    proc,
                    this.barH,
                    isInverted);
            } else {
                this.drawTexturedModalRect(
                    offsetX + this.invert(this.barX),
                    offsetY + this.barY,
                    this.barU,
                    this.barV + this.barH * 8,
                    proc,
                    this.barH,
                    isInverted);
            }
        }
    }

    private void renderAirBar(final EntityLivingBase entity, final int offsetX, final int offsetY,
        final boolean isInverted) {
        final float curr = (float) this.mc.thePlayer.getAir();
        final int proc = this.getProcent(curr, 300.0f, this.barW);
        if (curr < 300.0f) {
            this.drawTexturedModalRect(
                offsetX + this.invert(this.barX),
                offsetY + this.barY,
                this.barU,
                this.barV,
                this.barW,
                this.barH,
                isInverted);
            this.drawTexturedModalRect(
                offsetX + this.invert(this.barX),
                offsetY + this.barY,
                this.barU,
                this.barV + this.barH * 10,
                proc,
                this.barH,
                isInverted);
        }
    }

    public void renderChargeBar(final int offsetX, final int offsetY) {
        final ItemStack stack;
        if ((stack = this.mc.thePlayer.getItemInUse()) != null && stack.getItemUseAction() == EnumAction.bow
            && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager()
                .bindTexture(RPGGuiIngame.TEXTURE);
            this.drawTexturedModalRect(offsetX, offsetY, this.chargeU, this.chargeV, this.chargeW, this.chargeH);
            final int useDuration = this.mc.thePlayer.getItemInUseDuration();
            final float maxCharge = ItemAttributes.SHOT_SPEED.get(stack, (EntityPlayer) this.mc.thePlayer);
            float power = useDuration / maxCharge;
            power = (power * power + power * 2.0f) / 3.0f;
            int proc = this.getProcent(power, 1.0f, this.chargeW);
            if (proc > 0) {
                this.drawTexturedModalRect(
                    offsetX,
                    offsetY,
                    this.chargeU,
                    this.chargeV + this.chargeH,
                    proc,
                    this.chargeH);
            }
            if (ItemAttributes.MIN_CUST_TIME.hasIt(stack)) {
                final float tmp = ItemAttributes.MIN_CUST_TIME.get(stack, (EntityPlayer) this.mc.thePlayer);
                proc = this.getProcent(maxCharge * tmp, maxCharge, this.chargeW);
                this.drawTexturedModalRect(
                    offsetX + proc,
                    offsetY,
                    this.chargeU,
                    this.chargeV + this.chargeH * 2,
                    1,
                    this.chargeH);
            }
        }
    }

    private void renderEnemyBar(final int offsetX, final int offsetY, final boolean isInverted,
        final ScaledResolution res) {
        final MovingObjectPosition mop = RPGHelper.getMouseOver(0.0f, 10.0f);
        if (mop != null && mop.entityHit != null) {
            if (mop.entityHit instanceof EntityDragonPart) {
                if (((EntityDragonPart) mop.entityHit).entityDragonObj instanceof EntityDragon) {
                    this.renderEntityBar(
                        (EntityLivingBase) ((EntityDragonPart) mop.entityHit).entityDragonObj,
                        offsetX,
                        offsetY,
                        true,
                        res);
                }
            } else if (mop.entityHit instanceof EntityLivingBase) {
                this.renderEntityBar((EntityLivingBase) mop.entityHit, offsetX, offsetY, true, res);
            }
        }
    }

    private int getProcent(float curr, final float max, final int width) {
        curr = ((curr > max) ? max : ((curr < -max) ? (-max) : curr));
        return (int) Utils.alignment(curr / max * width, (float) (-width), (float) width);
    }

    private String genValueStr(final double value) {
        if (value < 100.0) {
            return String.format("%.1f", value);
        }

        char unit;
        double scaledValue;

        if (value < 1000.0) {
            unit = ' ';
            scaledValue = Math.round(value);
        } else if (value < 1000000.0) {
            unit = 'K';
            scaledValue = Math.round(value) / 1000.0;
        } else {
            unit = 'M';
            scaledValue = Math.round(value) / 1000000.0;
        }

        return String.format("%.1f%s", scaledValue, unit);
    }
    
    private double invert(final double value) {
        return Utils.invert(value, this.isInvert);
    }

    private int invert(final int value) {
        return (int) Utils.invert(value, this.isInvert);
    }

    private int getOffsetX(String s, final int offset, final int width, final boolean isInverted) {
        s = this.fr.trimStringToWidth(s, width);
        final int size = this.fr.getStringWidth(s);
        final int value = offset + (width - size) / 2;
        if (isInverted) {
            return -(value + size);
        }
        return value;
    }

    private int getOffsetX(final String s, final int offset, final boolean isInverted) {
        if (isInverted) {
            final int size = this.fr.getStringWidth(s);
            return -(offset + size);
        }
        return offset;
    }

    private void renderTestString(final int x, final int y, final Object... str) {
        int i = 0;
        for (final Object tmp : str) {
            this.fr.drawStringWithShadow(tmp.toString(), x, y + this.fr.FONT_HEIGHT * i++, 16777215);
        }
    }

    public void drawTexturedModalRect(final int x, final int y, final int u, final int v, final int width,
        final int heght, final boolean isInverted) {
        final float f = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        if (!isInverted) {
            tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + heght),
                (double) this.zLevel,
                (double) ((u + 0) * f),
                (double) ((v + heght) * f));
            tessellator.addVertexWithUV(
                (double) (x + width),
                (double) (y + heght),
                (double) this.zLevel,
                (double) ((u + width) * f),
                (double) ((v + heght) * f));
            tessellator.addVertexWithUV(
                (double) (x + width),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((u + width) * f),
                (double) ((v + 0) * f));
            tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((u + 0) * f),
                (double) ((v + 0) * f));
        } else {
            tessellator.addVertexWithUV(
                (double) (x - width),
                (double) (y + heght),
                (double) this.zLevel,
                (double) ((u + width) * f),
                (double) ((v + heght) * f));
            tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + heght),
                (double) this.zLevel,
                (double) ((u + 0) * f),
                (double) ((v + heght) * f));
            tessellator.addVertexWithUV(
                (double) (x + 0),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((u + 0) * f),
                (double) ((v + 0) * f));
            tessellator.addVertexWithUV(
                (double) (x - width),
                (double) (y + 0),
                (double) this.zLevel,
                (double) ((u + width) * f),
                (double) ((v + 0) * f));
        }
        tessellator.draw();
    }

    static {
        INSTANCE = new RPGGuiIngame();
        TEXTURE = new ResourceLocation("dangerrpg", "textures/gui/gui_in_game.png");
    }
}
