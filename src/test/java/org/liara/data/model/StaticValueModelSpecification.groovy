package org.liara.data.model

import org.liara.data.primitive.Primitives
import org.mockito.Mockito
import spock.lang.Specification

class StaticValueModelSpecification extends Specification {
  def "#StaticValueModel allows to instantiate a value model for a given model" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    when: "we call #StaticValueModel with the given context"
    final StaticValueModel element = new StaticValueModel(context, Primitives.SHORT)

    then: "we expect to instantiate a valid model element"
    element.getModel() == model
    element.getType() == Primitives.SHORT
    element.getIdentifier() == identifier
    element.getChildren().empty
  }
}
