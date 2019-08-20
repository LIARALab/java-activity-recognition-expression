package org.liara.data.graph.implementation

import org.liara.data.graph.Column
import org.liara.data.graph.Graph
import org.liara.data.graph.builder.ColumnBuilder
import org.liara.data.graph.builder.GraphBuildingContext
import org.liara.data.graph.builder.TableBuilder
import org.liara.support.view.View
import org.mockito.Mockito
import spock.lang.Specification

class StaticTableSpecification
        extends Specification {
    def "#StaticTable allows to instantiate a new table for a data graph"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        when: "we instantiate a new table"
        final StaticTable table = new StaticTable(context, builder)

        then: "we expect to get a valid element"
        table.getIdentifier() == 12
        table.getGraph() == graph
        table.getColumnNames().getSize() == builder.getColumnNames().getSize()

        for (int index = 0; index < builder.getColumnNames().getSize(); ++index) {
            table.getColumnNames().get(index) == builder.getColumnNames().get(index)
        }
    }

    def "#getColumns returns a view of the table columns"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(Mockito.mock(Column.class))
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)

        expect: "the table instance to returns a view over its columns"
        table.getColumns().getSize() == builder.getColumns().getSize()

        for (int index = 0; index < table.getColumns().getSize(); ++index) {
            table.getColumns().get(index) == graph.getColumns().get(
                    context.getIdentifier(builder.getColumns().get(index))
            )
        }
    }

    def "#getNameOf returns the name of a column of the table"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            final Column column = Mockito.mock(Column.class)

            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(column)
            Mockito.when(column.getIdentifier()).thenReturn(index * 3 * 5)
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(table.getColumns().get(index).getTable()).thenReturn(table)
        }

        expect: "the table instance to returns the names of its columns"
        table.getColumns().getSize() == builder.getColumns().getSize()

        for (int index = 0; index < table.getColumns().getSize(); ++index) {
            table.getNameOf(table.getColumns().get(index)) == builder.getColumnNames().get(index)
        }
    }

    def "#getIndexOf returns the index of a column of the table"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            final Column column = Mockito.mock(Column.class)

            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(column)
            Mockito.when(column.getIdentifier()).thenReturn(index * 3 * 5)
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(table.getColumns().get(index).getTable()).thenReturn(table)
        }

        expect: "the table instance to returns the indexes of its columns"
        table.getColumns().getSize() == builder.getColumns().getSize()

        for (int index = 0; index < table.getColumns().getSize(); ++index) {
            table.getIndexOf(table.getColumns().get(index)) == index
        }
    }

    def "#getColumn returns a column by using its name"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            final Column column = Mockito.mock(Column.class)

            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(column)
            Mockito.when(column.getIdentifier()).thenReturn(index * 3 * 5)
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(table.getColumns().get(index).getTable()).thenReturn(table)
        }

        expect: "the table instance to returns the its columns by using their names"
        table.getColumns().getSize() == builder.getColumns().getSize()

        for (int index = 0; index < table.getColumns().getSize(); ++index) {
            table.getColumn(table.getColumnNames().get(index)) == table.getColumns().get(index)
        }
    }

    def "#hasColumn returns true if the table contains a column with the given name"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            final Column column = Mockito.mock(Column.class)

            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(column)
            Mockito.when(column.getIdentifier()).thenReturn(index * 3 * 5)
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(table.getColumns().get(index).getTable()).thenReturn(table)
        }

        expect: "the table instance to returns the its columns by using their names"
        table.getColumns().getSize() == builder.getColumns().getSize()

        for (int index = 0; index < table.getColumns().getSize(); ++index) {
            table.hasColumn(table.getColumnNames().get(index))
        }

        !table.hasColumn("not_a_column_name")
    }

    def "#getName return the name of the table from its parent"() {
        given: "a graph"
        final Graph graph = Mockito.mock(Graph.class)
        Mockito.when(graph.getColumns()).thenReturn(Mockito.mock(View.class))

        and: "an element builder"
        final TableBuilder builder = Mockito.mock(TableBuilder.class)

        Mockito.when(builder.getColumnNames()).thenReturn(
                View.readonly(String.class, ["created_at", "deleted_at", "identifier", "name", "last_name"])
        )

        Mockito.when(builder.getColumns()).thenReturn(
                View.readonly(ColumnBuilder.class, [
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class),
                        Mockito.mock(ColumnBuilder.class)
                ])
        )

        and: "a building context"
        final GraphBuildingContext context = Mockito.mock(GraphBuildingContext.class)
        Mockito.when(context.getIdentifier(builder)).thenReturn(12)
        for (int index = 0; index < builder.getColumns().getSize(); ++index) {
            Mockito.when(context.getIdentifier(builder.getColumns().get(index))).thenReturn(index * 3 * 5)
            Mockito.when(graph.getColumns().get(index * 3 * 5)).thenReturn(Mockito.mock(Column.class))
        }
        Mockito.when(context.getGraph()).thenReturn(graph)

        and: "a table instance"
        final StaticTable table = new StaticTable(context, builder)
        Mockito.when(graph.getNameOf(table)).thenReturn("name_of_the_table")

        expect: "the table instance to returns its name"
        table.getName() == "name_of_the_table"
    }
}
