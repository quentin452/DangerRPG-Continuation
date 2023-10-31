package mixac1.dangerrpg.hook;

import mixac1.hooklib.minecraft.*;

public class RPGHookLoader extends HookLoader
{
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { PrimaryClassTransformer.class.getName() };
    }
    
    public void registerHooks() {
        HookLoader.registerHookContainer(HookItems.class.getName());
        HookLoader.registerHookContainer(HookEntities.class.getName());
        HookLoader.registerHookContainer(HookArmorSystem.class.getName());
        HookLoader.registerHookContainer(HookItemBow.class.getName());
        HookLoader.registerHookContainer(HookFixEntityMotion.class.getName());
        HookLoader.registerHookContainer(HookFixEntityAttributes.class.getName());
    }
}
