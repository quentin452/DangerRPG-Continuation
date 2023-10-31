package mixac1.dangerrpg.block;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.*;

public class BlockRPGWorkbench extends Block
{
    public static final String NAME = "rpg_workbench";
    public IIcon[] icons;
    
    public BlockRPGWorkbench() {
        super(Material.iron);
        this.icons = new IIcon[2];
        this.setBlockName("rpg_workbench");
        this.setBlockTextureName(Utils.toString("dangerrpg", ":", "rpg_workbench"));
        this.setHardness(2.0f);
        this.setResistance(6.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGBlocks);
    }
    
    public void registerBlockIcons(final IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName() + "_side");
        this.icons[0] = reg.registerIcon(this.getTextureName() + "_top");
        this.icons[1] = reg.registerIcon(this.getTextureName() + "_front");
    }
    
    public IIcon getIcon(final int side, final int meta) {
        return (side == 1) ? this.icons[0] : ((side == 0) ? Blocks.iron_block.getBlockTextureFromSide(side) : ((side != 2 && side != 4) ? this.blockIcon : this.icons[1]));
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par1, final float par2, final float par3, final float par4) {
        if (!world.isRemote) {
            player.openGui((Object)DangerRPG.instance, 3, world, x, y, z);
        }
        return true;
    }
}
