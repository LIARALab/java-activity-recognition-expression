package org.liara.data.model

import org.mockito.Mockito
import spock.lang.Specification

class StaticObjectModelSpecification
  extends Specification {
  ModelElement modelElement (final StaticModel model, final int identifier) {
    final ModelElement result = Mockito.mock(ModelElement.class)
    Mockito.when(result.getIdentifier()).thenReturn(identifier)

    return result
  }

  def "#StaticObjectModel allows to instantiate an object model for a given model" () {
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

    and: "a list of keys"
    final List<String> modelKeys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    when: "we call #StaticObjectModel with the given context"
    final StaticObjectModel element = new StaticObjectModel(context, modelKeys, modelElements)

    then: "we expect to instantiate a valid model element"
    element.getModel() == model
    element.getIdentifier() == identifier
    element.getChildren().size == modelElements.size()
    element.getKeys().size == modelKeys.size()

    for (int index = 0; index < modelElements.size(); ++index) {
      element.getChildren().get(index) == modelElements.get(index)
      element.getKeys().get(index) == modelKeys.get(index)
    }
  }

  def "#getFieldOf return the field index of the given key" () {
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

    and: "a list of keys"
    final List<String> modelKeys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "a valid StaticObjectModel"
    final StaticObjectModel element = new StaticObjectModel(context, modelKeys, modelElements)

    expect: "that #getFieldOf returns the field index of the given key"
    for (int index = 0; index < modelElements.size(); ++index) {
      element.getFieldOf(modelKeys.get(index)) == index
    }
  }

  def "#getFieldOf return -1 if the given string is not a valid key" () {
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

    and: "a list of keys"
    final List<String> modelKeys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "a valid StaticObjectModel"
    final StaticObjectModel element = new StaticObjectModel(context, modelKeys, modelElements)

    expect: "that #getFieldOf returns -1 if the given string is not a valid key"
    element.getFieldOf("First") == -1
    element.getFieldOf("fiRsT") == -1
    element.getFieldOf("pwet") == -1
    element.getFieldOf("five") == -1
  }

  def "#getFieldOf return the field index of the given element" () {
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

    and: "a list of keys"
    final List<String> modelKeys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "a valid StaticObjectModel"
    final StaticObjectModel element = new StaticObjectModel(context, modelKeys, modelElements)

    expect: "that #getFieldOf returns the field index of the given element"
    for (int index = 0; index < modelElements.size(); ++index) {
      element.getFieldOf(modelElements.get(index)) == index
    }
  }

  def "#getFieldOf return -1 if the given element is not an element of the object" () {
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

    and: "a list of keys"
    final List<String> modelKeys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "a valid StaticObjectModel"
    final StaticObjectModel element = new StaticObjectModel(context, modelKeys, modelElements)

    expect: "that #getFieldOf returns -1 if the given element is not an element of the object"
    element.getFieldOf(modelElement(model, 4)) == -1
    element.getFieldOf(modelElement(model, 8)) == -1
    element.getFieldOf(modelElement(model, -12)) == -1
    element.getFieldOf(modelElement(model, 69)) == -1
    element.getFieldOf(modelElement(Mockito.mock(StaticModel.class), 5)) == -1
    element.getFieldOf(modelElement(Mockito.mock(StaticModel.class), 8)) == -1
    element.getFieldOf(modelElement(Mockito.mock(StaticModel.class), 42)) == -1
    element.getFieldOf(modelElement(Mockito.mock(StaticModel.class), -12)) == -1
    element.getFieldOf(modelElement(Mockito.mock(StaticModel.class), 69)) == -1
  }
}
