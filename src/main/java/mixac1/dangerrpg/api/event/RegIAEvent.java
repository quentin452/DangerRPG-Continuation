package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.capability.data.*;

public class RegIAEvent extends Event
{
    public Item item;
    public RPGItemRegister.RPGItemData map;
    
    protected RegIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
        this.item = item;
        this.map = map;
    }
    
    public static class DefaultIAEvent extends RegIAEvent
    {
        public DefaultIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemModIAEvent extends RegIAEvent
    {
        public ItemModIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemSwordIAEvent extends RegIAEvent
    {
        public ItemSwordIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemToolIAEvent extends RegIAEvent
    {
        public ItemToolIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemArmorIAEvent extends RegIAEvent
    {
        public ItemArmorIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemBowIAEvent extends RegIAEvent
    {
        public ItemBowIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemGunIAEvent extends RegIAEvent
    {
        public ItemGunIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
    
    public static class ItemStaffIAEvent extends RegIAEvent
    {
        public ItemStaffIAEvent(final Item item, final RPGItemRegister.RPGItemData map) {
            super(item, map);
        }
    }
}
