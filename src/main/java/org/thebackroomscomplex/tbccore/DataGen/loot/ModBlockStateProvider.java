package org.thebackroomscomplex.tbccore.DataGen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.thebackroomscomplex.tbccore.Block.ModBlocks;
import org.thebackroomscomplex.tbccore.TBCcore;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TBCcore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 方块逻辑

        simpleBlock(ModBlocks.LEVEL1_PORTAL.get());

        ModelFile magicModel = models().cubeAll("magic_block", modLoc("Block/magic_block"));
        simpleBlock(ModBlocks.LEVEL1_PORTAL.get(), magicModel);

        simpleBlockWithItem(ModBlocks.LEVEL1_PORTAL.get(), models().cubeAll("your_block_with_item", modLoc("Block/your_block_with_item")));
    }

    private void crossBlock(RegistryObject<Block> block) {
        String name = block.getId().getPath();
        ModelFile model = models().cross(name, modLoc("Block/" + name));
        simpleBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
}