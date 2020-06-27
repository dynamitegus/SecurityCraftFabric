package net.geforcemods.securitycraft.containers;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.tileentity.ProjectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProjectorContainer extends Container {

	public static final int SIZE = 1;

	public ProjectorTileEntity te;

	// A custom slot that prevents non-Block items from being inserted into the projector
	private Slot projectedItemSlot;

	public ProjectorContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory) 
	{
		super(SCContent.cTypeProjector, windowId);

		if(world.getTileEntity(pos) instanceof ProjectorTileEntity)
			te = (ProjectorTileEntity) world.getTileEntity(pos);

		for(int i = 0; i < 9; i++)
			addSlot(new Slot(inventory, i, 8 + i * 18, 142));

		projectedItemSlot = new Slot(te, 9, 79, 20) 
		{
			@Override
			public boolean isItemValid(ItemStack stack) 
			{
				return stack.getItem() != null && stack.getItem() instanceof BlockItem;
			}
		};

		addSlot(new Slot(te, 9, 79, 20));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) 
	{
		return true;
	}

}
