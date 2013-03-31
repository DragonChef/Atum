package rebelkeithy.mods.atum;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.atum.artifacts.HorusFlight;
import rebelkeithy.mods.atum.artifacts.ItemNutsAgility;
import rebelkeithy.mods.atum.artifacts.ItemPtahsDecadence;
import rebelkeithy.mods.atum.artifacts.ItemRasGlory;
import rebelkeithy.mods.atum.artifacts.ItemSekhmetsWrath;
import rebelkeithy.mods.atum.blocks.AtumStone;
import rebelkeithy.mods.atum.blocks.BlockArrowTrap;
import rebelkeithy.mods.atum.blocks.BlockAtumLeaves;
import rebelkeithy.mods.atum.blocks.BlockAtumLog;
import rebelkeithy.mods.atum.blocks.BlockAtumPortal;
import rebelkeithy.mods.atum.blocks.BlockAtumSand;
import rebelkeithy.mods.atum.blocks.BlockAtumSlab;
import rebelkeithy.mods.atum.blocks.BlockAtumStairs;
import rebelkeithy.mods.atum.blocks.BlockAtumStone;
import rebelkeithy.mods.atum.blocks.BlockSandLayered;
import rebelkeithy.mods.atum.blocks.BlockShrub;
import rebelkeithy.mods.atum.blocks.ItemSandLayered;
import rebelkeithy.mods.atum.blocks.TileEntityArrowTrap;
import rebelkeithy.mods.atum.cursedchest.BlockChestSpawner;
import rebelkeithy.mods.atum.cursedchest.TileEntityChestSpawner;
import rebelkeithy.mods.atum.entities.EntityBanditArcher;
import rebelkeithy.mods.atum.entities.EntityBanditWarrior;
import rebelkeithy.mods.atum.entities.EntityDustySkeleton;
import rebelkeithy.mods.atum.entities.EntityMummy;
import rebelkeithy.mods.atum.entities.EntityPharoh;
import rebelkeithy.mods.atum.entities.ModelDustySkeleton;
import rebelkeithy.mods.atum.entities.RenderBandit;
import rebelkeithy.mods.atum.furnace.BlockLimeStoneFurnace;
import rebelkeithy.mods.atum.furnace.TileEntityLimestoneFurnace;
import rebelkeithy.mods.atum.items.ItemAtumBow;
import rebelkeithy.mods.atum.items.ItemScimitar;
import rebelkeithy.mods.atum.world.AtumWorldProvider;
import rebelkeithy.mods.atum.world.biome.BiomeGenAtumDesert;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;


@Mod(modid="Atum", name="Atum", version="0.0.0.1")
@NetworkMod(channels = {"Atum"}, clientSideRequired = true, serverSideRequired = false)
public class Atum 
{
	@Instance(value="Atum")
	public static Atum instance;
	
	@SidedProxy(clientSide = "rebelkeithy.mods.atum.ClientProxy", serverSide = "rebelkeithy.mods.atum.CommonProxy")
	public static CommonProxy proxy;
	
	public static BlockAtumPortal portal;
	public static Block cursedChest;
	public static Block atumSand;
	public static Block atumStone;
	public static Block atumCobble;
	public static Block atumLargeBrick;
	public static Block atumSmallBrick;
	public static Block atumCarvedBrick;
	public static BlockAtumSlab atumSlabs;
	public static BlockAtumSlab atumDoubleSlab;
	public static Block atumSmoothStairs;
	public static Block atumCobbleStairs;
	public static Block atumLargeStoneStairs;
	public static Block atumSmallStoneStairs;
	public static Block atumSandLayered;
	
	public static Block atumFurnaceIdle;
	public static Block atumFurnaceActive;
	
	public static Block atumShrub;
	public static Block atumWeed;
	
	public static Block atumLog;
	public static Block atumLeaves;
	
	public static Block atumTrapArrow;
	
	public static Item itemScarab;
	public static Item itemScimitar;
	public static Item itemBow;
	
	public static Item ptahsPick;
	public static Item rasGlory;
	public static Item sekhmetsWrath;
	public static Item nutsAgility;
	public static Item horusFlight;

	public static int dimensionID = 17;
	
	public static BiomeGenBase atumDesert;

