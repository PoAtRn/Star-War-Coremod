package poatrn.sciencereconbination.starwarcore.mixins;

import net.lointain.cosmos.procedures.GravityDataProviderProcedure;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileWriter;
import java.io.IOException;

@Mixin(GravityDataProviderProcedure.class)
public class GravityMixin {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void gravityMixin(LevelAccessor world, String dimension, CallbackInfoReturnable<Double> cir) {
        if (dimension.equals("starwarcore:subspace")) {
            cir.setReturnValue(0.0);
            cir.cancel();
        }
    }
}
