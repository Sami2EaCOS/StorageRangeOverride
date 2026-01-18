package fr.samiracle.srange;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class StorageRangeConfig {

    public static final BuilderCodec<StorageRangeConfig> CODEC = BuilderCodec.builder(StorageRangeConfig.class, StorageRangeConfig::new)
            .append(new KeyedCodec<>("StorageHorizontalRange", Codec.INTEGER),
                    (config, value, _) -> config.storageHorizontalRange = value,
                    (config, _) -> config.storageHorizontalRange).add()
            .append(new KeyedCodec<>("StorageVerticalRange", Codec.INTEGER),
                    (config, value, _) -> config.storageVerticalRange = value,
                    (config, _) -> config.storageVerticalRange).add()
            .append(new KeyedCodec<>("StorageChestLimit", Codec.INTEGER),
                    (config, value, _) -> config.storageChestLimit = value,
                    (config, _) -> config.storageChestLimit).add()
            .build();

    private int storageHorizontalRange = 20;
    private int storageVerticalRange = 10;
    private int storageChestLimit = 100;

    public int getStorageHorizontalRange() {
        return storageHorizontalRange;
    }

    public int getStorageVerticalRange() {
        return storageVerticalRange;
    }

    public int getStorageChestLimit() {
        return storageChestLimit;
    }

    public void setStorageHorizontalRange(int storageHorizontalRange) {
        this.storageHorizontalRange = storageHorizontalRange;
    }

    public void setStorageVerticalRange(int storageVerticalRange) {
        this.storageVerticalRange = storageVerticalRange;
    }

    public void setStorageChestLimit(int storageChestLimit) {
        this.storageChestLimit = storageChestLimit;
    }

}
