package org.liara.data.type.resolver;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.DataType;
import org.liara.data.type.DataTypes;

import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;

public class CommonTypeResolver
  implements TypeResolver
{
  @NonNull
  public static final CommonTypeResolver INSTANCE = new CommonTypeResolver();

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable DataType> NON_NULL_BEHAVIOR;

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable DataType> NULLABLE_BEHAVIOR;

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable DataType> DEFAULT_BEHAVIOR;

  static {
    NON_NULL_BEHAVIOR = new HashMap<>(15);
    NULLABLE_BEHAVIOR = new HashMap<>(7);
    DEFAULT_BEHAVIOR = new HashMap<>(15);

    NON_NULL_BEHAVIOR.put(byte.class, DataTypes.BYTE);
    NON_NULL_BEHAVIOR.put(boolean.class, DataTypes.BOOLEAN);
    NON_NULL_BEHAVIOR.put(short.class, DataTypes.SHORT);
    NON_NULL_BEHAVIOR.put(int.class, DataTypes.INTEGER);
    NON_NULL_BEHAVIOR.put(long.class, DataTypes.LONG);
    NON_NULL_BEHAVIOR.put(float.class, DataTypes.FLOAT);
    NON_NULL_BEHAVIOR.put(double.class, DataTypes.DOUBLE);
    NON_NULL_BEHAVIOR.put(char.class, DataTypes.CHARACTER);
    NON_NULL_BEHAVIOR.put(Byte.class, DataTypes.NULLABLE_BYTE);
    NON_NULL_BEHAVIOR.put(Boolean.class, DataTypes.NULLABLE_BOOLEAN);
    NON_NULL_BEHAVIOR.put(Short.class, DataTypes.NULLABLE_SHORT);
    NON_NULL_BEHAVIOR.put(Integer.class, DataTypes.NULLABLE_INTEGER);
    NON_NULL_BEHAVIOR.put(Float.class, DataTypes.NULLABLE_LONG);
    NON_NULL_BEHAVIOR.put(Double.class, DataTypes.NULLABLE_FLOAT);
    NON_NULL_BEHAVIOR.put(Character.class, DataTypes.NULLABLE_DOUBLE);

    NULLABLE_BEHAVIOR.put(Byte.class, DataTypes.NULLABLE_BYTE);
    NULLABLE_BEHAVIOR.put(Boolean.class, DataTypes.NULLABLE_BOOLEAN);
    NULLABLE_BEHAVIOR.put(Short.class, DataTypes.NULLABLE_SHORT);
    NULLABLE_BEHAVIOR.put(Integer.class, DataTypes.NULLABLE_INTEGER);
    NULLABLE_BEHAVIOR.put(Float.class, DataTypes.NULLABLE_LONG);
    NULLABLE_BEHAVIOR.put(Double.class, DataTypes.NULLABLE_FLOAT);
    NULLABLE_BEHAVIOR.put(Character.class, DataTypes.NULLABLE_DOUBLE);

    DEFAULT_BEHAVIOR.putAll(NON_NULL_BEHAVIOR);
    DEFAULT_BEHAVIOR.putAll(NULLABLE_BEHAVIOR);
  }

  @Override
  public @Nullable DataType resolve (final java.lang.reflect.@NonNull Type type) {
    System.out.println(type.toString());

    if (type instanceof Class) {
      return DEFAULT_BEHAVIOR.getOrDefault(type, null);
    } else if (type instanceof AnnotatedType) {
      @NonNull final AnnotatedType annotatedType = (AnnotatedType) type;

      if (annotatedType.isAnnotationPresent(NonNull.class)) {
        return NON_NULL_BEHAVIOR.getOrDefault(annotatedType.getType(), null);
      } else if (annotatedType.isAnnotationPresent(Nullable.class)) {
        return NULLABLE_BEHAVIOR.getOrDefault(annotatedType.getType(), null);
      }
    }

    return null;
  }
}
