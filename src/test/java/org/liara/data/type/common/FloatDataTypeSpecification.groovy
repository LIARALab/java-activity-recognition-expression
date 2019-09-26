package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class FloatDataTypeSpecification
        extends DataTypeSpecification<Float> {
    @Override
    DataType<Float> getType() {
        return DataTypes.FLOAT
    }

    @Override
    Float getRandomValue() {
        return random.nextFloat()
    }
}
