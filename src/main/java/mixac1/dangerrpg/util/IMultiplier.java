package mixac1.dangerrpg.util;

import java.io.Serializable;
import java.util.Objects;

public interface IMultiplier<Type> extends Serializable {

    Type up(Type value, Object... objs);

    interface IMultiplierE<Type> extends IMultiplier<Type> {

        Type down(Type value, Object... objs);
    }

    enum MulType {

        ADD {

            @Override
            public Multiplier getMul(Float d) {
                return new MultiplierAdd(d);
            }
        },

        MUL {

            @Override
            public Multiplier getMul(Float d) {
                return new MultiplierMul(d);
            }
        },

        SQRT {

            @Override
            public Multiplier getMul(Float d) {
                return new MultiplierSQRT(d);
            }
        },

        HARD {

            @Override
            public String toString(Float value) {
                return name();
            }
        },

        ;

        public Multiplier getMul(Float value) {
            return null;
        }

        public String toString(Float value) {
            return Utils.toString(name(), " ", value);
        }

        public static Multiplier getMul(String str) {
            String[] strs = str.split(" ");
            if (strs.length == 2) {
                MulType type = MulType.valueOf(strs[0].toUpperCase());
                Float value = Float.valueOf(strs[1]);
                return type.getMul(value);
            }
            return null;
        }
    }

    abstract class Multiplier implements IMultiplierE<Float> {

        public final Float mul;
        public final MulType mulType;

        public Multiplier(Float mul, MulType mulType) {
            this.mul = mul;
            this.mulType = mulType;
        }

        @Override
        public String toString() {
            return mulType.toString(mul);
        }
    }

    class MultiplierAdd extends Multiplier {

        public MultiplierAdd(Float mul) {
            super(mul, MulType.ADD);
        }

        @Override
        public Float up(Float value, Object... objs) {
            return value + mul;
        }

        @Override
        public Float down(Float value, Object... objs) {
            return value - mul;
        }
    }

    class MultiplierMul extends Multiplier {

        public MultiplierMul(Float mul) {
            super(mul, MulType.MUL);
        }

        @Override
        public Float up(Float value, Object... objs) {
            return value * mul;
        }

        @Override
        public Float down(Float value, Object... objs) {
            return value / mul;
        }
    }

    class MultiplierSQRT extends Multiplier {

        public MultiplierSQRT(Float mul) {
            super(mul, MulType.SQRT);
        }

        @Override
        public Float up(Float value, Object... objs) {
            return (float) (value + Math.sqrt(value * mul));
        }

        @Override
        public Float down(Float value, Object... objs) {
            float a = -1;
            float b = 2 * value + mul;
            float c = -value * value;
            float d = (float) Math.sqrt((Math.pow(b, 2) - 4 * a * c));
            float tmp1 = (-b - d) / 2 * a;
            float tmp2 = (-b + d) / 2 * a;
            return Objects.equals(up(tmp1), value) ? tmp1 : tmp2;
        }
    }

    MultiplierAdd ADD_1 = new MultiplierAdd(1F);

    MultiplierMul MUL_1 = new MultiplierMul(1F);
}
