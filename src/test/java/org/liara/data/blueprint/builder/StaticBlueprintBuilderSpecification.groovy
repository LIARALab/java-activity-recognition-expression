package org.liara.data.blueprint.builder

import org.liara.data.blueprint.Blueprint
import org.liara.data.blueprint.NullBlueprint
import org.liara.data.blueprint.ObjectBlueprint
import org.liara.data.blueprint.TupleBlueprint
import org.liara.data.blueprint.ValueBlueprint
import org.liara.data.primitive.Primitives
import spock.lang.Specification

class StaticBlueprintBuilderSpecification
  extends Specification {
  def "#build allows to build empty models" () {
    given: "a StaticBlueprintBuilder instance"
    final StaticBlueprintBuilder builder = new StaticBlueprintBuilder()

    when: "we call build without any descriptor"
    final Blueprint model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 1
    model.getElements().get(0) instanceof NullBlueprint
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build null models" () {
    given: "a StaticBlueprintBuilder instance"
    final StaticBlueprintBuilder builder = new StaticBlueprintBuilder()

    and: "a model to build"
    builder.describeNull()

    when: "we build the described model"
    final Blueprint model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 1
    model.getElements().get(0) instanceof NullBlueprint
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build single value models" () {
    given: "a StaticBlueprintBuilder instance"
    final StaticBlueprintBuilder builder = new StaticBlueprintBuilder()

    and: "a model to build"
    builder.describeValue(Primitives.BYTE)

    when: "we build the described model"
    final Blueprint model = builder.build()

    then: "we expect to get a valid model"
    model.getElements().size == 1
    model.getElements().get(0) instanceof ValueBlueprint
    (model.getElements().get(0) as ValueBlueprint).type == Primitives.BYTE
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build tuple models" () {
    given: "a StaticBlueprintBuilder instance"
    final StaticBlueprintBuilder builder = new StaticBlueprintBuilder()

    and: "a model to build"
    builder.describeTuple()
           .appendValue(Primitives.BYTE)
           .appendValue(Primitives.STRING)
           .appendValue(Primitives.STRING)
           .appendNull()
           .appendValue(Primitives.NULLABLE_INTEGER)
           .endTuple()

    when: "we build the described model"
    final Blueprint model = builder.build()

    then: "we expect to get a valid model"
    model.getElements().size == 6
    model.getRootElement() instanceof TupleBlueprint
    model.getElements().get(model.getRootElement().getIdentifier()) == model.getRootElement()

    final TupleBlueprint tuple = model.getRootElement() as TupleBlueprint
    tuple.getChildren().get(0) instanceof ValueBlueprint
    (tuple.getChildren().get(0) as ValueBlueprint).getType() == Primitives.BYTE
    model.getElements().get(tuple.getChildren().get(0).getIdentifier()) == tuple.getChildren().get(0)
    tuple.getChildren().get(1) instanceof ValueBlueprint
    (tuple.getChildren().get(1) as ValueBlueprint).getType() == Primitives.STRING
    model.getElements().get(tuple.getChildren().get(1).getIdentifier()) == tuple.getChildren().get(1)
    tuple.getChildren().get(2) instanceof ValueBlueprint
    (tuple.getChildren().get(2) as ValueBlueprint).getType() == Primitives.STRING
    model.getElements().get(tuple.getChildren().get(2).getIdentifier()) == tuple.getChildren().get(2)
    tuple.getChildren().get(3) instanceof NullBlueprint
    model.getElements().get(tuple.getChildren().get(3).getIdentifier()) == tuple.getChildren().get(3)
    tuple.getChildren().get(4) instanceof ValueBlueprint
    (tuple.getChildren().get(4) as ValueBlueprint).getType() == Primitives.NULLABLE_INTEGER
    model.getElements().get(tuple.getChildren().get(4).getIdentifier()) == tuple.getChildren().get(4)
  }

  def "#build can build object models" () {
    given: "a StaticBlueprintBuilder instance"
    final StaticBlueprintBuilder builder = new StaticBlueprintBuilder()

    and: "a model to build"
    builder.describeObject()
           .appendValue("first", Primitives.BYTE)
           .appendValue("second", Primitives.STRING)
           .appendValue("third", Primitives.STRING)
           .appendNull("fourth")
           .appendValue("last", Primitives.NULLABLE_INTEGER)
           .endObject()

    when: "we call build without any descriptor"
    final Blueprint model = builder.build()

    then: "we expect to get a valid model"
    model.getElements().size == 6
    model.getRootElement() instanceof ObjectBlueprint
    model.getElements().get(model.getRootElement().getIdentifier()) == model.getRootElement()

    final ObjectBlueprint object = model.getRootElement() as ObjectBlueprint
    object.getKeys().get(0) == "first"
    object.getChildren().get(0) instanceof ValueBlueprint
    (object.getChildren().get(0) as ValueBlueprint).getType() == Primitives.BYTE
    model.getElements().get(object.getChildren().get(0).getIdentifier()) == object.getChildren().get(0)
    object.getKeys().get(1) == "second"
    object.getChildren().get(1) instanceof ValueBlueprint
    (object.getChildren().get(1) as ValueBlueprint).getType() == Primitives.STRING
    model.getElements().get(object.getChildren().get(1).getIdentifier()) == object.getChildren().get(1)
    object.getKeys().get(2) == "third"
    object.getChildren().get(2) instanceof ValueBlueprint
    (object.getChildren().get(2) as ValueBlueprint).getType() == Primitives.STRING
    model.getElements().get(object.getChildren().get(2).getIdentifier()) == object.getChildren().get(2)
    object.getKeys().get(3) == "fourth"
    object.getChildren().get(3) instanceof NullBlueprint
    model.getElements().get(object.getChildren().get(3).getIdentifier()) == object.getChildren().get(3)
    object.getKeys().get(4) == "last"
    object.getChildren().get(4) instanceof ValueBlueprint
    (object.getChildren().get(4) as ValueBlueprint).getType() == Primitives.NULLABLE_INTEGER
    model.getElements().get(object.getChildren().get(4).getIdentifier()) == object.getChildren().get(4)
  }
}
