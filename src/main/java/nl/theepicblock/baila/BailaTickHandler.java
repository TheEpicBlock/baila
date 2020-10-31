package nl.theepicblock.baila;

import net.minecraft.block.BlockState;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

        ServerWorld world = player.getServerWorld();

        HitResult blockHit = player.rayTrace(Baila.getConfig().raytraceDistance, 0, playerConfig.fluids);
        BlockState blockState = world.getBlockState(((BlockHitResult)blockHit).getBlockPos());

        if (!blockState.isAir()) {
            send(player, new TranslatableText(blockState.getBlock().getTranslationKey()));
        } else {
            send(player, LiteralText.EMPTY);
        }
    }

    private static PlayerConfig getConfig(ServerPlayerEntity player) {
        return ((ConfigProvider)player).getConfig();
    }

    public static void send(ServerPlayerEntity player, Text text) {
        if (!text.equals(((PlayerCachedTitle) player).getCachedActionbarTitle())) {
            player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.ACTIONBAR,text));
            ((PlayerCachedTitle) player).setCachedActionbarTitle(text);
        }
    }
}
