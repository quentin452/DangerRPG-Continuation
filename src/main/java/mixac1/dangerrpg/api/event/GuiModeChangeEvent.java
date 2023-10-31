package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.client.gui.*;

public class GuiModeChangeEvent extends Event
{
    public GuiMode.GuiModeType type;
    
    public GuiModeChangeEvent(final GuiMode.GuiModeType type) {
        this.type = type;
    }
}
