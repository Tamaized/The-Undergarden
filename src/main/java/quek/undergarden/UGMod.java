package quek.undergarden;

import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.data.DataGenerator;
import net.minecraft.dispenser.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import quek.undergarden.client.ClientStuff;
import quek.undergarden.data.*;
import quek.undergarden.entity.projectile.BlisterbombEntity;
import quek.undergarden.entity.projectile.GooBallEntity;
import quek.undergarden.entity.projectile.RottenBlisterberryEntity;
import quek.undergarden.entity.projectile.SlingshotAmmoEntity;
import quek.undergarden.item.UGSpawnEggItem;
import quek.undergarden.registry.*;

@Mod(UGMod.MODID)
public class UGMod {
	
	public static final String MODID = "undergarden";

	public UGMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::setup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::gatherData);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, UGStructures::addDimensionalSpacing);

		DeferredRegister<?>[] registers = {
				UGBiomes.BIOMES,
				UGBlocks.BLOCKS,
				UGCarvers.CARVERS,
				UGEffects.EFFECTS,
				UGEntityTypes.ENTITIES,
				UGFeatures.FEATURES,
				UGFluids.FLUIDS,
				UGItems.ITEMS,
				UGParticleTypes.PARTICLES,
				UGPointOfInterests.POI,
				UGPotions.POTIONS,
				UGSoundEvents.SOUNDS,
				UGStructures.STRUCTURES,
				UGSurfaceBuilders.SURFACE_BUILDERS,
				UGTileEntities.TILE_ENTITIES,
		};

		for (DeferredRegister<?> register : registers) {
			register.register(bus);
		}
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			UGEntityTypes.spawnPlacements();
			UGFeatures.registerConfiguredFeatures();
			UGCarvers.registerConfiguredCarvers();
			UGStructures.registerStructures();
			UGStructures.registerConfiguredStructures();
			UGCriteria.register();
			UGBiomes.toDictionary();
			UGDimensions.registerDimensionStuff();

			AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.SMOGSTEM_LOG.get(), UGBlocks.STRIPPED_SMOGSTEM_LOG.get());
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.SMOGSTEM_WOOD.get(), UGBlocks.STRIPPED_SMOGSTEM_WOOD.get());
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.WIGGLEWOOD_LOG.get(), UGBlocks.STRIPPED_WIGGLEWOOD_LOG.get());
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.WIGGLEWOOD_WOOD.get(), UGBlocks.STRIPPED_WIGGLEWOOD_WOOD.get());
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.GRONGLE_STEM.get(), UGBlocks.STRIPPED_GRONGLE_STEM.get());
			AxeItem.BLOCK_STRIPPING_MAP.put(UGBlocks.GRONGLE_HYPHAE.get(), UGBlocks.STRIPPED_GRONGLE_HYPHAE.get());

			HoeItem.HOE_LOOKUP.put(UGBlocks.DEEPTURF_BLOCK.get(), UGBlocks.DEEPSOIL_FARMLAND.get().getDefaultState());
			HoeItem.HOE_LOOKUP.put(UGBlocks.DEEPSOIL.get(), UGBlocks.DEEPSOIL_FARMLAND.get().getDefaultState());
			HoeItem.HOE_LOOKUP.put(UGBlocks.COARSE_DEEPSOIL.get(), UGBlocks.DEEPSOIL.get().getDefaultState());

			IDispenseItemBehavior bucketBehavior = new DefaultDispenseItemBehavior() {
				private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

				public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
					BucketItem bucketitem = (BucketItem)stack.getItem();
					BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
					World world = source.getWorld();
					if (bucketitem.tryPlaceContainedLiquid(null, world, blockpos, null)) {
						bucketitem.onLiquidPlaced(world, stack, blockpos);
						return new ItemStack(Items.BUCKET);
					} else {
						return this.defaultBehavior.dispense(source, stack);
					}
				}
			};

			DefaultDispenseItemBehavior eggBehavior = new DefaultDispenseItemBehavior() {
				public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
					Direction direction = source.getBlockState().get(DispenserBlock.FACING);
					EntityType<?> type = ((UGSpawnEggItem)stack.getItem()).getType(stack.getTag());
					type.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
					stack.shrink(1);
					return stack;
				}
			};

			DispenserBlock.registerDispenseBehavior(UGItems.VIRULENT_MIX_BUCKET.get(), bucketBehavior);
			DispenserBlock.registerDispenseBehavior(UGItems.GWIBLING_BUCKET.get(), bucketBehavior);

			for(UGSpawnEggItem item : UGSpawnEggItem.eggs()) {
				DispenserBlock.registerDispenseBehavior(item, eggBehavior);
			}

			DispenserBlock.registerDispenseBehavior(UGItems.DEPTHROCK_PEBBLE.get(), new ProjectileDispenseBehavior() {
				protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					return Util.make(new SlingshotAmmoEntity(worldIn, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stackIn));
				}
			});

			DispenserBlock.registerDispenseBehavior(UGItems.GOO_BALL.get(), new ProjectileDispenseBehavior() {
				protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					return Util.make(new GooBallEntity(worldIn, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stackIn));
				}
			});

			DispenserBlock.registerDispenseBehavior(UGItems.ROTTEN_BLISTERBERRY.get(), new ProjectileDispenseBehavior() {
				protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					return Util.make(new RottenBlisterberryEntity(worldIn, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stackIn));
				}
			});

			DispenserBlock.registerDispenseBehavior(UGItems.BLISTERBOMB.get(), new ProjectileDispenseBehavior() {
				protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					return Util.make(new BlisterbombEntity(worldIn, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stackIn));
				}
			});

			PotionBrewing.addMix(Potions.AWKWARD, UGBlocks.BLOOD_MUSHROOM_GLOBULE.get().asItem(), UGPotions.BRITTLENESS.get());
			PotionBrewing.addMix(UGPotions.BRITTLENESS.get(), Items.REDSTONE, UGPotions.LONG_BRITTLENESS.get());
			PotionBrewing.addMix(UGPotions.BRITTLENESS.get(), Items.GLOWSTONE_DUST, UGPotions.STRONG_BRITTLENESS.get());

			PotionBrewing.addMix(Potions.AWKWARD, UGBlocks.VEIL_MUSHROOM.get().asItem(), UGPotions.FEATHERWEIGHT.get());
			PotionBrewing.addMix(UGPotions.FEATHERWEIGHT.get(), Items.REDSTONE, UGPotions.LONG_FEATHERWEIGHT.get());
			PotionBrewing.addMix(UGPotions.FEATHERWEIGHT.get(), Items.GLOWSTONE_DUST, UGPotions.STRONG_FEATHERWEIGHT.get());

			PotionBrewing.addMix(Potions.AWKWARD, UGBlocks.GLOOMGOURD.get().asItem(), UGPotions.VIRULENT_RESISTANCE.get());
			PotionBrewing.addMix(UGPotions.VIRULENT_RESISTANCE.get(), Items.REDSTONE, UGPotions.LONG_VIRULENT_RESISTANCE.get());

			PotionBrewing.addMix(Potions.AWKWARD, UGItems.DROOPVINE.get(), UGPotions.GLOWING.get());
			PotionBrewing.addMix(UGPotions.GLOWING.get(), Items.REDSTONE, UGPotions.LONG_GLOWING.get());

			ComposterBlock.registerCompostable(0.1F, UGItems.DROOPVINE.get());
			ComposterBlock.registerCompostable(0.1F, UGItems.UNDERBEANS.get());
			ComposterBlock.registerCompostable(0.2F, UGItems.BLISTERBERRY.get());
			ComposterBlock.registerCompostable(0.3F, UGItems.GLOOMGOURD_SEEDS.get());
			ComposterBlock.registerCompostable(0.3F, UGItems.GLOWING_KELP.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.SMOGSTEM_LEAVES.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.WIGGLEWOOD_LEAVES.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.GRONGLE_CAP.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.SMOGSTEM_SAPLING.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.WIGGLEWOOD_SAPLING.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.GRONGLET.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.DEEPTURF.get());
			ComposterBlock.registerCompostable(0.3F, UGBlocks.SHIMMERWEED.get());
			ComposterBlock.registerCompostable(0.5F, UGBlocks.TALL_DEEPTURF.get());
			ComposterBlock.registerCompostable(0.5F, UGBlocks.DITCHBULB_PLANT.get());
			ComposterBlock.registerCompostable(0.5F, UGItems.DITCHBULB.get());
			ComposterBlock.registerCompostable(0.5F, UGBlocks.TALL_SHIMMERWEED.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.INDIGO_MUSHROOM.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.VEIL_MUSHROOM.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.INK_MUSHROOM.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.INDIGO_MUSHROOM.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.GLOOMGOURD.get());
			ComposterBlock.registerCompostable(0.65F, UGBlocks.CARVED_GLOOMGOURD.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.INDIGO_MUSHROOM_CAP.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.INDIGO_MUSHROOM_STALK.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.VEIL_MUSHROOM_CAP.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.VEIL_MUSHROOM_STALK.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.INK_MUSHROOM_CAP.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.BLOOD_MUSHROOM_CAP.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.BLOOD_MUSHROOM_GLOBULE.get());
			ComposterBlock.registerCompostable(0.85F, UGBlocks.BLOOD_MUSHROOM_STALK.get());

			FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

			pot.addPlant(UGBlocks.SMOGSTEM_SAPLING.getId(), UGBlocks.POTTED_SMOGSTEM_SAPLING);
			pot.addPlant(UGBlocks.WIGGLEWOOD_SAPLING.getId(), UGBlocks.POTTED_WIGGLEWOOD_SAPLING);
			pot.addPlant(UGBlocks.SHIMMERWEED.getId(), UGBlocks.POTTED_SHIMMERWEED);
			pot.addPlant(UGBlocks.INDIGO_MUSHROOM.getId(), UGBlocks.POTTED_INDIGO_MUSHROOM);
			pot.addPlant(UGBlocks.VEIL_MUSHROOM.getId(), UGBlocks.POTTED_VEIL_MUSHROOM);
			pot.addPlant(UGBlocks.INK_MUSHROOM.getId(), UGBlocks.POTTED_INK_MUSHROOM);
			pot.addPlant(UGBlocks.BLOOD_MUSHROOM.getId(), UGBlocks.POTTED_BLOOD_MUSHROOM);
			pot.addPlant(UGBlocks.GRONGLET.getId(), UGBlocks.POTTED_GRONGLET);
		});
	}

	public void clientSetup(FMLClientSetupEvent event) {
		ClientStuff.registerBlockRenderers();
		ClientStuff.registerEntityRenderers();
		ClientStuff.registerBlockColors();
		ClientStuff.registerItemColors();

		ItemModelsProperties.registerProperty(UGItems.SLINGSHOT.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				return entity.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(UGItems.SLINGSHOT.get(), new ResourceLocation("pulling"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
		ItemModelsProperties.registerProperty(UGItems.CLOGGRUM_SHIELD.get(), new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		DimensionRenderInfo.field_239208_a_.put(UGDimensions.UNDERGARDEN_DIMENSION.getLocation(), new DimensionRenderInfo(Float.NaN, false, DimensionRenderInfo.FogType.NONE, false, true) {
			@Override
			public Vector3d func_230494_a_(Vector3d vector3d, float sun) {
				return vector3d;
			}

			@Override
			public boolean func_230493_a_(int x, int y) {
				return false;
			}
		});
		//TODO: OthersideDRI
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if(event.includeClient()) {
			generator.addProvider(new UGBlockStates(generator, helper));
			generator.addProvider(new UGItemModels(generator, helper));
			generator.addProvider(new UGLang(generator));
		}
		if(event.includeServer()) {
			generator.addProvider(new UGRecipes(generator));
			generator.addProvider(new UGLootTables(generator));
			UGBlockTags blockTags = new UGBlockTags(generator, helper);
			generator.addProvider(blockTags);
			generator.addProvider(new UGItemTags(generator, blockTags, helper));
			generator.addProvider(new UGEntityTags(generator, helper));
		}
	}
}