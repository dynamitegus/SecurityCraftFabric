package net.geforcemods.securitycraft.screen;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;

import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.containers.GenericTEContainer;
import net.geforcemods.securitycraft.network.server.CheckPassword;
import net.geforcemods.securitycraft.screen.components.ClickButton;
import net.geforcemods.securitycraft.util.ClientUtils;
import net.geforcemods.securitycraft.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class CheckPasswordScreen extends ContainerScreen<GenericTEContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("securitycraft:textures/gui/container/blank.png");
	private TileEntity tileEntity;
	private char[] allowedChars = {'0', '1', '2', '3', '4', '5', '6' ,'7' ,'8', '9', '\u0008', '\u001B'}; //0-9, backspace and escape
	private String blockName;
	private TextFieldWidget keycodeTextbox;
	private String currentString = "";
	private static final int MAX_CHARS = 11;

	public CheckPasswordScreen(GenericTEContainer container, PlayerInventory inv, ITextComponent name){
		super(container, inv, name);
		this.tileEntity = container.te;
		blockName = ClientUtils.localize(tileEntity.getBlockState().getBlock().getTranslationKey());
	}

	@Override
	public void func_231160_c_(){
		super.func_231160_c_();
		field_230706_i_.keyboardListener.enableRepeatEvents(true);

		func_230480_a_(new ClickButton(0, field_230708_k_ / 2 - 38, field_230709_l_ / 2 + 30 + 10, 80, 20, "0", this::actionPerformed));
		func_230480_a_(new ClickButton(1, field_230708_k_ / 2 - 38, field_230709_l_ / 2 - 60 + 10, 20, 20, "1", this::actionPerformed));
		func_230480_a_(new ClickButton(2, field_230708_k_ / 2 - 8, field_230709_l_ / 2 - 60 + 10, 20, 20, "2", this::actionPerformed));
		func_230480_a_(new ClickButton(3, field_230708_k_ / 2 + 22, field_230709_l_ / 2 - 60 + 10, 20, 20, "3", this::actionPerformed));
		func_230480_a_(new ClickButton(4, field_230708_k_ / 2 - 38, field_230709_l_ / 2 - 30 + 10, 20, 20, "4", this::actionPerformed));
		func_230480_a_(new ClickButton(5, field_230708_k_ / 2 - 8, field_230709_l_ / 2 - 30 + 10, 20, 20, "5", this::actionPerformed));
		func_230480_a_(new ClickButton(6, field_230708_k_ / 2 + 22, field_230709_l_ / 2 - 30 + 10, 20, 20, "6", this::actionPerformed));
		func_230480_a_(new ClickButton(7, field_230708_k_ / 2 - 38, field_230709_l_ / 2 + 10, 20, 20, "7", this::actionPerformed));
		func_230480_a_(new ClickButton(8, field_230708_k_ / 2 - 8, field_230709_l_ / 2 + 10, 20, 20, "8", this::actionPerformed));
		func_230480_a_(new ClickButton(9, field_230708_k_ / 2 + 22, field_230709_l_ / 2 + 10, 20, 20, "9", this::actionPerformed));
		func_230480_a_(new ClickButton(10, field_230708_k_ / 2 + 48, field_230709_l_ / 2 + 30 + 10, 25, 20, "<-", this::actionPerformed));

		keycodeTextbox = new TextFieldWidget(field_230712_o_, field_230708_k_ / 2 - 37, field_230709_l_ / 2 - 67, 77, 12, "");

		keycodeTextbox.setTextColor(-1);
		keycodeTextbox.setDisabledTextColour(-1);
		keycodeTextbox.setEnableBackgroundDrawing(true);
		keycodeTextbox.setMaxStringLength(MAX_CHARS);
		keycodeTextbox.setFocused2(true);
	}

	@Override
	public void func_231175_as__(){
		super.func_231175_as__();
		field_230706_i_.keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
		super.render(mouseX, mouseY, partialTicks);
		RenderSystem.disableLighting();
		keycodeTextbox.render(mouseX, mouseY, partialTicks);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		field_230712_o_.drawString(blockName, xSize / 2 - field_230712_o_.getStringWidth(blockName) / 2, 6, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		func_230446_a_();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		field_230706_i_.getTextureManager().bindTexture(TEXTURE);
		int startX = (field_230708_k_ - xSize) / 2;
		int startY = (field_230709_l_ - ySize) / 2;
		this.blit(startX, startY, 0, 0, xSize, ySize);
	}

	@Override
	public boolean func_231046_a_(int keyCode, int scanCode, int modifiers)
	{
		if(keyCode == GLFW.GLFW_KEY_BACKSPACE && currentString.length() > 0){
			Minecraft.getInstance().player.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("random.click")), 0.15F, 1.0F);
			currentString = Utils.removeLastChar(currentString);
			setTextboxCensoredText(keycodeTextbox, currentString);
			checkCode(currentString);
			return true;
		}

		return super.func_231046_a_(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean func_231042_a_(char typedChar, int keyCode) {
		if(isValidChar(typedChar) && currentString.length() < MAX_CHARS){
			Minecraft.getInstance().player.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("random.click")), 0.15F, 1.0F);
			currentString += typedChar;
			setTextboxCensoredText(keycodeTextbox, currentString);
			checkCode(currentString);
		}
		else
			return super.func_231042_a_(typedChar, keyCode);
		return true;
	}

	private boolean isValidChar(char c) {
		for(int i = 0; i < allowedChars.length; i++)
			if(c == allowedChars[i])
				return true;
			else
				continue;

		return false;
	}

	protected void actionPerformed(ClickButton button){
		if (currentString.length() < MAX_CHARS) {
			if(button.id >= 0 && button.id <= 9) {
				currentString += "" + button.id;
				setTextboxCensoredText(keycodeTextbox, currentString);
				checkCode(currentString);
			}
		}

		if(button.id == 10 && currentString.length() > 0)
		{
			currentString = Utils.removeLastChar(currentString);
			setTextboxCensoredText(keycodeTextbox, currentString);
		}
	}

	private void setTextboxCensoredText(TextFieldWidget textField, String text) {
		String x = "";
		for(int i = 1; i <= text.length(); i++)
			x += "*";

		textField.setText(x);
	}

	public void checkCode(String code) {
		SecurityCraft.channel.sendToServer(new CheckPassword(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), code));
	}
}
