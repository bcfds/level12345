package level12345.level.Item;

import level12345.level.BackroomsLevel;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
    public static final RegistryObject<Item> LIGHTNING_IN_A_BOTTLE = AtuoRegister("lightning_in_a_bottle", () -> new LightningInABottleItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> WarpBerriesItem = AtuoRegister("warp_berries",() -> new WarpBerriesItem(new Item.Properties()));
    public static final RegistryObject<Item> crowbar = AtuoRegister("crowbar",() -> new Modtoolcrowbar(Tiers.WOOD,5,-2.8F,new Item.Properties().durability(256)));
    public static final RegistryObject<Item> steel_sword = AtuoRegister("steel_sword",() -> new SwordItem(Tiers.NETHERITE,12,-2.4F,new Item.Properties().durability(352757)));
    public static final RegistryObject<Item> BULLET = AtuoRegister("bullet", () -> new BulletItem(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> Compressfiresalt = AtuoRegister("compressfiresalt", () -> new Compress_firesalt(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> BOWB = AtuoRegister("bowb",()->new BowBItem(new Item.Properties().durability(128)));
    public static final RegistryObject<Item> RIFLE = AtuoRegister("rifle",()->new Rifle(new Item.Properties().durability(12800)));
    public static final RegistryObject<Item> BSTEEL = AtuoRegister("bsteel",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> HELMET = AtuoRegister("bsteel_helmet",()->new ArmorItem(ModArmors.BSTEEL,ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> CHESTPLATE = AtuoRegister("bsteel_chestplate",()->new ArmorItem(ModArmors.BSTEEL,ArmorItem.Type.CHESTPLATE,new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
