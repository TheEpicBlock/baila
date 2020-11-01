package nl.theepicblock.baila.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import nl.theepicblock.baila.ConfigProvider;
import nl.theepicblock.baila.CachedBlockProvider;
import nl.theepicblock.baila.PlayerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements CachedBlockProvider, ConfigProvider {
	@Unique	BlockState cachedBlock;
	@Unique	PlayerConfig config = new PlayerConfig();

	@Override
	public BlockState getCachedBlock() {
		return cachedBlock;
	}

	@Override
	public void setCachedBlock(BlockState v) {
		this.cachedBlock = v;
	}

	@Override
	public PlayerConfig getConfig() {
		return config;
	}

	@Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
	public void writeInject(CompoundTag tag, CallbackInfo ci) {
		if (config.hasChangedFromDefault()) {
			tag.put("bailaplayerconfig", config.toTag());
		}
	}

	@Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
	public void readInject(CompoundTag tag, CallbackInfo ci) {
		if (tag.contains("bailaplayerconfig")) {
			config.fromTag(tag.getCompound("bailaplayerconfig"));
		}
	}
}
