package mixac1.dangerrpg.capability.data;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.util.*;
import java.util.*;
import mixac1.dangerrpg.init.*;
import java.io.*;

public class RPGEntityRegister extends RPGDataRegister<Class<? extends EntityLivingBase>, RPGEntityData, String, HashMap<Integer, EntityTransferData>>
{
    public Class<? extends EntityLivingBase> getClass(final EntityLivingBase entity) {
        return (entity instanceof EntityPlayer) ? EntityPlayer.class : entity.getClass();
    }
    
    public boolean isActivated(final EntityLivingBase entity) {
        return super.isActivated((Object)this.getClass(entity));
    }
    
    public RPGEntityData get(final EntityLivingBase entity) {
        return (RPGEntityData)super.get((Object)this.getClass(entity));
    }
    
    public void put(final EntityLivingBase entity, final RPGEntityData data) {
        super.put((Object)this.getClass(entity), (Object)data);
    }
    
    protected String codingKey(final Class<? extends EntityLivingBase> key) {
        return EntityList.classToStringMapping.containsKey(key) ? EntityList.classToStringMapping.get(key) : (EntityPlayer.class.isAssignableFrom(key) ? "player" : null);
    }
    
    protected Class<? extends EntityLivingBase> decodingKey(final String key) {
        return (Class<? extends EntityLivingBase>)(EntityList.stringToClassMapping.containsKey(key) ? EntityList.stringToClassMapping.get(key) : ("player".equals(key) ? EntityPlayer.class : null));
    }
    
    public static class RPGEntityData extends RPGDataRegister.ElementData<Class<? extends EntityLivingBase>, HashMap<Integer, EntityTransferData>>
    {
        public HashMap<EntityAttribute, EntityAttrParams> attributes;
        public List<LvlEAProvider> lvlProviders;
        public IRPGEntity rpgComponent;
        
        public RPGEntityData(final IRPGEntity rpgComponent, final boolean isSupported) {
            this.attributes = new LinkedHashMap<EntityAttribute, EntityAttrParams>();
            this.lvlProviders = new LinkedList<LvlEAProvider>();
            this.rpgComponent = rpgComponent;
            this.isSupported = isSupported;
        }
        
        public <T> void registerEALvlable(final EntityAttribute<T> attr, final T startValue, final LvlEAProvider<T> lvlProvider) {
            this.registerEALvlable(attr, startValue, lvlProvider, null);
        }
        
        public <T> void registerEALvlable(final EntityAttribute<T> attr, final T startValue, final LvlEAProvider<T> lvlProvider, final IMultiplier.Multiplier staticMul) {
            lvlProvider.attr = attr;
            this.attributes.put(attr, new EntityAttrParams(startValue, lvlProvider, staticMul));
            this.lvlProviders.add(lvlProvider);
        }
        
        public <T> void registerEA(final EntityAttribute<T> attr, final T startValue) {
            this.registerEA(attr, startValue, null);
        }
        
        public <T> void registerEA(final EntityAttribute<T> attr, final T startValue, final IMultiplier.Multiplier staticMul) {
            this.attributes.put(attr, new EntityAttrParams(startValue, null, staticMul));
        }
        
        public HashMap<Integer, EntityTransferData> getTransferData(final Class<? extends EntityLivingBase> key) {
            if (EntityPlayer.class.isAssignableFrom(key)) {
                final HashMap<Integer, EntityTransferData> tmp = new HashMap<Integer, EntityTransferData>();
                for (final Map.Entry<EntityAttribute, EntityAttrParams> entry : this.attributes.entrySet()) {
                    if (entry.getValue().lvlProvider != null) {
                        tmp.put(entry.getKey().hash, new EntityTransferData(entry.getValue().lvlProvider));
                    }
                }
                return tmp;
            }
            return null;
        }
        
        public void unpackTransferData(final HashMap<Integer, EntityTransferData> data) {
            for (final Map.Entry<Integer, EntityTransferData> entry : data.entrySet()) {
                if (RPGCapability.mapIntToEntityAttribute.containsKey(entry.getKey())) {
                    final EntityAttribute attr = RPGCapability.mapIntToEntityAttribute.get(entry.getKey());
                    if (!this.attributes.containsKey(attr)) {
                        continue;
                    }
                    final EntityAttrParams tmp = this.attributes.get(attr);
                    tmp.lvlProvider.mulValue = entry.getValue().mulValue;
                    tmp.lvlProvider.startExpCost = entry.getValue().startExpCost;
                    tmp.lvlProvider.maxLvl = entry.getValue().maxLvl;
                    tmp.lvlProvider.mulExpCost = entry.getValue().mulExpCost;
                }
            }
        }
    }
    
    public static class EntityTransferData implements Serializable
    {
        public IMultiplier.IMultiplierE mulValue;
        public int maxLvl;
        public int startExpCost;
        public IMultiplier.Multiplier mulExpCost;
        
        public EntityTransferData(final LvlEAProvider lvlProv) {
            this.mulValue = lvlProv.mulValue;
            this.maxLvl = lvlProv.maxLvl;
            this.startExpCost = lvlProv.startExpCost;
            this.mulExpCost = lvlProv.mulExpCost;
        }
    }
    
    public static class EntityAttrParams<Type>
    {
        public Type startValue;
        public LvlEAProvider<Type> lvlProvider;
        public IMultiplier.Multiplier mulValue;
        private static final IMultiplier.Multiplier MUL_0d1;
        
        public EntityAttrParams(final Type startValue, final LvlEAProvider<Type> lvlProvider, final IMultiplier.Multiplier mulValue) {
            this.startValue = startValue;
            this.lvlProvider = lvlProvider;
            this.mulValue = ((mulValue == null) ? EntityAttrParams.MUL_0d1 : mulValue);
        }
        
        static {
            MUL_0d1 = new IMultiplier.MultiplierMul(0.1f);
        }
    }
}
