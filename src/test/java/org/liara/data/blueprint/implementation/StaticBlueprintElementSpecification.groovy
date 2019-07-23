package org.liara.data.blueprint.implementation

import org.liara.data.blueprint.BlueprintElement
import org.liara.data.blueprint.ModelElementBuildingContext
import org.liara.data.blueprint.builder.BlueprintBuildingContext
import org.liara.data.blueprint.builder.BlueprintElementBuilder
import org.mockito.Mockito
import spock.lang.Specification

class StaticBlueprintElementSpecification
  extends Specification {
  StaticBlueprintElement buildElement () {
    final StaticBlueprint model = Mockito.mock(StaticBlueprint.class)

    final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)

    final BlueprintBuildingContext context = Mockito.mock(BlueprintBuildingContext.class)
    Mockito.doReturn(10).when(context).getIdentifier(builder)
    Mockito.when(context.getBlueprint()).thenReturn(model)

    return buildElement(context, builder)
  }

  StaticBlueprintElement buildElement (
    final BlueprintBuildingContext context,
    final BlueprintElementBuilder builder
  ) {
    return new StaticBlueprintElement(context, builder)
  }

  def "#StaticBlueprintElement allows to instantiate an element for a given blueprint" () {
    given: "a model"
    final StaticBlueprint model = Mockito.mock(StaticBlueprint.class)

    and: "a builder"
    final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)

    and: "a building context"
    final BlueprintBuildingContext context = Mockito.mock(BlueprintBuildingContext.class)
    Mockito.doReturn(10).when(context).getIdentifier(builder)
    Mockito.when(context.getBlueprint()).thenReturn(model)

    when: "we call #StaticBlueprintElement with the given context and builder"
    final StaticBlueprintElement element = new StaticBlueprintElement(context, builder)

    then: "we expect to instantiate a valid blueprint element"
    element.getBlueprint() == model
    element.getIdentifier() == 10
  }

  def "#getParent return the current element parent from the parent blueprint object" () {
    given: "a StaticBlueprintElement"
    final StaticBlueprintElement element = buildElement()

    and: "a parent"
    final BlueprintElement parent = Mockito.mock(BlueprintElement.class)
    Mockito.when(element.getBlueprint().getParentOf(element)).thenReturn(parent)

    expect: "to get a valid parent from the StaticBlueprintElement"
    element.getParent() == parent
  }

  def "#getChildren always return an empty children view" () {
    given: "a StaticBlueprintElement"
    final StaticBlueprintElement element = buildElement()

    expect: "to get an empty children view when we call #getChildren"
    element.getChildren().empty
  }
}
