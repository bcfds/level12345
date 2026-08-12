package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.thebackroomscomplex.tbccore.Item.Bottle.BottleItem;
import org.thebackroomscomplex.tbccore.TBCcore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final List<RegistryObject<Item>> CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final List<RegistryObject<Item>> ITEMS_CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final List<RegistryObject<Item>> BLOCKITEMS_CREATIVETAB_SUPPLIER = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TBCcore.MOD_ID);

    /***
     * AutoRegister will atuomaticlly add the object into crative mod etab(创造物品栏)
     * @apiNote use this to register new item
     * @param name item name like "example"
     * @param sup supplier
     * @return RegistryObject<Item>
     */
    public static RegistryObject<Item> AutoRegister(final String name, final Supplier<Item> sup){
        RegistryObject<Item> supplierItem =ITEMS.register(name, sup);
        CREATIVETAB_SUPPLIER.add(supplierItem);
        return supplierItem;
    }

    public static final RegistryObject<Item> FIRESALT = AutoRegister("fire_salt", () -> new FiresaltItem(new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_IN_A_BOTTLE = AutoRegister("lightning_in_a_bottle", () -> new LightningInABottleItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IRON_BOTTLE = AutoRegister("iron_bottle",()-> new BottleItem(new Item.Properties()));
    public static final RegistryObject<Item> WarpBerriesItem = AutoRegister("warp_berries",() -> new WarpBerriesItem(new Item.Properties()));
    public static final RegistryObject<Item> crowbar = AutoRegister("crowbar",() -> new Modtoolcrowbar(ModTools.BSTEEL,5,-2.8F,new Item.Properties().durability(256)));
    public static final RegistryObject<Item> steel_sword = AutoRegister("steel_sword",() -> new SwordItem(ModTools.BSTEEL,15,-2.8F,new Item.Properties().durability(2757)));
    public static final RegistryObject<Item> BULLET = AutoRegister("bullet", () -> new BulletItem(new Item.Properties().stacksTo(60)));
    public static final RegistryObject<Item> Compressfiresalt = AutoRegister("compressfiresalt", () -> new Compress_firesalt(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> BOWB = AutoRegister("bowb",()->new BowBItem(new Item.Properties().durability(128)));
    public static final RegistryObject<Item> RIFLE = AutoRegister("rifle",()->new Rifle(new Item.Properties().durability(3600)));
    public static final RegistryObject<Item> BSTEEL = AutoRegister("bsteel",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> HELMET = AutoRegister("bsteel_helmet",()->new ArmorItem(ModArmors.BSTEEL,ArmorItem.Type.HELMET,new Item.Properties().durability(700)){@Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {tooltip.add(Component.translatable("tooltip.tbccore.bsteel_helmet.desc"));}});
    public static final RegistryObject<Item> CHESTPLATE = AutoRegister("bsteel_chestplate",()->new ArmorItem(ModArmors.BSTEEL,ArmorItem.Type.CHESTPLATE,new Item.Properties().durability(820)){@Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {tooltip.add(Component.translatable("tooltip.tbccore.bsteel_chestplate.desc"));}});
    public static final RegistryObject<Item> kneepads = AutoRegister("rigal_kneepads",()->new ArmorItem(ModArmors.RIGAL,ArmorItem.Type.LEGGINGS,new Item.Properties().durability(790)){@Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {tooltip.add(Component.translatable("tooltip.tbccore.rigal_kneepads.desc"));}});
    public static final RegistryObject<Item> BOOTS = AutoRegister("rigal_boots",()->new ArmorItem(ModArmors.RIGAL,ArmorItem.Type.BOOTS,new Item.Properties().durability(740)){@Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {tooltip.add(Component.translatable("tooltip.tbccore.rigal_boots.desc"));}});
    public static final RegistryObject<Item> flamethrower = AutoRegister("flamethrower",()->new Flamethrower(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
