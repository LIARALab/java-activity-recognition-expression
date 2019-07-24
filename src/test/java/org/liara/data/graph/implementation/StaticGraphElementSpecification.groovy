package org.liara.data.graph.implementation

import org.liara.data.graph.Graph
import org.liara.data.graph.builder.GraphBuildingContext
import org.liara.data.graph.builder.GraphElementBuilder
import org.mockito.Mockito
import spock.lang.Specification

class StaticGraphElementSpecification extends Specification {
  def "#StaticGraphElement allows to instantiate a new element of a data graph" () {
    given: "a graph"
    final Graph graph = Mockito.mock(Graph.class)

    and: "an element builder"
    final GraphElementBuilder builder = Mockito.mock(GraphElementBuilder.class)

    and: "a building context"
    final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
    Mockito.when(context.getIdentifier(builder)).thenReturn(12)
    Mockito.when(context.getGraph()).thenReturn(graph)

    when: "we instantiate a new element"
    final StaticGraphElement element = new StaticGraphElement(context, builder)

    then: "we expect to get a valid element"
    element.identifier == 12
    element.graph == graph
  }
}
