package tehnut.xtones.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLampFlat extends BlockEnum<EnumFacing> {

    public static final ImmutableMap<EnumFacing, AxisAlignedBB> SHAPES =
        Maps.immutableEnumMap(ImmutableMap.<EnumFacing, AxisAlignedBB>builder()
            .put(EnumFacing.UP, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D))
            .put(EnumFacing.DOWN, new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D))
            .put(EnumFacing.NORTH, new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D))
            .put(EnumFacing.EAST, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875, 1.0D, 1.0D))
            .put(EnumFacing.SOUTH, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D))
            .put(EnumFacing.WEST, new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D))
            .build());

    public static final IProperty<Boolean> ACTIVE = PropertyBool.create("active");

    public BlockLampFlat() {
        super(Material.GLASS, EnumFacing.class, "facing");

        setDefaultState(getDefaultState().withProperty(ACTIVE, false));
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(ACTIVE) ? 15 : 0;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess access, IBlockState state, BlockPos pos, EnumFacing facing) {
        return state.getValue(getProperty()) == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SHAPES.get(state.getValue(getProperty()));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (state.getValue(ACTIVE) && !world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, false), 2);
        else if (!state.getValue(ACTIVE) && world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, true), 2);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(ACTIVE) && !world.isBlockPowered(pos))
            world.scheduleUpdate(pos, this, 4);
        else if (!state.getValue(ACTIVE) && world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, true));

        EnumFacing facing = state.getValue(getProperty());
        BlockPos reversePos = pos.offset(facing.getOpposite());
        if (!world.isSideSolid(reversePos, facing, false)) {
            world.setBlockToAir(pos);
            dropBlockAsItem(world, pos, state, 0);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.values()[meta & 0b111];
        boolean lit = meta >> 0b11 == 1;
        return getDefaultState().withProperty(getProperty(), facing).withProperty(ACTIVE, lit);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(getProperty()).ordinal();
        int active = (state.getValue(ACTIVE) ? 1 : 0) << 0b11;
        return facing | active;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (state.getValue(ACTIVE) && !world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, false));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(getProperty(), facing);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        subBlocks.add(new ItemStack(this));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightOpacity(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer.Builder(this).add(getProperty(), ACTIVE).build();
    }
}
