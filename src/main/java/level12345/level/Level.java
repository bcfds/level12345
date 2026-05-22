package level12345.level;

import com.mojang.logging.LogUtils;
import level12345.level.ModCapability.ModCapabilities;
import level12345.level.ModCapability.sanity.SanityEventHandler;
import level12345.level.ModCapability.sanity.SanityRender;
import level12345.level.ModCapability.sanity.SanitySyncPacket;
import level12345.level.block.ModBlocks;
import level12345.level.item.Moditems;
import net.minecraft.client.Minecraft;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Level.MOD_ID)
public class Level {
    /**
     * ⚠️  The two lines below must not be changed.They define entrypoint!
     */
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "level";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public Level(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        //region ModEventBus
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        Moditems.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        //end region

        //触发类加载，虽然不知道是否真的需要
        ModCapabilities.class.toString();
        SanityEventHandler.class.toString();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

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
            Level.LOGGER.info("SanityRender registered for client.");
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
        Level.LOGGER.info("Sanity sync packet registered with ID: {}", packetId - 1);
    }
}
