package mixac1.dangerrpg.item;

public class RPGItemComponent {

    public static final RPGItemComponent NULL;
    public static final RPGToolComponent SWORD;
    public static final RPGToolComponent NAGINATA;
    public static final RPGToolComponent KATANA;
    public static final RPGToolComponent SCYTHE;
    public static final RPGToolComponent HAMMER;
    public static final RPGToolComponent TOMAHAWK;
    public static final RPGToolComponent KNIFE;
    public static final RPGStaffComponent STAFF;
    public static final RPGStaffComponent POWER_STAFF;
    public static final RPGToolComponent AXE;
    public static final RPGToolComponent PICKAXE;
    public static final RPGToolComponent SHOVEL;
    public static final RPGToolComponent HOE;
    public static final RPGToolComponent MULTITOOL;
    public static final RPGBowComponent BOW;
    public static final RPGBowComponent SHADOW_BOW;
    public static final RPGBowComponent SNIPER_BOW;
    public static final RPGArmorComponent ARMOR;
    public static final RPGArmorComponent MAGE_ARMOR;
    public String name;

    static {
        NULL = new RPGItemComponent();
        SWORD = new RPGToolComponent("sword");
        NAGINATA = new RPGToolComponent("naginata");
        KATANA = new RPGToolComponent("katana");
        SCYTHE = new RPGToolComponent("scythe");
        HAMMER = new RPGToolComponent("hammer");
        TOMAHAWK = new RPGToolComponent("tomahawk");
        KNIFE = new RPGToolComponent("knife");
        STAFF = new RPGStaffComponent("staff");
        POWER_STAFF = new RPGStaffComponent("power_staff");
        AXE = new RPGToolComponent("axe");
        PICKAXE = new RPGToolComponent("pickaxe");
        SHOVEL = new RPGToolComponent("shovel");
        HOE = new RPGToolComponent("hoe");
        MULTITOOL = new RPGToolComponent("multitool");
        BOW = new RPGBowComponent("bow");
        SHADOW_BOW = new RPGBowComponent("shadow_bow");
        SNIPER_BOW = new RPGBowComponent("sniper_bow");
        ARMOR = new RPGArmorComponent("armor");
        MAGE_ARMOR = new RPGArmorComponent("mage_armor");
        RPGItemComponent.SWORD.init(4.0f, 10.0f, 0.5f, 0.1f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.NAGINATA.init(4.0f, 12.0f, 0.5f, 0.08f, 0.25f, 0.5f, 0.075f, 1.5f);
        RPGItemComponent.KATANA.init(3.5f, 9.0f, 0.45f, 0.11f, 0.25f, 0.0f, 0.025f, 0.0f);
        RPGItemComponent.SCYTHE.init(5.0f, 15.0f, 0.65f, 0.07f, 0.25f, 0.5f, 0.075f, 1.0f);
        RPGItemComponent.HAMMER.init(6.0f, 20.0f, 0.8f, 0.03f, 0.25f, 1.0f, 0.12f, 0.0f);
        RPGItemComponent.TOMAHAWK.init(3.5f, 9.5f, 0.3f, 0.11f, 0.25f, 0.0f, 0.0f, 0.0f);
        RPGItemComponent.KNIFE.init(1.0f, 7.5f, 0.2f, 0.125f, 0.25f, 0.0f, 0.0f, 0.0f);
        RPGItemComponent.AXE.init(3.0f, 11.0f, 0.55f, 0.08f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.PICKAXE.init(2.0f, 10.0f, 0.3f, 0.08f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.SHOVEL.init(1.0f, 10.0f, 0.3f, 0.08f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.HOE.init(1.0f, 10.0f, 0.4f, 0.08f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.MULTITOOL.init(1.0f, 10.0f, 0.4f, 0.08f, 0.25f, 0.0f, 0.05f, 0.0f);
        RPGItemComponent.STAFF.init(1.0f, 13.0f, 0.1f, 0.07f, 0.5f, 0.0f, 0.075f, 0.5f, 2.0f, 10.0f, 1.0f, 2.0f);
        RPGItemComponent.POWER_STAFF.init(1.0f, 13.0f, 0.1f, 0.07f, 0.5f, 0.0f, 0.075f, 0.5f, 2.0f, 10.0f, 1.0f, 3.0f);
        RPGItemComponent.BOW
            .init(1.0f, 10.0f, 0.16f, 0.1f, 0.25f, 0.0f, 0.05f, 0.0f, 2.0f, 20.0f, 0.4f, 3.0f, -0.0f, 3.0f);
        RPGItemComponent.SHADOW_BOW
            .init(4.0f, 10.0f, 0.16f, 0.1f, 0.25f, 0.0f, 0.05f, 0.0f, 4.0f, 16.0f, 0.2f, 4.0f, 4000.0f, 14.0f);
        RPGItemComponent.SNIPER_BOW
            .init(1.0f, 10.0f, 0.16f, 0.1f, 0.25f, 1.0f, 0.1f, 0.0f, 10.0f, 40.0f, 0.8f, 9.0f, 10000.0f, 22.0f);
        RPGItemComponent.ARMOR.init(1.0f, 0.5f);
        RPGItemComponent.MAGE_ARMOR.init(0.5f, 1.0f);
    }

    public static class RPGToolComponent extends RPGItemComponent {

        public float meleeDamage;
        public float meleeSpeed;
        public float strMul;
        public float agiMul;
        public float intMul;
        public float knBack;
        public float knbMul;
        public float reach;

        public RPGToolComponent(final String name) {
            this.name = name;
        }

        protected void init(final float meleeDamage, final float meleeSpeed, final float strMul, final float agiMul,
            final float intMul, final float knBack, final float knbMul, final float reach) {
            this.meleeDamage = meleeDamage;
            this.meleeSpeed = meleeSpeed;
            this.strMul = strMul;
            this.agiMul = agiMul;
            this.intMul = intMul;
            this.knBack = knBack;
            this.knbMul = knbMul;
            this.reach = reach;
        }
    }

    public static class RPGGunComponent extends RPGToolComponent {

        public float shotDamage;
        public float shotSpeed;
        public float shotMinCastTime;

        public RPGGunComponent(final String name) {
            super(name);
        }

        protected void init(final float meleeDamage, final float meleeSpeed, final float strMul, final float agiMul,
            final float intMul, final float knBack, final float knbMul, final float reach, final float shotDamage,
            final float shotSpeed, final float shotMinCastTime) {
            super.init(meleeDamage, meleeSpeed, strMul, agiMul, intMul, knBack, knbMul, reach);
            this.shotDamage = shotDamage;
            this.shotSpeed = shotSpeed;
            this.shotMinCastTime = shotMinCastTime;
        }
    }

    public static class RPGStaffComponent extends RPGGunComponent {

        public float needMana;

        public RPGStaffComponent(final String name) {
            super(name);
        }

        protected void init(final float meleeDamage, final float meleeSpeed, final float strMul, final float agiMul,
            final float intMul, final float knBack, final float knbMul, final float reach, final float shotDamage,
            final float shotSpeed, final float shotMinPower, final float needMana) {
            super.init(
                meleeDamage,
                meleeSpeed,
                strMul,
                agiMul,
                intMul,
                knBack,
                knbMul,
                reach,
                shotDamage,
                shotSpeed,
                shotMinPower);
            this.needMana = needMana;
        }
    }

    public static class RPGBowComponent extends RPGGunComponent implements IWithoutToolMaterial {

        private RPGICWithoutTM itemComponent;
        public float shotPower;

        public RPGBowComponent(final String name) {
            super(name);
            this.itemComponent = new RPGICWithoutTM();
        }

        protected void init(final float meleeDamage, final float meleeSpeed, final float strMul, final float agiMul,
            final float intMul, final float knBack, final float knbMul, final float reach, final float shotDamage,
            final float shotSpeed, final float shotMinPower, final float shotPower, final float durab,
            final float ench) {
            super.init(
                meleeDamage,
                meleeSpeed,
                strMul,
                agiMul,
                intMul,
                knBack,
                knbMul,
                reach,
                shotDamage,
                shotSpeed,
                shotMinPower);
            this.itemComponent.init(durab, ench);
            this.shotPower = shotPower;
        }

        @Override
        public float getMaxDurability() {
            return this.itemComponent.durab;
        }

        @Override
        public float getEnchantability() {
            return this.itemComponent.ench;
        }
    }

    public static class RPGArmorComponent extends RPGItemComponent {

        public float phisicalResMul;
        public float magicResMul;

        public RPGArmorComponent(final String name) {
            this.name = name;
        }

        protected void init(final float phisicalResMul, final float magicResMul) {
            this.phisicalResMul = phisicalResMul;
            this.magicResMul = magicResMul;
        }
    }

    public static class RPGICWithoutTM extends RPGItemComponent implements IWithoutToolMaterial {

        public float durab;
        public float ench;

        protected void init(final float durab, final float ench) {
            this.durab = durab;
            this.ench = ench;
        }

        @Override
        public float getMaxDurability() {
            return this.durab;
        }

        @Override
        public float getEnchantability() {
            return this.ench;
        }
    }

    public interface IWithoutToolMaterial {

        float getMaxDurability();

        float getEnchantability();
    }
}
