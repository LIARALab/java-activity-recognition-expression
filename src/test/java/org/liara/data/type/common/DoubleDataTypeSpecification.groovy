package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class DoubleDataTypeSpecification
        extends DataTypeSpecification<Double> {
    @Override
    DataType<Double> getType() {
        return DataTypes.DOUBLE
    }

    @Override
    Generic<Double> getExpextedGeneric() {
        return Generics.DOUBLE
    }

    @Override
    Double getRandomValue() {
        return random.nextDouble()
    }
}
