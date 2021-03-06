package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class BooleanDataTypeSpecification extends DataTypeSpecification<Boolean> {
    @Override
    DataType<Boolean> getType() {
        return DataTypes.BOOLEAN
    }

    @Override
    Boolean getRandomValue() {
        return random.nextBoolean()
    }
}
