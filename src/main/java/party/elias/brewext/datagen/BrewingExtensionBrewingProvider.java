package party.elias.brewext.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBrewingProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import party.elias.brewext.BrewingExtension;
import party.elias.brewext.CustomPotions;

public class BrewingExtensionBrewingProvider extends FabricBrewingProvider {

    public BrewingExtensionBrewingProvider(RecipeOutput output) {
        super(output);
    }

    @Override
    protected void buildMixes() {
        buildMix(Potions.STRONG_SWIFTNESS, Items.GOLDEN_APPLE, CustomPotions.HORSE_ELECTROLYTES);
        buildMix(CustomPotions.HORSE_ELECTROLYTES, Items.REDSTONE, CustomPotions.LONG_HORSE_ELECTROLYTES);

        buildMix(Potions.STRONG_LEAPING, Items.SLIME_BLOCK, CustomPotions.FROG);
        buildMix(CustomPotions.FROG, Items.OCHRE_FROGLIGHT, CustomPotions.HYPERFROG);
        buildMix(CustomPotions.FROG, Items.PEARLESCENT_FROGLIGHT, CustomPotions.HYPERFROG);
        buildMix(CustomPotions.FROG, Items.VERDANT_FROGLIGHT, CustomPotions.HYPERFROG);

        buildStartMix(Items.GLOW_INK_SAC, CustomPotions.GLOWING);
        buildMix(CustomPotions.GLOWING, Items.REDSTONE, CustomPotions.LONG_GLOWING);
        buildMix(CustomPotions.GLOWING, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
        buildMix(CustomPotions.LONG_GLOWING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);

        buildMix(Potions.INVISIBILITY, Items.INK_SAC, CustomPotions.BLINDNESS);
        buildMix(CustomPotions.BLINDNESS, Items.REDSTONE, CustomPotions.LONG_BLINDNESS);
        buildMix(Potions.LONG_INVISIBILITY, Items.INK_SAC, CustomPotions.LONG_BLINDNESS);

        buildMix(Potions.WATER, Items.ROTTEN_FLESH, CustomPotions.NAUSEA);
        buildMix(CustomPotions.NAUSEA, Items.REDSTONE, CustomPotions.LONG_NAUSEA);

        buildMix(Potions.SWIFTNESS, Items.GOLDEN_PICKAXE, CustomPotions.HASTE);
        buildMix(CustomPotions.HASTE, Items.REDSTONE, CustomPotions.LONG_HASTE);
        buildMix(CustomPotions.HASTE, Items.GLOWSTONE_DUST, CustomPotions.STRONG_HASTE);

        buildMix(Potions.INVISIBILITY, Items.BLUE_ORCHID, CustomPotions.TRUE_INVISIBILITY);
        buildMix(CustomPotions.TRUE_INVISIBILITY, Items.REDSTONE, CustomPotions.LONG_TRUE_INVISIBILITY);
        buildMix(Potions.LONG_INVISIBILITY, Items.BLUE_ORCHID, CustomPotions.LONG_TRUE_INVISIBILITY);

        buildMix(Potions.POISON, Items.WITHER_ROSE, CustomPotions.WITHER);
        buildMix(CustomPotions.WITHER, Items.REDSTONE, CustomPotions.LONG_WITHER);
        buildMix(Potions.LONG_POISON, Items.WITHER_ROSE, CustomPotions.LONG_WITHER);
        buildMix(CustomPotions.WITHER, Items.GLOWSTONE_DUST, CustomPotions.STRONG_WITHER);
        buildMix(Potions.STRONG_POISON, Items.WITHER_ROSE, CustomPotions.STRONG_WITHER);

        buildMix(Potions.STRONG_HEALING, Items.CAKE, CustomPotions.RESTORATION);
        buildMix(CustomPotions.RESTORATION, Items.HONEY_BLOCK, CustomPotions.GREATER_RESTORATION);

        buildMix(Potions.SLOW_FALLING, Items.SHULKER_SHELL, CustomPotions.LEVITATION);
        buildMix(CustomPotions.LEVITATION, Items.GLOWSTONE_DUST, CustomPotions.STRONG_LEVITATION);

        buildMix(Potions.STRONG_LEAPING, Items.EMERALD_BLOCK, Potions.LUCK);

        buildExtraLongPotionMix(Potions.LONG_SWIFTNESS);
        buildExtraLongPotionMix(Potions.LONG_SLOWNESS);
        buildExtraLongPotionMix(Potions.LONG_LEAPING);
        buildExtraLongPotionMix(Potions.LONG_STRENGTH);
        buildExtraLongPotionMix(Potions.LONG_POISON);
        buildExtraLongPotionMix(Potions.LONG_REGENERATION);
        buildExtraLongPotionMix(Potions.LONG_FIRE_RESISTANCE);
        buildExtraLongPotionMix(Potions.LONG_WATER_BREATHING);
        buildExtraLongPotionMix(Potions.LONG_NIGHT_VISION);
        buildExtraLongPotionMix(Potions.LONG_INVISIBILITY);
        buildExtraLongPotionMix(Potions.LONG_TURTLE_MASTER);
        buildExtraLongPotionMix(Potions.LONG_SLOW_FALLING);
        buildExtraLongPotionMix(Potions.LONG_WEAKNESS);
        buildExtraLongPotionMix(CustomPotions.LONG_HORSE_ELECTROLYTES);
        buildExtraLongPotionMix(CustomPotions.LONG_GLOWING);
        buildExtraLongPotionMix(CustomPotions.LONG_BLINDNESS);
        buildExtraLongPotionMix(CustomPotions.LONG_NAUSEA);
        buildExtraLongPotionMix(CustomPotions.LONG_HASTE);
        buildExtraLongPotionMix(CustomPotions.LONG_TRUE_INVISIBILITY);
        buildExtraLongPotionMix(CustomPotions.LONG_WITHER);
    }

    private void buildExtraLongPotionMix(Holder<Potion> potion) {
        Identifier xlPotionIdentifier = Identifier.fromNamespaceAndPath(BrewingExtension.MODID, "extra_" + potion.unwrapKey().get().identifier().getPath());
        buildMix(potion, Items.PITCHER_PLANT, BuiltInRegistries.POTION.getOrThrow(ResourceKey.create(Registries.POTION, xlPotionIdentifier)));
    }
}
