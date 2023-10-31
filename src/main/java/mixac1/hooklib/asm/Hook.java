package mixac1.hooklib.asm;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
public @interface Hook {

    ReturnCondition returnCondition() default ReturnCondition.NEVER;

    HookPriority priority() default HookPriority.NORMAL;

    String targetMethod() default "";

    String returnType() default "";

    boolean injectOnExit() default false;

    @Deprecated
    int injectOnLine() default -1;

    String returnAnotherMethod() default "";

    boolean returnNull() default false;

    boolean booleanReturnConstant() default false;

    byte byteReturnConstant() default 0;

    short shortReturnConstant() default 0;

    int intReturnConstant() default 0;

    long longReturnConstant() default 0L;

    float floatReturnConstant() default 0.0f;

    double doubleReturnConstant() default 0.0;

    char charReturnConstant() default '\0';

    String stringReturnConstant() default "";

    @Target({ ElementType.PARAMETER })
    public @interface ReturnValue {}

    @Target({ ElementType.PARAMETER })
    public @interface LocalVariable {

        int value();
    }
}
