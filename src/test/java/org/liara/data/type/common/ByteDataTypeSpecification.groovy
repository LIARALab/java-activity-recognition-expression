package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes
import org.liara.support.generic.Generic
import org.liara.support.generic.Generics

class ByteDataTypeSpecification
        extends DataTypeSpecification<Byte> {
    @Override
    DataType<Byte> getType() {
        return DataTypes.BYTE
    }

    @Override
    Generic<Byte> getExpextedGeneric() {
        return Generics.BYTE
    }

    @Override
    Byte getRandomValue() {
        return (byte) random.nextInt()
    }
}
