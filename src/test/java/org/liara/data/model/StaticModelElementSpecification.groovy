package org.liara.data.model

import org.mockito.Mockito
import spock.lang.Specification

class StaticModelElementSpecification extends Specification {
  def "#StaticModelElement allows to instantiate an element for a given model" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    when: "we call #StaticModelElement with the given context"
    final StaticModelElement element = new StaticModelElement(context)

    then: "we expect to instantiate a valid model element"
    element.getModel() == model
    element.getIdentifier() == identifier
  }

  def "#getParent return the current element parent from the parent model object" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(10)
    Mockito.when(context.getModel()).thenReturn(model)

    and: "a valid StaticModelElement"
    final StaticModelElement element = new StaticModelElement(context)

    and: "a parent"
    final ModelElement parent = Mockito.mock(ModelElement.class)
    Mockito.when(model.getParentOf(element)).thenReturn(parent)

    expect: "to get a valid parent from the StaticModelElement"
    element.getParent() == parent
  }

  def "#getChildren always return an empty children view" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(10)
    Mockito.when(context.getModel()).thenReturn(model)

    and: "a valid StaticModelElement"
    final StaticModelElement element = new StaticModelElement(context)

    expect: "to get an empty children view when we call #getChildren"
    element.getChildren().empty
  }
}
