package poatrn.sciencereconbination.starwarcore.mixins;

import net.lointain.cosmos.procedures.ApplyGravityLogicProcedure;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileWriter;
import java.io.IOException;

@Mixin(ApplyGravityLogicProcedure.class)
public class GravityLogicMixin {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void logicMixin(LevelAccessor world, String dimension, CallbackInfoReturnable<Boolean> cir) throws IOException {
        if (dimension.equals("starwarcore:subspace")) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
