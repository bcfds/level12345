package level12345.level;

import com.mojang.logging.LogUtils;
import level12345.level.block.ModBlocks;
import level12345.level.item.Moditems;
import level12345.level.worldgen.dimensions.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.datafix.fixes.MissingDimensionFix;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import javax.xml.crypto.Data;
import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Level.MOD_ID)
public class Level {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "level";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public Level(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        Moditems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void addDataProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
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
        }
        public static void gatherData(GatherDataEvent event){
            DataGenerator gen = event.getGenerator();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
//            gen.addProvider(true,new MissingDimensionFix(gen,lookupProvider));
        }
    }
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "sanity_sync"),
            () -> "1.0",
            s -> true,
            s -> true
    );
    private static final ResourceLocation SANITY_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/sanity_icon.png");

    public Level() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::attachCapability);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(this::registerOverlay);
        }
    }

    // 将 SAN 值附加到玩家身上
    private void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(MOD_ID, "sanity"), new SanityProvider());
        }
    }

    // 注册 HUD 叠加层
    private void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "sanity", guiGraphics -> {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            // 获取玩家的 SAN 值
            player.getCapability(SanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
                int currentSanity = sanity.getSanity();

                // 绘制贴图 (x=10, y=10, 宽高=20)
                RenderSystem.enableBlend();
//                guiGraphics.blit(SANITY_TEXTURE, 10, 10, 0, 0, 20, 20, 20, 20);

                // 绘制文字
//                guiGraphics.drawString(Minecraft.getInstance().font, "SAN: " + currentSanity, 35, 15, 0xFFFFFF);
            });
        });
    }
    @SubscribeEvent
    public static void onRenderGameOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "sanity", (GuiGraphics guiGraphics) -> {
            // 1. 获取玩家
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;

            // 2. 获取 Sanity 数据
            player.getCapability(SanityProvider.PLAYER_SANITY).resolve().ifPresent((ISanity sanityData) -> {
                int currentSanity = sanityData.getSanity();

                // 3. 绘制贴图 (使用 guiGraphics)
                RenderSystem.enableBlend();
                guiGraphics.blit(SANITY_TEXTURE, 10, 10, 0, 0, 20, 20, 20, 20);

                // 4. 绘制文字 (使用 guiGraphics)
                guiGraphics.drawString(mc.font, "SAN: " + currentSanity, 35, 15, 0xFFFFFF);
            });
        });
    }
}
