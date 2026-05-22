package level12345.level.ModCapability.sanity;

import net.minecraft.nbt.CompoundTag;

public class SanityDataManager implements ISanity {
    // 定义理智值的最大值
    public static final int MAX_SANITY = 100;
    private int sanityValue = MAX_SANITY;

    @Override
    public int getSanity() {
        return sanityValue;
    }

    @Override
    public void setSanity(int value) {
        sanityValue = Math.max(0, Math.min(value, MAX_SANITY)); // 限制在0-MAX之间
        // TODO: 在网络代码完成后，在这里调用同步方法
        // syncToClient();
    }

    @Override
    public void addSanity(int amount) {
        setSanity(sanityValue + amount);
    }

    @Override
    public boolean isSanityFull() {
        return sanityValue >= MAX_SANITY;
    }

    @Override
    public boolean isSanityEmpty() {
        return sanityValue <= 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("sanity", sanityValue);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        sanityValue = tag.getInt("sanity");
    }
}

