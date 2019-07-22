package org.liara.data.model

import org.liara.data.model.descriptor.ModelDescriptorFactory
import org.liara.data.primitive.Primitives
import spock.lang.Specification

class StaticModelBuilderSpecification extends Specification {
  def "#build allows to build empty models" () {
    given: "a StaticModelBuilder instance"
    final StaticModelBuilder builder = new StaticModelBuilder()

    when: "we call build without any descriptor"
    final Model model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 1
    model.getElements().get(0) instanceof NullModel
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build null models" () {
    given: "a StaticModelBuilder instance"
    final StaticModelBuilder builder = new StaticModelBuilder()

    and: "a descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "a model to build"
    builder.setDescriptor(factory.empty())

    when: "we call build without any descriptor"
    final Model model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 1
    model.getElements().get(0) instanceof NullModel
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build single value models" () {
    given: "a StaticModelBuilder instance"
    final StaticModelBuilder builder = new StaticModelBuilder()

    and: "a descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "a model to build"
    builder.setDescriptor(factory.value(Primitives.BYTE))

    when: "we call build without any descriptor"
    final Model model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 1
    model.getElements().get(0) instanceof ValueModel
    (model.getElements().get(0) as ValueModel).type == Primitives.BYTE
    model.getRootElement() == model.getElements().get(0)
  }

  def "#build can build tuple models" () {
    given: "a StaticModelBuilder instance"
    final StaticModelBuilder builder = new StaticModelBuilder()

    and: "a descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "a model to build"
    builder.setDescriptor(factory.tuple(
      factory.value(Primitives.BYTE),
      factory.value(Primitives.STRING),
      factory.value(Primitives.STRING),
      factory.empty(),
      factory.value(Primitives.NULLABLE_INTEGER)
    ))

    when: "we call build without any descriptor"
    final Model model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 6
    model.getRootElement() instanceof TupleModel
    model.getElements().get(model.getRootElement().getIdentifier()) == model.getRootElement()

    final TupleModel tuple = model.getRootElement() as TupleModel
    tuple.getChildren().get(0) instanceof ValueModel
    (tuple.getChildren().get(0) as ValueModel).getType() == Primitives.BYTE
    model.getElements().get(tuple.getChildren().get(0).getIdentifier()) == tuple.getChildren().get(0)
    tuple.getChildren().get(1) instanceof ValueModel
    (tuple.getChildren().get(1) as ValueModel).getType() == Primitives.STRING
    model.getElements().get(tuple.getChildren().get(1).getIdentifier()) == tuple.getChildren().get(1)
    tuple.getChildren().get(2) instanceof ValueModel
    (tuple.getChildren().get(2) as ValueModel).getType() == Primitives.STRING
    model.getElements().get(tuple.getChildren().get(2).getIdentifier()) == tuple.getChildren().get(2)
    tuple.getChildren().get(3) instanceof NullModel
    model.getElements().get(tuple.getChildren().get(3).getIdentifier()) == tuple.getChildren().get(3)
    tuple.getChildren().get(4) instanceof ValueModel
    (tuple.getChildren().get(4) as ValueModel).getType() == Primitives.NULLABLE_INTEGER
    model.getElements().get(tuple.getChildren().get(4).getIdentifier()) == tuple.getChildren().get(4)
  }

  def "#build can build object models" () {
    given: "a StaticModelBuilder instance"
    final StaticModelBuilder builder = new StaticModelBuilder()

    and: "a descriptor factory"
    final ModelDescriptorFactory factory = new ModelDescriptorFactory()

    and: "a model to build"
    builder.setDescriptor(factory.object(
      [
        "first",
        "second",
        "third",
        "fourth",
        "last"
      ],
      [
        factory.value(Primitives.BYTE),
        factory.value(Primitives.STRING),
        factory.value(Primitives.STRING),
        factory.empty(),
        factory.value(Primitives.NULLABLE_INTEGER)
      ]
    ))

    when: "we call build without any descriptor"
    final Model model = builder.build()

    then: "we expect to get a model with only a null value descriptor"
    model.getElements().size == 6
    model.getRootElement() instanceof ObjectModel
    model.getElements().get(model.getRootElement().getIdentifier()) == model.getRootElement()

    final ObjectModel object = model.getRootElement() as ObjectModel
    object.getKeys().get(0) == "first"
    object.getChildren().get(0) instanceof ValueModel
    (object.getChildren().get(0) as ValueModel).getType() == Primitives.BYTE
    model.getElements().get(object.getChildren().get(0).getIdentifier()) == object.getChildren().get(0)
    object.getKeys().get(1) == "second"
    object.getChildren().get(1) instanceof ValueModel
    (object.getChildren().get(1) as ValueModel).getType() == Primitives.STRING
    model.getElements().get(object.getChildren().get(1).getIdentifier()) == object.getChildren().get(1)
    object.getKeys().get(2) == "third"
    object.getChildren().get(2) instanceof ValueModel
    (object.getChildren().get(2) as ValueModel).getType() == Primitives.STRING
    model.getElements().get(object.getChildren().get(2).getIdentifier()) == object.getChildren().get(2)
    object.getKeys().get(3) == "fourth"
    object.getChildren().get(3) instanceof NullModel
    model.getElements().get(object.getChildren().get(3).getIdentifier()) == object.getChildren().get(3)
    object.getKeys().get(4) == "last"
    object.getChildren().get(4) instanceof ValueModel
    (object.getChildren().get(4) as ValueModel).getType() == Primitives.NULLABLE_INTEGER
    model.getElements().get(object.getChildren().get(4).getIdentifier()) == object.getChildren().get(4)
  }
}
