package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class BooleanDataTypeSpecification extends DataTypeSpecification<Boolean> {
    @Override
    DataType<Boolean> getType() {
        return DataTypes.BOOLEAN
    }

    @Override
    Generic<Boolean> getExpextedGeneric() {
        return Generics.BOOLEAN
    }

    @Override
    Boolean getRandomValue() {
        return random.nextBoolean()
    }
}
