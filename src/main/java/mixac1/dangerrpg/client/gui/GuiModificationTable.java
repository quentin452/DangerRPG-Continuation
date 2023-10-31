package mixac1.dangerrpg.client.gui;

import net.minecraft.client.gui.inventory.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import mixac1.dangerrpg.api.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import mixac1.dangerrpg.inventory.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.capability.*;

@SideOnly(Side.CLIENT)
public class GuiModificationTable extends GuiContainer
{
    public static final ResourceLocation TEXTURE;
    public static int slotU;
    public static int slotV;
    public static int iconU;
    public static int iconV;
    public static int invStrX;
    public static int invStrY;
    private IInventory playerInv;
    private static HashMap<GemType, Integer> iconMap;
    
    public GuiModificationTable(final InventoryPlayer inventory, final World world, final int x, final int y, final int z) {
        super((Container)new ContainerModificationTable((IInventory)inventory, world, x, y, z));
        this.playerInv = (IInventory)inventory;
        this.xSize = 176;
        this.ySize = 227;
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        this.mc.getTextureManager().bindTexture(GuiModificationTable.TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        final ContainerModificationTable cmt = (ContainerModificationTable)this.inventorySlots;
        for (int i = cmt.staticSize + 1; i < cmt.inventorySlots.size(); ++i) {
            final Slot slot = cmt.inventorySlots.get(i);
            this.drawTexturedModalRect(this.guiLeft + slot.xDisplayPosition - 1, this.guiTop + slot.yDisplayPosition - 1, GuiModificationTable.slotU, GuiModificationTable.slotV, 18, 18);
            if (!slot.getHasStack()) {
                final int iconIndex = this.getGemTypeIconIndex(((InventoryModificationTable)slot.inventory).getGemTypeSlot(slot.getSlotIndex()));
                this.drawTexturedModalRect(this.guiLeft + slot.xDisplayPosition, this.guiTop + slot.yDisplayPosition, GuiModificationTable.iconU, GuiModificationTable.iconV + 16 * iconIndex, 16, 16);
            }
        }
    }
    
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        final String s1 = StatCollector.translateToLocal(RPGBlocks.modificationTable.getLocalizedName());
        final String s2 = StatCollector.translateToLocal("key.inventory");
        this.fontRendererObj.drawString(s1, (this.xSize - this.fontRendererObj.getStringWidth(s1)) / 2, 5, 4210752);
        this.fontRendererObj.drawString(s2, GuiModificationTable.invStrX, GuiModificationTable.invStrY, 4210752);
    }
    
    private int getGemTypeIconIndex(final GemType gemType) {
        if (gemType != null && GuiModificationTable.iconMap.containsKey(gemType)) {
            return GuiModificationTable.iconMap.get(gemType);
        }
        return 0;
    }
    
    static {
        TEXTURE = new ResourceLocation("dangerrpg", "textures/gui/container/gui_modification_table.png");
        GuiModificationTable.slotU = 176;
        GuiModificationTable.slotV = 0;
        GuiModificationTable.iconU = 194;
        GuiModificationTable.iconV = 0;
        GuiModificationTable.invStrX = 8;
        GuiModificationTable.invStrY = 133;
        GuiModificationTable.iconMap = new HashMap<GemType, Integer>() {
            {
                this.put((GemType)GemTypes.PA, 1);
                this.put((GemType)GemTypes.AM, 2);
            }
        };
    }
}
