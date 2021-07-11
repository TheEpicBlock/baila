package nl.theepicblock.baila;

import net.minecraft.nbt.NbtCompound;

public class PlayerConfig {
    public boolean enabled = true;
    public boolean fluids = false;

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("enabled", enabled);
        return tag;
    }

    public void fromTag(NbtCompound tag) {
        if (tag.contains("enabled")) {
            enabled = tag.getBoolean("enabled");
        }
    }

    @SuppressWarnings("PointlessBooleanExpression")
    public boolean hasChangedFromDefault() {
        return enabled != true ||
                fluids != false;
    }
}
