package info.tehnut.xtones.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public final class FlatLampBlock extends BlockDirectional {
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> SHAPES =
        Maps.immutableEnumMap(ImmutableMap.<EnumFacing, AxisAlignedBB>builder()
            .put(EnumFacing.UP, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D))
            .put(EnumFacing.DOWN, new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D))
            .put(EnumFacing.NORTH, new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D))
            .put(EnumFacing.EAST, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875, 1.0D, 1.0D))
            .put(EnumFacing.SOUTH, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D))
            .put(EnumFacing.WEST, new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D))
            .build());

    private static final IProperty<Boolean> ACTIVE = PropertyBool.create("active");

    public FlatLampBlock() {
        super(Material.GLASS);
        this.setLightOpacity(0);
        this.setDefaultState(this.getDefaultState()
            .withProperty(ACTIVE, false)
            .withProperty(FACING, EnumFacing.NORTH)
        );
    }

    @Override
    @Deprecated
    public int getLightValue(final IBlockState state) {
        return state.getValue(ACTIVE) ? 15 : 0;
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(final IBlockAccess access, final IBlockState state, final BlockPos pos, final EnumFacing facing) {
        return state.getValue(FACING) == facing.getOpposite() ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void onBlockAdded(final World world, final BlockPos pos, final IBlockState state) {
        if (!world.isRemote) {
            final boolean powered = world.isBlockPowered(pos);
            if (state.getValue(ACTIVE) != powered) {
                world.setBlockState(pos, state.withProperty(ACTIVE, powered), 2);
            }
        }
    }

    @Override
    @Deprecated
    public void neighborChanged(final IBlockState state, final World world, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        final boolean active = state.getValue(ACTIVE);
        final boolean powered = world.isBlockPowered(pos);
        if (active && !powered) {
            world.scheduleUpdate(pos, this, 4);
        } else if (!active && powered) {
            world.setBlockState(pos, state.withProperty(ACTIVE, true), 2);
        }
        final EnumFacing facing = state.getValue(FACING);
        if (!world.isSideSolid(pos.offset(facing.getOpposite()), facing, false)) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean doesSideBlockRendering(final IBlockState state, final IBlockAccess access, final BlockPos pos, final EnumFacing side) {
        final EnumFacing facing = state.getValue(FACING);
        if (side != facing.getOpposite()) {
            final IBlockState other = access.getBlockState(pos.offset(side));
            return this == other.getBlock() && facing.getAxis() == other.getValue(FACING).getAxis();
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos pos, final EnumFacing side) {
        final BlockPos offset = pos.offset(side.getOpposite());
        return BlockFaceShape.SOLID == world.getBlockState(offset).getBlockFaceShape(world, offset, side);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(final int meta) {
        final boolean active = meta >> 0b11 == 1;
        final EnumFacing facing = EnumFacing.values()[meta & 0b111];
        return this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, active);
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        final int active = (state.getValue(ACTIVE) ? 1 : 0) << 0b11;
        final int facing = state.getValue(FACING).ordinal();
        return facing | active;
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!world.isRemote && state.getValue(ACTIVE) && !world.isBlockPowered(pos)) {
            world.setBlockState(pos, state.withProperty(ACTIVE, false), 2);
        }
    }

    @Override
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(final IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(final IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ACTIVE, FACING);
    }
}
