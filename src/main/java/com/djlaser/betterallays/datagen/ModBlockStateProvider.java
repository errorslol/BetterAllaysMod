package com.djlaser.betterallays.datagen;

import com.djlaser.betterallays.BetterAllays;
import com.djlaser.betterallays.block.ModBlocks;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.ArrayList;

public class ModBlockStateProvider extends BlockStateProvider {

    private static class BlockItemModelProvider extends ItemModelProvider {
        private final ArrayList<ResourceLocation> blocks = new ArrayList<>();

        public BlockItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, BetterAllays.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            for (ResourceLocation block : blocks) {
                getBuilder(block.toString())
                        .parent(getExistingFile(
                                new ResourceLocation(block.getNamespace(),"block/" + block.getPath())
                        ));
            }
        }
    }

    private final BlockItemModelProvider itemModelProvider;

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BetterAllays.MODID, exFileHelper);
        this.itemModelProvider = new BlockItemModelProvider(gen, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.JADE_BLOCK.get());
        simpleBlock(ModBlocks.BUDDING_JADE.get());

    }

    @Override
    public VariantBlockStateBuilder getVariantBuilder(Block b) {
        itemModelProvider.blocks.add(ForgeRegistries.BLOCKS.getKey(b));
        return super.getVariantBuilder(b);
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        super.run(cache);
        itemModelProvider.run(cache);
    }
}
