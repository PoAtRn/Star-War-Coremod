package poatrn.sciencereconbination.starwarcore.tile_entities;

import com.dfdyz.void_power.compat.vs.ship.EngineController;
import com.dfdyz.void_power.world.blocks.void_engine.VoidEngineTE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class WarpEngineTE extends VoidEngineTE {
    @JsonIgnore
    private LoadedServerShip ship;

    public WarpEngineTE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
    }

    @Override
    public void tick() {
        super.tick();

        if(level.isClientSide) return;
        if(this.ship == null){
            ship = VSGameUtilsKt.getShipObjectManagingPos((ServerLevel) level, getBlockPos());
            if(ship != null){
                EngineController ec = EngineController.getOrCreate(ship);
                ec.addEngine(this);
            }
        }

        calculateStressApplied();
        updateSpeed = true;
    }

    public double sizeCanDrive(){
        //System.out.println(STRESS * Config.MassPerStress * Mth.abs(getSpeed()));
        return Mth.abs(getSpeed());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if(level.isClientSide) return;
        if(ship != null){
            EngineController ec = EngineController.getOrCreate(ship);
            ec.removeEngine(this);
        }
        ship = null;
    }

    @Override
    public void remove() {
        super.remove();

        if(level.isClientSide) return;
        if(ship != null){
            EngineController ec = EngineController.getOrCreate(ship);
            ec.removeEngine(this);
        }
    }
}
