package quek.undergarden.item;

import net.minecraft.item.Item;
import quek.undergarden.registry.UndergardenItemGroups;

public class UndergardenItem extends Item {
    public UndergardenItem() {
        super(new Properties()
                .group(UndergardenItemGroups.UNDERGARDEN_ITEMS));
    }
}
