package mixac1.dangerrpg.util;

import java.util.ArrayList;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class CopyPastedWorldHelper {

    /**
     * Returns randomly selected blocks in the set range, with the total attempts passed in, starting at the coordinates
     * passed in
     *
     * @param world
     * @param range
     * @param passes
     * @return
     */
    public static Block[] getRandomBlocksInSetRange(World world, int x, int y, int z, int range, int passes) {
        return getRandomBlocksInSetRangeWithRandomChance(world, x, y, z, range, passes, 4);
    }

    /**
     * Returns randomly selected blocks in the set range, this one allows the ability to change the chance it picks any
     * block
     *
     * @param world
     * @param range
     * @param passes
     * @param chance
     * @return
     */
    public static Block[] getRandomBlocksInSetRangeWithRandomChance(World world, int x, int y, int z, int range,
        int passes, int chance) {
        return null;
    }

    public static Block[] getRandomBlocksInSetRangeWithPrejudice(World world, Set set, int range) {
        return getRandomBlocksInSetRangeWithPrejudiceWithRandomChance(world, set, range, 4);
    }

    public static Block[] getRandomBlocksInSetRangeWithPrejudiceWithRandomChance(World world, Set set, int range,
        int chance) {
        return null;
    }

    /**
     * Returns randomly selected entities within the range set for the entity passed in
     *
     * @param world
     * @param range
     * @return
     */
    public static Entity[] getRandomEntitiesInRange(World world, Entity entity, double range) {
        return getRandomEntitiesInRangeWithRandomChance(world, entity, range, 4);
    }

    /**
     * Returns randomly selected entities within the range set for the entity passed in, with random chance to ignore it
     *
     * @param world
     * @param entity
     * @param range
     * @param chance
     * @return
     */
    public static Entity[] getRandomEntitiesInRangeWithRandomChance(World world, Entity entity, double range,
        int chance) {
        return null;
    }

    /**
     * Universally useful method to get if mob griefing is enabled, less typing to use this
     *
     * @param world
     * @return
     */
    public static boolean getMobGriefing(World world) {
        return world.getGameRules()
            .getGameRuleBooleanValue("mobGriefing");
    }

    /**
     * Universal method for getting the current difficulty setting, useful for switches (not much of a time saver but
     * here if it's ever needed)
     *
     * @param world
     * @return
     */
    public static EnumDifficulty getWorldDifficulty(World world) {
        return world.difficultySetting;
    }

    /**
     * The x, y, z coordinates to be passed in should be for the origin of the circle, returns mappings with coordinates
     * of every block in the circle's area
     *
     * @param world
     * @param radius
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static ArrayList<int[]> getBlocksInCircularRange(World world, double radius, double x, double y, double z) {
        ArrayList<int[]> list = new ArrayList();
        if (radius <= 0) throw new IllegalArgumentException("Radius cannot be negative!");

        int[] coords;

        for (double x2 = -radius - 0.55D; x2 < radius + 0.55D; x2 += 0.5D) {
            for (double z2 = -radius - 0.55D; z2 < radius + 0.55D; z2 += 0.5D) {
                coords = new int[] { (int) ((int) x + x2), (int) y, (int) ((int) z + z2) };

                if (MathHelper.sqrt_double(x2 * x2 + z2 * z2) <= radius) {
                    if (!list.contains(coords)) {
                        list.add(coords);
                    }
                }
            }
        }

        return list;
    }

    /**
     * The x, y, z coordinates to be passed in should be the origin of the sphere, returns mappings with coordinates of
     * every block in the sphere's area
     *
     * @param world
     * @param radius
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static ArrayList<int[]> getBlocksInSphericalRange(World world, double radius, double x, double y, double z) {
        ArrayList<int[]> list = new ArrayList();
        if (radius <= 0) throw new IllegalArgumentException("Radius cannot be negative!");

        double distance = radius + 1.5D;

        int[] coords;

        for (double y1 = -distance; y1 < distance; y1 += 0.5D) {
            for (double x1 = -distance; x1 < distance; x1 += 0.5D) {
                for (double z1 = -distance; z1 < distance; z1 += 0.5D) {
                    if (MathHelper.sqrt_double(x1 * x1 + z1 * z1 + y1 * y1) < radius) {
                        coords = new int[] { (int) ((int) x + x1), (int) ((int) y + y1), (int) ((int) z + z1) };

                        if (!list.contains(coords)) {
                            list.add(coords);
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     * Get a MovingObjectPosition based on the input entity's motion and rotation for the specified distance
     *
     * @param ent
     * @param distance
     * @return
     */
    public static MovingObjectPosition getMOPFromEntity(Entity ent, double distance) {
        float f = 1.0F;
        float f1 = ent.prevRotationPitch + (ent.rotationPitch - ent.prevRotationPitch) * f;
        float f2 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * f;
        double d0 = ent.prevPosX + (ent.posX - ent.prevPosX) * f;
        double d1 = ent.prevPosY + (ent.posY - ent.prevPosY) * f + (ent.getEyeHeight());
        double d2 = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = distance;

        if (ent instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP) ent).theItemInWorldManager.getBlockReachDistance() + (d3 - 4.0D);
        }
        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);

        return ent.worldObj.func_147447_a(vec3, vec31, true, false, true);
    }

    /**
     * Get a vector based on the input entity's motion and rotation for the specified distance
     *
     * @param ent
     * @param distance
     * @return
     */
    public static Vec3 getVecFromEntity(Entity ent, double distance) {
        return getMOPFromEntity(ent, distance).hitVec;
    }

    /**
     * Get a vector based on the input entity's motion and rotation
     *
     * @param ent
     * @param distance
     * @return
     */
    public static Vec3 getVecFromEntity(Entity ent, float distance) {
        return getMOPFromEntity(ent, distance).hitVec;
    }

    public static double[] getTransportPositionFromSide(int sideHit, double x, double y, double z) {
        switch (sideHit) {
            case 0:
                y -= 2.2D;
                break;
            default:
            case 1:
                break;
            case 2:
                z -= 1.0D;
                break;
            case 3:
                z += 1.0D;
                break;
            case 4:
                x -= 1.0D;
                break;
            case 5:
                x += 1.0D;
                break;
        }

        return new double[] { x, y, z };
    }

    public static double getXPositionFromSide(int sideHit, double x) {
        return getTransportPositionFromSide(sideHit, x, 0.0, 0.0)[0];
    }

    public static double getYPositionFromSide(int sideHit, double y) {
        return getTransportPositionFromSide(sideHit, 0.0, y, 0.0)[1];
    }

    public static double getZPositionFromSide(int sideHit, double z) {
        return getTransportPositionFromSide(sideHit, 0.0, 0.0, z)[2];
    }

    public static ArrayList<int[]> getBlocksAdjacent(int[] start) {
        ArrayList<int[]> list = new ArrayList();
        list.add(new int[] { start[0] + 1, start[1], start[2] });
        list.add(new int[] { start[0] - 1, start[1], start[2] });
        list.add(new int[] { start[0], start[1] + 1, start[2] });
        list.add(new int[] { start[0], start[1] - 1, start[2] });
        list.add(new int[] { start[0], start[1], start[2] + 1 });
        list.add(new int[] { start[0], start[1], start[2] - 1 });
        return list;
    }

    public static int getDistanceToGround(Entity entity) {
        return getDistanceToGround(
            entity.worldObj,
            MathHelper.floor_double(entity.posX),
            MathHelper.floor_double(entity.boundingBox.minY),
            MathHelper.floor_double(entity.posZ));
    }

    public static int getDistanceToGround(World world, int x, int y, int z) {
        for (int i = 0; y - i > 0; ++i) {
            if (world.getBlock(x, y - i, z)
                .getMaterial()
                .blocksMovement()) return i;
        }

        return y;
    }
}
