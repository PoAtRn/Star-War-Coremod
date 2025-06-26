package poatrn.sciencereconbination.starwarcore.dimensions;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import poatrn.sciencereconbination.starwarcore.StarWarCore;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class SubspaceDimension {
    public static final ResourceKey<LevelStem> LEVEL_STEM_RESOURCE_KEY = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(StarWarCore.MODID, "subspace"));
    public static final ResourceKey<Level> LEVEL_RESOURCE_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(StarWarCore.MODID, "subspace"));
    public static final ResourceKey<DimensionType> DIMENSION_TYPE_RESOURCE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(StarWarCore.MODID, "subspace_type"));

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(DIMENSION_TYPE_RESOURCE_KEY, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                64.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.END_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {

        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.THE_VOID)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.END)
        );

        FlatLevelGeneratorSettings generatorSettings = new FlatLevelGeneratorSettings(Optional.empty(), biomeRegistry.getOrThrow(Biomes.THE_VOID), List.of());

        VoidGenerator voidGenerator = new VoidGenerator(generatorSettings);

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(SubspaceDimension.DIMENSION_TYPE_RESOURCE_KEY), voidGenerator);

        context.register(LEVEL_STEM_RESOURCE_KEY, stem);
    }
}
