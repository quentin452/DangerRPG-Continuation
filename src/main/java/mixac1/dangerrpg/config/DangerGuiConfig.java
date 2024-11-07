package mixac1.dangerrpg.config;

import net.minecraft.client.gui.GuiScreen;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;

import mixac1.dangerrpg.DangerRPG;

public class DangerGuiConfig extends SimpleGuiConfig {

    public DangerGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, DangerConfig.class, DangerRPG.MODID, DangerRPG.MODNAME);
    }
}
