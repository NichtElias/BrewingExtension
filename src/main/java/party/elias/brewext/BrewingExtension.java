package party.elias.brewext;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
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
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;

public class BrewingExtension implements ModInitializer {
    public static final String MODID = "brewext";

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
        Registry.registerForHolder(
                BuiltInRegistries.POTION,
                Identifier.fromNamespaceAndPath(MODID, "extra_" + potionHolder.unwrapKey().get().identifier().getPath()),
                new Potion(
                        potionHolder.value().name(),
                        potionHolder.value().getEffects().stream().map(
                                effect -> effect.withScaledDuration(2)
                                ).toArray(MobEffectInstance[]::new)
                )
        );
    }
}
