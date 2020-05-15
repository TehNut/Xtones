package tehnut.xtones.crafting;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import tehnut.xtones.config.XtonesConfig;

import java.util.function.BooleanSupplier;

public final class XtoneCraftingCondition implements IConditionFactory {
    @Override
    public BooleanSupplier parse(final JsonContext context, final JsonObject json) {
        return XtonesConfig::hasXtoneRecipes;
    }
}
