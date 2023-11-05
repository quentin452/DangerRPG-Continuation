package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import net.minecraft.item.*;

public class RegIAEvent extends Event {

    public Item item;
    public RPGItemData map;

    protected RegIAEvent(Item item, RPGItemData map) {
        this.item = item;
        this.map = map;
    }

    /**
     * It is fires whenever a {@link Item} registering own default {@link ItemAttribute}
     * and {@link Item} is lvlable item
     */
    public static class DefaultIAEvent extends RegIAEvent {

        public DefaultIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a modified {@link Item} registering own default {@link ItemAttribute}
     * and {@link Item} is lvlable item
     */
    public static class ItemModIAEvent extends RegIAEvent {

        public ItemModIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a {@link ItemSword} registering own default {@link ItemAttribute}
     * and {@link ItemSword} is lvlable item
     */
    public static class ItemSwordIAEvent extends RegIAEvent {

        public ItemSwordIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a {@link ItemTool} registering own default {@link ItemAttribute}
     * and {@link ItemTool} is lvlable item
     */
    public static class ItemToolIAEvent extends RegIAEvent {

        public ItemToolIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a {@link ItemArmor} registering own default {@link ItemAttribute}
     * and {@link ItemArmor} is lvlable item
     */
    public static class ItemArmorIAEvent extends RegIAEvent {

        public ItemArmorIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a {@link ItemBow} registering own default {@link ItemAttribute}
     * and {@link ItemBow} is lvlable item
     */
    public static class ItemBowIAEvent extends RegIAEvent {

        public ItemBowIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a ItemGun (ItemStaff, etc) registering own default {@link ItemAttribute}
     */
    public static class ItemGunIAEvent extends RegIAEvent {

        public ItemGunIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }

    /**
     * It is fires whenever a ItemGun (ItemStaff, etc) registering own default {@link ItemAttribute}
     */
    public static class ItemStaffIAEvent extends RegIAEvent {

        public ItemStaffIAEvent(Item item, RPGItemData map) {
            super(item, map);
        }
    }
}
