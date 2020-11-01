package nl.theepicblock.baila;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class BailaCommands {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        commandDispatcher.register(CommandManager.literal("baila")
                .then(CommandManager.literal("toggle")
                    .executes((context) -> {
                        PlayerConfig config = getConfig(context);
                        config.enabled = !config.enabled;
                        context.getSource().sendFeedback(new LiteralText("you have now "+enabledDisabled(config.enabled)+" baila"), false);
                        ((CachedBlockProvider)context.getSource().getPlayer()).setCachedBlock(null);
                        return Command.SINGLE_SUCCESS;
                    }))
                .then(CommandManager.literal("fluids")
                    .executes((context) -> {
                        PlayerConfig config = getConfig(context);
                        config.fluids = !config.fluids;
                        context.getSource().sendFeedback(new LiteralText("you have now "+enabledDisabled(config.fluids)+" fluids"), false);
                        return Command.SINGLE_SUCCESS;
                    }))
                .then(CommandManager.literal("getproperties")
                    .executes((context) -> {
                        BailaTickHandler.sendProperties(context.getSource());
                        return Command.SINGLE_SUCCESS;
                    })));
    }

    private static PlayerConfig getConfig(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        return ((ConfigProvider)player).getConfig();
    }
    private static String enabledDisabled(boolean in) {
        return in ? "enabled" : "disabled";
    }
}
