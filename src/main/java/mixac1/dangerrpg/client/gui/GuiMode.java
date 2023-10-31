package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.*;
import net.minecraftforge.common.*;
import mixac1.dangerrpg.api.event.*;
import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.util.*;

@SideOnly(Side.CLIENT)
public class GuiMode
{
    private static int curr;
    
    public static void change() {
        if (++GuiMode.curr >= GuiModeType.values().length) {
            GuiMode.curr = 0;
        }
        MinecraftForge.EVENT_BUS.post((Event)new GuiModeChangeEvent(curr()));
    }
    
    public static GuiModeType curr() {
        return GuiModeType.values()[GuiMode.curr];
    }
    
    public static void set(final int number) {
        GuiMode.curr = (int)Utils.alignment((float)number, 0.0f, (float)(GuiModeType.values().length - 1));
    }
    
    public static boolean isIt(final GuiModeType type) {
        return type.equals(curr());
    }
    
    static {
        GuiMode.curr = 0;
    }
    
    public enum GuiModeType
    {
        NORMAL(false, false), 
        NORMAL_DIGITAL(true, false), 
        SIMPLE(false, true), 
        SIMPLE_DIGITAL(true, true);
        
        public boolean isDigital;
        public boolean isSimple;
        
        private GuiModeType(final boolean isDigital, final boolean isSimple) {
            this.isDigital = isDigital;
            this.isSimple = isSimple;
        }
    }
}
