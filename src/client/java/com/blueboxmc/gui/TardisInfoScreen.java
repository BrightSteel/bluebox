package com.blueboxmc.gui;

import com.blueboxmc.Bluebox;
import com.blueboxmc.entity.TardisEntity;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.List;

public class TardisInfoScreen extends BaseOwoScreen<FlowLayout> {

    private final int ROTATION_FACTOR = 11;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    // TODO - this is just a sample of what this could contain.
    //  allow claiming here, viewing info, unclaiming, admin controls, open monitor screen, etc
    @Override
    protected void build(FlowLayout rootComponent) {
        TardisEntity tardisEntity = new TardisEntity(Bluebox.TARDIS_ENTITY, this.client.world);
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);
        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.label(Text.of("Type 40 TARDIS")).margins(Insets.bottom(5)))
                        .child(Components.label(Text.of(Formatting.BLUE + "Owned by Bright_Steel")))
                        .padding(Insets.of(10))
                        .surface(Surface.VANILLA_TRANSLUCENT)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER));
       rootComponent.child(
                new TransformEntityComponent(Sizing.fixed(70), tardisEntity)
                        .scale(0.18f)
                        .transform(matrixStack -> {
                            matrixStack.multiply(new Quaternionf().rotateY(
                                    (float) tardisEntity.getWorld().getTime() / ROTATION_FACTOR % 360)
                            );
                            matrixStack.translate(0, 0.25, 0);
                        })
                        .tooltip(List.of(
                                Text.of("Sweet baby"),
                                Text.of("X: 124"),
                                Text.of("Y: 100"),
                                Text.of("Z: 1034")
                        ))
        );
        rootComponent.child(
                Containers.horizontalFlow(Sizing.content(), Sizing.content())
                        .child(Components.button(
                                Text.literal(" Cancel "),
                                buttonComponent -> this.close()
                        ).margins(Insets.right(5)))
                        .child(Components.button(
                                Text.literal("  Claim  "),
                                buttonComponent -> {}
                        ).margins(Insets.left(5)))
                        .horizontalAlignment(HorizontalAlignment.CENTER)
                        .padding(Insets.top(5))
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
