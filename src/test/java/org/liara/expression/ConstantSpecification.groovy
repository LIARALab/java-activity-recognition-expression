package org.liara.expression

import org.liara.data.primitive.Primitives
import spock.lang.Specification

class ConstantSpecification extends Specification {
    def "#Constant allows to instantiate a new constant value of a given type"() {
        expect: "to instantiate a new constant value of a given type"
        new Constant<>(Primitives.BOOLEAN, true).value
        new Constant<>(Primitives.BOOLEAN, true).resultType == Primitives.BOOLEAN

        new Constant<>(Primitives.NULLABLE_INTEGER, null).value == null
        new Constant<>(Primitives.NULLABLE_INTEGER, null).resultType == Primitives.NULLABLE_INTEGER
    }

    def "#Constant allows to instantiate a copy of an existing constant"() {
        given: "a constant"
        final Constant<Integer> constant = new Constant<>(Primitives.INTEGER, 136)

        when: "we call the copy constructor"
        final Constant<Integer> copy = new Constant<>(constant)

        then: "we expect to get a copy of the given constant"
        copy.value == constant.value
        copy.resultType == constant.resultType
        !copy.is(constant)
    }
}
