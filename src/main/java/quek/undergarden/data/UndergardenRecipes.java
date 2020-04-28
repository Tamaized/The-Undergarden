package quek.undergarden.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import quek.undergarden.UndergardenMod;
import quek.undergarden.block.UndergardenBlock;
import quek.undergarden.data.provider.UndergardenRecipeProvider;
import quek.undergarden.registry.UndergardenBlocks;
import quek.undergarden.registry.UndergardenItems;
import quek.undergarden.registry.UndergardenTags;

import java.util.function.Consumer;

public class UndergardenRecipes extends UndergardenRecipeProvider {

    public UndergardenRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        makePlanks(UndergardenBlocks.smogstem_planks, UndergardenBlocks.smogstem_log).build(consumer, name("smogstem_planks"));
        makePlanks(UndergardenBlocks.wigglewood_planks, UndergardenBlocks.wigglewood_log).build(consumer, name("wigglewood_planks"));

        makeBricks(UndergardenBlocks.depthrock_bricks, UndergardenBlocks.depthrock).build(consumer);

        makeSticks(UndergardenItems.smogstem_stick, UndergardenTags.Items.SMOGSTEM_PLANKS).build(consumer, name("smogstem_stick"));

        ShapedRecipeBuilder.shapedRecipe(UndergardenBlocks.gloom_o_lantern.get())
                .patternLine("G")
                .patternLine("T")
                .key('G', UndergardenBlocks.carved_gloomgourd.get())
                .key('T', UndergardenItems.smogstem_torch.get())
                .addCriterion("has_" + UndergardenBlocks.carved_gloomgourd.get().getRegistryName().getPath(), hasItem(UndergardenBlocks.carved_gloomgourd.get()))
                .addCriterion("has_" + UndergardenItems.smogstem_torch.get().getRegistryName().getPath(), hasItem(UndergardenItems.smogstem_torch.get()))
                .build(consumer);


