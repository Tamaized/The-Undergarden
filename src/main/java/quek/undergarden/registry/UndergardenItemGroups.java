package quek.undergarden.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class UndergardenItemGroups {

    public static final ItemGroup UNDERGARDEN_BLOCKS = new ItemGroup("undergarden_blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistry.depthrock.get());
        }
    };

    public static final ItemGroup UNDERGARDEN_ITEMS = new ItemGroup("undergarden_items") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistry.smogstem_stick.get());
        }
    };
}
