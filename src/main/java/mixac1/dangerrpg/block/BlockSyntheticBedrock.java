package mixac1.dangerrpg.block;

import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSyntheticBedrock extends Block {

    public BlockSyntheticBedrock() {
        super(Material.rock);
        setHardness(200.0F);
        setResistance(6000000.0F);
        setStepSound(soundTypePiston);
        setCreativeTab(RPGCreativeTabs.tabRPGBlocks);

        setBlockName("synthetic_bedrock");
        setBlockTextureName("bedrock");
    }
}
