package com.polyhedraldevelopment.seismic.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class UnsafeUtils {
    public static boolean canUseUnsafe = true;
    public static final Unsafe UNSAFE = UnsafeUtils.findUnsafe();

    private static Unsafe findUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch(SecurityException se) {
            try {
                try {
                    Class.forName("java.security.AccessController");
                    return AccessControllerUtils.doPrivileged(() -> {
                        try {
                            return UnsafeUtils.getUnsafeWithoutAccessController();
                        } catch(IllegalAccessException e) {
                            System.out.println("Unsafe Unavailable" + System.lineSeparator() + e);
                            UnsafeUtils.canUseUnsafe = false;
                            return null;
                        }
                    });
                } catch(ClassNotFoundException e) {
                    if(UnsafeUtils.canUseUnsafe) {
                        return UnsafeUtils.getUnsafeWithoutAccessController();
                    }
                }
            } catch(Exception e) {
                System.out.println("Unsafe Unavailable" + System.lineSeparator() + e);
                UnsafeUtils.canUseUnsafe = false;
                return null;
            }
        }
        return null;
    }

    static Unsafe getUnsafeWithoutAccessController() throws IllegalAccessException {
        Class<Unsafe> type = Unsafe.class;
        try {
            Field field = ReflectionUtils.getField(type, "theUnsafe");
            ReflectionUtils.setFieldToPublic(field);
            return type.cast(field.get(type));
        } catch(Exception e) {
            for(Field field : type.getDeclaredFields()) {
                if(type.isAssignableFrom(field.getType())) {
                    ReflectionUtils.setFieldToPublic(field);
                    return type.cast(field.get(type));
                }
            }
        }
        return null;
    }


    /**
     * Retrieves the value of a static field as an Object.
     *
     * @param <T>         the type of the class containing the static field
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static <T> Object getStaticFieldObject(Class<T> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getObject(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as a char.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static char getStaticFieldChar(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getChar(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as an int.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static int getStaticFieldInt(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getInt(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as a long.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static long getStaticFieldLong(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getLong(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as a float.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static float getStaticFieldFloat(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getFloat(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as a double.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static double getStaticFieldDouble(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getDouble(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Retrieves the value of a static field as a boolean.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to retrieve the value from
     *
     * @return the value of the static field
     */
    public static boolean getStaticFieldBoolean(Class<?> targetClass, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getBoolean(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field));
    }

    /**
     * Sets the value of a static field as an Object.
     *
     * @param <T>         the type of the class containing the static field
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static <T> void putStaticFieldObject(Class<T> targetClass, Field field, Object value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putObject(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as a char.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldChar(Class<?> targetClass, Field field, char value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putChar(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as an int.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldInt(Class<?> targetClass, Field field, int value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putInt(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as a long.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldLong(Class<?> targetClass, Field field, long value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putLong(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as a float.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldFloat(Class<?> targetClass, Field field, float value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putFloat(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as a double.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldDouble(Class<?> targetClass, Field field, double value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putDouble(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Sets the value of a static field as a boolean.
     *
     * @param targetClass the class containing the static field
     * @param field       the field to set the value to
     * @param value       the value to set
     */
    public static void putStaticFieldBoolean(Class<?> targetClass, Field field, boolean value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putBoolean(UnsafeUtils.UNSAFE.staticFieldBase(field), UnsafeUtils.UNSAFE.staticFieldOffset(field), value);
    }

    /**
     * Retrieves the value of a field as an Object.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static Object getFieldObject(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getObject(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as a char.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static char getFieldChar(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getChar(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as an int.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static int getFieldInt(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getInt(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as a long.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static long getFieldLong(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getLong(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as a float.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static float getFieldFloat(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getFloat(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as a double.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static double getFieldDouble(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getDouble(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Retrieves the value of a field as a boolean.
     *
     * @param targetObject the object containing the field
     * @param field        the field to retrieve the value from
     *
     * @return the value of the field
     */
    public static boolean getFieldBoolean(Object targetObject, Field field) {
        assert UnsafeUtils.UNSAFE != null;
        return UnsafeUtils.UNSAFE.getBoolean(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field));
    }

    /**
     * Sets the value of a field as an Object.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param in           the value to set
     */
    public static void putFieldObject(Object targetObject, Field field, Object in) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putObject(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), in);
    }

    /**
     * Sets the value of a field as a char.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param value        the value to set
     */
    public static void putFieldChar(Object targetObject, Field field, char value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putChar(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), value);
    }

    /**
     * Sets the value of a field as an int.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param value        the value to set
     */
    public static void putFieldInt(Object targetObject, Field field, int value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putInt(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), value);
    }

    /**
     * Sets the value of a field as a long.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param value        the value to set
     */
    public static void putFieldLong(Object targetObject, Field field, long value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putLong(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), value);
    }

    /**
     * Sets the value of a field as a float.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param value        the value to set
     */
    public static void putFieldFloat(Object targetObject, Field field, float value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putFloat(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), value);
    }

    /**
     * Sets the value of a field as a double.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param value        the value to set
     */
    public static void putFieldDouble(Object targetObject, Field field, double value) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putDouble(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), value);
    }

    /**
     * Sets the value of a field as a boolean.
     *
     * @param targetObject the object containing the field
     * @param field        the field to set the value to
     * @param in           the value to set
     */
    public static void putFieldBoolean(Object targetObject, Field field, Boolean in) {
        assert UnsafeUtils.UNSAFE != null;
        UnsafeUtils.UNSAFE.putBoolean(targetObject, UnsafeUtils.UNSAFE.objectFieldOffset(field), in.booleanValue());
    }
}
