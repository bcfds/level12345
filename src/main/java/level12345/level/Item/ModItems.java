package level12345.level.Item;

import level12345.level.BackroomsLevel;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final List<RegistryObject<Item>> CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final List<RegistryObject<Item>> ITEMS_CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final List<RegistryObject<Item>> BLOCKITEMS_CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BackroomsLevel.MOD_ID);

    /***
     * AtuoRegister will atuomaticlly add the object into crative mod etab(创造物品栏)
     * @apiNote use this to register new item
     * @param name item name like "example"
     * @param sup supplier
     * @return RegistryObject<Item>
     */
    public static RegistryObject<Item> AtuoRegister(final String name, final Supplier<Item> sup){
        RegistryObject<Item> supplierItem =ITEMS.register(name, sup);
        CREATIVETAB_SUPPLIER.add(supplierItem);
        return supplierItem;
    }

    public static final RegistryObject<Item> FIRESALT = AtuoRegister("fire_salt", () -> new FiresaltItem(new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_IN_A_BOTTLE = AtuoRegister("lightning_in_a_bottle", () -> new LightningInABottleItem(new Item.Properties()));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
