package nl.theepicblock.baila;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class Baila implements ModInitializer {
    private static Config config;
    @Override
    public void onInitialize() {
        AutoConfig.register(Config.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();
    }

    public static Config getConfig() {
        return config;
    }
}
