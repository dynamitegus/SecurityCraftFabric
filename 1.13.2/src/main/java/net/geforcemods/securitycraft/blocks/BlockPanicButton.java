package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockPanicButton extends BlockButton implements ITileEntityProvider {

	public BlockPanicButton() {
		super(false, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(-1.0F, 6000000.0F));
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		if(state.get(POWERED).booleanValue()){
			BlockUtils.setBlockProperty(world, pos, POWERED, false, true);
			world.markBlockRangeForRenderUpdate(pos, pos);
			notifyNeighbors(world, pos, state.get(FACING));
			return true;
		}else{
			BlockUtils.setBlockProperty(world, pos, POWERED, true, true);
			world.markBlockRangeForRenderUpdate(pos, pos);
			notifyNeighbors(world, pos, state.get(FACING));
			return true;
		}
	}

	private void notifyNeighbors(World world, BlockPos pos, EnumFacing facing)
	{
		world.notifyNeighborsOfStateChange(pos, this);
		world.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param){
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader source, BlockPos pos)
	{
		//		EnumFacing facing = state.getValue(FACING);
		//		boolean isPowered = state.getValue(POWERED).booleanValue();
		//		float height = (isPowered ? 1 : 2) / 16.0F;
		//
		//		switch (BlockPanicButton.SwitchEnumFacing.FACING_LOOKUP[facing.ordinal()])
		//		{
		//			case 1:
		//				return new AxisAlignedBB(0.0F, 0.30F, 0.18F, height, 0.70F, 0.82F);
		//			case 2:
		//				return new AxisAlignedBB(1.0F - height, 0.30F, 0.18F, 1.0F, 0.70F, 0.82F);
		//			case 3:
		//				return new AxisAlignedBB(0.1800F, 0.300F, 0.0F, 0.8150F, 0.700F, height);
		//			case 4:
		//				return new AxisAlignedBB(0.1800F, 0.300F, 1.0F - height, 0.8150F, 0.700F, 1.0F);
		//			case 5:
		//				return new AxisAlignedBB(0.175F, 0.0F, 0.300F, 0.825F, 0.0F + height, 0.700F);
		//			case 6:
		//				return new AxisAlignedBB(0.175F, 1.0F - height, 0.300F, 0.8225F, 1.0F, 0.700F);
		//		}
		//
		//		return super.getBoundingBox(state, source, pos);
		return VoxelShapes.fullCube();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader) {
		return new TileEntityOwnable();
	}

	static final class SwitchEnumFacing
	{
		static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];

		static
		{
			try
			{
				FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 1;
			}
			catch (NoSuchFieldError e)
			{
				;
			}

			try
			{
				FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 2;
			}
			catch (NoSuchFieldError e)
			{
				;
			}

			try
			{
				FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
			}
			catch (NoSuchFieldError e)
			{
				;
			}

			try
			{
				FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 4;
			}
			catch (NoSuchFieldError e)
			{
				;
			}

			try
			{
				FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
			}
			catch (NoSuchFieldError e)
			{
				;
			}

			try
			{
				FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
			}
			catch (NoSuchFieldError e)
			{
				;
			}
		}
	}

	@Override
	protected SoundEvent getSoundEvent(boolean turningOn)
	{
		return turningOn ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
	}
}
