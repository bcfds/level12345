package level12345.level.Capability.sanity;

import net.minecraft.nbt.CompoundTag;

public class SanityDataManager implements ISanity {
    // 定义理智值的最大值
    public static final int MAX_SANITY = 100;
    private int sanityValue = MAX_SANITY;
    public  Runnable onChanged;

    public void setOnChanged(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public int getSanity() {
        return sanityValue;
    }

    @Override
    public void setSanity(int value) {
        int old = this.sanityValue;
        this.sanityValue = Math.max(0, Math.min(value, MAX_SANITY));
        if (old != this.sanityValue && onChanged != null) {
            onChanged.run();   // 当值真正变化时，执行回调
        }
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

