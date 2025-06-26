package poatrn.sciencereconbination.starwarcore.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import poatrn.sciencereconbination.starwarcore.StarWarCore;
import poatrn.sciencereconbination.starwarcore.dimensions.SubspaceDimension;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, SubspaceDimension::bootstrapType)
            .add(Registries.LEVEL_STEM, SubspaceDimension::bootstrapStem);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(StarWarCore.MODID));
    }
}
