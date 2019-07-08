package org.liara.data;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.liara.data.bean.Bean;
import org.liara.data.cursor.Cursor;
import org.liara.data.cursor.StaticCursor;
import org.liara.data.mapping.BeanMapping;
import org.liara.data.structure.Structure;
import org.liara.data.type.Type;
import org.liara.support.view.View;

import java.io.IOException;

public class StructuredData
{
  @NonNull
  private Structure _structure;

  @NonNull
  private Data _data;

  @NonNull
  private Cursor _cursor;

  /**
   * Create a new structured data view over a data object.
   *
   * @param data A data object to wrap.
   * @param structure A structure to follow.
   */
  public StructuredData (@NonNull final Data data, @NonNull final Structure structure) {
    _data = data;
    _structure = structure;
    _cursor = new StaticCursor(data);
  }

  /**
   * Read a row of data.
   *
   * @param row Index of the row to read.
   * @param result An array to use for storing the result of this reading.
   *
   * @return The number of fields read.
   */
  public @NonNegative int read (@NonNegative final int row, @NonNull final Object[] result)
  throws IOException {
    return read(row, 0, result);
  }

  /**
   * Read a row of data.
   *
   * @param row Index of the row to read.
   * @param offset Number of fields to ignore before starting to read a row.
   * @param result An array to use for storing the result of this reading.
   *
   * @return The number of fields read.
   */
  public @NonNegative int read (
    @NonNegative final int row,
    @NonNegative final int offset,
    @NonNull final Object[] result
  ) throws IOException {
    @NonNull final View<@NonNull Type> fields = _structure.getFields();

    if (offset > fields.getSize()) {
      throw new Error(
        "Unable to read fields of the " + row + "th row of data after skipping " + offset +
        "field(s) because the number of fields to skip is greater than the current number of " +
        "fields of a row (" + fields.getSize() + ")."
      );
    }

    _cursor.setOffset(row * _structure.getSize() + _structure.getOffsets().get(offset));

    @NonNegative final int read = Math.min(result.length, fields.getSize() - offset);

    for (int field = 0; field < read; ++field) {
      result[field] = fields.get(offset + field).read(_cursor);
    }

    return read;
  }



