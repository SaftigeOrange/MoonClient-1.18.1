package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final BiomeGenerationSettings EMPTY = new BiomeGenerationSettings(ImmutableMap.of(), ImmutableList.of());
    public static final MapCodec<BiomeGenerationSettings> CODEC = RecordCodecBuilder.mapCodec((p_186655_) ->
    {
        return p_186655_.group(Codec.simpleMap(GenerationStep.Carving.CODEC, ConfiguredWorldCarver.LIST_CODEC.promotePartial(Util.prefix("Carver: ", LOGGER::error)).flatXmap(ExtraCodecs.nonNullSupplierListCheck(), ExtraCodecs.nonNullSupplierListCheck()), StringRepresentable.a(GenerationStep.Carving.values())).fieldOf("carvers").forGetter((p_186661_) -> {
            return p_186661_.carvers;
        }), PlacedFeature.LIST_CODEC.promotePartial(Util.prefix("Feature: ", LOGGER::error)).flatXmap(ExtraCodecs.nonNullSupplierListCheck(), ExtraCodecs.nonNullSupplierListCheck()).listOf().fieldOf("features").forGetter((p_186653_) -> {
            return p_186653_.features;
        })).apply(p_186655_, BiomeGenerationSettings::new);
    });
    private final Map < GenerationStep.Carving, List < Supplier < ConfiguredWorldCarver<? >>> > carvers;
    private final List<List<Supplier<PlacedFeature>>> features;
    private final List < ConfiguredFeature <? , ? >> flowerFeatures;
    private final Set<PlacedFeature> featureSet;

    BiomeGenerationSettings(Map < GenerationStep.Carving, List < Supplier < ConfiguredWorldCarver<? >>> > p_186650_, List<List<Supplier<PlacedFeature>>> p_186651_)
    {
        this.carvers = p_186650_;
        this.features = p_186651_;
        this.flowerFeatures = p_186651_.stream().flatMap(Collection::stream).map(Supplier::get).flatMap(PlacedFeature::getFeatures).filter((p_186657_) ->
        {
            return p_186657_.feature == Feature.FLOWER;
        }).collect(ImmutableList.toImmutableList());
        this.featureSet = p_186651_.stream().flatMap(Collection::stream).map(Supplier::get).collect(Collectors.toSet());
    }

    public List < Supplier < ConfiguredWorldCarver<? >>> getCarvers(GenerationStep.Carving pStep)
    {
        return this.carvers.getOrDefault(pStep, ImmutableList.of());
    }

    public List < ConfiguredFeature <? , ? >> getFlowerFeatures()
    {
        return this.flowerFeatures;
    }

    public List<List<Supplier<PlacedFeature>>> features()
    {
        return this.features;
    }

    public boolean hasFeature(PlacedFeature p_186659_)
    {
        return this.featureSet.contains(p_186659_);
    }

    public static class Builder
    {
        private final Map < GenerationStep.Carving, List < Supplier < ConfiguredWorldCarver<? >>> > carvers = Maps.newLinkedHashMap();
        private final List<List<Supplier<PlacedFeature>>> features = Lists.newArrayList();

        public BiomeGenerationSettings.Builder addFeature(GenerationStep.Decoration pStep, PlacedFeature pFeatures)
        {
            return this.addFeature(pStep.ordinal(), () ->
            {
                return pFeatures;
            });
        }

        public BiomeGenerationSettings.Builder addFeature(int pStep, Supplier<PlacedFeature> pFeatures)
        {
            this.addFeatureStepsUpTo(pStep);
            this.features.get(pStep).add(pFeatures);
            return this;
        }

        public <C extends CarverConfiguration> BiomeGenerationSettings.Builder addCarver(GenerationStep.Carving pStep, ConfiguredWorldCarver<C> pCarver)
        {
            this.carvers.computeIfAbsent(pStep, (p_186663_) ->
            {
                return Lists.newArrayList();
            }).add(() ->
            {
                return pCarver;
            });
            return this;
        }

        private void addFeatureStepsUpTo(int pStep)
        {
            while (this.features.size() <= pStep)
            {
                this.features.add(Lists.newArrayList());
            }
        }

        public BiomeGenerationSettings build()
        {
            return new BiomeGenerationSettings(this.carvers.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_186672_) ->
            {
                return ImmutableList.copyOf(p_186672_.getValue());
            })), this.features.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()));
        }
    }
}
