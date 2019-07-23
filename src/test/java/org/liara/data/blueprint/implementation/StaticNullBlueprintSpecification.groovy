package org.liara.data.blueprint.implementation

import org.liara.data.blueprint.builder.BlueprintBuildingContext
import org.liara.data.blueprint.builder.BlueprintElementBuilder
import org.mockito.Mockito
import spock.lang.Specification

class StaticNullBlueprintSpecification
  extends Specification {
  def "#StaticNullBlueprint allows to instantiate a null value blueprint for a given blueprint" () {
    given: "a model"
    final StaticBlueprint model = Mockito.mock(StaticBlueprint.class)

    and: "a builder"
    final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)

    and: "a building context"
    final BlueprintBuildingContext context = Mockito.mock(BlueprintBuildingContext.class)
    Mockito.doReturn(10).when(context).getIdentifier(builder)
    Mockito.when(context.getBlueprint()).thenReturn(model)

    when: "we call #StaticNullBlueprint with the given context"
    final StaticNullBlueprint element = new StaticNullBlueprint(context, builder)

    then: "we expect to instantiate a valid blueprint element"
    element.getBlueprint() == model
    element.getIdentifier() == 10
    element.getChildren().empty
  }
}
