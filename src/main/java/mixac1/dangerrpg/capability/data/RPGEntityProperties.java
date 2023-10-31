package mixac1.dangerrpg.capability.data;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import mixac1.dangerrpg.util.*;

public class RPGEntityProperties implements IExtendedEntityProperties {

    protected static final String ID = "RPGEntityProperties";
    public final EntityLivingBase entity;
    public HashMap<Integer, Tuple.Stub> attributeMap;
    public HashMap<Integer, Integer> lvlMap;

    public RPGEntityProperties(final EntityLivingBase entity) {
        this.attributeMap = new HashMap<Integer, Tuple.Stub>();
        this.lvlMap = new HashMap<Integer, Integer>();
        this.entity = entity;
    }

    public void init(final Entity entity, final World world) {
        for (final EntityAttribute iter : this.getEntityAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
    }

    public void serverInit() {
        if (isServerSide(this.entity) && !EntityAttributes.LVL.isInitedEntity(this.entity)) {
            for (final EntityAttribute iter : this.getEntityAttributes()) {
                iter.serverInit(this.entity);
            }
            if (!(this.entity instanceof EntityPlayer)) {
                MinecraftForge.EVENT_BUS.post((Event) new InitRPGEntityEvent(this.entity));
            }
        }
    }

    public static void register(final EntityLivingBase entity) {
        entity.registerExtendedProperties(
            "RPGEntityProperties",
            (IExtendedEntityProperties) new RPGEntityProperties(entity));
    }

    public static RPGEntityProperties get(final EntityLivingBase entity) {
        return (RPGEntityProperties) entity.getExtendedProperties("RPGEntityProperties");
    }

    public static boolean isServerSide(final EntityLivingBase entity) {
        return !entity.worldObj.isRemote;
    }

    public boolean checkValid() {
        final boolean result = EntityAttributes.LVL.isInitedEntity(this.entity);
        if (!result) {
            if (isServerSide(this.entity)) {
                this.init((Entity) this.entity, this.entity.worldObj);
                RPGNetwork.net.sendToAll((IMessage) new MsgSyncEntityData(this.entity, this));
            } else if (DangerRPG.proxy.getTick(Side.CLIENT) % 100 == 0) {
                RPGNetwork.net.sendToServer((IMessage) new MsgSyncEntityData(this.entity));
            }
        }
        return result;
    }

    public void rebuildOnDeath() {
        int count = 0;
        final ArrayList<LvlEAProvider> pas = new ArrayList<LvlEAProvider>();
        for (final LvlEAProvider it : this.getLvlProviders()) {
            final int lvl;
            if ((lvl = it.getLvl(this.entity)) > 1) {
                pas.add(it);
                count += lvl - 1;
            }
        }
        if (count > RPGConfig.EntityConfig.d.playerLoseLvlCount) {
            count = RPGConfig.EntityConfig.d.playerLoseLvlCount;
        }
        for (int i = 0; i < count; ++i) {
            final int rand = RPGOther.rand.nextInt(pas.size());
            pas.get(rand)
                .up(this.entity, (EntityPlayer) null, false);
            if (pas.get(rand)
                .getLvl(this.entity) <= 1) {
                pas.remove(rand);
            }
        }
    }

    public void saveNBTData(final NBTTagCompound nbt) {
        final NBTTagCompound tmp = new NBTTagCompound();
        for (final EntityAttribute iter : this.getEntityAttributes()) {
            iter.toNBT(tmp, this.entity);
        }
        nbt.setTag("RPGEntityProperties", (NBTBase) tmp);
    }

    public void loadNBTData(final NBTTagCompound nbt) {
        final NBTTagCompound tmp = (NBTTagCompound) nbt.getTag("RPGEntityProperties");
        if (tmp != null) {
            for (final EntityAttribute iter : this.getEntityAttributes()) {
                iter.fromNBT(tmp, this.entity);
            }
        }
    }

    public EntityAttribute getEntityAttribute(final int hash) {
        final EntityAttribute attr = RPGCapability.mapIntToEntityAttribute.get(hash);
        return RPGCapability.rpgEntityRegistr.get(this.entity).attributes.containsKey(attr) ? attr : null;
    }

    public LvlEAProvider getLvlProvider(final int hash) {
        final EntityAttribute attr = this.getEntityAttribute(hash);
        return (attr != null) ? attr.getLvlProvider(this.entity) : null;
    }

    public Set<EntityAttribute> getEntityAttributes() {
        return RPGCapability.rpgEntityRegistr.get(this.entity).attributes.keySet();
    }

    public List<LvlEAProvider> getLvlProviders() {
        return RPGCapability.rpgEntityRegistr.get(this.entity).lvlProviders;
    }
}
