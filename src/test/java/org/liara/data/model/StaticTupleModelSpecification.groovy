package org.liara.data.model

import org.mockito.Mockito
import spock.lang.Specification

class StaticTupleModelSpecification
  extends Specification {
  ModelElement modelElement (final StaticModel model, final int identifier) {
    final ModelElement result = Mockito.mock(ModelElement.class)
    Mockito.when(result.getIdentifier()).thenReturn(identifier)

    return result
  }

  def "#StaticTupleModel allows to instantiate a tuple model for a given model" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    and: "a list of children model"
    final List<ModelElement> modelElements = [
      modelElement(model, 5),
      modelElement(model, 8),
      modelElement(model, 42),
      modelElement(model, 25),
      modelElement(model,18)
    ]

    when: "we call #StaticTupleModel with the given context"
    final StaticTupleModel element = new StaticTupleModel(context, modelElements)

    then: "we expect to instantiate a valid model element"
    element.getModel() == model
    element.getIdentifier() == identifier
    element.getChildren().size == modelElements.size()

    for (int index = 0; index < modelElements.size(); ++index) {
      element.getChildren().get(index) == modelElements.get(index)
    }
  }

  def "#getIndexOf allows to get the index of a child element of the tuple" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    and: "a list of children model"
    final List<ModelElement> modelElements = [
      modelElement(model, 5),
      modelElement(model, 8),
      modelElement(model, 42),
      modelElement(model, 25),
      modelElement(model,18)
    ]

    and: "a valid StaticTupleModel"
    final StaticTupleModel element = new StaticTupleModel(context, modelElements)

    expect: "that #getIndexOf return the index of the given child element of the tuple"
    for (int index = 0; index < modelElements.size(); ++index) {
      element.getIndexOf(modelElements.get(index)) == index
    }
  }

  def "#getIndexOf returns -1 if the given element is not a child element of the tuple" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    and: "a list of children model"
    final List<ModelElement> modelElements = [
      modelElement(model, 5),
      modelElement(model, 8),
      modelElement(model, 42),
      modelElement(model, 25),
      modelElement(model,18)
    ]

    and: "a valid StaticTupleModel"
    final StaticTupleModel element = new StaticTupleModel(context, modelElements)

    expect: "that #getIndexOf returns -1 if the given element is not a child element of the tuple"
    element.getIndexOf(modelElement(model, 4)) == -1
    element.getIndexOf(modelElement(model, 6)) == -1
    element.getIndexOf(modelElement(model, 63)) == -1
    element.getIndexOf(modelElement(model, -12)) == -1
    element.getIndexOf(modelElement(Mockito.mock(StaticModel.class), 5)) == -1
    element.getIndexOf(modelElement(Mockito.mock(StaticModel.class), 8)) == -1
    element.getIndexOf(modelElement(Mockito.mock(StaticModel.class), 42)) == -1
    element.getIndexOf(modelElement(Mockito.mock(StaticModel.class), 4)) == -1
  }
}
