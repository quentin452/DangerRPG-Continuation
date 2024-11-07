package mixac1.dangerrpg.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.util.Utils;

public class BlockRPGWorkbench extends Block {

    public static final String NAME = "rpg_workbench";

    public IIcon[] icons = new IIcon[2];

    public BlockRPGWorkbench() {
        super(Material.iron);
        setBlockName(NAME);
        setBlockTextureName(Utils.toString(DangerRPG.MODID, ":", NAME));
        setHardness(2.0f);
        setResistance(6.0f);
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(RPGCreativeTabs.tabRPGBlocks);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(getTextureName() + "_side");
        icons[0] = reg.registerIcon(getTextureName() + "_top");
        icons[1] = reg.registerIcon(getTextureName() + "_front");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? icons[0]
            : side == 0 ? Blocks.iron_block.getBlockTextureFromSide(side)
                : side != 2 && side != 4 ? blockIcon : icons[1];
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2,
        float par3, float par4) {
        if (!world.isRemote) {
            player.openGui(DangerRPG.instance, RPGGuiHandlers.GUI_RPG_WORKBENCH, world, x, y, z);
        }
        return true;
    }
}
