package org.thebackroomscomplex.tbccore;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.thebackroomscomplex.tbccore.Block.ModBlocks;
import org.thebackroomscomplex.tbccore.Capability.ModCapabilities;
import org.thebackroomscomplex.tbccore.Capability.sanity.SanityEventHandler;
import org.thebackroomscomplex.tbccore.Capability.sanity.SanitySyncPacket;
import org.thebackroomscomplex.tbccore.Capability.sanity.client.SanityRender;
import org.thebackroomscomplex.tbccore.CreativeModeTabs.ModCreativeModeTabs;
import org.thebackroomscomplex.tbccore.Entity.ModEntities;
import org.thebackroomscomplex.tbccore.Entity.client.smilerRenderer;
import org.thebackroomscomplex.tbccore.Entity.custom.SmilerEntity;
import org.thebackroomscomplex.tbccore.Item.Bottle.BottleItem;
import org.thebackroomscomplex.tbccore.Item.Bottle.DrinkEffectHandler;
import org.thebackroomscomplex.tbccore.Item.ModItems;

import static org.thebackroomscomplex.tbccore.Entity.ModEntities.smiler;
import static org.thebackroomscomplex.tbccore.Item.ModItems.*;

//TODO 注意看一下IDEA报的“未使用”变量或者方法，检查下是不是真的没有用，不要做超前的冗余设计
//FIXME 玩家在退出游戏时保存世界的速度越来越慢了，需要找出原因

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TBCcore.MOD_ID)
public class TBCcore {
    /**
     * ⚠️  The two lines below must not be changed.They define entrypoint!
     */
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "tbccore";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public TBCcore(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        //region ModEventBus
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCapabilities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new DrinkEffectHandler());
        //end region
        // 注册通用初始化方法
        modEventBus.addListener(this::commonSetup);
        // 注册自身到 Forge 事件总线（处理服务器启动等）
        MinecraftForge.EVENT_BUS.register(this);
        //触发类加载，虽然不知道是否真的需要
        SanityEventHandler.class.toString();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //AutoReg的一部分，用于智能化添加
        //TODO 就这样硬编码的判断类型，早晚会给玩家的机器塞到建筑组工具，总是要改的，而且绝对有优化空间
        for (RegistryObject<Item> regObj : CREATIVETAB_SUPPLIER) {
            if (regObj.get() instanceof BlockItem) {
                BLOCKITEMS_CREATIVETAB_SUPPLIER.add(regObj);
            } else {
                ITEMS_CREATIVETAB_SUPPLIER.add(regObj);
            }
        }
        //笑魇的生成
        event.enqueueWork(() -> SpawnPlacements.register(
                smiler.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SmilerEntity::canSpawn
        ));//TODO 这么注册消炎生成器早晚炸死我们,但现在没事所以咱不管
        ItemProperties.register(
                ModItems.IRON_BOTTLE.get(),
                    new ResourceLocation(MOD_ID, "filled"),
                (stack, level, entity, seed) -> BottleItem.getFluid(stack).isEmpty() ? 0.0f : 1.0f);
        LOGGER.info("TBCcore mod common setup completed.");
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            // 确保 SanityRender 类被加载，以触发其 @EventBusSubscriber
            SanityRender.class.toString();
            TBCcore.LOGGER.info("SanityRender registered for client.");
            event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.GLASS.get(),
                    RenderType.translucent()
            ));
            event.enqueueWork(() -> {
                EntityRenderers.register(ModEntities.smiler.get(), smilerRenderer::new);
            });
        }
    }

    @SubscribeEvent
    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(smiler.get(), SmilerEntity.createAttributes().build());
    }

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "sanity_sync"),
            () -> "1.0",
            s -> true,
            s -> true
    );
    static {
        int packetId = 0;
        NETWORK.registerMessage(packetId++, SanitySyncPacket.class, SanitySyncPacket::encode, SanitySyncPacket::decode, SanitySyncPacket::handle);
        TBCcore.LOGGER.info("Sanity sync packet registered with ID: {}", packetId - 1);
    }
}
