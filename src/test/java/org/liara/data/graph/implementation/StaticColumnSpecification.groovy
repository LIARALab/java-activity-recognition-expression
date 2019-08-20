package org.liara.data.graph.implementation

import org.liara.data.graph.Graph
import org.liara.data.graph.Table
import org.liara.data.graph.builder.ColumnBuilder
import org.liara.data.graph.builder.GraphBuildingContext
import org.liara.data.primitive.Primitives
import org.liara.support.view.View
import org.mockito.Mockito
import spock.lang.Specification

class StaticColumnSpecification
        extends Specification {
    def "#StaticColumn allows to instantiate a new column for a data graph"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)

        and: "an element builder"
        final ColumnBuilder builder = Mockito.mock(ColumnBuilder.class)
        Mockito.doReturn(Primitives.SHORT).when(builder).getType()

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        Mockito.when(context.getTableIdentifier(builder)).thenReturn(10)
        Mockito.when(context.getGraph()).thenReturn(graph)

        when: "we instantiate a new column"
        final StaticColumn column = new StaticColumn(context, builder)

        then: "we expect to get a valid element"
        column.identifier == 12
        column.graph == graph
        column.type == Primitives.SHORT
    }

    def "#getTable returns the column's table from its graph"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)

        and: "a table"
        final Table table = Mockito.mock(Table.class)
        Mockito.doReturn(Mockito.mock(View.class)).when(graph).getTables()
        Mockito.when(graph.getTables().get(10)).thenReturn(table)

        and: "an element builder"
        final ColumnBuilder builder = Mockito.mock(ColumnBuilder.class)
        Mockito.doReturn(Primitives.SHORT).when(builder).getType()

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        Mockito.when(context.getTableIdentifier(builder)).thenReturn(10)
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a column instance"
        final StaticColumn column = new StaticColumn(context, builder)

        expect: "the column instance to return it's parent table from it's graph"
        column.table == table
    }

    def "#getName returns the column's name from its table"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)

        and: "a table"
        final Table table = Mockito.mock(Table.class)
        Mockito.doReturn(Mockito.mock(View.class)).when(graph).getTables()
        Mockito.when(graph.getTables().get(10)).thenReturn(table)

        and: "an element builder"
        final ColumnBuilder builder = Mockito.mock(ColumnBuilder.class)
        Mockito.doReturn(Primitives.SHORT).when(builder).getType()

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        Mockito.when(context.getTableIdentifier(builder)).thenReturn(10)
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a column instance"
        final StaticColumn column = new StaticColumn(context, builder)
        Mockito.when(table.getNameOf(column)).thenReturn("identifier")

        expect: "the column instance to return it's name from it's table"
        column.name == "identifier"
    }
}
