package mixac1.dangerrpg.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomContainer<Type> {

    private static class TypeItem<Type> {

        public final Type obj;
        public final double chanceRaw;
        public double chance;

        public TypeItem(Type obj, double chanceRaw) {
            this.obj = obj;
            this.chanceRaw = chanceRaw;
        }
    }

    private List<TypeItem<Type>> list = new LinkedList<TypeItem<Type>>();

    public RandomContainer() {}

    public RandomContainer(Type[] objs, double[] chances) {
        add(objs, chances);
    }

    private boolean addRaw(Type obj, double chance) {
        return obj != null && chance > 0 && list.add(new TypeItem(obj, chance));
    }

    private void normalize() {
        double sum = 0;
        for (TypeItem<Type> it : list) {
            sum += it.chanceRaw;
        }
        for (TypeItem<Type> it : list) {
            it.chance = it.chanceRaw / sum;
        }
    }

    public void add(Type[] objs, double[] chances) {
        if (objs != null && chances != null && objs.length == chances.length) {
            for (int i = 0; i < objs.length; ++i) {
                addRaw(objs[i], chances[i]);
            }
            normalize();
        }
    }

    public void add(Type obj, double chance) {
        addRaw(obj, chance);
        normalize();
    }

    public Type get(Random rand) {
        return get(rand.nextDouble());
    }

    public Type get() {
        return get(Math.random());
    }

    public Type get(double randomValue) {
        for (TypeItem<Type> it : list) {
            if (it.chance >= randomValue) {
                return it.obj;
            } else {
                randomValue -= it.chance;
            }
        }
        return null;
    }
}
