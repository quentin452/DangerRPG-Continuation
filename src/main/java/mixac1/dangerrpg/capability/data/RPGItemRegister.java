package mixac1.dangerrpg.capability.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.Item;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemAttrParams;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.Tuple.Pair;
import mixac1.dangerrpg.util.Tuple.Stub;

public class RPGItemRegister extends
    RPGDataRegister<Item, RPGItemData, Integer, Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>>> {

    @Override
    protected Integer codingKey(Item key) {
        return Item.getIdFromItem(key);
    }

    @Override
    protected Item decodingKey(Integer key) {
        return Item.getItemById(key);
    }

    /******************************************************************************************************/

    public static class RPGItemData
        extends RPGDataRegister.ElementData<Item, Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>>> {

        public Map<ItemAttribute, ItemAttrParams> attributes = new LinkedHashMap<ItemAttribute, ItemAttrParams>();
        public Map<GemType, Stub<Integer>> gems = new LinkedHashMap<GemType, Stub<Integer>>();
        public IRPGItem rpgComponent;
        public ItemType itemType = ItemType.MELEE_WPN;

        public RPGItemData(IRPGItem lvlComponent, boolean isSupported) {
            this.rpgComponent = lvlComponent;
            this.isSupported = isSupported;
        }

        public void registerIAStatic(IAStatic attr, float value) {
            attributes.put(attr, new ItemAttrParams(value, null));
        }

        public void registerIADynamic(IADynamic attr, float value, Multiplier mul) {
            attributes.put(attr, new ItemAttrParams(value, mul));
        }

        public void registerGT(GemType gemType, int count) {
            for (GemType it : gems.keySet()) {
                if (it.name.equals(gemType.name)) {
                    gems.remove(it);
                    break;
                }
            }

            gems.put(gemType, Stub.create(Math.max(count, 1)));
        }

        @Override
        public Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>> getTransferData(Item key) {
            HashMap<Integer, ItemAttrParams> tmp1 = new HashMap<Integer, ItemAttrParams>();
            for (Entry<ItemAttribute, ItemAttrParams> entry : attributes.entrySet()) {
                tmp1.put(entry.getKey().hash, entry.getValue());
            }

            HashMap<Integer, Integer> tmp2 = new HashMap<Integer, Integer>();
            for (Entry<GemType, Stub<Integer>> entry : gems.entrySet()) {
                tmp2.put(entry.getKey().hash, entry.getValue().value1);
            }

            return new Pair<>(tmp1, tmp2);
        }

        @Override
        public void unpackTransferData(Pair<HashMap<Integer, ItemAttrParams>, HashMap<Integer, Integer>> data) {
            for (Entry<Integer, ItemAttrParams> entry : data.value1.entrySet()) {
                if (RPGCapability.mapIntToItemAttribute.containsKey(entry.getKey())) {
                    ItemAttribute attr = RPGCapability.mapIntToItemAttribute.get(entry.getKey());
                    if (attributes.containsKey(attr)) {
                        ItemAttrParams tmp = attributes.get(attr);
                        tmp.value = entry.getValue().value;
                        tmp.mul = entry.getValue().mul;
                    }
                }
            }

            for (Entry<Integer, Integer> entry : data.value2.entrySet()) {
                if (RPGCapability.mapIntToGemType.containsKey(entry.getKey())) {
                    GemType gemType = RPGCapability.mapIntToGemType.get(entry.getKey());
                    if (gems.containsKey(gemType)) {
                        gems.get(gemType).value1 = entry.getValue();
                    }
                }
            }
        }
    }

    public static class ItemAttrParams implements Serializable {

        public float value;
        public Multiplier mul;

        public ItemAttrParams(float value, Multiplier mul) {
            this.value = value;
            this.mul = mul;
        }

        public float up(float value) {
            return mul.up(value);
        }
    }

    public enum ItemType {

        MELEE_WPN,
        TOOL,
        ARMOR,
        BOW,
        RANGE_WPN,
        STAFF

        ;

        public String getDisplayName() {
            return DangerRPG.trans("it.".concat(name().toLowerCase()));
        }

        public static String getDisplayNameAll() {
            return DangerRPG.trans("it.all");
        }
    }
}
