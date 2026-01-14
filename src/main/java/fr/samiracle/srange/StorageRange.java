package fr.samiracle.srange;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.asset.type.gameplay.CraftingConfig;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class StorageRange extends JavaPlugin {

    private final Config<StorageRangeConfig> config;

    public StorageRange(@Nonnull JavaPluginInit init) {
        super(init);
        this.config = this.withConfig("StorageRange", StorageRangeConfig.CODEC);
    }

    @Override
    protected void setup() {
        super.setup();
        this.config.save();
        var eventBus = HytaleServer.get().getEventBus();

        eventBus.register(AllWorldsLoadedEvent.class, event -> {
            for (World world : Universe.get().getWorlds().values()) {
                applyStorageRange(world);
            }
        });

        eventBus.registerGlobal(AddWorldEvent.class, event -> applyStorageRange(event.getWorld()));
    }

    private void applyStorageRange(World world) {
        if (world == null) {
            return;
        }

        StorageRangeConfig cfg = this.config.get();
        int horizontalRange = cfg.getStorageHorizontalRange();
        int verticalRange = cfg.getStorageVerticalRange();
        int limit = cfg.getStorageChestLimit();

        try {
            CraftingConfig craftingConfig = world.getGameplayConfig().getCraftingConfig();
            setFieldInt(craftingConfig, "benchMaterialHorizontalChestSearchRadius", horizontalRange);
            setFieldInt(craftingConfig, "benchMaterialVerticalChestSearchRadius", verticalRange);
            setFieldInt(craftingConfig, "benchMaterialChestLimit", limit);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private static void setFieldInt(Object target, String fieldName, int value) throws ReflectiveOperationException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.setInt(target, value);
    }
}
