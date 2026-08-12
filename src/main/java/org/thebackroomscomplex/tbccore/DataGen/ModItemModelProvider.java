package org.thebackroomscomplex.tbccore.DataGen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.thebackroomscomplex.tbccore.Item.Bottle.BottleItem;
import org.thebackroomscomplex.tbccore.Item.ModItems;

import java.util.Objects;

import static org.thebackroomscomplex.tbccore.TBCcore.MOD_ID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper){
        super(output,MOD_ID,existingFileHelper);
    }
    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().forEach(entry ->{
            Item item = entry.get();
            String name = Objects.requireNonNull(entry.getId()).getPath();
            ResourceLocation texture = new ResourceLocation("tbccore", "item/" + name);//遍历获取所有注册物品以及其注册名，纹理默认路径
            if (item instanceof BlockItem) return; // 跳过 BlockItem，它们由 BlockStateProvider 自动生成
            if(item instanceof  ArmorItem) return; // 跳过 ArmorItem，因为它们的模型各种各样的
            if(item instanceof  BottleItem) return; // 跳过 BottleItem，因为它的模型特殊
            if (!existingFileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
                System.out.println("[Datagen] SKIP (texture missing): " + name);
                return;
            }//跳过还没准备的纹理，你懂为什么的
            // ========== 分类 1：手持物品（工具/武器）==========
            if (item instanceof SwordItem || item instanceof AxeItem || item instanceof PickaxeItem || item instanceof ShovelItem || item instanceof HoeItem) {
                withExistingParent(name, mcLoc("item/handheld"))
                        .texture("layer0", modLoc("item/" + name));
            }
            // ========== 分类 2：普通物品（默认）==========
            else {
                basicItem(item);
            }
        });
    }
}
