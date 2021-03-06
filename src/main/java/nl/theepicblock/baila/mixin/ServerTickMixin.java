package nl.theepicblock.baila.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import nl.theepicblock.baila.Baila;
import nl.theepicblock.baila.BailaTickHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ServerTickMixin {
	@Shadow private PlayerManager playerManager;

	@Shadow private int ticks;

	@Inject(at = @At("HEAD"), method = "tickWorlds(Ljava/util/function/BooleanSupplier;)V")
	private void tickInject(CallbackInfo info) {
		if (ticks % Baila.getConfig().frequency == 0) {
			playerManager.getPlayerList().forEach(BailaTickHandler::tick);
		}
	}
}
