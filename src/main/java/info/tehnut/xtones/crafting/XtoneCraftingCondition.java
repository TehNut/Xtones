package info.tehnut.xtones.crafting;

import com.google.gson.JsonObject;
import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.XtonesConfig;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public final class XtoneCraftingCondition implements IConditionFactory {
    @Override
    public BooleanSupplier parse(final JsonContext context, final JsonObject json) {
        return XtonesConfig::hasXtoneRecipes;
    }

    @Override
    public String toString() {
        return Xtones.ID + ":recipes_enabled";
    }
}
