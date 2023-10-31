package mixac1.dangerrpg.block;

import net.minecraft.block.*;
import net.minecraft.block.material.*;

import mixac1.dangerrpg.init.*;

public class BlockSyntheticBedrock extends Block {

    public BlockSyntheticBedrock() {
        super(Material.rock);
        this.setHardness(200.0f);
        this.setResistance(6000000.0f);
        this.setStepSound(BlockSyntheticBedrock.soundTypePiston);
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGBlocks);
        this.setBlockName("synthetic_bedrock");
        this.setBlockTextureName("bedrock");
    }
}
