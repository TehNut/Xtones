package info.tehnut.xtones.item;

import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.client.XtonesClientConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public final class XtonesCreativeTab extends CreativeTabs {
    private static final CreativeTabs INSTANCE = new XtonesCreativeTab();

    private XtonesCreativeTab() {
        super(Xtones.ID);
    }

    public static CreativeTabs instance() {
        return INSTANCE;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Xtones.baseItem());
    }

    @Override
    public String getBackgroundImageName() {
        return XtonesClientConfig.searchableCreativeTab ? "item_search.png" : "items.png";
    }

    @Override
    public boolean hasSearchBar() {
        return XtonesClientConfig.searchableCreativeTab;
    }
}
