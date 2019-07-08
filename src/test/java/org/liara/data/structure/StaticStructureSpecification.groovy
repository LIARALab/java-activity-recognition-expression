package org.liara.data.structure

import org.liara.data.structure.StaticStructure
import org.liara.data.type.Type
import spock.lang.Specification

class StaticStructureSpecification extends Specification {
  def "#StaticStructure allows to create a structure instance from an iterator of fields" () {
    given: "a static structure with some fields"
    final StaticStructure structure = new StaticStructure([
      Type.nonNullBoolean(),
      Type.nonNullByte(),
      Type.nullableLong()
    ].iterator())

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == Type.nonNullBoolean()
    structure.getFields().get(1) == Type.nonNullByte()
    structure.getFields().get(2) == Type.nullableLong()

    structure.getSize() == Type.nonNullBoolean().size +
                           Type.nonNullByte().size +
                           Type.nullableLong().size

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == Type.nonNullBoolean().size
    structure.getOffsets().get(2) == Type.nonNullBoolean().size + Type.nonNullByte().size
  }

  def "#StaticStructure allows to create a structure instance from an array of fields" () {
    given: "a static structure with some fields"
    final StaticStructure structure = new StaticStructure(
      (Type[]) [Type.nonNullBoolean(), Type.nonNullByte(), Type.nullableLong()]
    )

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == Type.nonNullBoolean()
    structure.getFields().get(1) == Type.nonNullByte()
    structure.getFields().get(2) == Type.nullableLong()

    structure.getSize() == Type.nonNullBoolean().size +
      Type.nonNullByte().size +
      Type.nullableLong().size

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == Type.nonNullBoolean().size
    structure.getOffsets().get(2) == Type.nonNullBoolean().size + Type.nonNullByte().size
  }

  def "#StaticStructure allows to create a structure instance from a variadic number of fields" () {
    given: "a static structure with some fields"
    final StaticStructure structure = new StaticStructure(
      Type.nonNullBoolean(),
      Type.nonNullByte(),
      Type.nullableLong()
    )

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == Type.nonNullBoolean()
    structure.getFields().get(1) == Type.nonNullByte()
    structure.getFields().get(2) == Type.nullableLong()

    structure.getSize() == Type.nonNullBoolean().size +
      Type.nonNullByte().size +
      Type.nullableLong().size

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == Type.nonNullBoolean().size
    structure.getOffsets().get(2) == Type.nonNullBoolean().size + Type.nonNullByte().size
  }
}
