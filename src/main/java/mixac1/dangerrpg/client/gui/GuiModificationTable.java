package mixac1.dangerrpg.client.gui;

import java.util.HashMap;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.GemTypes;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.inventory.ContainerModificationTable;
import mixac1.dangerrpg.inventory.InventoryModificationTable;

@SideOnly(Side.CLIENT)
public class GuiModificationTable extends GuiContainer {

    public static final ResourceLocation TEXTURE = new ResourceLocation(
        DangerRPG.MODID,
        "textures/gui/container/gui_modification_table.png");

    public static int slotU = 176;
    public static int slotV = 0;

    public static int iconU = 194;
    public static int iconV = 0;

    public static int invStrX = 8;
    public static int invStrY = 133;

    private IInventory playerInv;

    private static HashMap<GemType, Integer> iconMap = new HashMap<GemType, Integer>() {

        {
            put(GemTypes.PA, 1);
            put(GemTypes.AM, 2);
        }
    };

    public GuiModificationTable(InventoryPlayer inventory, World world, int x, int y, int z) {
        super(new ContainerModificationTable(inventory, world, x, y, z));

        playerInv = inventory;

        xSize = 176;
        ySize = 227;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager()
            .bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        ContainerModificationTable cmt = (ContainerModificationTable) inventorySlots;
        for (int i = cmt.staticSize + 1; i < cmt.inventorySlots.size(); ++i) {
            Slot slot = (Slot) cmt.inventorySlots.get(i);
            drawTexturedModalRect(
                guiLeft + slot.xDisplayPosition - 1,
                guiTop + slot.yDisplayPosition - 1,
                slotU,
                slotV,
                18,
                18);
            if (!slot.getHasStack()) {
                int iconIndex = getGemTypeIconIndex(
                    ((InventoryModificationTable) slot.inventory).getGemTypeSlot(slot.getSlotIndex()));
                drawTexturedModalRect(
                    guiLeft + slot.xDisplayPosition,
                    guiTop + slot.yDisplayPosition,
                    iconU,
                    iconV + 16 * iconIndex,
                    16,
                    16);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s1 = StatCollector.translateToLocal(RPGBlocks.modificationTable.getLocalizedName());
        String s2 = StatCollector.translateToLocal("key.inventory");
        fontRendererObj.drawString(s1, (xSize - fontRendererObj.getStringWidth(s1)) / 2, 5, 0x404040);
        fontRendererObj.drawString(s2, invStrX, invStrY, 0x404040);
    }

    private int getGemTypeIconIndex(GemType gemType) {
        if (gemType != null && iconMap.containsKey(gemType)) {
            return iconMap.get(gemType);
        }
        return 0;
    }
}
