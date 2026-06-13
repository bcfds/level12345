package level12345.level.DataGen.DimensionVisit;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DimensionVisitorData extends SavedData {
    private static final String DATA_NAME = "dimension_visitors";
    private final Map<ResourceKey<Level>, Set<UUID>> visitors = new HashMap<>();

    public static DimensionVisitorData get(ServerLevel overworld) {
        return overworld.getDataStorage().computeIfAbsent(
                DimensionVisitorData::load,
                DimensionVisitorData::new,
                DATA_NAME
        );
    }

    // 添加记录
    public void addVisitor(ResourceKey<Level> dimension, UUID playerUUID) {
        visitors.computeIfAbsent(dimension, k -> new HashSet<>()).add(playerUUID);
        setDirty(); // 标记数据已变，下次保存时写入磁盘
    }

    // 查询某维度的所有访客
    public Set<UUID> getVisitors(ResourceKey<Level> dimension) {
        return visitors.getOrDefault(dimension, Collections.emptySet());
    }

    // 是否访问过
    public boolean hasVisited(ResourceKey<Level> dimension, UUID playerUUID) {
        return visitors.getOrDefault(dimension, Collections.emptySet()).contains(playerUUID);
    }

    //返回指定玩家访问过的所有维度 ResourceKey
    public Set<ResourceKey<Level>> getPlayerVisitedDimensions(UUID playerUUID) {
        Set<ResourceKey<Level>> result = new HashSet<>();
        for (Map.Entry<ResourceKey<Level>, Set<UUID>> entry : visitors.entrySet()) {
            if (entry.getValue().contains(playerUUID)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    // 从NBT加载
    public static DimensionVisitorData load(CompoundTag nbt) {
        DimensionVisitorData data = new DimensionVisitorData();
        ListTag list = nbt.getList("Visitors", 10);
        for (Tag tag : list) {
            CompoundTag entry = (CompoundTag) tag;
            String dimNamespace = entry.getString("dim_namespace");
            String dimPath = entry.getString("dim_path");
            ResourceKey<Level> dimKey = ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation(dimNamespace, dimPath)
            );
            Set<UUID> uuids = new HashSet<>();
            ListTag uuidList = entry.getList("Players", 8);
            for (Tag uuidTag : uuidList) {
                uuids.add(NbtUtils.loadUUID(uuidTag));
            }
            data.visitors.put(dimKey, uuids);
        }
        return data;
    }

    // 保存为NBT
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag nbt) {
        ListTag list = new ListTag();
        for (Map.Entry<ResourceKey<Level>, Set<UUID>> entry : visitors.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            ResourceLocation loc = entry.getKey().location();
            entryTag.putString("dim_namespace", loc.getNamespace());
            entryTag.putString("dim_path", loc.getPath());
            ListTag players = new ListTag();
            for (UUID uuid : entry.getValue()) {
                players.add(NbtUtils.createUUID(uuid));
            }
            entryTag.put("Players", players);
            list.add(entryTag);
        }
        nbt.put("Visitors", list);
        return nbt;
    }
}
