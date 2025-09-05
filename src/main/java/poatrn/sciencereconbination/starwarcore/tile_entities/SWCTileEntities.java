package poatrn.sciencereconbination.starwarcore.tile_entities;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import poatrn.sciencereconbination.starwarcore.blocks.SWCBlocks;
import poatrn.sciencereconbination.starwarcore.blocks.WarpEngineBlock;
import poatrn.sciencereconbination.starwarcore.renderers.WarpEngineTERenderer;

import static poatrn.sciencereconbination.starwarcore.StarWarCore.REGISTRATE;

public class SWCTileEntities {
    public static final BlockEntityEntry<WarpEngineTE> WARP_ENGINE_TE = REGISTRATE.blockEntity(WarpEngineBlock.ID, WarpEngineTE::new)
            .instance(() ->WarpEngineTEInstance::new, false)
            .validBlock(SWCBlocks.WARP_ENGINE)
            .renderer(() -> WarpEngineTERenderer::new)
            .register();

    public static void register() {
    }
}
