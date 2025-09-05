package poatrn.sciencereconbination.starwarcore;

import net.jcm.vsch.event.AtmosphericCollision;
import net.minecraft.world.phys.Vec3;

public enum SystemPos {
    SOLAR_SYSTEM(new Vec3(0, 0, 0), "cosmos:solar_system"), ALPHA_CENTAURI(new Vec3(0, -10000, 2500), "cosmos:alpha_system"), V1400_CENTAURI(new Vec3(0, 10000, 10000), "cosmos:b_1400_centauri");

    private final Vec3 pos;
    private final String resourceKey;

    SystemPos(Vec3 pos, String resourceKey) {
        this.pos = pos;
        this.resourceKey = resourceKey;
    }

    public Vec3 getPos() {
        return pos;
    }

    public String getResourceKey() {
        return resourceKey;
    }
}
