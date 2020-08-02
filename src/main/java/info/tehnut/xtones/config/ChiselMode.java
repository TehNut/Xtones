package info.tehnut.xtones.config;

public final class ChiselMode {
    /**
     * Chisel the base block into the initial variants
     */
    public static final int BASE_CARVING = 0;

    /**
     * Chisel between variants
     */
    public static final int VARIANT_CARVING = 1;

    /**
     * No chisel functionality
     */
    public static final int NO_CARVING = 2;

    public static final int MIN = BASE_CARVING;
    public static final int MAX = NO_CARVING;
    public static final int DEFAULT = MIN;

    private ChiselMode() {
    }
}
