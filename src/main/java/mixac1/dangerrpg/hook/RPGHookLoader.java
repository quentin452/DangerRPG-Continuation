package mixac1.dangerrpg.hook;

import mixac1.dangerrpg.hook.core.*;
import mixac1.dangerrpg.hook.mod.etfuturumrequiem.HookFixEntityAttributesETFUTURUM;
import mixac1.dangerrpg.hook.mod.golemtweaks.HookFixEntityAttributesGolemTweaks;
import mixac1.dangerrpg.hook.mod.witchery.HookFixEntityAttributesWitchery;
import mixac1.hooklib.minecraft.HookLoader;
import mixac1.hooklib.minecraft.PrimaryClassTransformer;

public class RPGHookLoader extends HookLoader {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { PrimaryClassTransformer.class.getName() };
    }

    @Override
    public void registerHooks() {
        // Vanilla hooks

        registerHookContainer(HookItems.class.getName());
        registerHookContainer(HookEntities.class.getName());
        registerHookContainer(HookArmorSystem.class.getName());
        registerHookContainer(HookItemBow.class.getName());
        registerHookContainer(HookFixEntityMotion.class.getName());
        registerHookContainer(HookFixEntityAttributes.class.getName());

        // Modded hooks

        registerHookContainer(HookFixEntityAttributesETFUTURUM.class.getName());
        registerHookContainer(HookFixEntityAttributesGolemTweaks.class.getName());
        registerHookContainer(HookFixEntityAttributesWitchery.class.getName());

    }
}
