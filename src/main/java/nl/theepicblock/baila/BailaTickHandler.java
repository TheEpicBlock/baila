package nl.theepicblock.baila;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BailaTickHandler {
    public static void tick(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();
    }
}
