package level12345.level.ModCapability.sanity;

import net.minecraft.nbt.CompoundTag;

public interface ISanity {
    int getSanity();
    void setSanity(int sanity);
    void addSanity(int amount);

    //
    boolean isSanityFull(); // 理智值是否满值
    boolean isSanityEmpty(); // 理智值是否耗尽

    //Forge Method
    CompoundTag serializeNBT(); // 序列化数据，用于存档
    void deserializeNBT(CompoundTag tag);// 反序列化数据，用于读档
}
