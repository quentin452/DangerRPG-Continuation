package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.event.GuiModeChangeEvent;
import mixac1.dangerrpg.util.Utils;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class GuiMode
{
    private static int curr = 0;

    public static void change()
    {
        if (++curr >= GuiModeType.values().length) {
            curr = 0;
        }
        MinecraftForge.EVENT_BUS.post(new GuiModeChangeEvent(curr()));
    }

    public static GuiModeType curr()
    {
        return GuiModeType.values()[curr];
    }

    public static void set(int number)
    {
        curr = (int) Utils.alignment(number, 0, GuiModeType.values().length - 1);
    }

    public static boolean isIt(GuiModeType type)
    {
        return type.equals(curr());
    }

    public static enum GuiModeType
    {
        NORMAL          (false, false),
        NORMAL_DIGITAL  (true,  false),
        SIMPLE          (false, true),
        SIMPLE_DIGITAL  (true,  true),

        ;

        public boolean isDigital;
        public boolean isSimple;

        GuiModeType(boolean isDigital, boolean isSimple)
        {
            this.isDigital = isDigital;
            this.isSimple = isSimple;
        }
    }
}
