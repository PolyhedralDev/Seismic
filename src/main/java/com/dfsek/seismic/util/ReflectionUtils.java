package com.dfsek.seismic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    private static final ConcurrentHashMap<String, Class<?>> reflectedClasses = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<ClassMethod, Method> reflectedMethods = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<ClassField, Field> reflectedFields = new ConcurrentHashMap<>();

    /**
     * Retrieves the class object associated with the given class name.
     *
     * @param className the name of the class
     *
     * @return the class object for the given class name
     */
    public static Class<?> getClass(String className) {
        return ReflectionUtils.reflectedClasses.computeIfAbsent(className, ReflectionUtils::getReflectedClass);
    }

    /**
     * Retrieves the method object for the given class and method name.
     *
     * @param clss  the class containing the method
     * @param mthod the name of the method
     *
     * @return the method object for the given class and method name
     */
    public static Method getMethod(Class<?> clss, String mthod) {
        return ReflectionUtils.reflectedMethods.computeIfAbsent(new ClassMethod(clss, mthod), ReflectionUtils::getReflectedMethod);
    }

    /**
     * Retrieves the field object for the given class and field name.
     *
     * @param clss the class containing the field
     * @param fild the name of the field
     *
     * @return the field object for the given class and field name
     */
    public static Field getField(Class<?> clss, String fild) {
        return ReflectionUtils.reflectedFields.computeIfAbsent(new ClassField(clss, fild), ReflectionUtils::getReflectedField);
    }


    /**
     * Sets the specified method to be accessible, bypassing Java access control checks.
     *
     * @param mthod the method to set to public
     */
    public static void setMethodToPublic(Method mthod) {
        ReflectionUtils.setAccessibleObjectToPublic(mthod);
    }

    /**
     * Sets the specified field to be accessible, bypassing Java access control checks.
     *
     * @param fild the field to set to public
     */
    public static void setFieldToPublic(Field fild) {
        ReflectionUtils.setAccessibleObjectToPublic(fild);
    }

    private static void setAccessibleObjectToPublic(AccessibleObject obj) {
        try {
            obj.setAccessible(true);
        } catch(SecurityException se) {
            try {
                Class.forName("java.security.AccessController");
                AccessControllerUtils.doPrivileged(() -> {
                    obj.setAccessible(true);
                    return null;
                });
            } catch(Exception e) {
                if(UnsafeUtils.canUseUnsafe) {
                    UnsafeUtils.putFieldBoolean(obj, ReflectionUtils.getField(obj.getClass(), "override"), Boolean.TRUE);
                } else {
                    ReflectionUtils.LOGGER.error("Failed to set field to public: {}", obj);
                }
            }
        }
    }

    private static Field getReflectedField(ClassField clssfild) {
        try {
            return clssfild.clss.getField(clssfild.fild());
        } catch(NoSuchFieldException e) {
            ReflectionUtils.LOGGER.error("Field {} not found in class {}", clssfild.fild(), clssfild.clss.getName());
        }
        return null;
    }

    private static Method getReflectedMethod(ClassMethod clssmthod) {
        try {
            return clssmthod.clss.getMethod(clssmthod.mthod);
        } catch(NoSuchMethodException e) {
            ReflectionUtils.LOGGER.error("Method {} not found in class {}", clssmthod.mthod, clssmthod.clss.getName());
        }
        return null;
    }

    private static Class<?> getReflectedClass(String className) {
        try {
            Class<?> classObj;
            int $loc = className.indexOf('$');
            if($loc > -1) {
                classObj = ReflectionUtils.getNestedClass(Class.forName(className.substring(0, $loc)), className.substring($loc + 1));
            } else {
                classObj = Class.forName(className);
            }
            assert classObj != null;
            return classObj;
        } catch(ClassNotFoundException e) {
            ReflectionUtils.LOGGER.error("Class {} not found", className);
        }
        return null;
    }

    private static Class<?> getNestedClass(Class<?> upperClass, String nestedClassName) {
        Class<?>[] classObjArr = upperClass.getDeclaredClasses();
        for(Class<?> classArrObj : classObjArr) {
            if(classArrObj.getName().equals(upperClass.getName() + "$" + nestedClassName)) {
                return classArrObj;
            }
        }
        return null;
    }

    /**
     * Executes the given operation if the specified annotation is present on the element.
     *
     * @param <T>        the type of the annotation
     * @param element    the annotated element
     * @param annotation the annotation class
     * @param operation  the operation to execute if the annotation is present
     */
    public static <T extends Annotation> void ifAnnotationPresent(AnnotatedElement element, Class<? extends T> annotation,
                                                                  Consumer<T> operation) {
        T a = element.getAnnotation(annotation);
        if(a != null) operation.accept(a);
    }

    /**
     * Returns the raw type of the given type.
     *
     * @param type the type to get the raw type of
     *
     * @return the raw type of the given type
     */
    public static Class<?> getRawType(Type type) {
        switch(type) {
            case Class<?> aClass -> {
                return aClass;
            }
            case ParameterizedType parameterizedType -> {
                Type rawType = parameterizedType.getRawType();
                return (Class<?>) rawType;
            }
            case GenericArrayType genericArrayType -> {
                Type componentType = genericArrayType.getGenericComponentType();
                return Array.newInstance(ReflectionUtils.getRawType(componentType), 0).getClass();
            }
            case TypeVariable<?> ignored -> {
                return Object.class;
            }
            case WildcardType wildcardType -> {
                return ReflectionUtils.getRawType(wildcardType.getUpperBounds()[0]);
            }
            case null, default -> {
                String className = type == null ? "null" : type.getClass().getName();
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                                                   + "GenericArrayType, but <" + type + "> is of type " + className);
            }
        }
    }

    /**
     * Converts the given type to a string representation.
     *
     * @param type the type to convert to a string
     *
     * @return the string representation of the type
     */
    public static String typeToString(Type type) {
        return type instanceof Class<?> ? ((Class<?>) type).getName() : type.toString();
    }

    /**
     * Compares two types for equality.
     *
     * @param a the first type to compare
     * @param b the second type to compare
     *
     * @return true if the types are equal, false otherwise
     */
    public static boolean equals(Type a, Type b) {
        if(a == b) {
            return true;
        } else if(a instanceof Class) {
            return a.equals(b);
        } else if(a instanceof ParameterizedType pa) {
            if(!(b instanceof ParameterizedType pb)) {
                return false;
            }

            return Objects.equals(pa.getOwnerType(), pb.getOwnerType())
                   && pa.getRawType().equals(pb.getRawType())
                   && Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments());
        } else if(a instanceof GenericArrayType ga) {
            if(!(b instanceof GenericArrayType gb)) {
                return false;
            }

            return ReflectionUtils.equals(ga.getGenericComponentType(), gb.getGenericComponentType());
        } else if(a instanceof WildcardType wa) {
            if(!(b instanceof WildcardType wb)) {
                return false;
            }

            return Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())
                   && Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds());
        } else if(a instanceof TypeVariable<?> va) {
            if(!(b instanceof TypeVariable<?> vb)) {
                return false;
            }
            return va.getGenericDeclaration() == vb.getGenericDeclaration()
                   && va.getName().equals(vb.getName());
        } else {
            return false;
        }
    }

    private record ClassField(Class<?> clss, String fild) {
    }


    private record ClassMethod(Class<?> clss, String mthod) {
    }
}
