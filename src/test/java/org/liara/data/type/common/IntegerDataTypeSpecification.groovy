package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class IntegerDataTypeSpecification
        extends DataTypeSpecification<Integer> {
    @Override
    DataType<Integer> getType() {
        return DataTypes.INTEGER
    }

    @Override
    Generic<Integer> getExpextedGeneric() {
        return Generics.INTEGER
    }

    @Override
    Integer getRandomValue() {
        return random.nextInt()
    }
}
