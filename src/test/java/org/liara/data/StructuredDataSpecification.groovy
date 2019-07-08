package org.liara.data

import org.liara.data.structure.Structure
import org.liara.data.type.Type
import spock.lang.Specification

class StructuredDataSpecification
  extends Specification {
  def "#StructuredDataView create a new structured view over a data object" () {
    given: "a data object"
    final Data data = new Data(200)

    and: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    when: "we instantiate a new structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    then: "we expect the view to have been successfully initialized"
    view.structure == structure
    view.data == data
  }

  def "#getSize return the number of rows stored into the view" () {
    given: "a data object"
    final Data data = new Data(200)

    and: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    when: "we instantiate a new structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    then: "we expect #getSize to return the number of rows stored into the view"
    view.size == 0

    when: "we update the size of the underlying data object to 3 rows"
    data.size = structure.size * 3

    then: "we expect #getSize to return 3"
    view.size == 3

    when: "we update the size of the underlying data object to 4.3 rows"
    data.size = (int) (structure.size * 4.3)

    then: "we expect #getSize to return 4"
    view.size == 4
  }

  def "#getCapacity return the maximum number of rows that the view can store" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    when: "we instantiate a new structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    then: "we expect #getCapacity to return the number of rows that the view can store"
    view.capacity == 10

    when: "we reallocate the capacity of the underlying data object to 3 rows"
    data.reallocate(structure.size * 3)

    then: "we expect #getCapacity to return 3"
    view.capacity == 3

    when: "we reallocate the underlying data object to 4.3 rows"
    data.reallocate((int) (structure.size * 4.3))

    then: "we expect #getCapacity to return 4"
    view.capacity == 4
  }

  def "#write allows to write rows of data" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we write some content into the view"
    final Object[] row = ["McMuffin", false, 10.3f, 5]
    final int written = view.write(0, row)

    then: "we expect the underlying buffer to have been updated in consequences"
    data.size == structure.size

    and: "we expect #write to have returned the number of fields written into the buffer"
    written == 4

    and: "we expect that the row is readable"
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)

    Arrays.equals(fields, row)
  }

  def "#write initialize empty rows if the row to write exceed the current size of the buffer" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we write some content for the fourth row of the view"
    final Object[] row = ["McMuffin", false, 10.3f, 5]
    final int written = view.write(3, row)

    then: "we expect the underlying buffer to have been updated in consequences"
    data.size == structure.size * 4

    and: "we expect #write to have returned the number of fields written into the buffer"
    written == 4

    and: "we expect that the row is readable"
    final Object[] empty = ["", null, 0f, 0]
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)
    Arrays.equals(fields, empty)
    view.read(1, fields)
    Arrays.equals(fields, empty)
    view.read(2, fields)
    Arrays.equals(fields, empty)
    view.read(3, fields)
    Arrays.equals(fields, row)
  }

  def "#write allows to partially rewrite a row" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we write some content on a row by using an offset"
    final Object[] row = [10.3f, 5]
    final int written = view.write(0, 2, row)

    then: "we expect the underlying buffer to have been updated in consequences"
    data.size == structure.size * 1

    and: "we expect #write to have returned the number of fields written into the buffer"
    written == 2

    and: "we expect that the row is readable"
    final Object[] result = ["", null, 10.3f, 5]
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)
    Arrays.equals(fields, result)
  }

  def "#write may not write the entire array of values" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we update a row with more values than needed"
    final Object[] row = ["Potato", null, 10.3f, 5, "owl", 569]
    final int written = view.write(0, row)

    then: "we expect the underlying buffer to have been updated in consequences"
    data.size == structure.size * 1

    and: "we expect #write to have returned the number of fields written into the buffer"
    written == 4

    and: "we expect that the row is readable"
    final Object[] result = ["Potato", null, 10.3f, 5]
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)
    Arrays.equals(fields, result)
  }

  def "#write can partially update a row" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we update a row with less values than needed"
    final Object[] row = ["Potato", true]
    final int written = view.write(0, row)

    then: "we expect the underlying buffer to have been updated in consequences"
    data.size == structure.size * 1

    and: "we expect #write to have returned the number of fields written into the buffer"
    written == 2

    and: "we expect that the row is readable"
    final Object[] result = ["Potato", true, 0f, 0]
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)
    Arrays.equals(fields, result)
  }

  def "#write throw an error if you trying to skip to much fields" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we update a row with an invalid offset"
    final Object[] row = ["Potato", true]
    view.write(10, row)

    then: "we expect #write to throw"
    thrown(Error.class)
  }

  def "#write can update only one field of a given row" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we update a field of the object"
    view.write(3, 1, false)

    then: "we expect the underlying data to have been updated"
    final Object[] empty = ["", null, 0f, 0]
    final Object[] row = ["", false, 0f, 0]
    final Object[] fields = new Object[structure.fields.size]
    view.read(0, fields)
    Arrays.equals(fields, empty)
    view.read(1, fields)
    Arrays.equals(fields, empty)
    view.read(2, fields)
    Arrays.equals(fields, empty)
    view.read(3, fields)
    Arrays.equals(fields, row)
  }

  def "#write throw an error if you trying to update a field that does not exists" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object"
    final StructuredData view = new StructuredData(data, structure)

    when: "we update a field that does not exists"
    view.write(3, 8, false)

    then: "we expect #write to throw"
    thrown(Error.class)
  }

  def "#copy allows to copy some bunch of rows" () {
    given: "a row structure"
    final Structure structure = Structure.of(
      Type.nonNullString(10),
      Type.nullableBoolean(),
      Type.nonNullFloat(),
      Type.nonNullInteger()
    )

    and: "a data object that can contains 10 rows"
    final Data data = new Data(10 * structure.size)

    and: "a structured view over the given data object with some rows"
    final Object[][] rows = [
      ["Potato", false, 5.3f, 20],
      ["Owl", true, 6f, 5],
      ["Kayak", false, 8f, 12],
      ["Pwet", null, 5f, 26],
      ["Rat", null, 10f, 18]
    ]

    final StructuredData view = new StructuredData(data, structure)

    for (final Object[] row : rows) {
      view.add(row)
    }

    when: "we copy some rows"
    view.copy(2, 1, 3)

    then: "we expect the data to have been updated accordingly"
    final Object[] read = new Object[structure.fields.size]
    view.read(0, read)
    Arrays.equals(read, rows[0])
    view.read(1, read)
    Arrays.equals(read, rows[2])
    view.read(2, read)
    Arrays.equals(read, rows[3])
    view.read(3, read)
    Arrays.equals(read, rows[4])
    view.read(4, read)
    Arrays.equals(read, rows[4])
  }
}
