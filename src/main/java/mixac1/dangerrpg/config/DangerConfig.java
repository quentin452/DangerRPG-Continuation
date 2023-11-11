package mixac1.dangerrpg.config;

import com.falsepattern.lib.config.Config;

import mixac1.dangerrpg.DangerRPG;

@Config(modid = DangerRPG.MODID)
public class DangerConfig {
    @Config.Comment("Fix issues between Danger Rpg Gui and Shaders")
    @Config.DefaultBoolean(true)
    @Config.RequiresWorldRestart
    public static boolean enablefixGuiissueswithOptifine;
    @Config.Comment("Fix damage from Shulker Bullet to be scalable")
    @Config.DefaultBoolean(true)
    @Config.RequiresWorldRestart
    public static boolean enablefixShulkerBulletDamage;
}
