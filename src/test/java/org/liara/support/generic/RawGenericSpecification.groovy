package org.liara.support.generic

import org.checkerframework.checker.index.qual.NonNegative
import org.checkerframework.checker.nullness.qual.NonNull
import org.checkerframework.checker.nullness.qual.Nullable
import org.checkerframework.common.value.qual.MinLen
import spock.lang.Specification

class RawGenericSpecification
        extends Specification {
    def "#getAnnotations always return an empty view"() {
        expect: "#getAnnotations to always returns an empty view"
        new RawGeneric<Integer>(Integer.class).annotations.empty
        new RawGeneric<List>(List.class).annotations.empty
        new RawGeneric<String>(String.class).annotations.empty
        new RawGeneric<Float>(Float.class).annotations.empty
    }

    def "#getType always return the underlying type"() {
        expect: "#getType to always return the underlying type"
        new RawGeneric<Integer>(Integer.class).type == Integer.class
        new RawGeneric<List>(List.class).type == List.class
        new RawGeneric<String>(String.class).type == String.class
        new RawGeneric<Float>(Float.class).type == Float.class
    }

    def "#getAnnotation always return null"() {
        expect: "#getAnnotation to always return null"
        new RawGeneric<Integer>(Integer.class).getAnnotation(NonNull.class) == null
        new RawGeneric<Integer>(Integer.class).getAnnotation(Nullable.class) == null
        new RawGeneric<Integer>(Integer.class).getAnnotation(NonNull.class) == null
        new RawGeneric<Integer>(Float.class).getAnnotation(NonNull.class) == null
        new RawGeneric<Integer>(Float.class).getAnnotation(Nullable.class) == null
        new RawGeneric<Integer>(Float.class).getAnnotation(NonNegative.class) == null
        new RawGeneric<Integer>(List.class).getAnnotation(NonNull.class) == null
        new RawGeneric<Integer>(List.class).getAnnotation(Nullable.class) == null
        new RawGeneric<Integer>(String.class).getAnnotation(NonNull.class) == null
        new RawGeneric<Integer>(String.class).getAnnotation(Nullable.class) == null
        new RawGeneric<Integer>(String.class).getAnnotation(MinLen.class) == null
    }

    def "#isAnnotationPresent always return false"() {
        expect: "#isAnnotationPresent to always return false"
        !new RawGeneric<Integer>(Integer.class).isAnnotationPresent(NonNull.class)
        !new RawGeneric<Integer>(Integer.class).isAnnotationPresent(Nullable.class)
        !new RawGeneric<Integer>(Integer.class).isAnnotationPresent(NonNull.class)
        !new RawGeneric<Integer>(Float.class).isAnnotationPresent(NonNull.class)
        !new RawGeneric<Integer>(Float.class).isAnnotationPresent(Nullable.class)
        !new RawGeneric<Integer>(Float.class).isAnnotationPresent(NonNegative.class)
        !new RawGeneric<Integer>(List.class).isAnnotationPresent(NonNull.class)
        !new RawGeneric<Integer>(List.class).isAnnotationPresent(Nullable.class)
        !new RawGeneric<Integer>(String.class).isAnnotationPresent(NonNull.class)
        !new RawGeneric<Integer>(String.class).isAnnotationPresent(Nullable.class)
        !new RawGeneric<Integer>(String.class).isAnnotationPresent(MinLen.class)
    }
}
