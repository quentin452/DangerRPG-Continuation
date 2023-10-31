package mixac1.dangerrpg.block;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.*;
import net.minecraft.tileentity.*;
import mixac1.dangerrpg.tileentity.*;

public class BlockLvlupTable extends BlockContainer
{
    public static final String NAME = "lvlup_table";
    public IIcon[] icons;
    
    public BlockLvlupTable() {
        super(Material.rock);
        this.icons = new IIcon[2];
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setBlockName("lvlup_table");
        this.setBlockTextureName("dangerrpg:lvlup_table");
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGBlocks);
        this.setHardness(5.0f);
        this.setResistance(2000.0f);
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public void registerBlockIcons(final IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName() + "_side");
        this.icons[0] = reg.registerIcon(this.getTextureName() + "_top");
        this.icons[1] = reg.registerIcon(this.getTextureName() + "_bottom");
    }
    
    public IIcon getIcon(final int side, final int meta) {
        return (side == 0) ? this.icons[1] : ((side == 1) ? this.icons[0] : this.blockIcon);
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par1, final float par2, final float par3, final float par4) {
        if (!world.isRemote) {
            player.openGui((Object)DangerRPG.instance, 1, world, x, y, z);
        }
        return true;
    }
    
    public TileEntity createNewTileEntity(final World world, final int par) {
        return new TileEntityEmpty();
    }
}
