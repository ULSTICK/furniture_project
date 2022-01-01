package com.ulstick.sticksdeco.common.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ulstick.sticksdeco.SticksDeco;
import com.ulstick.sticksdeco.common.tileentityclass.ShelfContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShelfScreen extends ContainerScreen<ShelfContainer> {
    private final ResourceLocation GUI = new ResourceLocation(SticksDeco.MOD_ID,
            "textures/gui/container/shelf.png");

    public ShelfScreen(ShelfContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    public void render(MatrixStack matrixStack, int mX, int mY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mX, mY, partialTicks);
        this.renderTooltip(matrixStack, mX, mY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.getGuiLeft();
        int j = this.getGuiTop();
        this.blit(matrixStack, i, j, 0, 0, this.getXSize(), this.getYSize());


    }
}