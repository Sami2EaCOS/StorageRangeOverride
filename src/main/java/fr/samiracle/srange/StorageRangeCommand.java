package fr.samiracle.srange;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;

import javax.annotation.Nonnull;

public class StorageRangeCommand extends CommandBase {

    private final StorageRange plugin;
    private final RequiredArg<Integer> horizontalArg;
    private final RequiredArg<Integer> verticalArg;
    private final RequiredArg<Integer> limitArg;

    public StorageRangeCommand(StorageRange plugin) {
        super("storagerangeoverride", "Updates storage range config");
        this.plugin = plugin;
        this.horizontalArg = withRequiredArg("horizontal", "Horizontal range", ArgTypes.INTEGER);
        this.verticalArg = withRequiredArg("vertical", "Vertical range", ArgTypes.INTEGER);
        this.limitArg = withRequiredArg("limit", "Chest limit", ArgTypes.INTEGER);

        requirePermission(plugin.getBasePermission() + ".config");

        addAliases("sro");
        addAliases("srange");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        int horizontal = horizontalArg.get(context);
        int vertical = verticalArg.get(context);
        int limit = limitArg.get(context);

        if (horizontal < 0 || vertical < 0 || limit < 0) {
            context.sendMessage(Message.raw("Values must be non-negative."));
            return;
        }

        plugin.updateConfig(horizontal, vertical, limit);
        context.sendMessage(Message.raw(
                "Storage range updated: horizontal=" + horizontal + " vertical=" + vertical + " limit=" + limit));
    }
}
