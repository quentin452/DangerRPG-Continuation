package mixac1.dangerrpg.init;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.client.gui.*;
import mixac1.dangerrpg.inventory.*;

public abstract class RPGGuiHandlers implements IGuiHandler {

    public static final int GUI_MODIFICATION_TABLE = 0;
    public static final int GUI_LVLUP_TABLE = 1;
    public static final int GUI_INFO_BOOK = 2;
    public static final int GUI_RPG_WORKBENCH = 3;

    public static void load(final FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler((Object) DangerRPG.instance, (IGuiHandler) new RPGGuiHandlers() {});
    }

    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
        final int y, final int z) {
        switch (ID) {
            case 0: {
                return new ContainerModificationTable((IInventory) player.inventory, world, x, y, z);
            }
            case 1: {
                return new ContainerLvlupTable((IInventory) player.inventory, world, x, y, z);
            }
            case 3: {
                return new ContainerRPGWorkbench(player.inventory, world, x, y, z);
            }
            default: {
                return null;
            }
        }
    }

    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
        final int y, final int z) {
        switch (ID) {
            case 0: {
                return new GuiModificationTable(player.inventory, world, x, y, z);
            }
            case 1: {
                return new GuiLvlupTable(player.inventory, world, x, y, z);
            }
            case 2: {
                return new GuiInfoBook(player);
            }
            case 3: {
                return new GuiRPGWorkbench(player.inventory, world, x, y, z);
            }
            default: {
                return null;
            }
        }
    }
}
