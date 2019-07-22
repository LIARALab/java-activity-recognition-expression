package org.liara.expression


import org.liara.data.type.DataType
import spock.lang.Specification

class ConstantSpecification extends Specification {
  def "#Constant allows to instantiate a new constant value of a given type" () {
    expect: "to instantiate a new constant value of a given type"
    new Constant<>(DataType.nonNullBoolean(), true).value == true
    new Constant<>(DataType.nonNullBoolean(), true).resultType == DataType.nonNullBoolean()

    new Constant<>(DataType.nullableInteger(), null).value == null
    new Constant<>(DataType.nullableInteger(), null).resultType == DataType.nullableInteger()
  }

  def "#Constant allows to instantiate a copy of an existing constant" () {
    given: "a constant"
    final Constant<Integer> constant = new Constant<>(DataType.nonNullInteger(), 136)

    when: "we call the copy constructor"
    final Constant<Integer> copy = new Constant<>(constant)

    then: "we expect to get a copy of the given constant"
    copy.value == constant.value
    copy.resultType == constant.resultType
    !copy.is(constant)
  }
}
