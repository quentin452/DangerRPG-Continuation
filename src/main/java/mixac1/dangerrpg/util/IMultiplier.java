package mixac1.dangerrpg.util;

import java.io.*;

public interface IMultiplier<Type> extends Serializable
{
    public static final MultiplierAdd ADD_1 = new MultiplierAdd(1.0f);
    public static final MultiplierMul MUL_1 = new MultiplierMul(1.0f);
    
    Type up(final Type p0, final Object... p1);
    
    public enum MulType
    {
        ADD {
            @Override
            public Multiplier getMul(final Float d) {
                return new MultiplierAdd(d);
            }
        }, 
        MUL {
            @Override
            public Multiplier getMul(final Float d) {
                return new MultiplierMul(d);
            }
        }, 
        SQRT {
            @Override
            public Multiplier getMul(final Float d) {
                return new MultiplierSQRT(d);
            }
        }, 
        HARD {
            @Override
            public String toString(final Float value) {
                return this.name();
            }
        };
        
        public Multiplier getMul(final Float value) {
            return null;
        }
        
        public String toString(final Float value) {
            return Utils.toString(this.name(), " ", value);
        }
        
        public static Multiplier getMul(final String str) {
            final String[] strs = str.split(" ");
            if (strs.length == 2) {
                final MulType type = valueOf(strs[0].toUpperCase());
                final Float value = Float.valueOf(strs[1]);
                if (type != null && value != null) {
                    return type.getMul(value);
                }
            }
            return null;
        }
    }
    
    public abstract static class Multiplier implements IMultiplierE<Float>
    {
        public final Float mul;
        public final MulType mulType;
        
        public Multiplier(final Float mul, final MulType mulType) {
            this.mul = mul;
            this.mulType = mulType;
        }
        
        @Override
        public String toString() {
            return this.mulType.toString(this.mul);
        }
    }
    
    public static class MultiplierAdd extends Multiplier
    {
        public MultiplierAdd(final Float mul) {
            super(mul, MulType.ADD);
        }
        
        @Override
        public Float up(final Float value, final Object... objs) {
            return value + this.mul;
        }
        
        @Override
        public Float down(final Float value, final Object... objs) {
            return value - this.mul;
        }
    }
    
    public static class MultiplierMul extends Multiplier
    {
        public MultiplierMul(final Float mul) {
            super(mul, MulType.MUL);
        }
        
        @Override
        public Float up(final Float value, final Object... objs) {
            return value * this.mul;
        }
        
        @Override
        public Float down(final Float value, final Object... objs) {
            return value / this.mul;
        }
    }
    
    public static class MultiplierSQRT extends Multiplier
    {
        public MultiplierSQRT(final Float mul) {
            super(mul, MulType.SQRT);
        }
        
        @Override
        public Float up(final Float value, final Object... objs) {
            return (float)(value + Math.sqrt(value * this.mul));
        }
        
        @Override
        public Float down(final Float value, final Object... objs) {
            final float a = -1.0f;
            final float b = 2.0f * value + this.mul;
            final float c = -value * value;
            final float d = (float)Math.sqrt(Math.pow(b, 2.0) - 4.0f * a * c);
            final float tmp1 = (-b - d) / 2.0f * a;
            final float tmp2 = (-b + d) / 2.0f * a;
            return (this.up(tmp1, new Object[0]) == value) ? tmp1 : tmp2;
        }
    }
    
    public interface IMultiplierE<Type> extends IMultiplier<Type>
    {
        Type down(final Type p0, final Object... p1);
    }
}
