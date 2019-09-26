package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class DoubleDataTypeSpecification
        extends DataTypeSpecification<Double> {
    @Override
    DataType<Double> getType() {
        return DataTypes.DOUBLE
    }

    @Override
    Double getRandomValue() {
        return random.nextDouble()
    }
}
