package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiInfoBook;
import mixac1.dangerrpg.client.gui.GuiLvlupTable;
import mixac1.dangerrpg.client.gui.GuiModificationTable;
import mixac1.dangerrpg.client.gui.GuiRPGWorkbench;
import mixac1.dangerrpg.inventory.ContainerLvlupTable;
import mixac1.dangerrpg.inventory.ContainerModificationTable;
import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class RPGGuiHandlers implements IGuiHandler
{
    public static final int GUI_MODIFICATION_TABLE  = 0;
    public static final int GUI_LVLUP_TABLE         = 1;
    public static final int GUI_INFO_BOOK           = 2;
    public static final int GUI_RPG_WORKBENCH       = 3;

    public static void load(FMLInitializationEvent e)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(DangerRPG.instance, new RPGGuiHandlers() {});
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID) {
        case GUI_MODIFICATION_TABLE:
            return new ContainerModificationTable(player.inventory, world, x, y, z);
        case GUI_LVLUP_TABLE:
            return new ContainerLvlupTable(player.inventory, world, x, y, z);
        case GUI_RPG_WORKBENCH:
            return new ContainerRPGWorkbench(player.inventory, world, x, y, z);
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID) {
        case GUI_MODIFICATION_TABLE:
            return new GuiModificationTable(player.inventory, world, x, y, z);
        case GUI_LVLUP_TABLE:
            return new GuiLvlupTable(player.inventory, world, x, y, z);
        case GUI_INFO_BOOK:
            return new GuiInfoBook(player);
        case GUI_RPG_WORKBENCH:
            return new GuiRPGWorkbench(player.inventory, world, x, y, z);
        default:
            return null;
        }
    }
}
