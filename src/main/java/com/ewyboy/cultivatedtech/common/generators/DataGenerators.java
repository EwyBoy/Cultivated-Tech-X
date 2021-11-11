/*
 package com.ewyboy.cultivatedtech.common.generators;

 import com.ewyboy.cultivatedtech.common.register.Register;
 import com.google.common.collect.ImmutableList;
 import com.mojang.datafixers.util.Pair;
 import net.minecraft.advancements.criterion.InventoryChangeTrigger;
 import net.minecraft.block.Block;
 import net.minecraft.data.*;
 import net.minecraft.data.loot.BlockLootTables;
 import net.minecraft.util.IItemProvider;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.client.model.generators.ConfiguredModel;
 import net.minecraftforge.client.model.generators.ItemModelBuilder;
 import net.minecraftforge.client.model.generators.ModelFile;
 import net.minecraftforge.common.data.ExistingFileHelper;
 import net.minecraftforge.common.data.LanguageProvider;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import org.apache.commons.lang3.StringUtils;

 import javax.annotation.Nonnull;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import java.util.function.BiConsumer;
 import java.util.function.Consumer;
 import java.util.function.Supplier;
 import java.util.stream.Collectors;

 public class DataGenerators {

    public static class Lang extends LanguageProvider {

        public Lang(DataGenerator gen, String modid) {
            super(gen, modid, "en_us");
        }

        @Override
        protected void addTranslations() {
            //add("itemGroup.simplyrandom", "Cultivated Tech");
        }

        private void addBlock(Supplier<? extends Block> block) {
            addBlock(block, auto(block));
        }

        private String auto(Supplier<? extends IForgeRegistryEntry<?>> sup) {
            return Arrays.stream(sup.get().getRegistryName().getPath().toLowerCase(Locale.ROOT).split("_"))
                    .map(StringUtils :: capitalize).collect(Collectors.joining(" "));
        }

        @Override
        public String getName() {
            return "Cultivated Tech English Language";
        }
    }

    public static class ItemModels extends ItemModelProvider {

        public ItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
            super(generator, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            itemBlock(() -> Register.BLOCK.ecoflamer);
            itemBlock(() -> Register.BLOCK.adaptiveSoil);
            itemBlock(() -> Register.BLOCK.industrialDirt);
            itemBlock(() -> Register.BLOCK.industrialSoil1);
            itemBlock(() -> Register.BLOCK.industrialSoil2);
        }

        public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
            String name = name(block);
            return withExistingParent(name, modLoc("block/" + name));
        }

        private String name(Supplier<? extends IItemProvider> item) {
            return item.get().asItem().getRegistryName().getPath();
        }

        @Override
        public String getName() {
            return "Cultivated Tech Models";
        }
    }

    public static class BlockStates extends BlockStateProvider {

        public BlockStates(DataGenerator gen, String mod_id, ExistingFileHelper exFileHelper) {
            super(gen, mod_id, exFileHelper);
        }

        ModelFile ecoflamer = getBuilder("block/ecoflamer");
        ModelFile adaptiveSoil = getBuilder("block/adaptivesoil");
        ModelFile industrialDirt = getBuilder("block/industrialdirt");
        ModelFile industrialSoil1 = getBuilder("block/industrialsoil1");
        ModelFile industrialSoil2 = getBuilder("block/industrialsoil2");

        @Override
        protected void registerStatesAndModels() {
            getVariantBuilder(Register.BLOCK.ecoflamer)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(ecoflamer)
                            .build()
                    );
            getVariantBuilder(Register.BLOCK.adaptiveSoil)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(adaptiveSoil)
                            .build()
                    );
            getVariantBuilder(Register.BLOCK.industrialSoil1)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(industrialSoil1)
                            .build()
                    );
            getVariantBuilder(Register.BLOCK.industrialSoil2)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(industrialSoil2)
                            .build()
                    );
            getVariantBuilder(Register.BLOCK.industrialDirt)
                    .forAllStates(state -> ConfiguredModel.builder()
                            .modelFile(industrialDirt)
                            .build()
                    );
        }

        private void simpleBlock(Supplier<? extends Block> block) {
            simpleBlock(block.get());
        }

        @Nonnull
        @Override
        public String getName() {
            return "Cultivated Tech Block States";
        }
    }

    public static class Loots extends LootTableProvider {

        public Loots(DataGenerator dataGeneratorIn) {
            super(dataGeneratorIn);
        }

        @Override
        protected void validate(Map<ResourceLocation, LootTable> map, ValidationResults validationresults) {}

        @Override
        protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
            return ImmutableList.of (
                //Pair.of(BlockLoots :: new, LootParameterSets.BLOCK)
            );
        }

        @Override
        public String getName() {
            return "Cultivated Tech Loot Tables";
        }

        public static class BlockLoots extends BlockLootTables {

            @Override
            protected void addTables() {
                //dropSelf(() -> Register.BLOCK.adaptiveSoil);
                //dropSelf(() -> Register.BLOCK.industrialDirt);
                //dropSelf(() -> Register.BLOCK.ecoflamer);
                //dropSelf(() -> Register.BLOCK.industrialSoil1);
                //dropSelf(() -> Register.BLOCK.industrialSoil2);
            }

            public void dropSelf(Supplier<? extends Block> block) {
                registerDropSelfLootTable(block.get());
            }

        }
    }

    public static class Recipes extends RecipeProvider {

        public Recipes(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
            */
/*shapedRecipe(SimplyRandom.instance.cobblestone_generator_block)
                    .key('P', Items.IRON_PICKAXE)
                    .key('L', Items.LAVA_BUCKET)
                    .key('W', Items.WATER_BUCKET)
                    .key('C', Blocks.COBBLESTONE)
                    .patternLine("PPP")
                    .patternLine("WCL")
                    .patternLine("PPP")
                    .setGroup("cobblestone_generator")
                    .addCriterion("has_cobblestone_generator", hasItem(SimplyRandom.instance.cobblestone_generator_block))
                    .build(consumer);
            shapedRecipe(SimplyRandom.instance.tree_farm_block)
                    .key('A', Items.IRON_AXE)
                    .key('O', Blocks.OBSIDIAN)
                    .key('S', ItemTags.SAPLINGS)
                    .key('Q', Blocks.QUARTZ_BLOCK)
                    .patternLine("ASA")
                    .patternLine("AQA")
                    .patternLine("OOO")
                    .setGroup("tree_farm")
                    .addCriterion("has_tree_farm", hasItem(SimplyRandom.instance.tree_farm_block))
                    .build(consumer);*//*

        }

        private InventoryChangeTrigger.Instance hasItem(Supplier<? extends IItemProvider> item) {
            return hasItem(item.get());
        }

        private ShapedRecipeBuilder shapedRecipe(Supplier<? extends IItemProvider> item) {
            return shapedRecipe(item, 1);
        }

        private ShapedRecipeBuilder shapedRecipe(Supplier<? extends IItemProvider> item, int amount) {
            return ShapedRecipeBuilder.shapedRecipe(item.get(), amount);
        }

        @Override
        public String getName() {
            return "Cultivated Tech Recipes";
        }
    }
}
*/