  /**
   * Read the given row and store the result into a bean.
   *
   * @param row Index of the row to read.
   * @param instance Bean to update.
   * @param mapping Mapping to use for reading the bean state.
   */
  public void read (
    @NonNegative final int row,
    @NonNull final Object instance,
    @NonNegative final BeanMapping mapping
  ) throws IOException {
    @NonNull final Bean bean = mapping.getBean();
    @NonNull final View<@NonNull @NonNegative Integer> offsets = _structure.getOffsets();
    @NonNull final View<@NonNull Type> types = _structure.getFields();

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.isWritable(index) && mapping.isValueMapped(index)) {
        _cursor.setOffset(row * _structure.getSize() + offsets.get(mapping.getField(index)));
        bean.set(instance, index, types.get(mapping.getField(index)).read(_cursor));
      }
    }
  }

  /**
   * Read a field of a given row.
   *
   * @param row Index of the row to read.
   * @param field Index of the field to read.
   *
   * @return The field value.
   */
  public @Nullable Object read (@NonNegative final int row, @NonNegative final int field)
  throws IOException {
    _cursor.setOffset(row * _structure.getSize() + _structure.getOffsets().get(field));
    return _structure.getFields().get(field).read(_cursor);
  }

  /**
   * Write a row of data.
   *
   * @param row Index of the row to write.
   * @param values An array of values to set for each fields of the row.
   *
   * @return The number of fields written.
   */
  public @NonNegative int write (@NonNegative final int row, @NonNull final Object[] values)
  throws IOException {
    return write(row, 0, values);
  }

  /**
   * Write a row of data after skipping a given number of fields.
   *
   * @param row Index of the row to write.
   * @param offset Number of fields to skip before starting to write.
   * @param values An array of values to set for each fields of the row.
   *
   * @return The number of fields written.
   */
  public @NonNegative int write (
    @NonNegative final int row,
    @NonNegative final int offset,
    @NonNull final Object[] values
  ) throws IOException {
    @NonNull final View<@NonNull Type> fields = _structure.getFields();

    if (offset > fields.getSize()) {
      throw new Error(
        "Unable to write fields of the " + row + "th row of data after skipping " + offset +
        "field(s) because the number of fields to skip is greater than the current number of " +
        "fields of a row (" + fields.getSize() + ")."
      );
    }

    if (row >= getSize()) setSize(row + 1);

    _cursor.setOffset(row * _structure.getSize() + _structure.getOffsets().get(offset));

    @NonNegative final int written = Math.min(values.length, fields.getSize() - offset);

    for (int field = 0; field < written; ++field) {
      write((Type<?>) fields.get(offset + field), values[field]);
    }

    return written;
  }

  /**
   * Write the given bean at the given location.
   *
   * @param row Index of the row to write.
   * @param instance Bean to write.
   * @param mapping Mapping to use for writing the bean state.
   */
  public void write (
    @NonNegative final int row,
    @NonNull final Object instance,
    @NonNegative final BeanMapping mapping
  ) throws IOException {
    @NonNull final Bean bean = mapping.getBean();

    if (row >= getSize()) setSize(row + 1);

    @NonNull final View<@NonNull @NonNegative Integer> offsets = _structure.getOffsets();
    @NonNull final View<@NonNull Type> types = _structure.getFields();

    for (int index = 0, size = bean.getAttributes().getSize(); index < size; ++index) {
      if (bean.isReadable(index) && mapping.isValueMapped(index)) {
        _cursor.setOffset(row * _structure.getSize() + offsets.get(mapping.getField(index)));
        write((Type<?>) types.get(mapping.getField(index)), bean.get(instance, index));
      }
    }
  }

  /**
   * Update a value of a row.
   *
   * @param row Index of the row to write.
   * @param field Index of the field to write.
   * @param value The value to write.
   */
  public void write (
    @NonNegative final int row,
    @NonNegative final int field,
    @NonNull final Object value
  ) throws IOException {
    @NonNull final View<@NonNull Type> fields = _structure.getFields();

    if (field >= fields.getSize()) {
      throw new Error(
        "Unable to write field " + field + "  of the" + row + "th row of data because the given " +
        "field does not exists."
      );
    }

    if (row >= getSize()) setSize(row + 1);

    _cursor.setOffset(row * _structure.getSize() + _structure.getOffsets().get(field));
    write((Type<?>) fields.get(field), value);
  }

  /**
   * Write a given value at the current index position. An helper for generics.
   *
   * @param type Type of value to write at the current position.
   * @param value Value to write at the current position.
   * @param <T> Java type that store the value to write.
   *
   * @throws IOException If an error occurs during the update of the underlying data object.
   */
  private <T> void write (@NonNull final Type<T> type, @Nullable final Object value)
  throws IOException {
    type.write(type.getJavaClass().cast(value), _cursor);
  }

  /**
   * Writing a new row of data at the moveToEnd of the current set of rows.
   *
   * @param fields Values to set for each fields of the row.
   *
   * @return The number of fields written.
   */
  public @NonNegative int add (@NonNull final Object[] fields)
  throws IOException {
    return write(getSize(), fields);
  }

  /**
   * Copy some rows of the underlying data object.
   *
   * @param source The index of the row from which the copy operation read.
   * @param destination The index of the row from which the copy operation write.
   * @param length The number of rows to copy.
   *
   * @return The number of rows effectively copied.
   */
  public @NonNegative int copy (
    @NonNegative final int source,
    @NonNegative final int destination,
    @NonNegative final int length
  ) {
    return _data.copy(
      source * _structure.getSize(),
      destination * _structure.getSize(),
      length * _structure.getSize()
    ) / _structure.getSize();
  }

  /**
   * Return the offset in bytes from the moveToStart of the underlying data object to the beginning of
   * the given row.
   *
   * @param row A row index.
   *
   * @return The offset in bytes from the moveToStart of the underlying data object to the beginning of
   *         the given row.
   */
  public @NonNegative int getOffset (@NonNegative final int row) {
    return _structure.getSize() * row;
  }

  /**
   * Return the offset in bytes from the moveToStart of the underlying data object to the beginning of
   * the given field of the given row.
   *
   * @param row A row index.
   * @param field A field index.
   *
   * @return The offset in bytes from the moveToStart of the underlying data object to the beginning of
   *         the given field of the given row.
   */
  public @NonNegative int getOffset (@NonNegative final int row, @NonNegative final int field) {
    return _structure.getSize() * row + _structure.getOffsets().get(field);
  }

  /**
   * Reallocate the underlying data object capacity in order to store more or less rows.
   *
   * @param capacity The new underlying data object capacity to allocate, in rows.
   */
  public void reallocate (@NonNegative final int capacity) {
    _data.reallocate(capacity * _structure.getSize());
  }

  /**
   * Fit the underlying data object capacity to its current number of rows.
   */
  public void fit () {
    _data.reallocate(_structure.getSize() * getSize());
  }

  /**
   * @return The row structure followed by this view.
   */
  public @NonNull Structure getStructure () {
    return _structure;
  }

  /**
   * Update the row structure.
   *
   * As a consequence of this operation, the underlying data may change and may also be incoherent /
   * or corrupted.
   *
   * @param structure The new row structure to follow.
   */
  public void setStructure (@NonNull final Structure structure) {
    _structure = structure;
  }

  /**
   * @return The current number of rows stored into the underlying data object.
   */
  public @NonNegative int getSize () {
    return _data.getSize() / _structure.getSize();
  }

  /**
   * Update the size of this structure in rows.
   *
   * @param size The new number of rows stored into this structure.
   */
  public void setSize (@NonNegative final int size) {
    _data.setSize(size * _structure.getSize());
  }

  /**
   * @return The maximum capacity, in rows, of the underlying data object.
   */
  public @NonNegative int getCapacity () {
    return _data.getCapacity() / _structure.getSize();
  }

  /**
   * @return The underlying binary data object.
   */
  public @NonNull Data getData () {
    return _data;
  }

  /**
   * Change the underlying binary data object.
   *
   * As a consequence of this operation, the underlying data may change and may also be incoherent /
   * or corrupted.
   *
   * @param data The new underlying data object.
   */
  public void setData (@NonNull final Data data) {
    _data = data;
    _cursor.setTarget(_data);
  }
}
