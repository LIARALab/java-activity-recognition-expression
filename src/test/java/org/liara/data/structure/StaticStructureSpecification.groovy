package org.liara.data.structure


import org.liara.data.type.DataType
import spock.lang.Specification

class StaticStructureSpecification extends Specification {
  def "#StaticStructure allows to create a structure instance from an iterator of fields" () {
    given: "a static structure with some fields"
    final StaticRep structure = new StaticRep([
                                                            DataType.nonNullBoolean(),
                                                            DataType.nonNullByte(),
                                                            DataType.nullableLong()
    ].iterator())

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == DataType.nonNullBoolean()
    structure.getFields().get(1) == DataType.nonNullByte()
    structure.getFields().get(2) == DataType.nullableLong()

    structure.getBytes() == DataType.nonNullBoolean().bytes +
                           DataType.nonNullByte().bytes +
                           DataType.nullableLong().bytes

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == DataType.nonNullBoolean().bytes
    structure.getOffsets().get(2) == DataType.nonNullBoolean().bytes + DataType.nonNullByte().bytes
  }

  def "#StaticStructure allows to create a structure instance from an array of fields" () {
    given: "a static structure with some fields"
    final StaticRep structure = new StaticRep(
      (DataType[]) [DataType.nonNullBoolean(), DataType.nonNullByte(), DataType.nullableLong()]
    )

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == DataType.nonNullBoolean()
    structure.getFields().get(1) == DataType.nonNullByte()
    structure.getFields().get(2) == DataType.nullableLong()

    structure.getBytes() == DataType.nonNullBoolean().bytes +
      DataType.nonNullByte().bytes +
      DataType.nullableLong().bytes

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == DataType.nonNullBoolean().bytes
    structure.getOffsets().get(2) == DataType.nonNullBoolean().bytes + DataType.nonNullByte().bytes
  }

  def "#StaticStructure allows to create a structure instance from a variadic number of fields" () {
    given: "a static structure with some fields"
    final StaticRep structure = new StaticRep(
      DataType.nonNullBoolean(),
      DataType.nonNullByte(),
      DataType.nullableLong()
    )

    expect: "the resulting structure to be valid"
    structure.getFields().size == 3
    structure.getFields().get(0) == DataType.nonNullBoolean()
    structure.getFields().get(1) == DataType.nonNullByte()
    structure.getFields().get(2) == DataType.nullableLong()

    structure.getBytes() == DataType.nonNullBoolean().bytes +
      DataType.nonNullByte().bytes +
      DataType.nullableLong().bytes

    structure.getOffsets().get(0) == 0
    structure.getOffsets().get(1) == DataType.nonNullBoolean().bytes
    structure.getOffsets().get(2) == DataType.nonNullBoolean().bytes + DataType.nonNullByte().bytes
  }
}
