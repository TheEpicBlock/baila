package nl.theepicblock.baila;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BailaTickHandler {
    public static void tick(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();

        HitResult blockHit = player.rayTrace(20,0,false);
        BlockState blockState = world.getBlockState(((BlockHitResult)blockHit).getBlockPos());

        send(player, new TranslatableText(blockState.getBlock().getTranslationKey()));
    }

    public static void send(ServerPlayerEntity player, Text text) {
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.ACTIONBAR,text));
    }
}
