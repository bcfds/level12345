package org.thebackroomscomplex.tbccore.DataGen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tbccore", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent // 订阅事件以监听它
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        //↑初始化数据生成器
        if (event.includeClient()) {//生成客户端数据（模型、语言文件等）
            generator.addProvider(true, new ModItemModelProvider(output, helper));//确认生成物品模型
        }
    }
}
