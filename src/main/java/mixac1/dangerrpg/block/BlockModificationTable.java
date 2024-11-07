package mixac1.dangerrpg.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;

public class BlockModificationTable extends Block {

    public static final String NAME = "modification_table";

    public IIcon[] icons = new IIcon[2];

    public BlockModificationTable() {
        super(Material.rock);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setLightOpacity(0);
        setBlockName(NAME);
        setBlockTextureName(DangerRPG.MODID + ":" + NAME);
        setCreativeTab(RPGCreativeTabs.tabRPGBlocks);
        setHardness(5.0F);
        setResistance(2000.0F);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(getTextureName() + "_side");
        icons[0] = reg.registerIcon(getTextureName() + "_top");
        icons[1] = reg.registerIcon(getTextureName() + "_bottom");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 0 ? icons[1] : (side == 1 ? icons[0] : blockIcon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2,
        float par3, float par4) {
        if (!world.isRemote) {
            player.openGui(DangerRPG.instance, RPGGuiHandlers.GUI_MODIFICATION_TABLE, world, x, y, z);
        }
        return true;
    }
}
