package org.liara.data.graph.builder

import org.liara.data.graph.Graph
import org.liara.data.graph.Table
import org.liara.data.primitive.Primitives
import spock.lang.Specification

class StaticGraphBuilderSpecification extends Specification {
  void defineUsersTable (StaticGraphBuilder builder) {
    builder.table("users")
           .column("created_at").ofType(Primitives.DATE_TIME)
           .column("deleted_at").ofType(Primitives.DATE_TIME)
           .column("identifier").ofType(Primitives.INTEGER)
           .column("first_name").ofType(Primitives.STRING)
           .column("family_name").ofType(Primitives.STRING)
           .endTable()
  }

  boolean hasUsersTable (Graph graph) {
    if (!graph.hasTable("users")) return false

    final Table table = graph.getTable("users")

    return table.getColumn("created_at").getType() == Primitives.DATE_TIME &&
           table.getColumn("deleted_at").getType() == Primitives.DATE_TIME &&
           table.getColumn("identifier").getType() == Primitives.INTEGER &&
           table.getColumn("first_name").getType() == Primitives.STRING &&
           table.getColumn("family_name").getType() == Primitives.STRING
  }

  void defineUsersJobsTable (StaticGraphBuilder builder) {
    builder.table("users_jobs")
           .column("created_at").ofType(Primitives.DATE_TIME)
           .column("deleted_at").ofType(Primitives.DATE_TIME)
           .column("identifier").ofType(Primitives.INTEGER)
           .column("user_identifier").ofType(Primitives.INTEGER)
           .column("job_identifier").ofType(Primitives.INTEGER)
           .endTable()
  }

  boolean hasUsersJobsTable (Graph graph) {
    if (!graph.hasTable("users_jobs")) return false

    final Table table = graph.getTable("users_jobs")

    return table.getColumn("created_at").getType() == Primitives.DATE_TIME &&
           table.getColumn("deleted_at").getType() == Primitives.DATE_TIME &&
           table.getColumn("identifier").getType() == Primitives.INTEGER &&
           table.getColumn("user_identifier").getType() == Primitives.INTEGER &&
           table.getColumn("job_identifier").getType() == Primitives.INTEGER
  }

  void defineJobsTable (StaticGraphBuilder builder) {
    builder.table("jobs")
           .column("created_at").ofType(Primitives.DATE_TIME)
           .column("deleted_at").ofType(Primitives.DATE_TIME)
           .column("identifier").ofType(Primitives.INTEGER)
           .column("name").ofType(Primitives.STRING)
           .endTable()
  }

  boolean hasJobsTable (Graph graph) {
    if (!graph.hasTable("jobs")) return false

    final Table table = graph.getTable("jobs")

    return table.getColumn("created_at").getType() == Primitives.DATE_TIME &&
      table.getColumn("deleted_at").getType() == Primitives.DATE_TIME &&
      table.getColumn("identifier").getType() == Primitives.INTEGER &&
      table.getColumn("name").getType() == Primitives.STRING
  }

  def "its able to build a complete graph" () {
    given: "a builder"
    final StaticGraphBuilder builder = new StaticGraphBuilder()

    and: "a graph description"
    defineUsersTable(builder)
    defineJobsTable(builder)
    defineUsersJobsTable(builder)

    when: "we build the described graph"
    final Graph graph = builder.build()

    then: "we expect to get the described graph"
    hasUsersTable(graph)
    hasJobsTable(graph)
    hasUsersJobsTable(graph)
  }
}
