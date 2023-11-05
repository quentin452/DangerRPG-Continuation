package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.client.gui.GuiMode.GuiModeType;

/**
 * It is fires whenever changed GuiMode
 */
public class GuiModeChangeEvent extends Event {

    public GuiModeType type;

    public GuiModeChangeEvent(GuiModeType type) {
        this.type = type;
    }
}
