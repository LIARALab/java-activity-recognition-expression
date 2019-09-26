package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class ShortDataTypeSpecification
        extends DataTypeSpecification<Short> {
    @Override
    DataType<Short> getType() {
        return DataTypes.SHORT
    }

    @Override
    Short getRandomValue() {
        return (short) random.nextInt()
    }
}
