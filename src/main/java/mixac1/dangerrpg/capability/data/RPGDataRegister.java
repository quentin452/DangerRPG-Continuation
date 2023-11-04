package mixac1.dangerrpg.capability.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import mixac1.dangerrpg.capability.data.RPGDataRegister.ElementData;
import mixac1.dangerrpg.util.Tuple.Pair;
import mixac1.dangerrpg.util.Utils;

public abstract class RPGDataRegister<Key, Data extends ElementData<Key, TransferData>, TransferKey, TransferData> extends HashMap<Key, Data>
{
    private byte[] transferData;

    public boolean isActivated(Key key)
    {
        return containsKey(key) && get(key).isActivated;
    }

    public boolean isSupported(Key key)
    {
        return containsKey(key) && get(key).isSupported;
    }

    public Map<Key, Data> getActiveElements()
    {
        HashMap<Key, Data> map = new HashMap<Key, Data>();
        for (Entry<Key, Data> entry : entrySet()) {
            if (entry.getValue().isActivated) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    protected abstract TransferKey codingKey(Key key);

    protected abstract Key decodingKey(TransferKey key);

    public void createTransferData()
    {
        LinkedList<Pair<TransferKey, TransferData>> list = new LinkedList<Pair<TransferKey, TransferData>>();
        for (Entry<Key, Data> entry : getActiveElements().entrySet()) {
            TransferKey key = codingKey(entry.getKey());
            if (key != null) {
                list.add(new Pair<TransferKey, TransferData>(key, entry.getValue() != null ? entry.getValue().getTransferData(entry.getKey()) : null));
            }
        }

        transferData = Utils.serialize(list);
    }

    public byte[] getTransferData()
    {
        return transferData;
    }

    public void extractTransferData(byte[] tranferData)
    {
        for (Entry<Key, Data> entry : entrySet()) {
            entry.getValue().isActivated = false;
        }

        LinkedList<Pair<TransferKey, TransferData>> list = Utils.deserialize(tranferData);
        for (Pair<TransferKey, TransferData> data : list) {
            Key key = decodingKey(data.value1);
            if (key != null && containsKey(key)) {
                if (data.value2 != null) {
                    get(key).unpackTransferData(data.value2);
                }
                get(key).isActivated = true;
            }
        }
    }

    public static abstract class ElementData<Key, TransferData>
    {
        public boolean isActivated;
        public boolean isSupported;

        public abstract TransferData getTransferData(Key key);

        public abstract void unpackTransferData(TransferData data);
    }
}
