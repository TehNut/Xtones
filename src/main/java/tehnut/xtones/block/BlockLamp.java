package tehnut.xtones.block;

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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import tehnut.xtones.Xtones;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Random;

public class BlockLamp extends BlockEnum<EnumFacing> {

    public static final IProperty<Boolean> ACTIVE = PropertyBool.create("active");

    private final LampShape shape;

    public BlockLamp(LampShape shape) {
        super(Material.GLASS, EnumFacing.class, "facing");

        this.shape = shape;

        setUnlocalizedName(Xtones.ID + ".lamp." + shape.getName());
        setCreativeTab(Xtones.TAB);
        setDefaultState(getDefaultState().withProperty(ACTIVE, false));
        setHardness(0.5F);
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
        return shape.getBound(state.getValue(getProperty()));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote)
            return;

        if (state.getValue(ACTIVE) && !world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, false), 2);
        else if (!state.getValue(ACTIVE) && world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, true), 2);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (world.isRemote)
            return;

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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote)
            return;

        if (state.getValue(ACTIVE) && !world.isBlockPowered(pos))
            world.setBlockState(pos, state.withProperty(ACTIVE, false));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(getProperty(), facing);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldAccess, BlockPos pos) {
        if (worldAccess instanceof ChunkCache) {
            World world = ReflectionHelper.getPrivateValue(ChunkCache.class, (ChunkCache) worldAccess, "field_72815_e", "world");
            if (world.isBlockPowered(pos))
                return state.withProperty(ACTIVE, true);
        }

        return super.getActualState(state, worldAccess, pos);
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

    public enum LampShape implements IStringSerializable {
        FLAT(Shapes.FLAT_UP, Shapes.FLAT_DOWN, Shapes.FLAT_NORTH, Shapes.FLAT_EAST, Shapes.FLAT_SOUTH, Shapes.FLAT_WEST),
        ;

        private final EnumMap<EnumFacing, AxisAlignedBB> boundings;

        LampShape(@Nonnull AxisAlignedBB boundUp, @Nonnull AxisAlignedBB boundDown, @Nonnull AxisAlignedBB boundNorth, @Nonnull AxisAlignedBB boundEast, @Nonnull AxisAlignedBB boundSouth, @Nonnull AxisAlignedBB boundWest) {
            boundings = Maps.newEnumMap(EnumFacing.class);
            boundings.put(EnumFacing.UP, boundUp);
            boundings.put(EnumFacing.DOWN, boundDown);
            boundings.put(EnumFacing.NORTH, boundNorth);
            boundings.put(EnumFacing.EAST, boundEast);
            boundings.put(EnumFacing.SOUTH, boundSouth);
            boundings.put(EnumFacing.WEST, boundWest);
        }

        @Nonnull
        public AxisAlignedBB getBound(EnumFacing facing) {
            return boundings.get(facing);
        }

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public static class Shapes {
        public static final AxisAlignedBB FLAT_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
        public static final AxisAlignedBB FLAT_DOWN = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
        public static final AxisAlignedBB FLAT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
        public static final AxisAlignedBB FLAT_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875, 1.0D, 1.0D);
        public static final AxisAlignedBB FLAT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
        public static final AxisAlignedBB FLAT_WEST = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    }
}
