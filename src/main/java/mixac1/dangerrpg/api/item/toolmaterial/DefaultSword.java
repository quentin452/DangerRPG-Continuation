package mixac1.dangerrpg.api.item.toolmaterial;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class DefaultSword extends ItemSword {
    private final Item.ToolMaterial customToolMaterial;

    public DefaultSword(Item.ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.customToolMaterial = toolMaterial;
    }

    public Item.ToolMaterial getCustomToolMaterial() {
        return customToolMaterial;
    }
}