        ShapedRecipeBuilder.shapedRecipe(UndergardenItems.cloggrum_shears.get())
                .patternLine("C ")
                .patternLine(" C")
                .key('C', UndergardenItems.cloggrum_ingot.get())
                .addCriterion("has_" + UndergardenItems.cloggrum_ingot.get().getRegistryName().getPath(), hasItem(UndergardenItems.cloggrum_ingot.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(UndergardenItems.smogstem_torch.get(), 4)
                .patternLine("C")
                .patternLine("S")
                .key('C', Items.COAL)
                .key('S', UndergardenItems.smogstem_stick.get())
                .addCriterion("has_coal", hasItem(Items.COAL))
                .addCriterion("has_" + UndergardenItems.smogstem_stick.get().getRegistryName().getPath(), hasItem(UndergardenItems.smogstem_stick.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(UndergardenItems.smogstem_torch.get(), 2)
                .patternLine("D")
                .patternLine("S")
                .key('D', UndergardenItems.ditchbulb.get())
                .key('S', UndergardenItems.smogstem_stick.get())
                .addCriterion("has_" + UndergardenItems.ditchbulb.get().getRegistryName().getPath(), hasItem(UndergardenItems.ditchbulb.get()))
                .addCriterion("has_" + UndergardenItems.smogstem_stick.get().getRegistryName().getPath(), hasItem(UndergardenItems.smogstem_stick.get()))
                .build(consumer, name("smogstem_torch_ditchbulb"));

        ShapedRecipeBuilder.shapedRecipe(UndergardenItems.undergarden_portal_catalyst.get())
                .patternLine("GIG")
                .patternLine("IGI")
                .patternLine("GIG")
                .key('G', Items.GOLD_INGOT)
                .key('I', Items.IRON_INGOT)
                .addCriterion("has_gold", hasItem(Items.GOLD_INGOT))
                .addCriterion("has_iron", hasItem(Items.IRON_INGOT))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(UndergardenItems.gloomgourd_pie.get())
                .addIngredient(UndergardenTags.Items.MUSHROOMS)
                .addIngredient(UndergardenBlocks.gloomgourd.get())
                .addIngredient(UndergardenItems.ditchbulb.get())
                .addCriterion("has_mushroom", hasItem(UndergardenTags.Items.MUSHROOMS))
                .addCriterion("has_" + UndergardenBlocks.gloomgourd.get().getRegistryName().getPath(), hasItem(UndergardenBlocks.gloomgourd.get()))
                .addCriterion("has_" + UndergardenItems.ditchbulb.get().getRegistryName().getPath(), hasItem(UndergardenItems.ditchbulb.get()))
                .build(consumer);

        makeShardToIngot().build(consumer, name("shard_to_ingot"));

        makeIngotToBlock(UndergardenBlocks.cloggrum_block, UndergardenItems.cloggrum_ingot).build(consumer);
        makeIngotToBlock(UndergardenBlocks.froststeel_block, UndergardenItems.froststeel_ingot).build(consumer);
        makeIngotToBlock(UndergardenBlocks.utherium_block, UndergardenItems.utherium_ingot).build(consumer);

        makeBlockToIngot(UndergardenItems.cloggrum_ingot, UndergardenBlocks.cloggrum_block).build(consumer, name("cloggrum_block_to_ingot"));
        makeBlockToIngot(UndergardenItems.froststeel_ingot, UndergardenBlocks.froststeel_block).build(consumer, name("froststeel_block_to_ingot"));
        makeBlockToIngot(UndergardenItems.utherium_ingot, UndergardenBlocks.utherium_block).build(consumer, name("utherium_block_to_ingot"));

        makeIngotToNugget(UndergardenItems.cloggrum_nugget, UndergardenItems.cloggrum_ingot).build(consumer, name("cloggrum_ingot_to_nugget"));
        makeIngotToNugget(UndergardenItems.froststeel_nugget, UndergardenItems.froststeel_ingot).build(consumer, name("froststeel_ingot_to_nugget"));
        makeIngotToNugget(UndergardenItems.utherium_chunk, UndergardenItems.utherium_ingot).build(consumer, name("utherium_ingot_to_nugget"));

        makeNuggetToIngot(UndergardenItems.cloggrum_ingot, UndergardenItems.cloggrum_nugget).build(consumer, name("cloggrum_nugget_to_ingot"));
        makeNuggetToIngot(UndergardenItems.froststeel_ingot, UndergardenItems.froststeel_nugget).build(consumer, name("froststeel_nugget_to_ingot"));
        makeNuggetToIngot(UndergardenItems.utherium_ingot, UndergardenItems.utherium_chunk).build(consumer, name("utherium_chunk_to_ingot"));

        makeTagSword(UndergardenItems.smogstem_sword, UndergardenTags.Items.SMOGSTEM_PLANKS).build(consumer, name("smogstem_sword"));
        makeSword(UndergardenItems.cloggrum_sword, UndergardenItems.cloggrum_ingot).build(consumer, name("cloggrum_sword"));
        makeSword(UndergardenItems.froststeel_sword, UndergardenItems.froststeel_ingot).build(consumer, name("froststeel_sword"));
        makeSword(UndergardenItems.utheric_sword, UndergardenItems.utherium_ingot).build(consumer, name("utheric_sword"));

        makeTagPickaxe(UndergardenItems.smogstem_pickaxe, UndergardenTags.Items.SMOGSTEM_PLANKS).build(consumer, name("smogstem_pickaxe"));
        makePickaxe(UndergardenItems.cloggrum_pickaxe, UndergardenItems.cloggrum_ingot).build(consumer, name("cloggrum_pickaxe"));
        makePickaxe(UndergardenItems.froststeel_pickaxe, UndergardenItems.froststeel_ingot).build(consumer, name("froststeel_pickaxe"));
        makePickaxe(UndergardenItems.utheric_pickaxe, UndergardenItems.utherium_ingot).build(consumer, name("utheric_pickaxe"));

        makeTagAxe(UndergardenItems.smogstem_axe, UndergardenTags.Items.SMOGSTEM_PLANKS).build(consumer, name("smogstem_axe"));
        makeAxe(UndergardenItems.cloggrum_axe, UndergardenItems.cloggrum_ingot).build(consumer, name("cloggrum_axe"));
        makeAxe(UndergardenItems.froststeel_axe, UndergardenItems.froststeel_ingot).build(consumer, name("froststeel_axe"));
        makeAxe(UndergardenItems.utheric_axe, UndergardenItems.utherium_ingot).build(consumer, name("utheric_axe"));

        makeTagShovel(UndergardenItems.smogstem_shovel, UndergardenTags.Items.SMOGSTEM_PLANKS).build(consumer, name("smogstem_shovel"));
        makeShovel(UndergardenItems.cloggrum_shovel, UndergardenItems.cloggrum_ingot).build(consumer, name("cloggrum_shovel"));
        makeShovel(UndergardenItems.froststeel_shovel, UndergardenItems.froststeel_ingot).build(consumer, name("froststeel_shovel"));
        makeShovel(UndergardenItems.utheric_shovel, UndergardenItems.utherium_ingot).build(consumer, name("utheric_shovel"));

        makeHelmet(UndergardenItems.cloggrum_helmet, UndergardenItems.cloggrum_ingot).build(consumer);
        makeChestplate(UndergardenItems.cloggrum_chestplate, UndergardenItems.cloggrum_ingot).build(consumer);
        makeLeggings(UndergardenItems.cloggrum_leggings, UndergardenItems.cloggrum_ingot).build(consumer);
        makeBoots(UndergardenItems.cloggrum_boots, UndergardenItems.cloggrum_ingot).build(consumer);

        makeStairs(UndergardenBlocks.depthrock_stairs, UndergardenBlocks.depthrock).build(consumer);
        makeStairs(UndergardenBlocks.cobbled_depthrock_stairs, UndergardenBlocks.cobbled_depthrock).build(consumer);
        makeStairs(UndergardenBlocks.depthrock_brick_stairs, UndergardenBlocks.depthrock_bricks).build(consumer);
        makeStairs(UndergardenBlocks.smogstem_stairs, UndergardenBlocks.smogstem_planks).build(consumer);
        makeStairs(UndergardenBlocks.wigglewood_stairs, UndergardenBlocks.wigglewood_planks).build(consumer);

        makeSlab(UndergardenBlocks.depthrock_slab, UndergardenBlocks.depthrock).build(consumer);
        makeSlab(UndergardenBlocks.cobbled_depthrock_slab, UndergardenBlocks.cobbled_depthrock).build(consumer);
        makeSlab(UndergardenBlocks.depthrock_brick_slab, UndergardenBlocks.depthrock_bricks).build(consumer);
        makeSlab(UndergardenBlocks.smogstem_slab, UndergardenBlocks.smogstem_planks).build(consumer);
        makeSlab(UndergardenBlocks.wigglewood_slab, UndergardenBlocks.wigglewood_planks).build(consumer);

        makeWall(UndergardenBlocks.cobbled_depthrock_wall, UndergardenBlocks.cobbled_depthrock).build(consumer);
        makeWall(UndergardenBlocks.depthrock_brick_wall, UndergardenBlocks.depthrock_bricks).build(consumer);

        makeFence(UndergardenBlocks.smogstem_fence, UndergardenBlocks.smogstem_planks).build(consumer);
        makeFence(UndergardenBlocks.wigglewood_fence, UndergardenBlocks.wigglewood_planks).build(consumer);

        smeltingRecipe(Items.IRON_INGOT, UndergardenItems.undergarden_portal_catalyst.get(), .90F).build(consumer, "smelt_catalyst");

        smeltingRecipe(UndergardenItems.cloggrum_ingot.get(), UndergardenBlocks.cloggrum_ore.get(), .7F).build(consumer, name("smelt_cloggrum_ore"));
        blastingRecipe(UndergardenItems.cloggrum_ingot.get(), UndergardenBlocks.cloggrum_ore.get(), .7F).build(consumer, name("blast_cloggrum_ore"));
        smeltingRecipe(UndergardenItems.froststeel_ingot.get(), UndergardenBlocks.froststeel_ore.get(), .7F).build(consumer, name("smelt_froststeel_ore"));
        blastingRecipe(UndergardenItems.froststeel_ingot.get(), UndergardenBlocks.froststeel_ore.get(), .7F).build(consumer, name("blast_froststeel_ore"));
        smeltingRecipe(UndergardenItems.utherium_chunk.get(), UndergardenBlocks.utherium_ore.get(), .9F).build(consumer, name("smelt_utherium_ore"));
        blastingRecipe(UndergardenItems.utherium_chunk.get(), UndergardenBlocks.utherium_ore.get(), .9F).build(consumer, name("blast_utherium_ore"));

        smeltingRecipeTag(UndergardenItems.cloggrum_nugget.get(), UndergardenTags.Items.CLOGGRUM_ITEMS, .1F).build(consumer, name("smelt_cloggrum_item"));
        blastingRecipeTag(UndergardenItems.cloggrum_nugget.get(), UndergardenTags.Items.CLOGGRUM_ITEMS, .1F).build(consumer, name("blast_cloggrum_item"));

        smeltingRecipeTag(UndergardenItems.froststeel_nugget.get(), UndergardenTags.Items.FROSTSTEEL_ITEMS, .1F).build(consumer, name("smelt_froststeel_item"));
        blastingRecipeTag(UndergardenItems.froststeel_nugget.get(), UndergardenTags.Items.FROSTSTEEL_ITEMS, .1F).build(consumer, name("blast_froststeel_item"));

        smeltingRecipeTag(UndergardenItems.utheric_shard.get(), UndergardenTags.Items.UTHERIUM_ITEMS, .1F).build(consumer, name("smelt_utherium_item"));
        blastingRecipeTag(UndergardenItems.utheric_shard.get(), UndergardenTags.Items.UTHERIUM_ITEMS, .1F).build(consumer, name("blast_utherium_item"));


        smeltingRecipe(UndergardenItems.dweller_steak.get(), UndergardenItems.raw_dweller_meat.get(), .35F).build(consumer, name("smelt_dweller_meat"));
        smokingRecipe(UndergardenItems.dweller_steak.get(), UndergardenItems.raw_dweller_meat.get(), .35F).build(consumer, name("smoke_dweller_meat"));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(UndergardenMod.MODID, name);
    }
}
