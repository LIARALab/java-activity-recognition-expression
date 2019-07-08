package org.liara.data.type.resolver;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.type.Type;

import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;

public class CommonTypeResolver
  implements TypeResolver
{
  @NonNull
  public static final CommonTypeResolver INSTANCE = new CommonTypeResolver();

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable Type> NON_NULL_BEHAVIOR;

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable Type> NULLABLE_BEHAVIOR;

  @NonNull
  private static final Map<java.lang.reflect.@NonNull Type, @Nullable Type> DEFAULT_BEHAVIOR;

  static {
    NON_NULL_BEHAVIOR = new HashMap<>(15);
    NULLABLE_BEHAVIOR = new HashMap<>(7);
    DEFAULT_BEHAVIOR = new HashMap<>(15);

    NON_NULL_BEHAVIOR.put(byte.class, Type.nonNullByte());
    NON_NULL_BEHAVIOR.put(boolean.class, Type.nonNullBoolean());
    NON_NULL_BEHAVIOR.put(short.class, Type.nonNullShort());
    NON_NULL_BEHAVIOR.put(int.class, Type.nonNullInteger());
    NON_NULL_BEHAVIOR.put(long.class, Type.nonNullLong());
    NON_NULL_BEHAVIOR.put(float.class, Type.nonNullFloat());
    NON_NULL_BEHAVIOR.put(double.class, Type.nonNullDouble());
    NON_NULL_BEHAVIOR.put(char.class, Type.nonNullCharacter());
    NON_NULL_BEHAVIOR.put(Byte.class, Type.nonNullByte());
    NON_NULL_BEHAVIOR.put(Boolean.class, Type.nonNullBoolean());
    NON_NULL_BEHAVIOR.put(Short.class, Type.nonNullShort());
    NON_NULL_BEHAVIOR.put(Integer.class, Type.nonNullInteger());
    NON_NULL_BEHAVIOR.put(Float.class, Type.nonNullLong());
    NON_NULL_BEHAVIOR.put(Double.class, Type.nonNullFloat());
    NON_NULL_BEHAVIOR.put(Character.class, Type.nonNullDouble());


    NULLABLE_BEHAVIOR.put(Byte.class, Type.nullableByte());
    NULLABLE_BEHAVIOR.put(Boolean.class, Type.nullableBoolean());
    NULLABLE_BEHAVIOR.put(Short.class, Type.nullableShort());
    NULLABLE_BEHAVIOR.put(Integer.class, Type.nullableInteger());
    NULLABLE_BEHAVIOR.put(Float.class, Type.nullableFloat());
    NULLABLE_BEHAVIOR.put(Double.class, Type.nullableDouble());
    NULLABLE_BEHAVIOR.put(Character.class, Type.nullableCharacter());

    DEFAULT_BEHAVIOR.putAll(NON_NULL_BEHAVIOR);
    DEFAULT_BEHAVIOR.putAll(NULLABLE_BEHAVIOR);
  }

  @Override
  public @Nullable Type resolve (final java.lang.reflect.@NonNull Type type) {
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
