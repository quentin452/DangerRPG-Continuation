package mixac1.dangerrpg.util;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class CopyPastedWorldHelper {

    public static Block[] getRandomBlocksInSetRange(final World world, final int x, final int y, final int z,
        final int range, final int passes) {
        return getRandomBlocksInSetRangeWithRandomChance(world, x, y, z, range, passes, 4);
    }

    public static Block[] getRandomBlocksInSetRangeWithRandomChance(final World world, final int x, final int y,
        final int z, final int range, final int passes, final int chance) {
        return null;
    }

    public static Block[] getRandomBlocksInSetRangeWithPrejudice(final World world, final Set set, final int range) {
        return getRandomBlocksInSetRangeWithPrejudiceWithRandomChance(world, set, range, 4);
    }

    public static Block[] getRandomBlocksInSetRangeWithPrejudiceWithRandomChance(final World world, final Set set,
        final int range, final int chance) {
        return null;
    }

    public static Entity[] getRandomEntitiesInRange(final World world, final Entity entity, final double range) {
        return getRandomEntitiesInRangeWithRandomChance(world, entity, range, 4);
    }

    public static Entity[] getRandomEntitiesInRangeWithRandomChance(final World world, final Entity entity,
        final double range, final int chance) {
        return null;
    }

    public static boolean getMobGriefing(final World world) {
        return world.getGameRules()
            .getGameRuleBooleanValue("mobGriefing");
    }

    public static EnumDifficulty getWorldDifficulty(final World world) {
        return world.difficultySetting;
    }

    public static ArrayList<int[]> getBlocksInCircularRange(final World world, final double radius, final double x,
        final double y, final double z) {
        final ArrayList<int[]> list = new ArrayList<int[]>();
        if (radius <= 0.0) {
            throw new IllegalArgumentException("Radius cannot be negative!");
        }
        for (double x2 = -radius - 0.55; x2 < radius + 0.55; x2 += 0.5) {
            for (double z2 = -radius - 0.55; z2 < radius + 0.55; z2 += 0.5) {
                final int[] coords = { (int) ((int) x + x2), (int) y, (int) ((int) z + z2) };
                if (MathHelper.sqrt_double(x2 * x2 + z2 * z2) <= radius && !list.contains(coords)) {
                    list.add(coords);
                }
            }
        }
        return list;
    }

    public static ArrayList<int[]> getBlocksInSphericalRange(final World world, final double radius, final double x,
        final double y, final double z) {
        final ArrayList<int[]> list = new ArrayList<int[]>();
        if (radius <= 0.0) {
            throw new IllegalArgumentException("Radius cannot be negative!");
        }
        for (double distance = radius + 1.5, y2 = -distance; y2 < distance; y2 += 0.5) {
            for (double x2 = -distance; x2 < distance; x2 += 0.5) {
                for (double z2 = -distance; z2 < distance; z2 += 0.5) {
                    if (MathHelper.sqrt_double(x2 * x2 + z2 * z2 + y2 * y2) < radius) {
                        final int[] coords = { (int) ((int) x + x2), (int) ((int) y + y2), (int) ((int) z + z2) };
                        if (!list.contains(coords)) {
                            list.add(coords);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static MovingObjectPosition getMOPFromEntity(final Entity ent, final double distance) {
        final float f = 1.0f;
        final float f2 = ent.prevRotationPitch + (ent.rotationPitch - ent.prevRotationPitch) * f;
        final float f3 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * f;
        final double d0 = ent.prevPosX + (ent.posX - ent.prevPosX) * f;
        final double d2 = ent.prevPosY + (ent.posY - ent.prevPosY) * f + ent.getEyeHeight();
        final double d3 = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * f;
        final Vec3 vec3 = Vec3.createVectorHelper(d0, d2, d3);
        final float f4 = MathHelper.cos(-f3 * 0.017453292f - 3.1415927f);
        final float f5 = MathHelper.sin(-f3 * 0.017453292f - 3.1415927f);
        final float f6 = -MathHelper.cos(-f2 * 0.017453292f);
        final float f7 = MathHelper.sin(-f2 * 0.017453292f);
        final float f8 = f5 * f6;
        final float f9 = f4 * f6;
        double d4 = distance;
        if (ent instanceof EntityPlayerMP) {
            d4 = ((EntityPlayerMP) ent).theItemInWorldManager.getBlockReachDistance() + (d4 - 4.0);
        }
        final Vec3 vec4 = vec3.addVector(f8 * d4, f7 * d4, f9 * d4);
        return ent.worldObj.func_147447_a(vec3, vec4, true, false, true);
    }

    public static Vec3 getVecFromEntity(final Entity ent, final double distance) {
        return getMOPFromEntity(ent, distance).hitVec;
    }

    public static Vec3 getVecFromEntity(final Entity ent, final float distance) {
        return getMOPFromEntity(ent, distance).hitVec;
    }

    public static double[] getTransportPositionFromSide(final int sideHit, double x, double y, double z) {
        switch (sideHit) {
            case 0: {
                y -= 2.2;
                break;
            }
            case 2: {
                --z;
                break;
            }
            case 3: {
                ++z;
                break;
            }
            case 4: {
                --x;
                break;
            }
            case 5: {
                ++x;
                break;
            }
        }
        return new double[] { x, y, z };
    }

    public static double getXPositionFromSide(final int sideHit, final double x) {
        return getTransportPositionFromSide(sideHit, x, 0.0, 0.0)[0];
    }

    public static double getYPositionFromSide(final int sideHit, final double y) {
        return getTransportPositionFromSide(sideHit, 0.0, y, 0.0)[1];
    }

    public static double getZPositionFromSide(final int sideHit, final double z) {
        return getTransportPositionFromSide(sideHit, 0.0, 0.0, z)[2];
    }

    public static ArrayList<int[]> getBlocksAdjacent(final int[] start) {
        final ArrayList<int[]> list = new ArrayList<int[]>();
        list.add(new int[] { start[0] + 1, start[1], start[2] });
        list.add(new int[] { start[0] - 1, start[1], start[2] });
        list.add(new int[] { start[0], start[1] + 1, start[2] });
        list.add(new int[] { start[0], start[1] - 1, start[2] });
        list.add(new int[] { start[0], start[1], start[2] + 1 });
        list.add(new int[] { start[0], start[1], start[2] - 1 });
        return list;
    }

    public static int getDistanceToGround(final Entity entity) {
        return getDistanceToGround(
            entity.worldObj,
            MathHelper.floor_double(entity.posX),
            MathHelper.floor_double(entity.boundingBox.minY),
            MathHelper.floor_double(entity.posZ));
    }

    public static int getDistanceToGround(final World world, final int x, final int y, final int z) {
        for (int i = 0; y - i > 0; ++i) {
            if (world.getBlock(x, y - i, z)
                .getMaterial()
                .blocksMovement()) {
                return i;
            }
        }
        return y;
    }
}
