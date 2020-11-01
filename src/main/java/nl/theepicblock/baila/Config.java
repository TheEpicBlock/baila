package nl.theepicblock.baila;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@me.sargunvohra.mcmods.autoconfig1u.annotation.Config(name = "baila")
public class Config implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int raytraceDistance = 8;

    @Comment("how frequent a raytrace is done. If set to 4, a raytrace is done every 4 ticks. If set to 1, it is done every tick")
    public int frequency = 1;

    @Comment("If set to true, the /baila getProperties command will never take info from the cache. And always raytrace instead. Useful when frequency is high")
    public boolean alwaysRaytraceWhenGettingProperties = false;

    @Override
    public void validatePostLoad() throws ValidationException {
        if (raytraceDistance < 0) raytraceDistance = 0;
        if (raytraceDistance > 100) raytraceDistance = 100;
    }
}
