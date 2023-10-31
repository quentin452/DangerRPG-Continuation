package mixac1.dangerrpg.capability.data;

import java.util.*;
import mixac1.dangerrpg.util.*;

public abstract class RPGDataRegister<Key, Data extends ElementData<Key, TransferData>, TransferKey, TransferData> extends HashMap<Key, Data>
{
    private byte[] transferData;
    
    public boolean isActivated(final Key key) {
        return this.containsKey(key) && this.get(key).isActivated;
    }
    
    public boolean isSupported(final Key key) {
        return this.containsKey(key) && this.get(key).isSupported;
    }
    
    public HashMap<Key, Data> getActiveElements() {
        final HashMap<Key, Data> map = new HashMap<Key, Data>();
        for (final Map.Entry<Key, Data> entry : this.entrySet()) {
            if (entry.getValue().isActivated) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }
    
    protected abstract TransferKey codingKey(final Key p0);
    
    protected abstract Key decodingKey(final TransferKey p0);
    
    public void createTransferData() {
        final LinkedList<Tuple.Pair<TransferKey, TransferData>> list = new LinkedList<Tuple.Pair<TransferKey, TransferData>>();
        for (final Map.Entry<Key, Data> entry : this.getActiveElements().entrySet()) {
            final TransferKey key = this.codingKey(entry.getKey());
            if (key != null) {
                list.add(new Tuple.Pair<TransferKey, TransferData>(key, (entry.getValue() != null) ? entry.getValue().getTransferData(entry.getKey()) : null));
            }
        }
        this.transferData = Utils.serialize(list);
    }
    
    public byte[] getTransferData() {
        return this.transferData;
    }
    
    public void extractTransferData(final byte[] tranferData) {
        for (final Map.Entry<Key, Data> entry : this.entrySet()) {
            entry.getValue().isActivated = false;
        }
        final LinkedList<Tuple.Pair<TransferKey, TransferData>> list = Utils.deserialize(tranferData);
        for (final Tuple.Pair<TransferKey, TransferData> data : list) {
            final Key key = this.decodingKey(data.value1);
            if (key != null && this.containsKey(key)) {
                if (data.value2 != null) {
                    this.get(key).unpackTransferData(data.value2);
                }
                this.get(key).isActivated = true;
            }
        }
    }
    
    public abstract static class ElementData<Key, TransferData>
    {
        public boolean isActivated;
        public boolean isSupported;
        
        public abstract TransferData getTransferData(final Key p0);
        
        public abstract void unpackTransferData(final TransferData p0);
    }
}
