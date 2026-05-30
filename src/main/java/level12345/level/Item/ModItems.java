package level12345.level.Item;

import level12345.level.Level;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Level.MOD_ID);
    public static final RegistryObject<Item> Firesalt = ITEMS.register("FiresaltItem", () -> new FiresaltItem(new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_IN_A_BOTTLE = ITEMS.register("LIGHTNING_IN_A_BOTTLE", () -> new LightningInABottleItem(new Item.Properties()));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
