package org.liara.data.blueprint.implementation

import org.liara.data.blueprint.Blueprint
import org.liara.data.blueprint.BlueprintElement
import org.liara.data.blueprint.builder.BlueprintBuildingContext
import org.liara.data.blueprint.builder.BlueprintElementBuilder
import org.liara.data.blueprint.builder.ObjectBlueprintBuilder
import org.liara.support.view.View
import org.mockito.Mockito
import spock.lang.Specification

import java.util.stream.Collectors

class StaticObjectBlueprintSpecification
  extends Specification {
  Blueprint someBlueprint () {
    final Blueprint result = Mockito.mock(Blueprint.class)
    Mockito.doReturn(Mockito.mock(View.class)).when(result).getElements()

    return result
  }

  BlueprintElementBuilder someBuilder (final Blueprint blueprint, final int identifier) {
    final BlueprintElement result = Mockito.mock(BlueprintElement.class)
    Mockito.when(result.getIdentifier()).thenReturn(identifier)
    Mockito.when(blueprint.getElements().get(identifier)).thenReturn(result)

    final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)
    Mockito.when(builder.build(Mockito.any(BlueprintBuildingContext.class))).thenReturn(
      result
    )

    return builder
  }

  BlueprintElement someElement (final BlueprintElement brother, final int identifier) {
    return someElement(brother.getBlueprint(), identifier)
  }

  BlueprintElement someElement (final Blueprint blueprint, final int identifier) {
    final BlueprintElement result = Mockito.mock(BlueprintElement.class)
    Mockito.when(result.getIdentifier()).thenReturn(identifier)
    Mockito.when(result.getBlueprint()).thenReturn(blueprint)

    return result
  }

  ObjectBlueprintBuilder someBuilder (
    final List<String> keys,
    final List<BlueprintElementBuilder> elements
  ) {
    final ObjectBlueprintBuilder builder = Mockito.mock(ObjectBlueprintBuilder.class)

    Mockito.when(builder.getChildren()).thenReturn(Mockito.mock(View.class))
    Mockito.when(builder.getKeys()).thenReturn(Mockito.mock(View.class))
    Mockito.when(builder.getChildren().getSize()).thenReturn(elements.size())
    Mockito.when(builder.getKeys().getSize()).thenReturn(keys.size())
    Mockito.when(builder.getKeys().toArray()).thenReturn((String[]) keys.toArray())

    for (final int index = 0; index < elements.size(); ++index) {
      Mockito.when(builder.getChildren().get(index)).thenReturn(elements.get(index))
      Mockito.when(builder.getKeys().get(index)).thenReturn(keys.get(index))
    }

    return builder
  }

  BlueprintBuildingContext someContext (
    final Blueprint blueprint,
    final List<BlueprintElementBuilder> elements
  ) {
    final BlueprintBuildingContext context = Mockito.mock(BlueprintBuildingContext.class)

    Mockito.when(context.getBlueprint()).thenReturn(blueprint)

    for (int index = 0; index < elements.size(); ++index) {
      int identifier = elements.get(index).build(context).getIdentifier()
      Mockito.when(context.getIdentifier(elements.get(index))).thenReturn(identifier)
    }

    return context
  }

  StaticObjectBlueprint buildSomeObject (final List<String> keys, final List<Integer> children) {
    final Blueprint blueprint = someBlueprint()
    final List<BlueprintElementBuilder> childrenElements = children.stream().map(
      { x -> someBuilder(blueprint, x) }
    ).collect(Collectors.toList())

    final ObjectBlueprintBuilder builder = someBuilder(keys, childrenElements)
    final BlueprintBuildingContext context = someContext(blueprint, childrenElements)
    Mockito.doReturn(10).when(context).getIdentifier(builder)

    return new StaticObjectBlueprint(context, builder)
  }

  def "#StaticObjectBlueprint allows to instantiate an object blueprint for a given blueprint" () {
    given: "a blueprint"
    final Blueprint blueprint = someBlueprint()

    and: "a list of keys"
    final List<String> keys = [
      "first",
      "second",
      "third",
      "fourth",
      "last"
    ]

    and: "a list of elements"
    final List<BlueprintElementBuilder> children = [
      someBuilder(blueprint, 5),
      someBuilder(blueprint, 8),
      someBuilder(blueprint, 42),
      someBuilder(blueprint, 25),
      someBuilder(blueprint,18)
    ]

    and: "a builder"
    final ObjectBlueprintBuilder builder = someBuilder(keys, children)

    and: "a building context"
    final BlueprintBuildingContext context = someContext(blueprint, children)
    Mockito.doReturn(10).when(context).getIdentifier(builder)

    when: "we call #StaticObjectBlueprint with the given context"
    final StaticObjectBlueprint element = new StaticObjectBlueprint(context, builder)

    then: "we expect to instantiate a valid blueprint element"
    element.getBlueprint() == blueprint
    element.getIdentifier() == 10
    element.getChildren().size == children.size()
    element.getKeys().size == keys.size()

    for (int index = 0; index < children.size(); ++index) {
      element.getChildren().get(index) == children.get(index)
      element.getKeys().get(index) == keys.get(index)
    }
  }

  def "#getFieldOf return the field index of the given key" () {
    given: "an object"
    final StaticObjectBlueprint element = buildSomeObject(
      ["first", "second", "third", "fourth", "last"],
      [5, 8, 42, 25, 18]
    )

    expect: "that #getFieldOf returns the field index of the given key"
    for (int index = 0; index < element.getChildren().size(); ++index) {
      element.getFieldOf(element.getChildren().get(index)) == index
    }
  }

  def "#getFieldOf return -1 if the given element is not an element of the object" () {
    given: "an object"
    final StaticObjectBlueprint element = buildSomeObject(
      ["first", "second", "third", "fourth", "last"],
      [5, 8, 42, 25, 18]
    )

    and: "another blueprint"
    final Blueprint other = someBlueprint()

    expect: "that #getFieldOf returns -1 if the given element is not an element of the object"
    element.getFieldOf(someElement(element.getBlueprint(), 4)) == -1
    element.getFieldOf(someElement(element.getBlueprint(), 6)) == -1
    element.getFieldOf(someElement(element.getBlueprint(), -12)) == -1
    element.getFieldOf(someElement(element.getBlueprint(), 69)) == -1
    element.getFieldOf(someElement(other, 5)) == -1
    element.getFieldOf(someElement(other, 8)) == -1
    element.getFieldOf(someElement(other, 42)) == -1
    element.getFieldOf(someElement(other, -12)) == -1
    element.getFieldOf(someElement(other, 69)) == -1
  }

  def "#getFieldOf return the field index of the given element" () {
    given: "an object"
    final StaticObjectBlueprint element = buildSomeObject(
      ["first", "second", "third", "fourth", "last"],
      [5, 8, 42, 25, 18]
    )

    expect: "that #getFieldOf returns the field index of the given element"
    for (int index = 0; index < element.getKeys().size(); ++index) {
      element.getFieldOf(element.getKeys().get(index)) == index
    }
  }

  def "#getFieldOf return -1 if the given string is not a valid key" () {
    given: "an object"
    final StaticObjectBlueprint element = buildSomeObject(
      ["first", "second", "third", "fourth", "last"],
      [5, 8, 42, 25, 18]
    )

    expect: "that #getFieldOf returns -1 if the given string is not a valid key"
    element.getFieldOf("First") == -1
    element.getFieldOf("fiRsT") == -1
    element.getFieldOf("pwet") == -1
    element.getFieldOf("five") == -1
  }
}
