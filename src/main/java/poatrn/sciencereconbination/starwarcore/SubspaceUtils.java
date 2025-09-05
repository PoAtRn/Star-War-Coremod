package poatrn.sciencereconbination.starwarcore;

import com.mojang.logging.LogUtils;
import net.jcm.vsch.util.VSCHUtils;
import net.lointain.cosmos.network.CosmosModVariables;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubspaceUtils {
    static Logger logger = LogUtils.getLogger();

    public static ServerLevel calculateTpLevel(ServerLevel subspace, Vec3 pos) {
        SystemPos[] centers = SystemPos.values();
        List<Double> posRelative = new ArrayList<>();
        for (int i = 0; i < centers.length; i++) {
            posRelative.add(i, pos.distanceTo(centers[i].getPos()));
        }
        //TODO:correct comparison
        int index = posRelative.indexOf(Collections.min(posRelative));
        return subspace.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(centers[index].getResourceKey())));
    }

    public static Vec3 calculateTpPos(boolean enterSubspace, Vec3 initial, ServerLevel fromLevel, ServerLevel targetLevel) {
        //TODO:different systems debug
        SystemPos[] centers = SystemPos.values();
        if (enterSubspace) {
            initial = initial.scale(1.0 / 16.0);
            for (SystemPos center : centers) {
                if (fromLevel.dimension().location().toString().equals(center.getResourceKey())) {
                    return initial.add(center.getPos());
                }
            }
            return initial;
        } else {
            for (SystemPos center : centers) {
                if (targetLevel.dimension().location().toString().equals(center.getResourceKey())) {
                    initial = initial.subtract(center.getPos());
                    break;
                }
            }
            return initial.scale(16.0);
        }
    }

    public static boolean isSpace(Level world, ResourceKey<Level> dimension) {
        return ((CompoundTag)(CosmosModVariables.WorldVariables.get(world).dimensional_data.get(dimension.location().toString()))).get("dimension_type").toString().equals("\"space\"");
    }
}
