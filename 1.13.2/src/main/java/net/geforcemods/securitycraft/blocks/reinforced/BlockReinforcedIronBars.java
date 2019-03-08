package net.geforcemods.securitycraft.blocks.reinforced;

import java.util.Arrays;
import java.util.Random;

import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//TODO: delete and instantiate with BlockReinforcedPane
public class BlockReinforcedIronBars extends BlockPane implements IReinforcedBlock {

	public BlockReinforcedIronBars(Material material) {
		super(Block.Properties.create(material).sound(SoundType.METAL).hardnessAndResistance(-1.0F, 6000000.0F));
	}

	@Override
	public void tick(IBlockState state, World world, BlockPos pos, Random random) {
		BlockUtils.setBlock(world, pos, Blocks.IRON_BARS);
	}

	@Override
	public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving)
	{
		super.onReplaced(state, world, pos, newState, isMoving);
		world.removeTileEntity(pos);
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param){
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity != null ? tileentity.receiveClientEvent(id, param) : false;
	}

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, IBlockState state){
		return new ItemStack(asItem());
	}

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune){
		return asItem();
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader reader) {
		return new TileEntityOwnable();
	}

	@Override
	public Block getVanillaBlock()
	{
		return Arrays.asList(new Block[] {
				Blocks.IRON_BARS
		});
	}
}
