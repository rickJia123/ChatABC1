package river.chat.lib_core.router.plugin.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import river.chat.lib_core.router.plugin.core.annotations.SpecifyBooleanValue;
import river.chat.lib_core.router.plugin.core.annotations.SpecifyClassValue;
import river.chat.lib_core.router.plugin.core.annotations.SpecifyIntegerValue;
import river.chat.lib_core.router.plugin.core.annotations.SpecifyStringValue;

public class EmptyPluginProxy implements InvocationHandler {

    public static <T> T newInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(), new Class[]{clazz},
                new EmptyPluginProxy());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        final SpecifyClassValue specifyClassValue = method.getAnnotation(SpecifyClassValue.class);
        if (specifyClassValue != null) return specifyClassValue.returnValue();
        final SpecifyIntegerValue specifyIntegerValue = method.getAnnotation(SpecifyIntegerValue.class);
        if (specifyIntegerValue != null) return specifyIntegerValue.returnValue();
        final SpecifyStringValue specifyStringValue = method.getAnnotation(SpecifyStringValue.class);
        if (specifyStringValue != null) return specifyStringValue.returnValue();
        final SpecifyBooleanValue specifyBooleanValue = method.getAnnotation(SpecifyBooleanValue.class);
        if (specifyBooleanValue != null) return specifyBooleanValue.returnValue();
        return defaultValueByType(method.getReturnType());
    }

    private Object defaultValueByType(Class type) {
        if (type == boolean.class) {
            return false;
        } else if (type == int.class) {
            return 0;
        } else if (type == short.class) {
            return (short) 0;
        } else if (type == char.class) {
            return (char) 0;
        } else if (type == byte.class) {
            return (byte) 0;
        } else if (type == long.class) {
            return 0L;
        } else if (type == float.class) {
            return 0f;
        } else if (type == double.class) {
            return 0D;
        } else {
            return null;
        }
    }
}