package org.liara.data

import spock.lang.Specification

class DataSpecification extends Specification {
  private Random _random = new Random()

  byte[] getArbitraryContent (int size) {
    final byte[] content = new byte[size]
    _random.nextBytes(content)
    return content
  }

  def "#DataSpecification instantiate an empty data object with a given capacity" () {
    expect: "#DataSpecification constructor to be able to instantiate empty data object with a given capacity"
    new Data(125).capacity == 125
    new Data(2000).capacity == 2000
    new Data(125).size == 0
    new Data(2000).size == 0
  }

  def "#DataSpecification instantiate a copy of an existing data object" () {
    given: "a source data object initialized with arbitrary content"
    final Data source = new Data(125)
    source.write(getArbitraryContent(110))

    when: "we instantiate a copy"
    final Data copy = new Data(source)

    then: "we expect to have a copy of the source data object"
    copy.capacity == source.capacity
    copy.size == source.size
    Data.equals(copy, source)
    !copy.is(source)
  }

  def "#read allows to copy the data object inner content to an existing buffer" () {
    given: "a data object initialized with arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(110)
    data.write(content)

    when: "we want to read some content"
    final byte[] readContent = new byte[80]
    final int readBytes = data.read(20, readContent)

    then: "we expect to get a slice of the data object inner content"
    Arrays.equals(content, 20, 20 + 80, readContent, 0, 80)
    readBytes == 80
  }

  def "#read may partially fill the given buffer when the buffer is bigger than the content to read" () {
    given: "a data object initialized with arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(110)
    data.write(content)

    when: "we want to read some content with a buffer bigger than the content to get"
    final byte[] readContent = new byte[80]
    final int readBytes = data.read(60, readContent)

    then: "we expect to get a slice of the data object inner content"
    Arrays.equals(content, 60, 110, readContent, 0, 110 - 60)
    readBytes == 110 - 60
  }

  def "#read throws an error if you trying to use an offset greater than or equal to the current data object size" () {
    given: "a data object initialized with arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(110)
    data.write(content)

    when: "we try to read some content from an invalid location"
    data.read(130, new byte[80])

    then: "we expect that the data objects throws an error"
    thrown(IllegalArgumentException.class)
  }

  def "#write allows to write some content into the data object" () {
    given: "an empty data object"
    final Data data = new Data(125)

    and: "some arbitrary content"
    final byte[] content = getArbitraryContent(50)

    when: "we write the given content into the empty data object"
    final int wroteBytes = data.write(content)

    then: "we expect that the given content was written into the given data object"
    data.size == content.length
    wroteBytes == content.length

    final byte[] buffer = new byte[50]
    data.read(buffer)
    Arrays.equals(content, buffer)
  }

  def "#write allows to write some content at any location into the data object" () {
    given: "an empty data object"
    final Data data = new Data(125)

    and: "some arbitrary content"
    final byte[] content = getArbitraryContent(50)

    when: "we write the given content into the empty data object at a given location"
    final int wroteBytes = data.write(20, content)

    then: "we expect that the given content was written into the given data object"
    data.size == content.length + 20
    wroteBytes == content.length

    final byte[] buffer = new byte[50]
    data.read(20, buffer)
    Arrays.equals(content, buffer)

    final byte[] zeroes = new byte[20]
    data.read(0, zeroes)
    Arrays.equals(new byte[20], zeroes)
  }

  def "#write may not write the entire buffer if the buffer was bigger than the data object remaining free space" () {
    given: "an empty data object"
    final Data data = new Data(125)

    and: "some arbitrary content"
    final byte[] content = getArbitraryContent(50)

    when: "we write a content bigger than the remaining free space"
    final int wroteBytes = data.write(80, content)

    then: "we expect that the given content was partially written into the given data object"
    data.size == data.capacity
    wroteBytes == 125 - 80

    final byte[] buffer = new byte[125 - 80]
    data.read(80, buffer)
    Arrays.equals(content, 0, buffer.length, buffer, 0, buffer.length)
  }

  def "#write throw an error if you trying to write some content beyond the data object capacity" () {
    given: "an empty data object"
    final Data data = new Data(125)

    and: "some arbitrary content"
    final byte[] content = getArbitraryContent(50)

    when: "we write the given content into the empty data object at a given location"
    data.write(130, content)

    then: "we expect that the given content was written into the given data object"
    thrown(IllegalArgumentException.class)
  }

  def "#write allows to write only one byte" () {
    given: "an empty data object"
    final Data data = new Data(125)

    when: "we write the given content into the empty data object at a given location"
    final int wroteBytes = data.write(39, (byte) 10)

    then: "we expect that the given content was written into the given data object"
    wroteBytes == 1
    data.size == 40

    final byte[] content = new byte[40]
    data.read(content)

    Arrays.equals(content, 0, 39, new byte[39], 0, 39)
    content[39] == (byte) 10
  }

