package tehnut.xtones.block.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockLamp extends ItemBlock {

    public ItemBlockLamp(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        if (!state.getBlock().isReplaceable(world, pos))
            pos = pos.offset(facing);

        if (!state.isSideSolid(world, pos, facing))
            return EnumActionResult.FAIL;

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
}
