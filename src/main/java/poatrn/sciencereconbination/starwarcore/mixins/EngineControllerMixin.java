package poatrn.sciencereconbination.starwarcore.mixins;

import com.dfdyz.void_power.compat.vs.ship.EngineController;
import com.dfdyz.void_power.world.blocks.void_engine.VoidEngineTE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poatrn.sciencereconbination.starwarcore.tile_entities.WarpEngineTE;

import java.util.Set;

@Mixin(EngineController.class)
public class EngineControllerMixin {
    @Unique
    @JsonIgnore
    double starwarcore$sizeCanDrive;

    @Unique
    @JsonIgnore
    Set<WarpEngineTE> starwarcore$warp = Sets.newConcurrentHashSet();

    @Inject(method = "addEngine", at = @At("HEAD"), cancellable = true, remap = false)
    public void addEngineMixin(VoidEngineTE te, CallbackInfo ci) {
        if (te instanceof WarpEngineTE warpEngineTE) {
            starwarcore$warp.add(warpEngineTE);
            ci.cancel();
        }
    }

    @Inject(method = "removeEngine", at = @At("HEAD"), cancellable = true, remap = false)
    public void removeEngineMixin(VoidEngineTE te, CallbackInfo ci) {
        if (te instanceof WarpEngineTE warpEngineTE) {
            starwarcore$warp.remove(warpEngineTE);
            ci.cancel();
        }
    }

    //TODO:substitute logic in subspace
}
