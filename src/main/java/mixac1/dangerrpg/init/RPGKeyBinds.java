package mixac1.dangerrpg.init;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.settings.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.client.registry.*;
import mixac1.dangerrpg.*;

@SideOnly(Side.CLIENT)
public abstract class RPGKeyBinds
{
    public static KeyBinding specialItemKey;
    public static KeyBinding extraItemKey;
    public static KeyBinding infoBookKey;
    public static KeyBinding guiModeKey;
    
    public static void load(final FMLPreInitializationEvent e) {
        ClientRegistry.registerKeyBinding(RPGKeyBinds.specialItemKey);
        ClientRegistry.registerKeyBinding(RPGKeyBinds.extraItemKey);
        ClientRegistry.registerKeyBinding(RPGKeyBinds.infoBookKey);
        ClientRegistry.registerKeyBinding(RPGKeyBinds.guiModeKey);
    }
    
    static {
        RPGKeyBinds.specialItemKey = new KeyBinding(DangerRPG.trans("rpgkey.special_use"), 33, "DangerRPG");
        RPGKeyBinds.extraItemKey = new KeyBinding(DangerRPG.trans("rpgkey.extra_use"), 46, "DangerRPG");
        RPGKeyBinds.infoBookKey = new KeyBinding(DangerRPG.trans("rpgkey.info_book"), 23, "DangerRPG");
        RPGKeyBinds.guiModeKey = new KeyBinding(DangerRPG.trans("rpgkey.gui_mode"), 24, "DangerRPG");
    }
}
