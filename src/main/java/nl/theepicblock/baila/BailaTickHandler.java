package nl.theepicblock.baila;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

@SuppressWarnings("PointlessBooleanExpression")
public class BailaTickHandler {
    public static void tick(ServerPlayerEntity player) {
        PlayerConfig playerConfig = getConfig(player);
        if (playerConfig.enabled == false) return;

        BlockState blockState = raytrace(player, playerConfig);

        if (blockState != ((CachedBlockProvider)player).getCachedBlock()) {
            ((CachedBlockProvider)player).setCachedBlock(blockState);

            if (!blockState.isAir()) {
                send(player, new TranslatableText(blockState.getBlock().getTranslationKey()));
            } else {
                send(player, LiteralText.EMPTY);
            }
        }
    }

    public static void sendProperties(ServerCommandSource source) throws CommandSyntaxException {
        BlockState blockState = ((CachedBlockProvider)source.getPlayer()).getCachedBlock();
        if (blockState == null || Baila.getConfig().alwaysRaytraceWhenGettingProperties) blockState = raytrace(source.getPlayer(), getConfig(source.getPlayer()));

        StringBuilder message = new StringBuilder();
        for (Property<?> property : blockState.getProperties()) {
            String propertyName = property.getName();
            String valueName = getValueName(property, blockState);

            message.append("\n - ");
            message.append(propertyName);
            message.append(" = ");
            message.append(valueName);
        }

        TranslatableText text = new TranslatableText(blockState.getBlock().getTranslationKey());
        text.append(new LiteralText(message.toString()));
        source.sendFeedback(text, false);
    }

    private static <T extends Comparable<T>> String getValueName(Property<T> property, BlockState state) {
        return property.name(state.get(property));
    }

    private static BlockState raytrace(ServerPlayerEntity playerEntity, PlayerConfig playerConfig) {
        HitResult blockHit = playerEntity.rayTrace(Baila.getConfig().raytraceDistance, 0, playerConfig.fluids);
        return playerEntity.world.getBlockState(((BlockHitResult)blockHit).getBlockPos());
    }

    private static PlayerConfig getConfig(ServerPlayerEntity player) {
        return ((ConfigProvider)player).getConfig();
    }

    public static void send(ServerPlayerEntity player, Text text) {
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.ACTIONBAR,text));
    }
}
