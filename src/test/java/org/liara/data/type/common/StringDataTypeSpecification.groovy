package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class StringDataTypeSpecification
        extends DataTypeSpecification<String> {
    @Override
    DataType<String> getType() {
        return DataTypes.string(100)
    }

    @Override
    String getRandomValue() {
        final int length = random.nextInt(60) + 20
        final char[] characters = new char[length]

        for (int index = 0; index < length; ++index) {
            characters[index] = (char) random.nextInt()
        }

        return new String(characters)
    }
}
