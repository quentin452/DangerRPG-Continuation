package mixac1.dangerrpg.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class InventoryRPGCrafting extends InventoryCrafting {

    private int invWidth;
    private int invHeight;
    private InventoryCrafting crafting;

    public InventoryRPGCrafting(final Container eventHandler, final int invWidth, final int invHeight) {
        super(eventHandler, invWidth, invHeight);
        this.invWidth = invWidth;
        this.invHeight = invHeight;
        this.crafting = new InventoryCrafting((Container) new ContainerStub(), 3, 3);
    }

    public boolean isValidCrafting(final int row, final int column) {
        for (int i = 0; i < this.invHeight; ++i) {
            for (int j = 0; j < this.invWidth; ++j) {
                if ((i < row || i > row + 2 || j < column || j > column + 2)
                    && this.getStackInRowAndColumn(i, j) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public InventoryCrafting getCrafting(final int row, final int column) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.crafting.setInventorySlotContents(j + i * 3, this.getStackInRowAndColumn(row + j, column + i));
            }
        }
        return this.crafting;
    }

    private static class ContainerStub extends Container {

        public boolean canInteractWith(final EntityPlayer player) {
            return false;
        }
    }
}
