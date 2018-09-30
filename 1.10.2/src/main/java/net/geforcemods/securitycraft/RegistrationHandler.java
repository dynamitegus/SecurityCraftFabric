package net.geforcemods.securitycraft;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.TileEntitySCTE;
import net.geforcemods.securitycraft.entity.EntityBouncingBetty;
import net.geforcemods.securitycraft.entity.EntityIMSBomb;
import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.geforcemods.securitycraft.entity.EntityTaserBullet;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedCompressedBlocks;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedLog;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedMetals;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedPlanks;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedPrismarine;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedPurpur;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedQuartz;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedSandstone;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedSlabs;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedSlabs2;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedStainedBlock;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedStone;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedStoneBrick;
import net.geforcemods.securitycraft.itemblocks.ItemBlockReinforcedWoodSlabs;
import net.geforcemods.securitycraft.misc.SCManualPage;
import net.geforcemods.securitycraft.network.packets.PacketCPlaySoundAtPos;
import net.geforcemods.securitycraft.network.packets.PacketCRequestTEOwnableUpdate;
import net.geforcemods.securitycraft.network.packets.PacketCSetPlayerPositionAndRotation;
import net.geforcemods.securitycraft.network.packets.PacketCUpdateNBTTag;
import net.geforcemods.securitycraft.network.packets.PacketGivePotionEffect;
import net.geforcemods.securitycraft.network.packets.PacketSAddModules;
import net.geforcemods.securitycraft.network.packets.PacketSCheckPassword;
import net.geforcemods.securitycraft.network.packets.PacketSMountCamera;
import net.geforcemods.securitycraft.network.packets.PacketSOpenGui;
import net.geforcemods.securitycraft.network.packets.PacketSRemoveCameraTag;
import net.geforcemods.securitycraft.network.packets.PacketSSetCameraRotation;
import net.geforcemods.securitycraft.network.packets.PacketSSetOwner;
import net.geforcemods.securitycraft.network.packets.PacketSSetPassword;
import net.geforcemods.securitycraft.network.packets.PacketSSyncTENBTTag;
import net.geforcemods.securitycraft.network.packets.PacketSToggleOption;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateNBTTag;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateSliderValue;
import net.geforcemods.securitycraft.network.packets.PacketSUpdateTEOwnable;
import net.geforcemods.securitycraft.network.packets.PacketSetBlock;
import net.geforcemods.securitycraft.network.packets.PacketSetExplosiveState;
import net.geforcemods.securitycraft.network.packets.PacketSetISType;
import net.geforcemods.securitycraft.network.packets.PacketSetKeycardLevel;
import net.geforcemods.securitycraft.network.packets.PacketUpdateLogger;
import net.geforcemods.securitycraft.tileentity.TileEntityAlarm;
import net.geforcemods.securitycraft.tileentity.TileEntityCageTrap;
import net.geforcemods.securitycraft.tileentity.TileEntityClaymore;
import net.geforcemods.securitycraft.tileentity.TileEntityIMS;
import net.geforcemods.securitycraft.tileentity.TileEntityInventoryScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityKeycardReader;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypad;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadFurnace;
import net.geforcemods.securitycraft.tileentity.TileEntityLaserBlock;
import net.geforcemods.securitycraft.tileentity.TileEntityLogger;
import net.geforcemods.securitycraft.tileentity.TileEntityMotionLight;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.tileentity.TileEntityPortableRadar;
import net.geforcemods.securitycraft.tileentity.TileEntityProtecto;
import net.geforcemods.securitycraft.tileentity.TileEntityRetinalScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityScannerDoor;
import net.geforcemods.securitycraft.tileentity.TileEntitySecretSign;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.geforcemods.securitycraft.tileentity.TileEntityTrackMine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegistrationHandler
{
	private static ItemStack[] harmingPotions = {PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HARMING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HARMING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.HARMING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.STRONG_HARMING)};
	private static ItemStack[] healingPotions = {PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HEALING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.HEALING),
			PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.STRONG_HEALING)};

	public static void registerContent()
	{
		registerBlock(SCContent.laserBlock);
		GameRegistry.registerBlock(SCContent.laserField, SCContent.laserField.getUnlocalizedName().substring(5));
		registerBlock(SCContent.keypad);
		registerBlock(SCContent.mine);
		GameRegistry.registerBlock(SCContent.mineCut,SCContent.mineCut.getUnlocalizedName().substring(5));
		registerBlock(SCContent.dirtMine);
		GameRegistry.registerBlock(SCContent.stoneMine, SCContent.stoneMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.cobblestoneMine, SCContent.cobblestoneMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.diamondOreMine, SCContent.diamondOreMine.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.sandMine, SCContent.sandMine.getUnlocalizedName().substring(5));
		registerBlock(SCContent.furnaceMine);
		registerBlock(SCContent.retinalScanner);
		GameRegistry.registerBlock(SCContent.reinforcedDoor, SCContent.reinforcedDoor.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.bogusLava, SCContent.bogusLava.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.bogusLavaFlowing, SCContent.bogusLavaFlowing.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.bogusWater, SCContent.bogusWater.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.bogusWaterFlowing, SCContent.bogusWaterFlowing.getUnlocalizedName().substring(5));
		registerBlock(SCContent.keycardReader);
		registerBlock(SCContent.ironTrapdoor);
		registerBlock(SCContent.bouncingBetty);
		registerBlock(SCContent.inventoryScanner);
		GameRegistry.registerBlock(SCContent.inventoryScannerField, SCContent.inventoryScannerField.getUnlocalizedName().substring(5));
		registerBlock(SCContent.trackMine);
		registerBlock(SCContent.cageTrap);
		registerBlock(SCContent.portableRadar);
		registerReinforcedBlock(SCContent.reinforcedStone, ItemBlockReinforcedStone.class);
		registerBlock(SCContent.keypadChest);
		registerBlock(SCContent.usernameLogger);
		registerBlock(SCContent.alarm);
		GameRegistry.registerBlock(SCContent.alarmLit, SCContent.alarmLit.getUnlocalizedName().substring(5));
		registerReinforcedBlock(SCContent.reinforcedIronBars);
		registerReinforcedBlock(SCContent.reinforcedSandstone, ItemBlockReinforcedSandstone.class);
		registerReinforcedBlock(SCContent.reinforcedDirt);
		registerReinforcedBlock(SCContent.reinforcedCobblestone);
		registerBlock(SCContent.reinforcedFencegate);
		registerReinforcedBlock(SCContent.reinforcedWoodPlanks, ItemBlockReinforcedPlanks.class);
		registerBlock(SCContent.panicButton);
		registerBlock(SCContent.frame);
		registerBlock(SCContent.claymore);
		registerBlock(SCContent.keypadFurnace);
		registerBlock(SCContent.securityCamera);
		GameRegistry.registerBlock(SCContent.reinforcedStairsOak, SCContent.reinforcedStairsOak.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsSpruce, SCContent.reinforcedStairsSpruce.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsCobblestone, SCContent.reinforcedStairsCobblestone.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsSandstone, SCContent.reinforcedStairsSandstone.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsBirch, SCContent.reinforcedStairsBirch.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsJungle, SCContent.reinforcedStairsJungle.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsAcacia, SCContent.reinforcedStairsAcacia.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(SCContent.reinforcedStairsDarkoak, SCContent.reinforcedStairsDarkoak.getUnlocalizedName().substring(5));
		registerBlock(SCContent.reinforcedStairsStone);
		registerBlock(SCContent.ironFence);
		registerBlock(SCContent.ims);
		registerReinforcedBlock(SCContent.reinforcedGlass);
		registerBlock(SCContent.reinforcedStainedGlass, ItemBlockReinforcedStainedBlock.class);
		registerBlock(SCContent.reinforcedWoodSlabs, ItemBlockReinforcedWoodSlabs.class);
		GameRegistry.registerBlock(SCContent.reinforcedDoubleWoodSlabs, SCContent.reinforcedDoubleWoodSlabs.getUnlocalizedName().substring(5));
		registerBlock(SCContent.reinforcedStoneSlabs, ItemBlockReinforcedSlabs.class);
		GameRegistry.registerBlock(SCContent.reinforcedDoubleStoneSlabs, SCContent.reinforcedDoubleStoneSlabs.getUnlocalizedName().substring(5));
		registerBlock(SCContent.protecto);
		GameRegistry.registerBlock(SCContent.scannerDoor, SCContent.scannerDoor.getUnlocalizedName().substring(5));
		registerReinforcedBlock(SCContent.reinforcedStoneBrick, ItemBlockReinforcedStoneBrick.class);
		registerBlock(SCContent.reinforcedStairsStoneBrick);
		registerReinforcedBlock(SCContent.reinforcedMossyCobblestone);
		registerReinforcedBlock(SCContent.reinforcedBrick);
		registerBlock(SCContent.reinforcedStairsBrick);
		registerReinforcedBlock(SCContent.reinforcedNetherBrick);
		registerBlock(SCContent.reinforcedStairsNetherBrick);
		registerReinforcedBlock(SCContent.reinforcedHardenedClay);
		registerBlock(SCContent.reinforcedStainedHardenedClay, ItemBlockReinforcedStainedBlock.class);
		registerReinforcedBlock(SCContent.reinforcedOldLogs, ItemBlockReinforcedLog.class);
		registerReinforcedBlock(SCContent.reinforcedNewLogs, ItemBlockReinforcedLog.class);
		registerReinforcedBlock(SCContent.reinforcedMetals, ItemBlockReinforcedMetals.class);
		registerReinforcedBlock(SCContent.reinforcedCompressedBlocks, ItemBlockReinforcedCompressedBlocks.class);
		registerReinforcedBlock(SCContent.reinforcedWool, ItemBlockReinforcedStainedBlock.class);
		registerReinforcedBlock(SCContent.reinforcedQuartz, ItemBlockReinforcedQuartz.class);
		registerBlock(SCContent.reinforcedStairsQuartz);
		registerReinforcedBlock(SCContent.reinforcedPrismarine, ItemBlockReinforcedPrismarine.class);
		registerReinforcedBlock(SCContent.reinforcedRedSandstone, ItemBlockReinforcedSandstone.class);
		registerBlock(SCContent.reinforcedStairsRedSandstone);
		registerReinforcedBlock(SCContent.reinforcedStoneSlabs2, ItemBlockReinforcedSlabs2.class); //technically not a reinforced block, but doesn't need a page
		GameRegistry.registerBlock(SCContent.reinforcedDoubleStoneSlabs2, SCContent.reinforcedDoubleStoneSlabs2.getUnlocalizedName().substring(5));
		registerReinforcedBlock(SCContent.reinforcedEndStoneBricks);
		registerReinforcedBlock(SCContent.reinforcedRedNetherBrick);
		registerReinforcedBlock(SCContent.reinforcedPurpur, ItemBlockReinforcedPurpur.class);
		registerBlock(SCContent.reinforcedStairsPurpur);
		GameRegistry.registerBlock(SCContent.secretSignWall, "secretSignWall");
		GameRegistry.registerBlock(SCContent.secretSignStanding, "secretSignStanding");
		registerBlock(SCContent.motionActivatedLight);
		registerReinforcedBlock(SCContent.reinforcedObsidian);
		registerReinforcedBlock(SCContent.reinforcedNetherrack);
		registerReinforcedBlock(SCContent.reinforcedEndStone);
		registerReinforcedBlock(SCContent.reinforcedSeaLantern);
		registerReinforcedBlock(SCContent.reinforcedBoneBlock);
		GameRegistry.registerBlock(SCContent.reinforcedGlassPane, "securitycraft:reinforced_glass_pane");
		registerBlock(SCContent.reinforcedStainedGlassPanes, "securitycraft:reinforced_stained_panes", ItemBlockReinforcedStainedBlock.class);
		registerReinforcedBlock(SCContent.reinforcedCarpet, ItemBlockReinforcedStainedBlock.class);
		registerReinforcedBlock(SCContent.reinforcedGlowstone);
		GameRegistry.registerBlock(SCContent.gravelMine, SCContent.gravelMine.getUnlocalizedName().substring(5));

		registerItem(SCContent.codebreaker);
		registerItem(SCContent.reinforcedDoorItem, SCContent.reinforcedDoorItem.getUnlocalizedName().substring(5));
		registerItem(SCContent.scannerDoorItem, SCContent.scannerDoorItem.getUnlocalizedName().substring(5));
		registerItem(SCContent.universalBlockRemover);
		registerItem(SCContent.keycardLvl1, SecurityCraft.config.ableToCraftKeycard1);
		registerItem(SCContent.keycardLvl2, SecurityCraft.config.ableToCraftKeycard2);
		registerItem(SCContent.keycardLvl3, SecurityCraft.config.ableToCraftKeycard3);
		registerItem(SCContent.keycardLvl4, SecurityCraft.config.ableToCraftKeycard4);
		registerItem(SCContent.keycardLvl5, SecurityCraft.config.ableToCraftKeycard5);
		registerItem(SCContent.limitedUseKeycard, SecurityCraft.config.ableToCraftLUKeycard);
		registerItem(SCContent.remoteAccessMine);
		registerItemWithCustomRecipe(SCContent.fWaterBucket, new ItemStack[]{ null, harmingPotions[0], null, null, new ItemStack(Items.WATER_BUCKET, 1), null, null, null, null});
		registerItemWithCustomRecipe(SCContent.fLavaBucket, new ItemStack[]{ null, healingPotions[0], null, null, new ItemStack(Items.LAVA_BUCKET, 1), null, null, null, null});
		registerItem(SCContent.universalBlockModifier);
		registerItem(SCContent.redstoneModule);
		registerItem(SCContent.whitelistModule);
		registerItem(SCContent.blacklistModule);
		registerItem(SCContent.harmingModule);
		registerItem(SCContent.smartModule);
		registerItem(SCContent.storageModule);
		registerItem(SCContent.disguiseModule);
		registerItem(SCContent.wireCutters);
		registerItem(SCContent.adminTool);
		registerItem(SCContent.keyPanel);
		registerItem(SCContent.cameraMonitor);
		registerItem(SCContent.taser);
		registerItem(SCContent.scManual);
		registerItem(SCContent.universalOwnerChanger);
		registerItem(SCContent.universalBlockReinforcerLvL1);
		registerItem(SCContent.universalBlockReinforcerLvL2);
		registerItem(SCContent.universalBlockReinforcerLvL3);
		registerItem(SCContent.briefcase);
		registerItem(SCContent.universalKeyChanger);
		GameRegistry.registerItem(SCContent.taserPowered, "taserPowered"); //won't show up in the manual
		registerItem(SCContent.secretSignItem);
	}

	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityOwnable.class, "abstractOwnable");
		GameRegistry.registerTileEntity(TileEntitySCTE.class, "abstractSC");
		GameRegistry.registerTileEntity(TileEntityKeypad.class, "keypad");
		GameRegistry.registerTileEntity(TileEntityLaserBlock.class, "laserBlock");
		GameRegistry.registerTileEntity(TileEntityCageTrap.class, "cageTrap");
		GameRegistry.registerTileEntity(TileEntityKeycardReader.class, "keycardReader");
		GameRegistry.registerTileEntity(TileEntityInventoryScanner.class, "inventoryScanner");
		GameRegistry.registerTileEntity(TileEntityPortableRadar.class, "portableRadar");
		GameRegistry.registerTileEntity(TileEntitySecurityCamera.class, "securityCamera");
		GameRegistry.registerTileEntity(TileEntityLogger.class, "usernameLogger");
		GameRegistry.registerTileEntity(TileEntityRetinalScanner.class, "retinalScanner");
		GameRegistry.registerTileEntity(TileEntityKeypadChest.class, "keypadChest");
		GameRegistry.registerTileEntity(TileEntityAlarm.class, "alarm");
		GameRegistry.registerTileEntity(TileEntityClaymore.class, "claymore");
		GameRegistry.registerTileEntity(TileEntityKeypadFurnace.class, "keypadFurnace");
		GameRegistry.registerTileEntity(TileEntityIMS.class, "ims");
		GameRegistry.registerTileEntity(TileEntityProtecto.class, "protecto");
		GameRegistry.registerTileEntity(CustomizableSCTE.class, "customizableSCTE");
		GameRegistry.registerTileEntity(TileEntityScannerDoor.class, "scannerDoor");
		GameRegistry.registerTileEntity(TileEntitySecretSign.class, "secretSign");
		GameRegistry.registerTileEntity(TileEntityMotionLight.class, "motionLight");
		GameRegistry.registerTileEntity(TileEntityTrackMine.class, "trackMineSC");
	}

	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(SCContent.keyPanel, 1), "III", "IBI", "III", 'I', Blocks.STONE_BUTTON, 'B', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.frame, 1), "III", "IBI", "I I", 'I', Items.IRON_INGOT, 'B', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(SCContent.laserBlock, 1), "III", "IBI", "IPI", 'I', Blocks.STONE, 'B', Blocks.REDSTONE_BLOCK, 'P', Blocks.GLASS_PANE);
		GameRegistry.addRecipe(new ItemStack(SCContent.mine, 3), " I ", "IBI", 'I', Items.IRON_INGOT, 'B', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedDoorItem, 1), "III", "IDI", "III", 'I', Items.IRON_INGOT, 'D', Items.IRON_DOOR);
		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockRemover, 1), "SII",'I', Items.IRON_INGOT, 'S', Items.SHEARS);
		GameRegistry.addRecipe(new ItemStack(SCContent.ironTrapdoor, 1), "###", "#P#", "###", '#', Items.IRON_INGOT, 'P', Blocks.TRAPDOOR);
		GameRegistry.addRecipe(new ItemStack(SCContent.keycardReader, 1), "SSS", "SHS", "SSS", 'S', Blocks.STONE, 'H', Blocks.HOPPER);
		GameRegistry.addRecipe(new ItemStack(SCContent.bouncingBetty, 1), " P ", "IBI", 'I', Items.IRON_INGOT, 'B', Items.GUNPOWDER, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.codebreaker, 1), "DTD", "GSG", "RER", 'D', Items.DIAMOND, 'T', Blocks.REDSTONE_TORCH, 'G', Items.GOLD_INGOT, 'S', Items.NETHER_STAR, 'R', Items.REDSTONE, 'E', Items.EMERALD);

		if(SecurityCraft.config.ableToCraftKeycard1)
			GameRegistry.addRecipe(new ItemStack(SCContent.keycardLvl1, 1), "III", "YYY", 'I', Items.IRON_INGOT, 'Y', Items.GOLD_INGOT);

		if(SecurityCraft.config.ableToCraftKeycard2)
			GameRegistry.addRecipe(new ItemStack(SCContent.keycardLvl2, 1), "III", "YYY", 'I', Items.IRON_INGOT, 'Y', Items.BRICK);

		if(SecurityCraft.config.ableToCraftKeycard3)
			GameRegistry.addRecipe(new ItemStack(SCContent.keycardLvl3, 1), "III", "YYY", 'I', Items.IRON_INGOT, 'Y', Items.NETHERBRICK);

		if(SecurityCraft.config.ableToCraftKeycard4)
			GameRegistry.addRecipe(new ItemStack(SCContent.keycardLvl4, 1), "III", "DDD", 'I', Items.IRON_INGOT, 'D', new ItemStack(Items.DYE, 1, 13));

		if(SecurityCraft.config.ableToCraftKeycard5)
			GameRegistry.addRecipe(new ItemStack(SCContent.keycardLvl5, 1), "III", "DDD", 'I', Items.IRON_INGOT, 'D', new ItemStack(Items.DYE, 1, 5));

		if(SecurityCraft.config.ableToCraftLUKeycard)
			GameRegistry.addRecipe(new ItemStack(SCContent.limitedUseKeycard, 1), "III", "LLL", 'I', Items.IRON_INGOT, 'L', new ItemStack(Items.DYE, 1, 4));

		GameRegistry.addRecipe(new ItemStack(SCContent.trackMine, 4), "X X", "X#X", "XGX", 'X', Items.IRON_INGOT, '#', Items.STICK, 'G', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(SCContent.portableRadar, 1), "III", "ITI", "IRI", 'I', Items.IRON_INGOT, 'T', Blocks.REDSTONE_TORCH, 'R', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(SCContent.remoteAccessMine, 1), " R ", " DG", "S  ", 'R', Blocks.REDSTONE_TORCH, 'D', Items.DIAMOND, 'G', Items.GOLD_INGOT, 'S', Items.STICK);

		for(int i = 0; i < 4; i++)
		{
			GameRegistry.addRecipe(new ItemStack(SCContent.fWaterBucket, 1), "P", "B", 'P', harmingPotions[i], 'B', Items.WATER_BUCKET);
			GameRegistry.addRecipe(new ItemStack(SCContent.fLavaBucket, 1), "P", "B", 'P', healingPotions[i], 'B', Items.LAVA_BUCKET);
		}

		GameRegistry.addRecipe(new ItemStack(SCContent.retinalScanner, 1), "SSS", "SES", "SSS", 'S', Blocks.STONE, 'E', Items.ENDER_EYE);
		GameRegistry.addRecipe(new ItemStack(SCContent.inventoryScanner, 1), "SSS", "SLS", "SCS", 'S', Blocks.STONE, 'L', SCContent.laserBlock, 'C', Blocks.ENDER_CHEST);
		GameRegistry.addRecipe(new ItemStack(SCContent.cageTrap, 1), "BBB", "GRG", "III", 'B', SCContent.reinforcedIronBars, 'G', Items.GOLD_INGOT, 'R', Items.REDSTONE, 'I', Blocks.IRON_BLOCK);
		GameRegistry.addRecipe(new ItemStack(SCContent.alarm, 1), "GGG", "GNG", "GRG", 'G', SCContent.reinforcedGlass, 'R', Items.REDSTONE, 'N', Blocks.NOTEBLOCK);

		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.ACACIA_FENCE_GATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.BIRCH_FENCE_GATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.DARK_OAK_FENCE_GATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.JUNGLE_FENCE_GATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.OAK_FENCE_GATE);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedFencegate, 1)," I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.SPRUCE_FENCE_GATE);

		GameRegistry.addRecipe(new ItemStack(SCContent.wireCutters, 1), "SI ", "I I", " I ", 'I', Items.IRON_INGOT, 'S', Items.SHEARS);
		GameRegistry.addRecipe(new ItemStack(SCContent.panicButton, 1), " I ", "IBI", " R ", 'I', Items.IRON_INGOT, 'B', Blocks.STONE_BUTTON, 'R', Items.REDSTONE);

		GameRegistry.addRecipe(new ItemStack(SCContent.whitelistModule, 1), "III", "IPI", "IPI", 'I', Items.IRON_INGOT, 'P', Items.PAPER);
		GameRegistry.addRecipe(new ItemStack(SCContent.blacklistModule, 1), "III", "IPI", "IDI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'D', new ItemStack(Items.DYE, 1, 0));
		GameRegistry.addRecipe(new ItemStack(SCContent.redstoneModule, 1), "III", "IPI", "IRI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'R', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(SCContent.harmingModule, 1), "III", "IPI", "IAI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'A', Items.ARROW);
		GameRegistry.addRecipe(new ItemStack(SCContent.smartModule, 1), "III", "IPI", "IEI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'E', Items.ENDER_PEARL);
		GameRegistry.addRecipe(new ItemStack(SCContent.storageModule, 1), "III", "IPI", "ICI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'C', SCContent.keypadChest);
		GameRegistry.addRecipe(new ItemStack(SCContent.disguiseModule, 1), "III", "IPI", "IAI", 'I', Items.IRON_INGOT, 'P', Items.PAPER, 'A', Items.PAINTING);

		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockModifier, 1), "ER ", "RI ", "  I", 'E', Items.EMERALD, 'R', Items.REDSTONE, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockModifier, 1), " RE", " IR", "I  ", 'E', Items.EMERALD, 'R', Items.REDSTONE, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(SCContent.usernameLogger, 1), "SPS", "SRS", "SSS", 'S', Blocks.STONE, 'P', SCContent.portableRadar, 'R', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(SCContent.claymore, 1), "HSH", "SBS", "RGR", 'H', Blocks.TRIPWIRE_HOOK, 'S', Items.STRING, 'B', SCContent.bouncingBetty, 'R', Items.REDSTONE, 'G', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(SCContent.ironFence, 1), " I ", "IFI", " I ", 'I', Items.IRON_INGOT, 'F', Blocks.OAK_FENCE);

		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsStone, 4), "S  ", "SS ", "SSS", 'S', new ItemStack(SCContent.reinforcedStone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsCobblestone, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedCobblestone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsSandstone, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedSandstone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsOak, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 0));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsSpruce, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 1));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsBirch, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 2));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsJungle, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 3));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsAcacia, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 4));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsDarkoak, 4), "W  ", "WW ", "WWW", 'W', new ItemStack(SCContent.reinforcedWoodPlanks, 1, 5));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsStoneBrick, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedStoneBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsBrick, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsNetherBrick, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedNetherBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsQuartz, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedQuartz);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsRedSandstone, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedRedSandstone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStairsPurpur, 4), "S  ", "SS ", "SSS", 'S', SCContent.reinforcedPurpur);

		GameRegistry.addRecipe(new ItemStack(SCContent.ims, 1), "BPB", " I ", "B B", 'B', SCContent.bouncingBetty, 'P', SCContent.portableRadar, 'I', Blocks.IRON_BLOCK);
		GameRegistry.addRecipe(new ItemStack(SCContent.cameraMonitor, 1), "III", "IGI", "III", 'I', Items.IRON_INGOT, 'G', Blocks.GLASS_PANE);
		GameRegistry.addRecipe(new ItemStack(SCContent.taser, 1), "BGI", "RSG", "  S", 'B', Items.BOW, 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(SCContent.securityCamera, 1), "III", "GRI", "IIS", 'I', Items.IRON_INGOT, 'G', SCContent.reinforcedGlass, 'R', Blocks.REDSTONE_BLOCK, 'S', Items.STICK);

		for(int i = 0; i < 16; i++)
		{
			GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStainedGlass, 8, 15 - i), "###", "#X#", "###", '#', new ItemStack(SCContent.reinforcedGlass), 'X', new ItemStack(Items.DYE, 1, i));
			GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStainedGlassPanes, 16, i), "###", "###", '#', new ItemStack(SCContent.reinforcedStainedGlass, 1, i));
			GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStainedHardenedClay, 8, ~i & 15), "###", "#X#", "###", '#', new ItemStack(SCContent.reinforcedHardenedClay), 'X', new ItemStack(Items.DYE, 1, i));
			GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedCarpet, 3, i), "##", '#', new ItemStack(SCContent.reinforcedWool, 1, i));
		}

		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockReinforcerLvL1, 1), " DG", "RLD", "SR ", 'G', Blocks.GLASS, 'D', Items.DIAMOND, 'L', SCContent.laserBlock, 'R', Items.REDSTONE, 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockReinforcerLvL2, 1), " DG", "RLD", "SR ", 'G', new ItemStack(SCContent.reinforcedStainedGlass, 1, 15), 'D', Blocks.DIAMOND_BLOCK, 'L', SCContent.laserBlock, 'R', Items.REDSTONE, 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(SCContent.universalBlockReinforcerLvL3, 1), " EG", "RNE", "SR ", 'G', new ItemStack(SCContent.reinforcedStainedGlass, 1, 6), 'E', Blocks.EMERALD_BLOCK, 'N', Items.NETHER_STAR, 'R', Blocks.REDSTONE_BLOCK, 'S', Items.STICK);

		for(int i = 0; i < 6; i++)
		{
			GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedWoodSlabs, 6, i), "MMM", 'M', new ItemStack(SCContent.reinforcedWoodPlanks, 1, i));
		}

		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 0), "MMM", 'M', new ItemStack(SCContent.reinforcedStone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 1), "MMM", 'M', SCContent.reinforcedCobblestone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 2), "MMM", 'M', SCContent.reinforcedSandstone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 3), "MMM", 'M', SCContent.reinforcedStoneBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 4), "MMM", 'M', SCContent.reinforcedBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 5), "MMM", 'M', SCContent.reinforcedNetherBrick);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs, 6, 6), "MMM", 'M', SCContent.reinforcedQuartz);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs2, 6, 0), "MMM", 'M', SCContent.reinforcedRedSandstone);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStoneSlabs2, 6, 1), "MMM", 'M', SCContent.reinforcedPurpur);

		GameRegistry.addRecipe(new ItemStack(SCContent.protecto, 1), "ODO", "OEO", "OOO", 'O', Blocks.OBSIDIAN, 'D', Blocks.DAYLIGHT_DETECTOR, 'E', Items.ENDER_EYE);
		GameRegistry.addRecipe(new ItemStack(SCContent.briefcase, 1), "SSS", "ICI", "III", 'S', Items.STICK, 'I', Items.IRON_INGOT, 'C', SCContent.keypadChest);
		GameRegistry.addRecipe(new ItemStack(SCContent.universalKeyChanger, 1), " RL", " IR", "I  ", 'R', Items.REDSTONE, 'L', SCContent.laserBlock, 'I', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(SCContent.motionActivatedLight, 1), "L", "R", "S", 'L', Blocks.REDSTONE_LAMP, 'R', SCContent.portableRadar, 'S', Items.STICK);

		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStone, 2, 3), "CQ", "QC", 'C', SCContent.reinforcedCobblestone, 'Q', Items.QUARTZ);
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStone, 4, 2), "GG", "GG", 'G', new ItemStack(SCContent.reinforcedStone, 1, 1));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStone, 4, 4), "DD", "DD", 'D', new ItemStack(SCContent.reinforcedStone, 1, 3));
		GameRegistry.addRecipe(new ItemStack(SCContent.reinforcedStone, 4, 6), "AA", "AA", 'A', new ItemStack(SCContent.reinforcedStone, 1, 5));

		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.dirtMine, 1), new Object[] {Blocks.DIRT, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.stoneMine, 1), new Object[] {Blocks.STONE, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.cobblestoneMine, 1), new Object[] {Blocks.COBBLESTONE, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.diamondOreMine, 1), new Object[] {Blocks.DIAMOND_ORE, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.sandMine, 1), new Object[] {Blocks.SAND, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.furnaceMine, 1), new Object[] {Blocks.FURNACE, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.gravelMine, 1), new Object[] {Blocks.GRAVEL, SCContent.mine});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.universalOwnerChanger, 1), new Object[] {SCContent.universalBlockModifier, Items.NAME_TAG});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.scannerDoorItem, 1), new Object[]{SCContent.reinforcedDoorItem, SCContent.retinalScanner});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.secretSignItem, 3), new Object[]{Items.SIGN, Items.SIGN, Items.SIGN, SCContent.retinalScanner});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.universalKeyChanger), new Object[]{SCContent.briefcase, SCContent.universalKeyChanger});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.reinforcedMossyCobblestone), new Object[]{SCContent.reinforcedCobblestone, Blocks.VINE});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.reinforcedStone, 1, 1), new Object[]{new ItemStack(SCContent.reinforcedStone, 1, 3), Items.QUARTZ});
		GameRegistry.addShapelessRecipe(new ItemStack(SCContent.reinforcedStone, 2, 5), new Object[]{new ItemStack(SCContent.reinforcedStone, 1, 3), Blocks.COBBLESTONE});
	}

	public static void registerEntities()
	{
		EntityRegistry.registerModEntity(EntityBouncingBetty.class, "BBetty", 0, SecurityCraft.instance, 128, 1, true);
		EntityRegistry.registerModEntity(EntityTaserBullet.class, "TazerBullet", 2, SecurityCraft.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntityIMSBomb.class, "IMSBomb", 3, SecurityCraft.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntitySecurityCamera.class, "SecurityCamera", 4, SecurityCraft.instance, 256, 20, false);
	}

	public static void registerPackets(SimpleNetworkWrapper network)
	{
		network.registerMessage(PacketSetBlock.Handler.class, PacketSetBlock.class, 1, Side.SERVER);
		network.registerMessage(PacketSetISType.Handler.class, PacketSetISType.class, 2, Side.SERVER);
		network.registerMessage(PacketSetKeycardLevel.Handler.class, PacketSetKeycardLevel.class, 3, Side.SERVER);
		network.registerMessage(PacketUpdateLogger.Handler.class, PacketUpdateLogger.class, 4, Side.CLIENT);
		network.registerMessage(PacketCUpdateNBTTag.Handler.class, PacketCUpdateNBTTag.class, 5, Side.CLIENT);
		network.registerMessage(PacketSUpdateNBTTag.Handler.class, PacketSUpdateNBTTag.class, 6, Side.SERVER);
		network.registerMessage(PacketCPlaySoundAtPos.Handler.class, PacketCPlaySoundAtPos.class, 7, Side.CLIENT);
		network.registerMessage(PacketSetExplosiveState.Handler.class, PacketSetExplosiveState.class, 8, Side.SERVER);
		network.registerMessage(PacketGivePotionEffect.Handler.class, PacketGivePotionEffect.class, 9, Side.SERVER);
		network.registerMessage(PacketSSetOwner.Handler.class, PacketSSetOwner.class, 10, Side.SERVER);
		network.registerMessage(PacketSAddModules.Handler.class, PacketSAddModules.class, 11, Side.SERVER);
		network.registerMessage(PacketSSetPassword.Handler.class, PacketSSetPassword.class, 12, Side.SERVER);
		network.registerMessage(PacketSCheckPassword.Handler.class, PacketSCheckPassword.class, 13, Side.SERVER);
		network.registerMessage(PacketSSyncTENBTTag.Handler.class, PacketSSyncTENBTTag.class, 14, Side.SERVER);
		network.registerMessage(PacketSMountCamera.Handler.class, PacketSMountCamera.class, 15, Side.SERVER);
		network.registerMessage(PacketSSetCameraRotation.Handler.class, PacketSSetCameraRotation.class, 16, Side.SERVER);
		network.registerMessage(PacketCSetPlayerPositionAndRotation.Handler.class, PacketCSetPlayerPositionAndRotation.class, 17, Side.CLIENT);
		network.registerMessage(PacketSOpenGui.Handler.class, PacketSOpenGui.class, 18, Side.SERVER);
		network.registerMessage(PacketSToggleOption.Handler.class, PacketSToggleOption.class, 19, Side.SERVER);
		network.registerMessage(PacketCRequestTEOwnableUpdate.Handler.class, PacketCRequestTEOwnableUpdate.class, 20, Side.SERVER);
		network.registerMessage(PacketSUpdateTEOwnable.Handler.class, PacketSUpdateTEOwnable.class, 21, Side.CLIENT);
		network.registerMessage(PacketSUpdateSliderValue.Handler.class, PacketSUpdateSliderValue.class, 22, Side.SERVER);
		network.registerMessage(PacketSRemoveCameraTag.Handler.class, PacketSRemoveCameraTag.class, 23, Side.SERVER);
	}

	@SideOnly(Side.CLIENT)
	public static void registerResourceLocations()
	{
		//Blocks
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.keypad), 0, new ModelResourceLocation("securitycraft:keypad", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.frame), 0, new ModelResourceLocation("securitycraft:keypadFrame", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 0, new ModelResourceLocation("securitycraft:reinforcedStone_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 1, new ModelResourceLocation("securitycraft:reinforcedStone_granite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 2, new ModelResourceLocation("securitycraft:reinforcedStone_smooth_granite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 3, new ModelResourceLocation("securitycraft:reinforcedStone_diorite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 4, new ModelResourceLocation("securitycraft:reinforcedStone_smooth_diorite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 5, new ModelResourceLocation("securitycraft:reinforcedStone_andesite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStone), 6, new ModelResourceLocation("securitycraft:reinforcedStone_smooth_andesite", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.laserBlock), 0, new ModelResourceLocation("securitycraft:laserBlock", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.laserField), 0, new ModelResourceLocation("securitycraft:laser", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.keypadChest), 0, new ModelResourceLocation("securitycraft:keypadChest", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedDoor), 0, new ModelResourceLocation("securitycraft:reinforcedIronDoor", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.ironTrapdoor), 0, new ModelResourceLocation("securitycraft:reinforcedIronTrapdoor", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.keycardReader), 0, new ModelResourceLocation("securitycraft:keycardReader", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.inventoryScanner), 0, new ModelResourceLocation("securitycraft:inventoryScanner", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.cageTrap), 0, new ModelResourceLocation("securitycraft:cageTrap", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.inventoryScannerField), 0, new ModelResourceLocation("securitycraft:inventoryScannerField", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.retinalScanner), 0, new ModelResourceLocation("securitycraft:retinalScanner", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedIronBars), 0, new ModelResourceLocation("securitycraft:reinforcedIronBars", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.portableRadar), 0, new ModelResourceLocation("securitycraft:portableRadar", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.alarm), 0, new ModelResourceLocation("securitycraft:alarm", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.alarmLit), 0, new ModelResourceLocation("securitycraft:alarmLit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.usernameLogger), 0, new ModelResourceLocation("securitycraft:usernameLogger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedFencegate), 0, new ModelResourceLocation("securitycraft:reinforcedFenceGate", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.ironFence), 0, new ModelResourceLocation("securitycraft:electrifiedIronFence", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 0, new ModelResourceLocation("securitycraft:reinforcedPlanks_Oak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 1, new ModelResourceLocation("securitycraft:reinforcedPlanks_Spruce", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 2, new ModelResourceLocation("securitycraft:reinforcedPlanks_Birch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 3, new ModelResourceLocation("securitycraft:reinforcedPlanks_Jungle", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 4, new ModelResourceLocation("securitycraft:reinforcedPlanks_Acacia", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodPlanks), 5, new ModelResourceLocation("securitycraft:reinforcedPlanks_DarkOak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsStone), 0, new ModelResourceLocation("securitycraft:reinforcedStairsStone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsCobblestone), 0, new ModelResourceLocation("securitycraft:reinforcedStairsCobblestone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsOak), 0, new ModelResourceLocation("securitycraft:reinforcedStairsOak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsSpruce), 0, new ModelResourceLocation("securitycraft:reinforcedStairsSpruce", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsBirch), 0, new ModelResourceLocation("securitycraft:reinforcedStairsBirch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsJungle), 0, new ModelResourceLocation("securitycraft:reinforcedStairsJungle", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsAcacia), 0, new ModelResourceLocation("securitycraft:reinforcedStairsAcacia", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsDarkoak), 0, new ModelResourceLocation("securitycraft:reinforcedStairsDarkoak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedGlass), 0, new ModelResourceLocation("securitycraft:reinforcedGlassBlock", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 0, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_white", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 1, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_orange", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 2, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_magenta", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 3, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_light_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 4, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_yellow", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 5, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_lime", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 6, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_pink", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 7, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_gray", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 8, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_silver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 9, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_cyan", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 10, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_purple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 11, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 12, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_brown", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 13, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_green", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 14, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_red", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlass), 15, new ModelResourceLocation("securitycraft:reinforcedStainedGlass_black", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.keypadChest), 0, new ModelResourceLocation("securitycraft:keypadChest", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.keypadFurnace), 0, new ModelResourceLocation("securitycraft:keypadFurnace", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.panicButton), 0, new ModelResourceLocation("securitycraft:panicButton", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.securityCamera), 0, new ModelResourceLocation("securitycraft:securityCamera", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedDirt), 0, new ModelResourceLocation("securitycraft:reinforcedDirt", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCobblestone), 0, new ModelResourceLocation("securitycraft:reinforcedCobblestone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedSandstone), 0, new ModelResourceLocation("securitycraft:reinforcedSandstone_normal", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedSandstone), 1, new ModelResourceLocation("securitycraft:reinforcedSandstone_chiseled", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedSandstone), 2, new ModelResourceLocation("securitycraft:reinforcedSandstone_smooth", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 0, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_oak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 1, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_spruce", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 2, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_birch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 3, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_jungle", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 4, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_acacia", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWoodSlabs), 5, new ModelResourceLocation("securitycraft:reinforcedWoodSlabs_darkoak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsCobblestone), 0, new ModelResourceLocation("securitycraft:reinforcedStairsCobblestone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsSandstone), 0, new ModelResourceLocation("securitycraft:reinforcedStairsSandstone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 0, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_stone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 1, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_cobblestone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 2, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_sandstone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 3, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_stonebrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 4, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_brick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 5, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_netherbrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs), 6, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs_quartz", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs2), 0, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs2_red_sandstone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneSlabs2), 1, new ModelResourceLocation("securitycraft:reinforcedStoneSlabs2_purpur", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.protecto), 0, new ModelResourceLocation("securitycraft:protecto", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.scannerDoor), 0, new ModelResourceLocation("securitycraft:scannerDoor", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneBrick), 0, new ModelResourceLocation("securitycraft:reinforcedStoneBrick_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneBrick), 1, new ModelResourceLocation("securitycraft:reinforcedStoneBrick_mossy", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneBrick), 2, new ModelResourceLocation("securitycraft:reinforcedStoneBrick_cracked", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStoneBrick), 3, new ModelResourceLocation("securitycraft:reinforcedStoneBrick_chiseled", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsStoneBrick), 0, new ModelResourceLocation("securitycraft:reinforcedStairsStoneBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedMossyCobblestone), 0, new ModelResourceLocation("securitycraft:reinforcedMossyCobblestone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedBrick), 0, new ModelResourceLocation("securitycraft:reinforcedBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsBrick), 0, new ModelResourceLocation("securitycraft:reinforcedStairsBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedNetherBrick), 0, new ModelResourceLocation("securitycraft:reinforcedNetherBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsNetherBrick), 0, new ModelResourceLocation("securitycraft:reinforcedStairsNetherBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedHardenedClay), 0, new ModelResourceLocation("securitycraft:reinforcedHardenedClay", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 0, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_white", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 1, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_orange", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 2, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_magenta", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 3, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_light_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 4, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_yellow", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 5, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_lime", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 6, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_pink", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 7, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_gray", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 8, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_silver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 9, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_cyan", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 10, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_purple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 11, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 12, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_brown", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 13, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_green", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 14, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_red", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedHardenedClay), 15, new ModelResourceLocation("securitycraft:reinforcedStainedHardenedClay_black", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedOldLogs), 0, new ModelResourceLocation("securitycraft:reinforcedLogs_oak", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedOldLogs), 1, new ModelResourceLocation("securitycraft:reinforcedLogs_spruce", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedOldLogs), 2, new ModelResourceLocation("securitycraft:reinforcedLogs_birch", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedOldLogs), 3, new ModelResourceLocation("securitycraft:reinforcedLogs_jungle", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedNewLogs), 0, new ModelResourceLocation("securitycraft:reinforcedLogs2_acacia", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedNewLogs), 1, new ModelResourceLocation("securitycraft:reinforcedLogs2_big_oak", "inventory"));;
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedMetals), 0, new ModelResourceLocation("securitycraft:reinforcedMetals_gold", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedMetals), 1, new ModelResourceLocation("securitycraft:reinforcedMetals_iron", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedMetals), 2, new ModelResourceLocation("securitycraft:reinforcedMetals_diamond", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedMetals), 3, new ModelResourceLocation("securitycraft:reinforcedMetals_emerald", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCompressedBlocks), 0, new ModelResourceLocation("securitycraft:reinforcedCompressedBlocks_lapis", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCompressedBlocks), 1, new ModelResourceLocation("securitycraft:reinforcedCompressedBlocks_coal", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 0, new ModelResourceLocation("securitycraft:reinforcedWool_white", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 1, new ModelResourceLocation("securitycraft:reinforcedWool_orange", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 2, new ModelResourceLocation("securitycraft:reinforcedWool_magenta", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 3, new ModelResourceLocation("securitycraft:reinforcedWool_light_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 4, new ModelResourceLocation("securitycraft:reinforcedWool_yellow", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 5, new ModelResourceLocation("securitycraft:reinforcedWool_lime", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 6, new ModelResourceLocation("securitycraft:reinforcedWool_pink", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 7, new ModelResourceLocation("securitycraft:reinforcedWool_gray", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 8, new ModelResourceLocation("securitycraft:reinforcedWool_silver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 9, new ModelResourceLocation("securitycraft:reinforcedWool_cyan", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 10, new ModelResourceLocation("securitycraft:reinforcedWool_purple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 11, new ModelResourceLocation("securitycraft:reinforcedWool_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 12, new ModelResourceLocation("securitycraft:reinforcedWool_brown", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 13, new ModelResourceLocation("securitycraft:reinforcedWool_green", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 14, new ModelResourceLocation("securitycraft:reinforcedWool_red", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedWool), 15, new ModelResourceLocation("securitycraft:reinforcedWool_black", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedQuartz), 0, new ModelResourceLocation("securitycraft:reinforcedQuartz_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedQuartz), 1, new ModelResourceLocation("securitycraft:reinforcedQuartz_chiseled", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedQuartz), 2, new ModelResourceLocation("securitycraft:reinforcedQuartz_pillar", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsQuartz), 0, new ModelResourceLocation("securitycraft:reinforcedStairsQuartz", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedPrismarine), 0, new ModelResourceLocation("securitycraft:reinforcedPrismarine_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedPrismarine), 1, new ModelResourceLocation("securitycraft:reinforcedPrismarine_bricks", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedPrismarine), 2, new ModelResourceLocation("securitycraft:reinforcedPrismarine_dark", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedRedSandstone), 0, new ModelResourceLocation("securitycraft:reinforcedRedSandstone_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedRedSandstone), 1, new ModelResourceLocation("securitycraft:reinforcedRedSandstone_chiseled", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedRedSandstone), 2, new ModelResourceLocation("securitycraft:reinforcedRedSandstone_smooth", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsRedSandstone), 0, new ModelResourceLocation("securitycraft:reinforcedStairsRedSandstone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedEndStoneBricks), 0, new ModelResourceLocation("securitycraft:reinforcedEndStoneBricks", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedRedNetherBrick), 0, new ModelResourceLocation("securitycraft:reinforcedRedNetherBrick", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedPurpur), 0, new ModelResourceLocation("securitycraft:reinforcedPurpur_default", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedPurpur), 1, new ModelResourceLocation("securitycraft:reinforcedPurpur_pillar", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStairsPurpur), 0, new ModelResourceLocation("securitycraft:reinforcedStairsPurpur", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.motionActivatedLight), 0, new ModelResourceLocation("securitycraft:motionActivatedLight", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedObsidian), 0, new ModelResourceLocation("securitycraft:reinforcedObsidian", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedNetherrack), 0, new ModelResourceLocation("securitycraft:reinforcedNetherrack", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedEndStone), 0, new ModelResourceLocation("securitycraft:reinforcedEndStone", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedSeaLantern), 0, new ModelResourceLocation("securitycraft:reinforcedSeaLantern", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedBoneBlock), 0, new ModelResourceLocation("securitycraft:reinforcedBoneBlock", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedGlassPane), 0, new ModelResourceLocation("securitycraft:reinforced_glass_pane", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 0, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_white", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 1, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_orange", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 2, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_magenta", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 3, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_light_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 4, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_yellow", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 5, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_lime", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 6, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_pink", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 7, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_gray", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 8, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_silver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 9, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_cyan", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 10, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_purple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 11, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 12, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_brown", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 13, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_green", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 14, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_red", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedStainedGlassPanes), 15, new ModelResourceLocation("securitycraft:reinforced_stained_glass_panes_black", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 0, new ModelResourceLocation("securitycraft:reinforcedCarpet_white", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 1, new ModelResourceLocation("securitycraft:reinforcedCarpet_orange", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 2, new ModelResourceLocation("securitycraft:reinforcedCarpet_magenta", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 3, new ModelResourceLocation("securitycraft:reinforcedCarpet_light_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 4, new ModelResourceLocation("securitycraft:reinforcedCarpet_yellow", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 5, new ModelResourceLocation("securitycraft:reinforcedCarpet_lime", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 6, new ModelResourceLocation("securitycraft:reinforcedCarpet_pink", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 7, new ModelResourceLocation("securitycraft:reinforcedCarpet_gray", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 8, new ModelResourceLocation("securitycraft:reinforcedCarpet_silver", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 9, new ModelResourceLocation("securitycraft:reinforcedCarpet_cyan", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 10, new ModelResourceLocation("securitycraft:reinforcedCarpet_purple", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 11, new ModelResourceLocation("securitycraft:reinforcedCarpet_blue", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 12, new ModelResourceLocation("securitycraft:reinforcedCarpet_brown", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 13, new ModelResourceLocation("securitycraft:reinforcedCarpet_green", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 14, new ModelResourceLocation("securitycraft:reinforcedCarpet_red", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedCarpet), 15, new ModelResourceLocation("securitycraft:reinforcedCarpet_black", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.reinforcedGlowstone), 0, new ModelResourceLocation("securitycraft:reinforcedGlowstone", "inventory"));

		//Items
		ModelLoader.setCustomModelResourceLocation(SCContent.codebreaker, 0, new ModelResourceLocation("securitycraft:codebreaker", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.remoteAccessMine, 0, new ModelResourceLocation("securitycraft:remoteAccessMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.reinforcedDoorItem, 0, new ModelResourceLocation("securitycraft:doorIndestructibleIronItem", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.fWaterBucket, 0, new ModelResourceLocation("securitycraft:bucketFWater", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.fLavaBucket, 0, new ModelResourceLocation("securitycraft:bucketFLava", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keycardLvl1, 0, new ModelResourceLocation("securitycraft:keycardLV1", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keycardLvl2, 0, new ModelResourceLocation("securitycraft:keycardLV2", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keycardLvl3, 0, new ModelResourceLocation("securitycraft:keycardLV3", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keycardLvl4, 0, new ModelResourceLocation("securitycraft:keycardLV4", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keycardLvl5, 0, new ModelResourceLocation("securitycraft:keycardLV5", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.limitedUseKeycard, 0, new ModelResourceLocation("securitycraft:limitedUseKeycard", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalBlockRemover, 0, new ModelResourceLocation("securitycraft:universalBlockRemover", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalBlockModifier, 0, new ModelResourceLocation("securitycraft:universalBlockModifier", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.whitelistModule, 0, new ModelResourceLocation("securitycraft:whitelistModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.blacklistModule, 0, new ModelResourceLocation("securitycraft:blacklistModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.redstoneModule, 0, new ModelResourceLocation("securitycraft:redstoneModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.harmingModule, 0, new ModelResourceLocation("securitycraft:harmingModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.storageModule, 0, new ModelResourceLocation("securitycraft:storageModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.smartModule, 0, new ModelResourceLocation("securitycraft:smartModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.disguiseModule, 0, new ModelResourceLocation("securitycraft:disguiseModule", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.wireCutters, 0, new ModelResourceLocation("securitycraft:wireCutters", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.keyPanel, 0, new ModelResourceLocation("securitycraft:keypadItem", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.adminTool, 0, new ModelResourceLocation("securitycraft:adminTool", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.cameraMonitor, 0, new ModelResourceLocation("securitycraft:cameraMonitor", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.scManual, 0, new ModelResourceLocation("securitycraft:scManual", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.taser, 0, new ModelResourceLocation("securitycraft:taser", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.taserPowered, 0, new ModelResourceLocation("securitycraft:taser_powered", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalOwnerChanger, 0, new ModelResourceLocation("securitycraft:universalOwnerChanger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalBlockReinforcerLvL1, 0, new ModelResourceLocation("securitycraft:universalBlockReinforcerLvL1", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalBlockReinforcerLvL2, 0, new ModelResourceLocation("securitycraft:universalBlockReinforcerLvL2", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalBlockReinforcerLvL3, 0, new ModelResourceLocation("securitycraft:universalBlockReinforcerLvL3", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.briefcase, 0, new ModelResourceLocation("securitycraft:briefcase", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.universalKeyChanger, 0, new ModelResourceLocation("securitycraft:universalKeyChanger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.scannerDoorItem, 0, new ModelResourceLocation("securitycraft:scannerDoorItem", "inventory"));
		ModelLoader.setCustomModelResourceLocation(SCContent.secretSignItem, 0, new ModelResourceLocation("securitycraft:secret_sign_item", "inventory"));

		//Mines
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.mine), 0, new ModelResourceLocation("securitycraft:mine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.dirtMine), 0, new ModelResourceLocation("securitycraft:dirtMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.stoneMine), 0, new ModelResourceLocation("securitycraft:stoneMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.cobblestoneMine), 0, new ModelResourceLocation("securitycraft:cobblestoneMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.sandMine), 0, new ModelResourceLocation("securitycraft:sandMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.diamondOreMine), 0, new ModelResourceLocation("securitycraft:diamondMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.furnaceMine), 0, new ModelResourceLocation("securitycraft:furnaceMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.trackMine), 0, new ModelResourceLocation("securitycraft:trackMine", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.bouncingBetty), 0, new ModelResourceLocation("securitycraft:bouncingBetty", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.claymore), 0, new ModelResourceLocation("securitycraft:claymore", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.ims), 0, new ModelResourceLocation("securitycraft:ims", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SCContent.gravelMine), 0, new ModelResourceLocation("securitycraft:gravelMine", "inventory"));
	}

	/**
	 * Registers the given block with GameRegistry.registerBlock(), and adds the help info for the block to the SecurityCraft manual item.
	 */
	private static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));

		SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info"));
	}

	private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
	{
		registerBlock(block, block.getUnlocalizedName().substring(5), itemClass);
	}

	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> itemClass)
	{
		GameRegistry.registerBlock(block, itemClass, name);

		SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help." + block.getUnlocalizedName().substring(5) + ".info"));
	}

	static boolean hasReinforcedPage = false;

	private static void registerReinforcedBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));

		if(!hasReinforcedPage)
		{
			SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help.securitycraft:reinforced.info"));
			hasReinforcedPage = true;
		}
	}

	private static void registerReinforcedBlock(Block block, Class<? extends ItemBlock> itemClass)
	{
		GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName().substring(5));

		if(!hasReinforcedPage)
		{
			SecurityCraft.instance.manualPages.add(new SCManualPage(Item.getItemFromBlock(block), "help.securitycraft:reinforced.info"));
			hasReinforcedPage = true;
		}
	}

	/**
	 * Registers the given item with GameRegistry.registerItem(), and adds the help info for the item to the SecurityCraft manual item.
	 */
	private static void registerItem(Item item)
	{
		registerItem(item, item.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the given item with GameData.register_implItem(), and adds the help info for the item to the SecurityCraft manual item.
	 * Additionally, a configuration value can be set to have this item's recipe show as disabled in the manual.
	 */
	private static void registerItem(Item item, boolean configValue)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		SecurityCraft.instance.manualPages.add(new SCManualPage(item, "help." + item.getUnlocalizedName().substring(5) + ".info", configValue));
	}

	/**
	 * Registers the given item with GameRegistry.registerItem(), and adds the help info for the item to the SecurityCraft manual item.
	 */
	private static void registerItem(Item item, String customName)
	{
		GameRegistry.registerItem(item, customName);

		SecurityCraft.instance.manualPages.add(new SCManualPage(item, "help." + item.getUnlocalizedName().substring(5) + ".info"));
	}

	/**
	 * Registers the given item with GameRegistry.registerItem(), and adds the help info for the item to the SecurityCraft manual item.
	 * Also overrides the default recipe that would've been drawn in the manual with a new recipe.
	 */
	private static void registerItemWithCustomRecipe(Item item, ItemStack... customRecipe)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));

		SecurityCraft.instance.manualPages.add(new SCManualPage(item, "help." + item.getUnlocalizedName().substring(5) + ".info", customRecipe));
	}
}
