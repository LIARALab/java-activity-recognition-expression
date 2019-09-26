package org.liara.support.tree

import org.liara.support.view.View
import org.mockito.Mockito
import spock.lang.Specification

class TreeWalkerSpecification
        extends Specification {
    TreeElement makeNode() {
        final TreeElement result = Mockito.mock(TreeElement.class)
        Mockito.when(result.getChildren()).thenReturn(View.empty())

        return result
    }

    TreeElement makeNode(final List<? extends TreeElement> children) {
        final View<? extends TreeElement> view = View.readonly(children)

        final TreeElement result = Mockito.mock(TreeElement.class)
        Mockito.when(result.getChildren()).thenReturn(view)

        return result
    }

    def "#TreeWalker instantiate a forward walker for a given expression"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode(),
                makeNode()
        ])

        when: "we instantiate a walker with the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        then: "we expect the walker to moveTo forwards"
        walker.doesMoveForward()

        and: "we expect the walker to use the given expression as a root"
        walker.root == tree
    }

    def "#TreeWalker allows to copy an existing walker"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker that moved backwards"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
        walker.movesBackward()
        walker.moveToEnd()

        while (walker.canEnter() && walker.enter() != tree.children.get(1)) {
            while (!walker.canEnter()) {
                walker.exit()
            }
        }

        when: "we copy the given walker"
        final TreeWalker<TreeElement> copy = new TreeWalker<>(walker)

        then: "we expect to get a copy of the given walker"
        copy.doesMoveForward() == walker.doesMoveForward()
        copy.root == walker.root
        copy.path == walker.path
        copy.isAtLocation(walker)
        copy.current() == walker.current()
    }

    def "#move allows to move a walker to the location of another one"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
        walker.movesBackward()
        walker.moveToEnd()

        while (walker.canEnter() && walker.enter() != tree.children.get(1)) {
            while (!walker.canEnter()) {
                walker.exit()
            }
        }

        when: "we instantiate a walker that moves to the given one"
        final TreeWalker<TreeElement> second = new TreeWalker<>(TreeElement.class, tree)
        second.moveTo(walker)

        then: "we expect to get a copy of the given walker"
        second.doesMoveForward() != walker.doesMoveForward()
        second.root == walker.root
        second.path == walker.path
        second.isAtLocation(walker)
        second.current() == walker.current()
    }

    def "#enter allows to enter forward into to the next available child node"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #enter multiple times"
        final TreeElement[] result = [
                walker.enter(),
                walker.enter(),
                walker.enter()
        ]

        then: "we expect to walk throughout the expression"
        result[0] == tree
        result[1] == tree.children.get(0)
        result[2] == tree.children.get(0).children.get(0)
    }

    def "#enter throws NoSuchElementException if the walker can't move forward into another child"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #enter to much times"
        walker.enter()
        walker.enter()
        walker.enter()
        walker.enter()

        then: "we expect the walker to throw"
        thrown(NoSuchElementException.class)
    }

    def "#enter allows to enter backward to the next available child node"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
        walker.movesBackward()
        walker.moveToEnd()

        when: "we call #enter multiple times"
        final TreeElement[] result = [
                walker.enter(),
                walker.enter(),
                walker.enter()
        ]

        then: "we expect to walk throughout the tree"
        result[0] == tree
        result[1] == tree.children.get(2)
        result[2] == tree.children.get(2).children.get(0)
    }

    def "#enter throws NoSuchElementException if the walker can't move backward to another child"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
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

    def "#exit allows to move outside of the current node"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #enter multiple times"
        final TreeElement[] entered = [
                walker.enter(),
                walker.enter(),
                walker.enter()
        ]

        and: "then calling #exit"
        final TreeElement[] exited = [
                walker.exit(),
                walker.exit(),
                walker.exit()
        ]

        then: "we expect to walk throughout the expression"
        exited[0] == entered[2]
        exited[1] == entered[1]
        exited[2] == entered[0]
    }

    def "#exit throws NoSuchElementException if the walker is not into a node"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #exit"
        walker.exit()

        then: "we expect the walker to throw"
        thrown(NoSuchElementException.class)
    }

    def "#exit and #enter allows to move forward throughout the tree"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #enter as much as possible and then #exit"
        final List<TreeElement> result = new LinkedList<>()

        while (!walker.atEnd) {
            while (walker.canEnter()) {
                walker.enter()
            }

            result.add(walker.exit())
        }

        then: "we expect to moves throughout the entire tree"
        result == [
                tree.children.get(0).children.get(0),
                tree.children.get(0).children.get(1),
                tree.children.get(0),
                tree.children.get(1),
                tree.children.get(2).children.get(0),
                tree.children.get(2),
                tree
        ]
    }

    def "#exit and #enter allows to move backward throughout the tree"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
        walker.movesBackward()
        walker.moveToEnd()

        when: "we call #enter as much as possible and then #exit"
        final List<TreeElement> result = new LinkedList<>()

        while (!walker.atStart) {
            while (walker.canEnter()) {
                walker.enter()
            }

            result.add(walker.exit())
        }

        then: "we expect to moves throughout the entire tree"
        result == [
                tree.children.get(2).children.get(0),
                tree.children.get(2),
                tree.children.get(1),
                tree.children.get(0).children.get(1),
                tree.children.get(0).children.get(0),
                tree.children.get(0),
                tree
        ]
    }

    def "#exit and #enter allows to move forward then backward throughout the tree"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #enter as much as possible and then #exit"
        final List<TreeElement> result = new LinkedList<>()

        while (walker.canEnter() && walker.enter() != tree.children.get(1)) {
            while (!walker.canEnter()) result.add(walker.exit())
        }

        walker.movesBackward()

        while (!walker.atStart) {
            while (walker.canEnter()) {
                walker.enter()
            }

            result.add(walker.exit())
        }

        then: "we expect to moves throughout the tree"
        result[0] == tree.children.get(0).children.get(0)
        result[1] == tree.children.get(0).children.get(1)
        result[2] == tree.children.get(0)
        result[3] == tree.children.get(1)
        result[4] == tree.children.get(0).children.get(1)
        result[5] == tree.children.get(0).children.get(0)
        result[6] == tree.children.get(0)
        result[7] == tree
    }

    def "#exit and #enter allows to move backward and then forward throughout the expression"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)
        walker.movesBackward()
        walker.moveToEnd()

        when: "we call #enter as much as possible and then #exit"
        final List<TreeElement> result = new LinkedList<>()

        while (walker.canEnter() && walker.enter() != tree.children.get(1)) {
            while (!walker.canEnter()) result.add(walker.exit())
        }

        walker.movesForward()

        while (!walker.atEnd) {
            while (walker.canEnter()) {
                walker.enter()
            }

            result.add(walker.exit())
        }

        then: "we expect to moves throughout the tree"
        result[0] == tree.children.get(2).children.get(0)
        result[1] == tree.children.get(2)
        result[2] == tree.children.get(1)
        result[3] == tree.children.get(2).children.get(0)
        result[4] == tree.children.get(2)
        result[5] == tree
    }

    def "#getPath always return the current path"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

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
                [tree, tree.children.get(0), tree.children.get(0).children.get(0)],
                [tree, tree.children.get(0), tree.children.get(0).children.get(1)],
                [tree, tree.children.get(0)],
                [tree, tree.children.get(1)],
                [tree, tree.children.get(2), tree.children.get(2).children.get(0)],
                [tree, tree.children.get(2)],
                [tree]
        ]
    }

    def "#setMovingForward make the walker going forward"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #setMovingForward"
        walker.setMovingForward(true)

        then: "we expect the walker to goes movesForward"
        walker.doesMoveForward()
    }

    def "#setMovingForward make the walker going backward"() {
        given: "a tree"
        final TreeElement tree = makeNode([
                makeNode([makeNode(), makeNode()]),
                makeNode(),
                makeNode([makeNode()])
        ])

        and: "a walker over the given tree"
        final TreeWalker<TreeElement> walker = new TreeWalker<>(TreeElement.class, tree)

        when: "we call #setMovingForward"
        walker.setMovingForward(false)

        then: "we expect the walker to goes movesBackward"
        !walker.doesMoveForward()
    }
}
