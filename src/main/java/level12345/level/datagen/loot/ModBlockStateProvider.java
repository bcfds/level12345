package level12345.level.datagen.loot;

import level12345.level.Level;
import level12345.level.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        // PackOutput 用于输出文件，ExistingFileHelper 用于检查文件是否存在
        super(output, Level.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 方块逻辑

        simpleBlock(ModBlocks.MOD_PORTAL.get());

        ModelFile magicModel = models().cubeAll("magic_block", modLoc("block/magic_block"));
        simpleBlock(ModBlocks.MOD_PORTAL.get(), magicModel);

        simpleBlockWithItem(ModBlocks.MOD_PORTAL.get(), models().cubeAll("your_block_with_item", modLoc("block/your_block_with_item")));
    }

    private void crossBlock(RegistryObject<Block> block) {
        String name = block.getId().getPath();
        ModelFile model = models().cross(name, modLoc("block/" + name));
        simpleBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
}