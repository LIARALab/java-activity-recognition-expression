package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class IntegerDataTypeSpecification
        extends DataTypeSpecification<Integer> {
    @Override
    DataType<Integer> getType() {
        return DataTypes.INTEGER
    }

    @Override
    Integer getRandomValue() {
        return random.nextInt()
    }
}
