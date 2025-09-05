package poatrn.sciencereconbination.starwarcore.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.jcm.vsch.util.TeleportationHandler;
import net.jcm.vsch.util.VSCHUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3d;
import org.joml.primitives.AABBic;
import org.slf4j.Logger;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;
import poatrn.sciencereconbination.starwarcore.SubspaceUtils;
import poatrn.sciencereconbination.starwarcore.dimensions.SubspaceDimension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class SubspaceCommand {
    public static final Logger LOGGER = LogUtils.getLogger();

    public SubspaceCommand() {
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent registerCommandsEvent) {
        registerCommandsEvent.getDispatcher().register(
                Commands.literal("subspace").requires((p_137736_) -> p_137736_.hasPermission(2)).then(Commands.argument("player", EntityArgument.player()).executes(ctx -> {
                    int returnVal = 1;
                    try {
                        returnVal = execute(ctx, ctx.getSource().getPlayer(), ctx.getSource().getLevel());
                    } catch (Exception e) {
                        LOGGER.error(Arrays.toString(e.getStackTrace()));
                    }
                    ctx.getSource().sendSuccess(() -> Component.literal("success" + ctx.getSource().getPlayer().toString()), true);
                    return returnVal;
                }))
        );
    }

    public static int execute(CommandContext<CommandSourceStack> ctx, ServerPlayer serverPlayer, ServerLevel spaceLevel) {
        if (!SubspaceUtils.isSpace(spaceLevel.getLevel(), spaceLevel.dimension())) {
            ctx.getSource().sendFailure(Component.literal("Must in space dimensions!"));
            return 1;
        }

        final TeleportationHandler teleportHandler;
        Vec3 initial = serverPlayer.getPosition(0.5f);
        ServerLevel destination;
        boolean enterSubspace = true;

        if (spaceLevel.dimension().equals(SubspaceDimension.LEVEL_RESOURCE_KEY)) {
            //out the subspace into diff systems
            destination = SubspaceUtils.calculateTpLevel(spaceLevel, serverPlayer.getPosition(0.5f));
            teleportHandler = new TeleportationHandler(destination, spaceLevel, false);
            enterSubspace = false;
        } else {
            destination = spaceLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation("starwarcore:subspace")));
            teleportHandler = new TeleportationHandler(destination, spaceLevel, false);
        }

        Vec3 after = SubspaceUtils.calculateTpPos(enterSubspace, initial, spaceLevel, destination);
        Vec3 delta = after.subtract(initial);

        Map<Ship, Vec3> teleport = new HashMap<>();
        for (final Ship ship : VSGameUtilsKt.getShipObjectWorld(destination).getLoadedShips()) {
            final AABBic shipBox = ship.getShipAABB();
            if (shipBox != null) {
                final AABB inflatedAABB = VectorConversionsMCKt.toMinecraft(VSCHUtils.transformToAABBd(ship.getPrevTickTransform(), shipBox)).inflate(10.0);
                if (serverPlayer.getBoundingBox().intersects(inflatedAABB)) {
                    teleport.put(ship, delta);
                }
            }
        }

        LOGGER.info("target ships");
        teleport.keySet().forEach(ship -> LOGGER.info(String.valueOf(ship)));

        for (Ship ship : teleport.keySet()) {
            teleportHandler.handleTeleport(ship, ship.getTransform().getPositionInWorld().add(delta.toVector3f(), new Vector3d()));
        }

        return 0;
    }

}
