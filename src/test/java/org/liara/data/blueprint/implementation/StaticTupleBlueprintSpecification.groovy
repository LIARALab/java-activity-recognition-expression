package org.liara.data.blueprint.implementation

import org.liara.data.blueprint.Blueprint
import org.liara.data.blueprint.BlueprintElement
import org.liara.data.blueprint.builder.BlueprintBuildingContext
import org.liara.data.blueprint.builder.BlueprintElementBuilder
import org.liara.support.view.View
import org.mockito.Mockito
import spock.lang.Specification

import java.util.stream.Collectors

class StaticTupleBlueprintSpecification
        extends Specification {
    Blueprint someBlueprint() {
        final Blueprint result = Mockito.mock(Blueprint.class)
        Mockito.doReturn(Mockito.mock(View.class)).when(result).getElements()

        return result
    }

    BlueprintElementBuilder someBuilder(final Blueprint blueprint, final int identifier) {
        final BlueprintElement result = Mockito.mock(BlueprintElement.class)
        Mockito.when(result.getIdentifier()).thenReturn(identifier)
        Mockito.when(blueprint.getElements().get(identifier)).thenReturn(result)

        final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)
        Mockito.when(builder.build(Mockito.any(BlueprintBuildingContext.class))).thenReturn(
                result
        )

        return builder
    }

    BlueprintElement someElement(final BlueprintElement brother, final int identifier) {
        return someElement(brother.getBlueprint(), identifier)
    }

    BlueprintElement someElement(final Blueprint blueprint, final int identifier) {
        final BlueprintElement result = Mockito.mock(BlueprintElement.class)
        Mockito.when(result.getIdentifier()).thenReturn(identifier)
        Mockito.when(result.getBlueprint()).thenReturn(blueprint)

        return result
    }

    BlueprintElementBuilder someBuilder(final List<BlueprintElementBuilder> elements) {
        final BlueprintElementBuilder builder = Mockito.mock(BlueprintElementBuilder.class)

        Mockito.when(builder.getChildren()).thenReturn(Mockito.mock(View.class))
        Mockito.when(builder.getChildren().getSize()).thenReturn(elements.size())

        for (final int index = 0; index < elements.size(); ++index) {
            Mockito.when(builder.getChildren().get(index)).thenReturn(elements.get(index))
        }

        return builder
    }

    BlueprintBuildingContext someContext(
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

    StaticTupleBlueprint buildSomeTuple(final List<Integer> children) {
        final Blueprint blueprint = someBlueprint()
        final List<BlueprintElementBuilder> childrenElements = children.stream().map(
                { x -> someBuilder(blueprint, x) }
        ).collect(Collectors.toList())

        final BlueprintElementBuilder builder = someBuilder(childrenElements)
        final BlueprintBuildingContext context = someContext(blueprint, childrenElements)
        Mockito.doReturn(10).when(context).getIdentifier(builder)

        return new StaticTupleBlueprint(context, builder)
    }

    def "#StaticTupleBlueprint allows to instantiate a tuple blueprint for a given blueprint"() {
        given: "a blueprint"
        final Blueprint blueprint = someBlueprint()

        and: "a list of elements"
        final List<BlueprintElementBuilder> tupleChildren = [
                someBuilder(blueprint, 5),
                someBuilder(blueprint, 8),
                someBuilder(blueprint, 42),
                someBuilder(blueprint, 25),
                someBuilder(blueprint, 18)
        ]

        and: "a builder"
        final BlueprintElementBuilder builder = someBuilder(tupleChildren)

        and: "a building context"
        final BlueprintBuildingContext context = someContext(blueprint, tupleChildren)
        Mockito.doReturn(10).when(context).getIdentifier(builder)

        when: "we call #StaticTupleBlueprint with the given context"
        final StaticTupleBlueprint element = new StaticTupleBlueprint(context, builder)

        then: "we expect to instantiate a valid blueprint element"
        element.getBlueprint() == blueprint
        element.getIdentifier() == 10
        element.getChildren().getSize() == tupleChildren.size()

        for (int index = 0; index < tupleChildren.size(); ++index) {
            element.getChildren().get(index) == tupleChildren.get(index).build(context)
        }
    }

    def "#getIndexOf allows to get the index of a child element of the tuple"() {
        given: "a static tuple"
        final StaticTupleBlueprint element = buildSomeTuple([5, 8, 42, 25, 18])

        expect: "that #getIndexOf return the index of the given child element of the tuple"
        for (int index = 0; index < element.getChildren().getSize(); ++index) {
            element.getIndexOf(element.getChildren().get(index)) == index
        }
    }

    def "#getIndexOf returns -1 if the given element is not a child element of the tuple"() {
        given: "a static tuple"
        final StaticTupleBlueprint element = buildSomeTuple([5, 8, 42, 25, 18])

        and: "another blueprint"
        final Blueprint other = someBlueprint()

        expect: "that #getIndexOf returns -1 if the given element is not a child element of the tuple"
        element.getIndexOf(someElement(element.getBlueprint(), 4)) == -1
        element.getIndexOf(someElement(element.getBlueprint(), 6)) == -1
        element.getIndexOf(someElement(element.getBlueprint(), 63)) == -1
        element.getIndexOf(someElement(element.getBlueprint(), -12)) == -1
        element.getIndexOf(someElement(other, 5)) == -1
        element.getIndexOf(someElement(other, 8)) == -1
        element.getIndexOf(someElement(other, 42)) == -1
        element.getIndexOf(someElement(other, 4)) == -1
    }
}
