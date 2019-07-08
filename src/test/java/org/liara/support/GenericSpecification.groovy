package org.liara.support

import spock.lang.Specification

class GenericSpecification extends Specification
{
  def "#hasSameAnnotationsAs return true if both types annotations are equals" () {
    expect: "#hasSameAnnotationsAs to return true if both types annotations are equals"
    (new StaticGeneric<Integer>() {}).hasSameAnnotationsAs(new StaticGeneric<Float>() {})
  }
}