	public static Block furnaceIdle;
	public static Block furnaceBurning;

	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		portal = new BlockAtumPortal(ConfigAtum.portalBlockID);
		cursedChest = new BlockChestSpawner(ConfigAtum.cursedChestID).setCreativeTab(CreativeTabs.tabDecorations);
		atumSand = new BlockAtumSand(ConfigAtum.sandID).setUnlocalizedName("Atum:AtumSand").setStepSound(Block.soundSandFootstep).setHardness(0.5F).setCreativeTab(CreativeTabs.tabBlock);
		atumStone = new AtumStone(ConfigAtum.stoneID).setUnlocalizedName("Atum:AtumStone").setHardness(1.5F).setCreativeTab(CreativeTabs.tabBlock);
		atumCobble = new Block(ConfigAtum.cobbleID, Material.rock).setUnlocalizedName("Atum:AtumCobble").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		atumLargeBrick = new BlockAtumStone(ConfigAtum.largeBrickID, Material.rock).setUnlocalizedName("Atum:AtumBrickLarge").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		atumSmallBrick = new BlockAtumStone(ConfigAtum.smallBrickID, Material.rock).setUnlocalizedName("Atum:AtumBrickSmall").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		atumCarvedBrick = new BlockAtumStone(ConfigAtum.carvedBrickID, Material.rock).setUnlocalizedName("Atum:AtumBrickCarved").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		atumSlabs = (BlockAtumSlab) new BlockAtumSlab(ConfigAtum.slabID, false, Material.rock).setUnlocalizedName("Atum:AtumSlab").setHardness(2.0F).setCreativeTab(CreativeTabs.tabBlock);
		atumDoubleSlab = (BlockAtumSlab) new BlockAtumSlab(ConfigAtum.doubleSlabID, true, Material.rock).setUnlocalizedName("Atum:AtumDoubleSlab").setHardness(2.0F);
		atumSmoothStairs = (new BlockAtumStairs(ConfigAtum.smoothStairsID, atumStone, 0)).setUnlocalizedName("Atum:SmoothStair");
		atumCobbleStairs = (new BlockAtumStairs(ConfigAtum.cobbleStairsID, atumCobble, 0)).setUnlocalizedName("Atum:CobbleStair");
		atumLargeStoneStairs = (new BlockAtumStairs(ConfigAtum.largeStoneStairsID, atumLargeBrick, 0)).setUnlocalizedName("Atum:LargeStoneStair");
		atumSmallStoneStairs = (new BlockAtumStairs(ConfigAtum.smallStoneStairsID, atumSmallBrick, 0)).setUnlocalizedName("Atum:SmallStoneStair");
		atumShrub = (new BlockShrub(ConfigAtum.shrubID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Atum:Shrub");
		atumWeed = (new BlockShrub(ConfigAtum.weedID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Atum:Weed");

	    atumSandLayered = (new BlockSandLayered(ConfigAtum.sandLayeredID)).setHardness(0.1F).setStepSound(Block.soundSnowFootstep).setUnlocalizedName("SandLayered").setLightOpacity(0);
	    
		atumLog = new BlockAtumLog(ConfigAtum.logID).setHardness(1F).setStepSound(Block.soundWoodFootstep);
		atumLeaves = new BlockAtumLeaves(ConfigAtum.leavesID).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("AtumLeaves");
		
		atumTrapArrow = new BlockArrowTrap(ConfigAtum.trapArrowID).setHardness(0.2F);
	    furnaceIdle = (new BlockLimeStoneFurnace(ConfigAtum.furnaceIdleID, false)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("limestonefurnaceidle").setCreativeTab(CreativeTabs.tabDecorations);
	    furnaceBurning = (new BlockLimeStoneFurnace(ConfigAtum.furnaceBurningID, true)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.875F).setUnlocalizedName("limestonefurnaceactive");
		
		
		ForgeHooks.canToolHarvestBlock(atumSand, 0, new ItemStack(Item.shovelSteel));
		MinecraftForge.setBlockHarvestLevel(atumSand, "shovel", 0);
		
		
		LanguageRegistry.addName(atumStone, "Limestone");
		LanguageRegistry.addName(atumSand, "Limestone sand");
		LanguageRegistry.addName(atumCobble, "Cracked Limestone");
		
		//EntityRegistry.registerModEntity(EntityMummy.class, "AtumMummy", ConfigAtum.mummyID, this, 16, 20, true);
		EntityRegistry.registerGlobalEntityID(EntityMummy.class, "AtumMummy", ConfigAtum.mummyID);
		EntityList.addMapping(EntityMummy.class, "AtumMummy", ConfigAtum.mummyID, 0x515838, 0x868F6B);
		RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class, new RenderLiving(new ModelZombie(), 0.5F));

		EntityRegistry.registerGlobalEntityID(EntityBanditWarrior.class, "AtumBanditWarrior", ConfigAtum.banditWarriorID);
		EntityList.addMapping(EntityBanditWarrior.class, "AtumBanditWarrior", ConfigAtum.banditWarriorID, 0xC2C2C2, 0x040F85);
		RenderingRegistry.registerEntityRenderingHandler(EntityBanditWarrior.class, new RenderBiped(new ModelBiped(), 0.5F));

		EntityRegistry.registerGlobalEntityID(EntityBanditArcher.class, "AtumBanditArcher", ConfigAtum.banditArcherID);
		EntityList.addMapping(EntityBanditArcher.class, "AtumBanditArcher", ConfigAtum.banditArcherID, 0xC2C2C2, 0x7E0C0C);
		RenderingRegistry.registerEntityRenderingHandler(EntityBanditArcher.class, new RenderBandit(new ModelBiped(), 0.5F));

		EntityRegistry.registerGlobalEntityID(EntityPharoh.class, "AtumPharaoh", ConfigAtum.pharaohID);
		EntityList.addMapping(EntityPharoh.class, "AtumPharaoh", ConfigAtum.pharaohID, 0xD4BC37, 0x3A4BE0);
		RenderingRegistry.registerEntityRenderingHandler(EntityPharoh.class, new RenderBiped(new ModelBiped(), 0.5F));

		EntityRegistry.registerGlobalEntityID(EntityDustySkeleton.class, "AtumDustySkeleton", ConfigAtum.dustySkeletonID);
		EntityList.addMapping(EntityDustySkeleton.class, "AtumDustySkeleton", ConfigAtum.dustySkeletonID, 0xB59C7D, 0x6F5C43);
		RenderingRegistry.registerEntityRenderingHandler(EntityDustySkeleton.class, new RenderBiped(new ModelDustySkeleton(), 0.5F));
		
		TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);		
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		
		//EntityList.addMapping(EntityBandit.class, "AtumBanditArcher", ConfigAtum.banditArcherID, 0xC2C2C2, 0x070C0C);
		
		GameRegistry.registerBlock(atumSand, "AtumSand");
		GameRegistry.registerBlock(atumStone, "AtumStone");
		GameRegistry.registerBlock(atumCobble, "AtumCobble");
		GameRegistry.registerBlock(atumLargeBrick, "AtumBrickLarge");
		GameRegistry.registerBlock(atumSmallBrick, "AtumBrickSmall");
		GameRegistry.registerBlock(atumCarvedBrick, "AtumBrickCarved");
		GameRegistry.registerBlock(atumSlabs, "AtumSlabs");
		GameRegistry.registerBlock(atumSmoothStairs, "AtumSmoothStairs");
		GameRegistry.registerBlock(atumCobbleStairs, "AtumCobbleStairs");
		GameRegistry.registerBlock(atumLargeStoneStairs, "AtumLargeStoneStairs");
		GameRegistry.registerBlock(atumSmallStoneStairs, "AtumSmallStoneStairs");
		GameRegistry.registerBlock(atumShrub, "AtumShrub");
		GameRegistry.registerBlock(atumLog, "AtumLog");
		GameRegistry.registerBlock(atumLeaves, "AtumLeaves");
		GameRegistry.registerBlock(atumWeed, "AtumWeed");
		GameRegistry.registerBlock(atumTrapArrow, "AtumArmorTrap");
		GameRegistry.registerBlock(cursedChest, "BlockCursedChest");
		GameRegistry.registerBlock(atumSandLayered, ItemSandLayered.class, "BlockSandLayered");
		GameRegistry.registerBlock(furnaceIdle, "limestonefurnaceidle");
		GameRegistry.registerBlock(furnaceBurning, "limestonefurnaceburning");
		
		GameRegistry.registerTileEntity(TileEntityChestSpawner.class, "CursedChest");
		GameRegistry.registerTileEntity(TileEntityArrowTrap.class, "ArrowTrap");
		GameRegistry.registerTileEntity(TileEntityLimestoneFurnace.class, "LimestoneFurnace");
		
		Item.itemsList[ConfigAtum.slabID] = (new ItemSlab(atumSlabs.blockID - 256, atumSlabs, atumDoubleSlab, false)).setUnlocalizedName("woodSlab");
        Item.itemsList[atumDoubleSlab.blockID] = (new ItemSlab(atumDoubleSlab.blockID - 256, atumSlabs, atumDoubleSlab, true)).setUnlocalizedName("woodSlab");
        
		
		itemScarab = new ItemPortalSpawner(ConfigAtum.portalSpawnerID).setUnlocalizedName("Atum:Scarab").setCreativeTab(CreativeTabs.tabTools);
		atumDesert = (new BiomeGenAtumDesert(ConfigAtum.biomeAtumDesertID)).setColor(16421912).setBiomeName("AtumDesert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.1F, 0.2F);

		//EnumToolMaterial scimitarEnum = EnumHelper.addToolMaterial("Scimitar", 2, 250, 6.0F, 2, 14);
		itemScimitar = (new ItemScimitar(ConfigAtum.scimitarID, EnumToolMaterial.IRON)).setUnlocalizedName("Atum:Scimitar").setCreativeTab(CreativeTabs.tabCombat);
		itemBow = (new ItemAtumBow(ConfigAtum.bowID)).setUnlocalizedName("Atum:Bow").setCreativeTab(CreativeTabs.tabCombat);
		
		ptahsPick = new ItemPtahsDecadence(ConfigAtum.ptahsPickID, EnumToolMaterial.EMERALD).setUnlocalizedName("Atum:PtahsDecadence").setCreativeTab(CreativeTabs.tabTools);
		rasGlory = new ItemRasGlory(ConfigAtum.rasGloryID, EnumArmorMaterial.DIAMOND, 0, 0).setTextureFile("EgyptianArmor").setUnlocalizedName("Atum:RasGlory").setCreativeTab(CreativeTabs.tabCombat);
		sekhmetsWrath = new ItemSekhmetsWrath(ConfigAtum.sekhmetsWrathID, EnumArmorMaterial.DIAMOND, 1, 1).setTextureFile("EgyptianArmor").setUnlocalizedName("Atum:SekhmetsWrath").setCreativeTab(CreativeTabs.tabCombat);
		nutsAgility = new ItemNutsAgility(ConfigAtum.nutsAgilityID, EnumArmorMaterial.DIAMOND, 2, 2).setTextureFile("EgyptianArmor").setUnlocalizedName("Atum:NutsAgility").setCreativeTab(CreativeTabs.tabCombat);
		horusFlight = new HorusFlight(ConfigAtum.horusFlightID, EnumArmorMaterial.DIAMOND, 3, 3).setTextureFile("EgyptianArmor").setUnlocalizedName("Atum:HorusFlight").setCreativeTab(CreativeTabs.tabCombat);
		
		LanguageRegistry.addName(ptahsPick, "Ptah's Decadence");
		LanguageRegistry.addName(rasGlory, "Ra's Glory");
		LanguageRegistry.addName(sekhmetsWrath, "Sekhmet's Wrath");
		LanguageRegistry.addName(nutsAgility, "Nut's Agility");
		LanguageRegistry.addName(horusFlight, "Horus's Flight");
		
		proxy.registerParticles();
		
		NetworkRegistry.instance().registerGuiHandler(this, new AtumGuiHandler());
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		DimensionManager.registerProviderType(Atum.dimensionID, AtumWorldProvider.class, true);
		DimensionManager.registerDimension(Atum.dimensionID , Atum.dimensionID);


		addRecipes();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	public void addRecipes()
	{
		FurnaceRecipes.smelting().addSmelting(atumCobble.blockID, new ItemStack(atumStone), 0.1F);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(atumLargeBrick, 4), "XX", "XX", 'X', atumStone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(atumSmallBrick, 4), "XX", "XX", 'X', atumCobble));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(atumCarvedBrick, 1), atumStone));
	}
}