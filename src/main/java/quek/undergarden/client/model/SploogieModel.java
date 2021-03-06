package quek.undergarden.client.model;
// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import quek.undergarden.entity.cavern.SploogieEntity;

@OnlyIn(Dist.CLIENT)
public class SploogieModel<T extends SploogieEntity> extends SegmentedModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer snout;
	private final ModelRenderer body;
	private final ModelRenderer leftLeg1;
	private final ModelRenderer leftLeg2;
	private final ModelRenderer leftLeg3;
	private final ModelRenderer rightLeg1;
	private final ModelRenderer rightLeg2;
	private final ModelRenderer rightLeg3;

	public SploogieModel() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 20.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(-4.0F, -9.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 20).addBox(1.0F, -9.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, true);

		snout = new ModelRenderer(this);
		snout.setRotationPoint(0.0F, 4.0F, 0.0F);
		head.addChild(snout);
		snout.setTextureOffset(0, 36).addBox(-1.0F, -9.0F, -7.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		snout.setTextureOffset(0, 31).addBox(-2.0F, -10.0F, -8.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(0, 20).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 3.0F, 8.0F, 0.0F, false);

		leftLeg1 = new ModelRenderer(this);
		leftLeg1.setRotationPoint(4.0F, 22.0F, -3.0F);
		setRotationAngle(leftLeg1, 0.0F, 0.0F, 0.6109F);
		leftLeg1.setTextureOffset(30, 8).addBox(-1.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);

		leftLeg2 = new ModelRenderer(this);
		leftLeg2.setRotationPoint(4.0F, 22.0F, -1.0F);
		setRotationAngle(leftLeg2, 0.0F, 0.0F, 0.6109F);
		leftLeg2.setTextureOffset(30, 8).addBox(-1.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);

		leftLeg3 = new ModelRenderer(this);
		leftLeg3.setRotationPoint(4.0F, 22.0F, 1.0F);
		setRotationAngle(leftLeg3, 0.0F, 0.0F, 0.6109F);
		leftLeg3.setTextureOffset(30, 8).addBox(-1.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);

		rightLeg1 = new ModelRenderer(this);
		rightLeg1.setRotationPoint(-4.0F, 22.0F, -3.0F);
		setRotationAngle(rightLeg1, 0.0F, 0.0F, -0.6109F);
		rightLeg1.setTextureOffset(30, 8).addBox(-4.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, true);

		rightLeg2 = new ModelRenderer(this);
		rightLeg2.setRotationPoint(-4.0F, 22.0F, -1.0F);
		setRotationAngle(rightLeg2, 0.0F, 0.0F, -0.6109F);
		rightLeg2.setTextureOffset(30, 8).addBox(-4.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, true);

		rightLeg3 = new ModelRenderer(this);
		rightLeg3.setRotationPoint(-4.0F, 22.0F, 1.0F);
		setRotationAngle(rightLeg3, 0.0F, 0.0F, -0.6109F);
		rightLeg3.setTextureOffset(30, 8).addBox(-4.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);

		this.leftLeg1.rotateAngleZ = 0.6109F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg2.rotateAngleZ = 0.6109F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.leftLeg3.rotateAngleZ = 0.6109F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		this.rightLeg1.rotateAngleZ = -0.6109F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.rightLeg2.rotateAngleZ = -0.6109F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg3.rotateAngleZ = -0.6109F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg3.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg1.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg3.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.head, this.body, this.leftLeg1, this.leftLeg2, this.leftLeg3, this.rightLeg1, this.rightLeg2, this.rightLeg3);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}