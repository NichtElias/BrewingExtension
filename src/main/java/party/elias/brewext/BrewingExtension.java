package party.elias.brewext;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BrewingExtension implements ModInitializer {
    public static final String MODID = "brewext";

    private final List<Consumer<PotionBrewing.Builder>> brewingRecipeRegistrationCallbacks = new ArrayList<>();

    private static InteractionResult feedPotion(Player player, Level world, InteractionHand hand, Entity entity, EntityHitResult hitResult) {
        ItemStack handItem = player.getItemInHand(hand);
        if (handItem.is(Items.POTION)
                && !player.getCooldowns().isOnCooldown(handItem)
                && !player.isCrouching()
                && entity instanceof LivingEntity livingEntity
                && (entity instanceof TamableAnimal tamable && tamable.isTame()
                || entity instanceof AbstractHorse horse && horse.isTamed())
        ) {
            PotionContents potionContents = handItem.getComponents().get(DataComponents.POTION_CONTENTS);
            Consumable consumable = handItem.getComponents().get(DataComponents.CONSUMABLE);

            if (potionContents != null && consumable != null) {
                consumable.emitParticlesAndSounds(entity.getRandom(), livingEntity, handItem, 16);

                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.awardStat(Stats.ITEM_USED.get(handItem.getItem()));
                }

                potionContents.applyToLivingEntity(livingEntity, 1);

                player.getCooldowns().addCooldown(handItem, 30);

                entity.gameEvent(GameEvent.DRINK);
                if (!player.hasInfiniteMaterials()) {
                    handItem.shrink(1);

                    if (handItem.getCount() == 0) {
                        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else {
                        player.addItem(new ItemStack(Items.GLASS_BOTTLE));
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onInitialize() {

        CustomPotions.init();

        registerPotions();

        UseEntityCallback.EVENT.register(BrewingExtension::feedPotion);

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            for (Consumer<PotionBrewing.Builder> callback: brewingRecipeRegistrationCallbacks) {
                callback.accept(builder);
            }

            builder.addMix(Potions.STRONG_SWIFTNESS, Items.GOLDEN_APPLE, CustomPotions.HORSE_ELECTROLYTES);
            builder.addMix(CustomPotions.HORSE_ELECTROLYTES, Items.REDSTONE, CustomPotions.LONG_HORSE_ELECTROLYTES);

            builder.addMix(Potions.STRONG_LEAPING, Items.SLIME_BLOCK, CustomPotions.FROG);
            builder.addMix(CustomPotions.FROG, Items.OCHRE_FROGLIGHT, CustomPotions.HYPERFROG);
            builder.addMix(CustomPotions.FROG, Items.PEARLESCENT_FROGLIGHT, CustomPotions.HYPERFROG);
            builder.addMix(CustomPotions.FROG, Items.VERDANT_FROGLIGHT, CustomPotions.HYPERFROG);

            builder.addStartMix(Items.GLOW_INK_SAC, CustomPotions.GLOWING);
            builder.addMix(CustomPotions.GLOWING, Items.REDSTONE, CustomPotions.LONG_GLOWING);
            builder.addMix(CustomPotions.GLOWING, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
            builder.addMix(CustomPotions.LONG_GLOWING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);

            builder.addMix(Potions.INVISIBILITY, Items.INK_SAC, CustomPotions.BLINDNESS);
            builder.addMix(CustomPotions.BLINDNESS, Items.REDSTONE, CustomPotions.LONG_BLINDNESS);
            builder.addMix(Potions.LONG_INVISIBILITY, Items.INK_SAC, CustomPotions.LONG_BLINDNESS);

            builder.addMix(Potions.WATER, Items.ROTTEN_FLESH, CustomPotions.NAUSEA);
            builder.addMix(CustomPotions.NAUSEA, Items.REDSTONE, CustomPotions.LONG_NAUSEA);

            builder.addMix(Potions.SWIFTNESS, Items.GOLDEN_PICKAXE, CustomPotions.HASTE);
            builder.addMix(CustomPotions.HASTE, Items.REDSTONE, CustomPotions.LONG_HASTE);
            builder.addMix(CustomPotions.HASTE, Items.GLOWSTONE_DUST, CustomPotions.STRONG_HASTE);

            builder.addMix(Potions.INVISIBILITY, Items.BLUE_ORCHID, CustomPotions.TRUE_INVISIBILITY);
            builder.addMix(CustomPotions.TRUE_INVISIBILITY, Items.REDSTONE, CustomPotions.LONG_TRUE_INVISIBILITY);
            builder.addMix(Potions.LONG_INVISIBILITY, Items.BLUE_ORCHID, CustomPotions.LONG_TRUE_INVISIBILITY);

            builder.addMix(Potions.POISON, Items.WITHER_ROSE, CustomPotions.WITHER);
            builder.addMix(CustomPotions.WITHER, Items.REDSTONE, CustomPotions.LONG_WITHER);
            builder.addMix(Potions.LONG_POISON, Items.WITHER_ROSE, CustomPotions.LONG_WITHER);
            builder.addMix(CustomPotions.WITHER, Items.GLOWSTONE_DUST, CustomPotions.STRONG_WITHER);
            builder.addMix(Potions.STRONG_POISON, Items.WITHER_ROSE, CustomPotions.STRONG_WITHER);

            builder.addMix(Potions.STRONG_HEALING, Items.CAKE, CustomPotions.RESTORATION);
            builder.addMix(CustomPotions.RESTORATION, Items.HONEY_BLOCK, CustomPotions.GREATER_RESTORATION);

            builder.addMix(Potions.SLOW_FALLING, Items.SHULKER_SHELL, CustomPotions.LEVITATION);
            builder.addMix(CustomPotions.LEVITATION, Items.GLOWSTONE_DUST, CustomPotions.STRONG_LEVITATION);

            builder.addMix(Potions.STRONG_LEAPING, Items.EMERALD_BLOCK, Potions.LUCK);
        });

    }

    private void registerPotions() {
        registerExtraLongPotion(Potions.LONG_SWIFTNESS);
        registerExtraLongPotion(Potions.LONG_SLOWNESS);
        registerExtraLongPotion(Potions.LONG_LEAPING);
        registerExtraLongPotion(Potions.LONG_STRENGTH);
        registerExtraLongPotion(Potions.LONG_POISON);
        registerExtraLongPotion(Potions.LONG_REGENERATION);
        registerExtraLongPotion(Potions.LONG_FIRE_RESISTANCE);
        registerExtraLongPotion(Potions.LONG_WATER_BREATHING);
        registerExtraLongPotion(Potions.LONG_NIGHT_VISION);
        registerExtraLongPotion(Potions.LONG_INVISIBILITY);
        registerExtraLongPotion(Potions.LONG_TURTLE_MASTER);
        registerExtraLongPotion(Potions.LONG_SLOW_FALLING);
        registerExtraLongPotion(Potions.LONG_WEAKNESS);
        registerExtraLongPotion(CustomPotions.LONG_HORSE_ELECTROLYTES);
        registerExtraLongPotion(CustomPotions.LONG_GLOWING);
        registerExtraLongPotion(CustomPotions.LONG_BLINDNESS);
        registerExtraLongPotion(CustomPotions.LONG_NAUSEA);
        registerExtraLongPotion(CustomPotions.LONG_HASTE);
        registerExtraLongPotion(CustomPotions.LONG_TRUE_INVISIBILITY);
        registerExtraLongPotion(CustomPotions.LONG_WITHER);
    }

    private void registerExtraLongPotion(Holder<Potion> potionHolder) {
        Holder<Potion> xlPotionHolder = Registry.registerForHolder(
                BuiltInRegistries.POTION,
                Identifier.fromNamespaceAndPath(MODID, "extra_long_" + potionHolder.unwrapKey().get().identifier().getPath()),
                new Potion(
                        potionHolder.value().name(),
                        potionHolder.value().getEffects().stream().map(
                                effect -> effect.withScaledDuration(2)
                                ).toArray(MobEffectInstance[]::new)
                )
        );

        brewingRecipeRegistrationCallbacks.add(builder ->
                builder.addMix(
                        potionHolder,
                        Items.PITCHER_PLANT,
                        xlPotionHolder
                )
        );
    }
}
