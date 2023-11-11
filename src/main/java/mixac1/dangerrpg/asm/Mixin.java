package mixac1.dangerrpg.asm;

import java.util.*;
import java.util.function.Predicate;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mixac1.dangerrpg.config.DangerConfig;

@RequiredArgsConstructor
public enum Mixin implements IMixin {
    client_core_MixinRenderLiving(Side.CLIENT, m -> DangerConfig.enablefixGuiissueswithOptifine,
        "core.MixinRenderLiving"),
    client_core_MixinFixShulkerBulletDamage(Side.COMMON , m -> DangerConfig.enablefixShulkerBulletDamage,
        "etfuturumrequiem.MixinFixShulkerBulletDamage"),

    ;

    @Getter
    public final Side side;
    @Getter
    public final Predicate<List<ITargetedMod>> filter;
    @Getter
    public final String mixin;

    static Predicate<List<ITargetedMod>> require(TargetedMod in) {
        return modList -> modList.contains(in);
    }

    static Predicate<List<ITargetedMod>> avoid(TargetedMod in) {
        return modList -> !modList.contains(in);
    }
}
