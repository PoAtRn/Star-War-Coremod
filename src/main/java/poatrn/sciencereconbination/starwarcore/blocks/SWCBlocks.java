package poatrn.sciencereconbination.starwarcore.blocks;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import poatrn.sciencereconbination.starwarcore.CreativeTabs;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static poatrn.sciencereconbination.starwarcore.StarWarCore.REGISTRATE;

public class SWCBlocks {

    static {
        REGISTRATE.setCreativeTab(CreativeTabs.TAB);
    }

    public static final BlockEntry<WarpEngineBlock> WARP_ENGINE = REGISTRATE
            .block(WarpEngineBlock.ID, WarpEngineBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(TagGen.axeOrPickaxe())
            .transform(BlockStressDefaults.setImpact(32.0))
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .item()
            .transform(customItemModel())
            .register();

    public static void register() {
    }
}
