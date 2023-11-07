package mixac1.dangerrpg.config;

import net.minecraft.client.gui.GuiScreen;

import com.falsepattern.lib.config.SimpleGuiFactory;

public class DangerGuiConfigFactory implements SimpleGuiFactory {

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return DangerGuiConfig.class;
    }
}
