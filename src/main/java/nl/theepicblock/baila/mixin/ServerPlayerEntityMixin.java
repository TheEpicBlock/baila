package nl.theepicblock.baila.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import nl.theepicblock.baila.ConfigProvider;
import nl.theepicblock.baila.PlayerCachedTitle;
import nl.theepicblock.baila.PlayerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerCachedTitle, ConfigProvider {
	@Unique	Text cachedActionbarTitle;
	@Unique	PlayerConfig config = new PlayerConfig();

	@Override
	public Text getCachedActionbarTitle() {
		return cachedActionbarTitle;
	}

	@Override
	public void setCachedActionbarTitle(Text cachedActionbarTitle) {
		this.cachedActionbarTitle = cachedActionbarTitle;
	}

	@Override
	public PlayerConfig getConfig() {
		return config;
	}

	@Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
	public void writeInject(CompoundTag tag, CallbackInfo ci) {
		tag.put("bailaplayerconfig", config.toTag());
	}

	@Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
	public void readInject(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains("bailaplayerconfig")) {
			config.fromTag(tag.getCompound("bailaplayerconfig"));
		}
	}
}
