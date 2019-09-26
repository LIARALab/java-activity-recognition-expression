package org.liara.data.type.common

import org.liara.data.type.DataType
import org.liara.data.type.DataTypeSpecification
import org.liara.data.type.DataTypes

class CharacterDataTypeSpecification
        extends DataTypeSpecification<Character> {
    @Override
    DataType<Byte> getType() {
        return DataTypes.CHARACTER
    }

    @Override
    Character getRandomValue() {
        return (char) random.nextInt()
    }
}
