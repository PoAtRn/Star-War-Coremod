package poatrn.sciencereconbination.starwarcore.blocks;

import com.dfdyz.void_power.registry.VPShapes;
import com.dfdyz.void_power.world.blocks.void_engine.VoidEngineBlock;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import poatrn.sciencereconbination.starwarcore.tile_entities.SWCTileEntities;
import poatrn.sciencereconbination.starwarcore.tile_entities.WarpEngineTE;

public class WarpEngineBlock extends HorizontalKineticBlock implements IBE<WarpEngineTE> {

    public static final String ID = "warp_engine";

    public WarpEngineBlock(Properties properties) {
        super(properties);
    }

    public static VoxelShape Shape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0001, 0.0001, 0.0001, 0.9999, 0.9999, 0.9999), BooleanOp.OR);
        return shape;
    }

    @Override
    public boolean skipRendering(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
        return super.skipRendering(p_60532_, p_60533_, p_60534_);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return (new AllShapes.Builder(WarpEngineBlock.Shape())).forHorizontal(Direction.NORTH).get(state.getValue(HORIZONTAL_FACING));
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public Class<WarpEngineTE> getBlockEntityClass() {
        return WarpEngineTE.class;
    }

    @Override
    public BlockEntityType<? extends WarpEngineTE> getBlockEntityType() {
        return SWCTileEntities.WARP_ENGINE_TE.get();
    }
}
