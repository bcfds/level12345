package level12345.level;

import com.mojang.logging.LogUtils;
import level12345.level.Capability.ModCapabilities;
import level12345.level.Capability.sanity.SanityEventHandler;
import level12345.level.Capability.sanity.client.SanityRender;
import level12345.level.Capability.sanity.SanitySyncPacket;
import level12345.level.Block.ModBlocks;
import level12345.level.CreativeModeTabs.ModCreativeModeTabs;
import level12345.level.Entity.ModEntities;
import level12345.level.Item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static level12345.level.Item.ModItems.*;

//TODO 注意看一下IDEA报的“未使用”变量或者方法，检查下是不是真的没有用，不要做超前的冗余设计
//FIXME 玩家在退出游戏时保存世界的速度越来越慢了，需要找出原因

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BackroomsLevel.MOD_ID)
public class BackroomsLevel {
    /**
     * ⚠️  The two lines below must not be changed.They define entrypoint!
     */
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "level";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public BackroomsLevel(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        //region ModEventBus
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCapabilities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

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
        LOGGER.info("BackroomsLevel mod common setup completed.");
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
            BackroomsLevel.LOGGER.info("SanityRender registered for client.");
        }
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
        BackroomsLevel.LOGGER.info("Sanity sync packet registered with ID: {}", packetId - 1);
    }
}
