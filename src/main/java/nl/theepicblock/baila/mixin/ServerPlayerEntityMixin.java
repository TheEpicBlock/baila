package nl.theepicblock.baila.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import nl.theepicblock.baila.PlayerCachedTitle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerCachedTitle {
	@Unique
	Text cachedActionbarTitle;

	@Override
	public Text getCachedActionbarTitle() {
		return cachedActionbarTitle;
	}

	@Override
	public void setCachedActionbarTitle(Text cachedActionbarTitle) {
		this.cachedActionbarTitle = cachedActionbarTitle;
	}
}
