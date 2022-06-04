package net.jacg.stop_that;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.jacg.stop_that.config.Util;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Locale;

import static net.jacg.stop_that.StopThatClient.CONFIG;
import static net.jacg.stop_that.config.Config.AllowWhen;

public class StopThatModMenu implements ModMenuApi {

    private static Text getName(Enum option) {
        return new TranslatableText("config.stop_that." + option.name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(new TranslatableText("config.stop_that.title"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            ConfigCategory configCategory = builder.getOrCreateCategory(new TranslatableText("config.stop_that.general"));

            configCategory.addEntry(entryBuilder.startEnumSelector(new TranslatableText("config.stop_that.allow_log_stripping"), AllowWhen.class, CONFIG.isLogStrippingAllowed)
                    .setDefaultValue(AllowWhen.ALWAYS)
                    .setEnumNameProvider(StopThatModMenu::getName)
                    .setSaveConsumer(newValue -> CONFIG.isLogStrippingAllowed = newValue)
                    .build());

            configCategory.addEntry(entryBuilder.startEnumSelector(new TranslatableText("config.stop_that.allow_berry_placing"), AllowWhen.class, CONFIG.isBerryPlacingAllowed)
                    .setDefaultValue(AllowWhen.ALWAYS)
                    .setEnumNameProvider(StopThatModMenu::getName)
                    .setSaveConsumer(newValue -> CONFIG.isBerryPlacingAllowed = newValue)
                    .build());

            configCategory.addEntry(entryBuilder.startEnumSelector(new TranslatableText("config.stop_that.hit_grass_with_sword"), AllowWhen.class, CONFIG.swordHitGrassAllowed)
                    .setDefaultValue(AllowWhen.ALWAYS)
                    .setEnumNameProvider(StopThatModMenu::getName)
                    .setSaveConsumer(newValue -> CONFIG.swordHitGrassAllowed = newValue)
                    .build());

            configCategory.addEntry(entryBuilder.startEnumSelector(new TranslatableText("config.stop_that.allow_wrong_tool"), AllowWhen.class, CONFIG.blockBreakingWrongToolAllowed)
                    .setDefaultValue(AllowWhen.ALWAYS)
                    .setEnumNameProvider(StopThatModMenu::getName)
                    .setSaveConsumer(newValue -> CONFIG.blockBreakingWrongToolAllowed = newValue)
                    .build());

            builder.setSavingRunnable(Util::saveConfig);

            return builder.build();
        };
    }
}
