package mixac1.dangerrpg.init;

import net.minecraft.block.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import mixac1.dangerrpg.block.*;

public abstract class RPGBlocks {

    public static Block modificationTable;
    public static Block lvlupTable;
    public static Block rpgWorkbench;
    public static Block syntheticBedrock;

    public static void load(final FMLPreInitializationEvent e) {
        registerBlock(RPGBlocks.modificationTable = (Block) new BlockModificationTable());
        registerBlock(RPGBlocks.lvlupTable = (Block) new BlockLvlupTable());
        registerBlock(RPGBlocks.rpgWorkbench = (Block) new BlockRPGWorkbench());
        registerBlock(RPGBlocks.syntheticBedrock = (Block) new BlockSyntheticBedrock());
    }

    private static void registerBlock(final Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }
}
