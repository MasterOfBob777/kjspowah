package com.bobvaraioa.kubejspowah;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import owmii.powah.api.PowahAPI;

import java.util.List;

public class KubeJSPowahPlugin implements KubeJSPlugin {
    public static EventGroup GROUP = EventGroup.of("PowahEvents");
    public static EventHandler COOLANTS = GROUP.server("registerCoolants", () -> CoolantsEvent.class);
    public static EventHandler HEAT_SOURCE = GROUP.server("registerHeatSource", () -> HeatSourceEvent.class);
    public static EventHandler MAGMATIC_FLUID = GROUP.server("registerMagmaticFluid", () -> MagmaticFluidEvent.class);

    interface EnergizingOrbRecipe {
        RecipeKey<List<Ingredient>> INPUTS = IngredientComponent.UNWRAPPED_INGREDIENT_LIST.key("ingredients", ComponentRole.INPUT);
        RecipeKey<ItemStack> OUTPUT = ItemStackComponent.ITEM_STACK.key("result", ComponentRole.OUTPUT);
        RecipeKey<Long> ENERGY = NumberComponent.LONG.key("energy", ComponentRole.OTHER);

        RecipeSchema SCHEMA = new RecipeSchema(INPUTS, OUTPUT, ENERGY);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry event) {
        event.register(ResourceLocation.fromNamespaceAndPath("powah", "energizing"), EnergizingOrbRecipe.SCHEMA);
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(GROUP);
    }

    static class CoolantsEvent implements KubeEvent {
        public static CoolantsEvent INSTANCE = new CoolantsEvent();
        public void addFluid(Fluid fluid) {
            PowahAPI.getCoolant(fluid);
        }

        public void addSolid(ItemStack res, int cool) {
            PowahAPI.getSolidCoolant(res.getItem());
        }
    }

    static class HeatSourceEvent implements KubeEvent {
        public static HeatSourceEvent INSTANCE = new HeatSourceEvent();

        public void addBlock(Block block) {
            PowahAPI.getHeatSource(block);
        }

        public void addFluid(Fluid fluid) {
            PowahAPI.getHeatSource(fluid);
        }
    }

    static class MagmaticFluidEvent implements KubeEvent {
        public static MagmaticFluidEvent INSTANCE = new MagmaticFluidEvent();

        public void add(Fluid fluid) {
            PowahAPI.getMagmaticFluidEnergyProduced(fluid);
        }
    }
}
