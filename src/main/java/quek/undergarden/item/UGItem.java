package quek.undergarden.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import quek.undergarden.registry.UGItemGroups;

import net.minecraft.item.Item.Properties;

public class UGItem extends Item {

    public UGItem() {
        super(new Properties()
                .group(UGItemGroups.GROUP)
        );
    }

    public UGItem(Rarity rarity) {
        super(new Properties()
                .group(UGItemGroups.GROUP)
                .rarity(rarity)
        );
    }

    public UGItem(Food food) {
        super(new Properties()
                .food(food)
                .group(UGItemGroups.GROUP));
    }
}