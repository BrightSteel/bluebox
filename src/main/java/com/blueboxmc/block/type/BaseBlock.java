package com.blueboxmc.block.type;

import com.blueboxmc.block.BaseSettings;
import com.blueboxmc.block.BlockRegister;
import com.blueboxmc.block.BlueBoxBlocks;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

@Getter @Setter
@Accessors(chain = true)
public class BaseBlock extends Block implements BlockRegister {

    private final String path;
    private VoxelShape shape;
    private boolean isTranslucent, isCutout;

    public BaseBlock(String path) {
        super(BaseSettings.BASIC_SETTINGS);
        this.path = path;
        // auto-register block
        BlueBoxBlocks.addBlockToRegistry(this);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape != null ? shape : super.getOutlineShape(state, world, pos, context);
    }
}