  def "#write throw an error if you trying to write only one byte beyond the data object capacity" () {
    given: "an empty data object"
    final Data data = new Data(125)

    when: "we write some content beyond the data object capacity"
    data.write(150, (byte) 10)

    then: "we expect that the given data object throws an error"
    thrown(IllegalArgumentException.class)
  }

  def "#reallocate allows to reallocate itself in order to expand its capacity" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we reallocate the data object in order to expand its capacity"
    data.reallocate(200)

    then: "we expect that the given data object grown"
    data.capacity == 200

    and: "we expect that the data state was successfully copied"
    data.size == 100

    final byte[] tmp = new byte[content.length]
    data.read(tmp)

    Arrays.equals(tmp, content)
  }

  def "#reallocate allows to reallocate itself in order to reduce its capacity" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we reallocate the data object in order to reduce its capacity"
    data.reallocate(100)

    then: "we expect that the given data object shrink"
    data.capacity == 100

    and: "we expect that the data state was successfully copied"
    data.size == 100

    final byte[] tmp = new byte[content.length]
    data.read(tmp)

    Arrays.equals(tmp, content)
  }

  def "#reallocate may truncate its content if during a capacity reduction if necessary" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we reallocate the data object in order to reduce its capacity"
    data.reallocate(50)

    then: "we expect that the given data object shrink"
    data.capacity == 50

    and: "we expect that the data state was successfully copied"
    data.size == 50

    final byte[] tmp = new byte[50]
    data.read(tmp)

    Arrays.equals(tmp, Arrays.copyOf(content, 50))
  }

  def "#copy allows to make copy operations" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we make a copy operation"
    final int copied = data.copy(70, 80, 50)

    then: "we expect that the given data object was successfully updated"
    copied == 30
    data.size == 110

    final byte[] tmp = new byte[copied]
    data.read(80, tmp)

    Arrays.equals(tmp, Arrays.copyOfRange(content, 70, 70 + copied))
  }

  def "#copy allows to make copy operations with a source overlapped by its destination" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we make a copy operation"
    final int copied = data.copy(70, 60, 50)

    then: "we expect that the given data object was successfully updated"
    copied == 30
    data.size == 100

    final byte[] tmp = new byte[copied]
    data.read(60, tmp)

    Arrays.equals(tmp, Arrays.copyOfRange(content, 70, 70 + copied))
  }

  def "#fit can reallocate the data object to fit its inner size" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we reallocate the data object to fit its inner size"
    data.fit()

    then: "we expect that the given data object fit its inner size"
    data.capacity == 100

    and: "we expect that the data state was successfully copied"
    data.size == 100

    final byte[] tmp = new byte[100]
    data.read(tmp)

    Arrays.equals(tmp, content)
  }

  def "#clear empty the data object of its content" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    when: "we clear the data object of its content"
    data.clear()

    then: "we expect that the given data object was clear"
    data.size == 0
  }

  def "static #equals returns true if a data object is compared to itself" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    expect: "the data object to be equals to itself"
    Data.equals(data, data)
    Data.equals(null, null)
  }

  def "static #equals returns false if the data object is compared to null" () {
    given: "a data object with some arbitrary content"
    final Data data = new Data(125)
    final byte[] content = getArbitraryContent(100)
    data.write(content)

    expect: "the data object not to be equals to null"
    !Data.equals(data, null)
    !Data.equals(null, data)
  }

  def "static #equals returns true if both data objects have the same content" () {
    given: "two data objects with the same arbitrary content"
    final Data bigger = new Data(300)
    final Data smaller = new Data(150)

    final byte[] content = getArbitraryContent(100)
    bigger.write(content)
    smaller.write(content)

    expect: "both objects to be equals"
    Data.equals(bigger, smaller)
  }

  def "static #equals return false if both data objects have different content" () {
    given: "two data objects with different content"
    final Data data = new Data(200)
    final Data different = new Data(200)

    final byte[] content = getArbitraryContent(100)
    data.write(content)

    content[23] = (byte) ~content[23]
    different.write(content)

    expect: "both objects not to be equals"
    !Data.equals(data, different)
  }

  def "static #equals return false if both data objects have different size" () {
    given: "two data objects with different sizes"
    final Data smaller = new Data(50)
    final Data bigger = new Data(100)

    final byte[] content = getArbitraryContent(100)
    smaller.write(content)
    bigger.write(content)

    expect: "both objects not to be equals"
    !Data.equals(smaller, bigger)
  }
}
