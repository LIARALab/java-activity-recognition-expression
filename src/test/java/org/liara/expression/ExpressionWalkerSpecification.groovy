package org.liara.expression

import spock.lang.Specification

class ExpressionWalkerSpecification extends Specification {
  def "#ExpressionWalker instantiate a forward walker for a given expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(factory.constant(15), factory.constant(12))

    when: "we instantiate a walker with an expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    then: "we expect the walker to moveTo forwards"
    walker.doesMoveForward()

    and: "we expect the walker to use the given expression as a root"
    walker.expression == expression
  }

  def "#ExpressionWalker allows to copy an existing walker" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker that moved backwards"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    while (walker.canEnter() && walker.enter() != expression.children.get(1)) {
      while (!walker.canEnter()) {
        walker.exit()
      }
    }

    when: "we copy the given walker"
    final ExpressionWalker copy = new ExpressionWalker(walker)

    then: "we expect to get a copy of the given walker"
    copy.doesMoveForward() == walker.doesMoveForward()
    copy.expression == walker.expression
    copy.path == walker.path
    copy.isAtLocation(walker)
    copy.current() == walker.current()
  }

  def "#move allows to move a walker to the location of another one" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker that moved backwards"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    while (walker.canEnter() && walker.enter() != expression.children.get(1)) {
      while (!walker.canEnter()) {
        walker.exit()
      }
    }

    when: "we instantiate a walker that moves to the given one"
    final ExpressionWalker second = new ExpressionWalker(expression)
    second.moveTo(walker)

    then: "we expect to get a copy of the given walker"
    second.doesMoveForward() != walker.doesMoveForward()
    second.expression == walker.expression
    second.path == walker.path
    second.isAtLocation(walker)
    second.current() == walker.current()
  }

  def "#enter allows to enter forward into to the next available child expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #enter multiple times"
    final Expression<?>[] result = [
      walker.enter(),
      walker.enter(),
      walker.enter()
    ]

    then: "we expect to walk throughout the expression"
    result[0] == expression
    result[1] == expression.children.get(0)
    result[2] == expression.children.get(0).children.get(0)
  }

  def "#enter throws NoSuchElementException if the walker can't move forward into another child" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #enter to much times"
    walker.enter()
    walker.enter()
    walker.enter()
    walker.enter()

    then: "we expect the walker to throw"
    thrown(NoSuchElementException.class)
  }

  def "#enter allows to enter backward to the next available child expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    when: "we call #enter multiple times"
    final Expression<?>[] result = [
      walker.enter(),
      walker.enter(),
      walker.enter()
    ]

    then: "we expect to walk throughout the expression"
    result[0] == expression
    result[1] == expression.children.get(2)
    result[2] == expression.children.get(2).children.get(0)
  }

  def "#enter throws NoSuchElementException if the walker can't move backward to another child" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    when: "we call #enter to much times"
    walker.enter()
    walker.enter()
    walker.enter()
    walker.enter()

    then: "we expect the walker to throw"
    thrown(NoSuchElementException.class)
  }

  def "#exit allows to move outside of the current expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #enter multiple times"
    final Expression<?>[] entered = [
      walker.enter(),
      walker.enter(),
      walker.enter()
    ]

    and: "then calling #exit"
    final Expression<?>[] exited = [
      walker.exit(),
      walker.exit(),
      walker.exit()
    ]

    then: "we expect to walk throughout the expression"
    exited[0] == entered[2]
    exited[1] == entered[1]
    exited[2] == entered[0]
  }

  def "#exit throws NoSuchElementException if the walker is not into an expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #exit"
    walker.exit()

    then: "we expect the walker to throw"
    thrown(NoSuchElementException.class)
  }

  def "#exit and #enter allows to move forward throughout the expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #enter as much as possible and then #exit"
    final List<Expression<?>> result = new LinkedList<>()

    while (!walker.atEnd) {
      while (walker.canEnter()) {
        walker.enter()
      }

      result.add(walker.exit())
    }

    then: "we expect to moves throughout the entire expression"
    result == [
      expression.children.get(0).children.get(0),
      expression.children.get(0).children.get(1),
      expression.children.get(0),
      expression.children.get(1),
      expression.children.get(2).children.get(0),
      expression.children.get(2),
      expression
    ]
  }

  def "#exit and #enter allows to move backward throughout the expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    when: "we call #enter as much as possible and then #exit"
    final List<Expression<?>> result = new LinkedList<>()

    while (!walker.atStart) {
      while (walker.canEnter()) {
        walker.enter()
      }

      result.add(walker.exit())
    }

    then: "we expect to moves throughout the entire expression"
    result == [
      expression.children.get(2).children.get(0),
      expression.children.get(2),
      expression.children.get(1),
      expression.children.get(0).children.get(1),
      expression.children.get(0).children.get(0),
      expression.children.get(0),
      expression
    ]
  }

  def "#exit and #enter allows to move forward then backward throughout the expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #enter as much as possible and then #exit"
    final List<Expression<?>> result = new LinkedList<>()

    while (walker.canEnter() && walker.enter() != expression.children.get(1)) {
      while (!walker.canEnter()) result.add(walker.exit())
    }

    walker.movesBackward()

    while (!walker.atStart) {
      while (walker.canEnter()) {
        walker.enter()
      }

      result.add(walker.exit())
    }

    then: "we expect to moves throughout the expression"
    result[0] == expression.children.get(0).children.get(0)
    result[1] == expression.children.get(0).children.get(1)
    result[2] == expression.children.get(0)
    result[3] == expression.children.get(1)
    result[4] == expression.children.get(0).children.get(1)
    result[5] == expression.children.get(0).children.get(0)
    result[6] == expression.children.get(0)
    result[7] == expression
  }

  def "#exit and #enter allows to move backward and then forward throughout the expression" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)
    walker.movesBackward()
    walker.moveToEnd()

    when: "we call #enter as much as possible and then #exit"
    final List<Expression<?>> result = new LinkedList<>()

    while (walker.canEnter() && walker.enter() != expression.children.get(1)) {
      while (!walker.canEnter()) result.add(walker.exit())
    }

    walker.movesForward()

    while (!walker.atEnd) {
      while (walker.canEnter()) {
        walker.enter()
      }

      result.add(walker.exit())
    }

    then: "we expect to moves throughout the expression"
    result[0] == expression.children.get(2).children.get(0)
    result[1] == expression.children.get(2)
    result[2] == expression.children.get(1)
    result[3] == expression.children.get(2).children.get(0)
    result[4] == expression.children.get(2)
    result[5] == expression
  }

  def "#getPath always return the current path" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #getPath throughout the entire expression"
    final List<List> result = new LinkedList<>()

    while (!walker.atEnd) {
      while (walker.canEnter()) {
        walker.enter()
      }

      result.add(new ArrayList<>(walker.path))
      walker.exit()
    }

    then: "we expect to moves throughout the entire expression"
    result == [
      [expression, expression.children.get(0), expression.children.get(0).children.get(0)],
      [expression, expression.children.get(0), expression.children.get(0).children.get(1)],
      [expression, expression.children.get(0)],
      [expression, expression.children.get(1)],
      [expression, expression.children.get(2), expression.children.get(2).children.get(0)],
      [expression, expression.children.get(2)],
      [expression]
    ]
  }

  def "#setMovingForward make the walker going forward" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #setMovingForward"
    walker.setMovingForward(true)

    then: "we expect the walker to goes movesForward"
    walker.doesMoveForward()
  }

  def "#setMovingForward make the walker going backward" () {
    given: "an expression factory"
    final ExpressionFactory factory = new ExpressionFactory()

    and: "an expression"
    final Expression<?> expression = factory.add(Arrays.asList(
      factory.multiply(
        factory.constant(15),
        factory.constant(12)
      ),
      factory.constant(32),
      factory.minus(factory.constant(30))
    ))

    and: "a walker over the given expression"
    final ExpressionWalker walker = new ExpressionWalker(expression)

    when: "we call #setMovingForward"
    walker.setMovingForward(false)

    then: "we expect the walker to goes movesBackward"
    !walker.doesMoveForward()
  }
}
