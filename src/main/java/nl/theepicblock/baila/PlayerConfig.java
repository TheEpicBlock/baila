package nl.theepicblock.baila;

import net.minecraft.nbt.CompoundTag;

public class PlayerConfig {
    public boolean enabled = true;
    public boolean fluids = false;

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enabled", enabled);
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        if (tag.contains("enabled")) {
            enabled = tag.getBoolean("enabled");
        }
    }
}
