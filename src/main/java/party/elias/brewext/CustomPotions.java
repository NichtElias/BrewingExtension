package party.elias.brewext;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class CustomPotions {

    public static final Holder<Potion> HORSE_ELECTROLYTES = registerPotion("horse_electrolytes", "horse_electrolytes",
            new MobEffectInstance(MobEffects.SPEED, 1800, 2),
            new MobEffectInstance(MobEffects.JUMP_BOOST, 1800, 0),
            new MobEffectInstance(MobEffects.NAUSEA, 1800, 0),
            new MobEffectInstance(MobEffects.BLINDNESS, 1800, 0)
    );

    public static final Holder<Potion> LONG_HORSE_ELECTROLYTES = registerPotion("long_horse_electrolytes", "horse_electrolytes",
            new MobEffectInstance(MobEffects.SPEED, 4800, 2),
            new MobEffectInstance(MobEffects.JUMP_BOOST, 4800, 0),
            new MobEffectInstance(MobEffects.NAUSEA, 4800, 0),
            new MobEffectInstance(MobEffects.BLINDNESS, 4800, 0)
    );

    public static final Holder<Potion> FROG = registerPotion("frog", "frog",
            new MobEffectInstance(MobEffects.JUMP_BOOST, 1200, 3)
    );

    public static final Holder<Potion> HYPERFROG = registerPotion("hyperfrog", "hyperfrog",
            new MobEffectInstance(MobEffects.JUMP_BOOST, 1200, 7),
            new MobEffectInstance(MobEffects.GLOWING, 1200, 0)
    );

    public static final Holder<Potion> GLOWING = registerPotion("glowing", "glowing",
            new MobEffectInstance(MobEffects.GLOWING, 3600, 0)
    );

    public static final Holder<Potion> LONG_GLOWING = registerPotion("long_glowing", "glowing",
            new MobEffectInstance(MobEffects.GLOWING, 9600, 0)
    );

    public static final Holder<Potion> BLINDNESS = registerPotion("blindness", "blindness",
            new MobEffectInstance(MobEffects.BLINDNESS, 1800, 0)
    );

    public static final Holder<Potion> LONG_BLINDNESS = registerPotion("long_blindness", "blindness",
            new MobEffectInstance(MobEffects.BLINDNESS, 4800, 0)
    );

    public static final Holder<Potion> NAUSEA = registerPotion("nausea", "nausea",
            new MobEffectInstance(MobEffects.NAUSEA, 900, 0)
    );

    public static final Holder<Potion> LONG_NAUSEA = registerPotion("long_nausea", "nausea",
            new MobEffectInstance(MobEffects.NAUSEA, 1800, 0)
    );

    public static final Holder<Potion> HASTE = registerPotion("haste", "haste",
            new MobEffectInstance(MobEffects.HASTE, 3600, 0)
    );

    public static final Holder<Potion> LONG_HASTE = registerPotion("long_haste", "haste",
            new MobEffectInstance(MobEffects.HASTE, 9600, 0)
    );

    public static final Holder<Potion> STRONG_HASTE = registerPotion("strong_haste", "haste",
            new MobEffectInstance(MobEffects.HASTE, 1800, 1)
    );

    public static final Holder<Potion> TRUE_INVISIBILITY = registerPotion("true_invisibility", "true_invisibility",
            new MobEffectInstance(MobEffects.INVISIBILITY, 1800, 0, false, false)
    );

    public static final Holder<Potion> LONG_TRUE_INVISIBILITY = registerPotion("long_true_invisibility", "true_invisibility",
            new MobEffectInstance(MobEffects.INVISIBILITY, 4800, 0, false, false)
    );

    public static final Holder<Potion> WITHER = registerPotion("wither", "wither",
            new MobEffectInstance(MobEffects.WITHER, 400, 0)
    );

    public static final Holder<Potion> LONG_WITHER = registerPotion("long_wither", "wither",
            new MobEffectInstance(MobEffects.WITHER, 800, 0)
    );

    public static final Holder<Potion> STRONG_WITHER = registerPotion("strong_wither", "wither",
            new MobEffectInstance(MobEffects.WITHER, 400, 1)
    );

    public static final Holder<Potion> RESTORATION = registerPotion("restoration", "restoration",
            new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 2),
            new MobEffectInstance(MobEffects.SATURATION, 1, 9)
    );

    public static final Holder<Potion> GREATER_RESTORATION = registerPotion("greater_restoration", "greater_restoration",
            new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 3),
            new MobEffectInstance(MobEffects.SATURATION, 1, 19)
    );

    public static final Holder<Potion> LEVITATION = registerPotion("levitation", "levitation",
            new MobEffectInstance(MobEffects.LEVITATION, 80, 3)
    );

    public static final Holder<Potion> STRONG_LEVITATION = registerPotion("strong_levitation", "levitation",
            new MobEffectInstance(MobEffects.LEVITATION, 80, 7)
    );

    public static void init() {

    }

    private static Holder<Potion> registerPotion(String registryName, String translationName, MobEffectInstance... effects) {
        return Registry.registerForHolder(
                BuiltInRegistries.POTION,
                Identifier.fromNamespaceAndPath(BrewingExtension.MODID, registryName),
                new Potion(translationName, effects)
        );
    }
}
