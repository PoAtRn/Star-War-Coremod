package poatrn.sciencereconbination.starwarcore.mixins;

import net.lointain.cosmos.network.CosmosModVariables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(LivingEntity.class)
//public class LivingEntityMixin {
//    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
//    private void NoVoidDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Level level = ((LivingEntity) (Object) this).level();
//        if (source.is(DamageTypes.FELL_OUT_OF_WORLD) && ((CompoundTag)(CosmosModVariables.WorldVariables.get(level).dimensional_data.get(level.dimension().location().toString()))).get("dimension_type").toString().equals("\"space\"")) cir.setReturnValue(false);
//    }
//}

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void NoVoidDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Level level = ((LivingEntity) (Object) this).level();
        CompoundTag levelType = ((CompoundTag)(CosmosModVariables.WorldVariables.get(level).dimensional_data.get(level.dimension().location().toString())));
        if (levelType == null) {
            LOGGER.info("WWWWWWWW");
            cir.setReturnValue(false);
        }
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD) && levelType.get("dimension_type").toString().equals("\"space\"")) cir.setReturnValue(false);
    }
}
