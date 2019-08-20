package org.liara.data.blueprint.implementation

import org.liara.data.blueprint.builder.BlueprintBuildingContext
import org.liara.data.blueprint.builder.StaticValueBlueprintBuilder
import org.liara.data.primitive.Primitives
import org.mockito.Mockito
import spock.lang.Specification

class StaticValueBlueprintSpecification
        extends Specification {
    def "#StaticValueBlueprint allows to instantiate a value blueprint for a given blueprint"() {
        given: "a model"
        final StaticBlueprint model = Mockito.mock(StaticBlueprint.class)

        and: "a builder"
        final StaticValueBlueprintBuilder builder = new StaticValueBlueprintBuilder()
        builder.setType(Primitives.FLOAT)

        and: "a building context"
        final BlueprintBuildingContext context = Mockito.mock(BlueprintBuildingContext.class)
        Mockito.doReturn(10).when(context).getIdentifier(builder)
        Mockito.when(context.getBlueprint()).thenReturn(model)

        when: "we call #StaticValueBlueprint with the given context"
        final StaticValueBlueprint element = new StaticValueBlueprint(context, builder)

        then: "we expect to instantiate a valid blueprint element"
        element.getBlueprint() == model
        element.getType() == Primitives.FLOAT
        element.getIdentifier() == 10
        element.getChildren().empty
    }
}
