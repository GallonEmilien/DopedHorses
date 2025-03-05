package fr.gallonemilien.mixin;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HorseArmorLayer.class)
public abstract class HorseArmorLayerMixin extends RenderLayer<HorseRenderState, HorseModel> {
    public HorseArmorLayerMixin(RenderLayerParent<HorseRenderState, HorseModel> renderLayerParent) {
        super(renderLayerParent);
    }
}
