package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class LongDataTypeSpecification
        extends DataTypeSpecification<Long> {
    @Override
    DataType<Long> getType() {
        return DataTypes.LONG
    }

    @Override
    Long getRandomValue() {
        return random.nextLong()
    }
}
