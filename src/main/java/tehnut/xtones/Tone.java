package tehnut.xtones;

import java.util.Locale;

public enum Tone {
    AGON,
    AZUR,
    BITT,
    CRAY,
    FORT,
    GLAXX,
    ISZM,
    JELT,
    KORP,
    KRYP,
    LAIR,
    LAVE,
    MINT,
    MYST,
    REDS,
    REED,
    ROEN,
    SOLS,
    SYNC,
    TANK,
    VECT,
    VENA,
    ZANE,
    ZECH,
    ZEST,
    ZETA,
    ZION,
    ZKUL,
    ZOEA,
    ZOME,
    ZONE,
    ZORG,
    ZTYL,
    ZYTH;

    public static final int VARIANTS = 16;

    public final boolean isGlassLike() {
        return this == GLAXX;
    }

    @Override
    public final String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
