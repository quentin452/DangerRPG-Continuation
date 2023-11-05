package mixac1.dangerrpg.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryRPGCrafting extends InventoryCrafting {

    private int invWidth;
    private int invHeight;
    private InventoryCrafting crafting;

    public InventoryRPGCrafting(Container eventHandler, int invWidth, int invHeight) {
        super(eventHandler, invWidth, invHeight);
        this.invWidth = invWidth;
        this.invHeight = invHeight;
        this.crafting = new InventoryCrafting(new ContainerStub(), 3, 3);
    }

    public boolean isValidCrafting(int row, int column) {
        for (int i = 0; i < invHeight; ++i) {
            for (int j = 0; j < invWidth; ++j) {
                if (i < row || i > row + 2 || j < column || j > column + 2) {
                    if (getStackInRowAndColumn(i, j) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public InventoryCrafting getCrafting(int row, int column) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                crafting.setInventorySlotContents(j + i * 3, getStackInRowAndColumn(row + j, column + i));
            }
        }
        return crafting;
    }

    private static class ContainerStub extends Container {

        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return false;
        }
    }
}
