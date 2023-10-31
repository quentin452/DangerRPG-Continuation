package mixac1.dangerrpg.capability.data;

import java.io.*;
import java.util.*;

import net.minecraft.item.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public class RPGItemRegister extends
    RPGDataRegister<Item, RPGItemRegister.RPGItemData, Integer, Tuple.Pair<HashMap<Integer, RPGItemRegister.ItemAttrParams>, HashMap<Integer, Integer>>> {

    protected Integer codingKey(final Item key) {
        return Item.getIdFromItem(key);
    }

    protected Item decodingKey(final Integer key) {
        return Item.getItemById((int) key);
    }

    public static class RPGItemData extends
        RPGDataRegister.ElementData<Item, Tuple.Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>>> {

        public HashMap<ItemAttribute, ItemAttrParams> attributes;
        public HashMap<GemType, Tuple.Stub<Integer>> gems;
        public IRPGItem rpgComponent;
        public ItemType itemType;

        public RPGItemData(final IRPGItem lvlComponent, final boolean isSupported) {
            this.attributes = new LinkedHashMap<ItemAttribute, ItemAttrParams>();
            this.gems = new LinkedHashMap<GemType, Tuple.Stub<Integer>>();
            this.itemType = ItemType.MELEE_WPN;
            this.rpgComponent = lvlComponent;
            this.isSupported = isSupported;
        }

        public void registerIAStatic(final IAStatic attr, final float value) {
            this.attributes.put((ItemAttribute) attr, new ItemAttrParams(value, null));
        }

        public void registerIADynamic(final IADynamic attr, final float value, final IMultiplier.Multiplier mul) {
            this.attributes.put((ItemAttribute) attr, new ItemAttrParams(value, mul));
        }

        public void registerGT(final GemType gemType, final int count) {
            for (final GemType it : this.gems.keySet()) {
                if (it.name.equals(gemType.name)) {
                    this.gems.remove(it);
                    break;
                }
            }
            this.gems.put(gemType, Tuple.Stub.create((count < 1) ? 1 : count));
        }

        public Tuple.Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>> getTransferData(final Item key) {
            final HashMap<Integer, ItemAttrParams> tmp1 = new HashMap<Integer, ItemAttrParams>();
            for (final Map.Entry<ItemAttribute, ItemAttrParams> entry : this.attributes.entrySet()) {
                tmp1.put(entry.getKey().hash, entry.getValue());
            }
            final HashMap<Integer, Integer> tmp2 = new HashMap<Integer, Integer>();
            for (final Map.Entry<GemType, Tuple.Stub<Integer>> entry2 : this.gems.entrySet()) {
                tmp2.put(entry2.getKey().hash, entry2.getValue().value1);
            }
            return new Tuple.Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>>(tmp1, tmp2);
        }

        public void unpackTransferData(
            final Tuple.Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>> data) {
            for (final Map.Entry<Integer, ItemAttrParams> entry : data.value1.entrySet()) {
                if (RPGCapability.mapIntToItemAttribute.containsKey(entry.getKey())) {
                    final ItemAttribute attr = RPGCapability.mapIntToItemAttribute.get(entry.getKey());
                    if (!this.attributes.containsKey(attr)) {
                        continue;
                    }
                    final ItemAttrParams tmp = this.attributes.get(attr);
                    tmp.value = entry.getValue().value;
                    tmp.mul = entry.getValue().mul;
                }
            }
            for (final Map.Entry<Integer, Integer> entry2 : data.value2.entrySet()) {
                if (RPGCapability.mapIntToGemType.containsKey(entry2.getKey())) {
                    final GemType gemType = RPGCapability.mapIntToGemType.get(entry2.getKey());
                    if (!this.gems.containsKey(gemType)) {
                        continue;
                    }
                    this.gems.get(gemType).value1 = entry2.getValue();
                }
            }
        }
    }

    public static class ItemAttrParams implements Serializable {

        public float value;
        public IMultiplier.Multiplier mul;

        public ItemAttrParams(final float value, final IMultiplier.Multiplier mul) {
            this.value = value;
            this.mul = mul;
        }

        public float up(final float value) {
            return this.mul.up(value, new Object[0]);
        }
    }

    public enum ItemType {

        MELEE_WPN,
        TOOL,
        ARMOR,
        BOW,
        RANGE_WPN,
        STAFF;

        public String getDisplayName() {
            return DangerRPG.trans(
                "it.".concat(
                    this.name()
                        .toLowerCase()));
        }

        public static String getDisplayNameAll() {
            return DangerRPG.trans("it.all");
        }
    }
}
