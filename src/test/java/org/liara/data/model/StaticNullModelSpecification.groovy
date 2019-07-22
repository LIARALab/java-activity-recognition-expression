package org.liara.data.model

import org.mockito.Mockito
import spock.lang.Specification

class StaticNullModelSpecification
  extends Specification {
  def "#StaticNullModel allows to instantiate a null value model for a given model" () {
    given: "a model"
    final StaticModel model = Mockito.mock(StaticModel.class)

    and: "an identifier"
    final int identifier = 10

    and: "a building context"
    final ModelElementBuildingContext context = Mockito.mock(ModelElementBuildingContext.class)
    Mockito.when(context.getIdentifier()).thenReturn(identifier)
    Mockito.when(context.getModel()).thenReturn(model)

    when: "we call #StaticNullModel with the given context"
    final StaticNullModel element = new StaticNullModel(context)

    then: "we expect to instantiate a valid model element"
    element.getModel() == model
    element.getIdentifier() == identifier
    element.getChildren().empty
  }
}
