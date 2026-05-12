package level12345.level.item;

import level12345.level.Level;
import level12345.level.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Level.MOD_ID);
    public static final RegistryObject<Item> MOD_PORTAL = ITEMS.register("my_block",
            () -> new BlockItem(ModBlocks.MOD_PORTAL.get(), new Item.Properties()));
//    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
//
//    }
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
