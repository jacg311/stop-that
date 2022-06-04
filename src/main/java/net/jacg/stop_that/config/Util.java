package net.jacg.stop_that.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.jacg.stop_that.StopThatClient;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static net.jacg.stop_that.StopThatClient.CONFIG;

public class Util {
    public static void saveConfig() {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("stop_that.json").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create();
            writer.write(gson.toJson(CONFIG));

        }
        catch (IOException e) {
            StopThatClient.LOGGER.error("Failed to save config! " + e.getMessage());
        }
    }

    public static Config readConfigOrDefault() {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("stop_that.json").toFile();
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)){
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                return gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                StopThatClient.LOGGER.error("Failed to read config! Using default instead. " + e.getMessage());
            }
        }
        return new Config();
    }

    public static boolean checkCondition(Config.AllowWhen allowWhen, PlayerEntity player) {
        return allowWhen == Config.AllowWhen.SNEAKING ? !player.isSneaking() : allowWhen == Config.AllowWhen.NEVER;
    }
}
