package org.liara.support

import org.liara.support.index.ListIndex
import org.liara.support.view.View
import spock.lang.Specification

class ListIndexSpecification extends Specification {
    private static final Comparator<String> STRING_COMPARATOR = (
            Comparator.comparing({ String x -> x })
    );

    def "#ListIndex allows to instantiate a new empty index"() {
        expect: "#ListIndex to instantiate a new empty index"
        new ListIndex(STRING_COMPARATOR).values.empty
        new ListIndex(STRING_COMPARATOR).keys.empty
        new ListIndex(STRING_COMPARATOR).size == 0
    }

    def "#put add key / value pairs to the index"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "a list of keys to put into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        when: "we put all values into the index"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        then: "we expect the index to have been updated"
        index.keys == View.readonly(keys)
        index.values == View.readonly([0, 1, 2, 3, 4])
    }

    def "#put update existing pair if the key is already used"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we put all values into the index twice"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(keys.size() - ith - 1), ith)
        }

        then: "we expect the index to have been updated"
        index.keys == View.readonly(keys)
        index.values == View.readonly([4, 3, 2, 1, 0])
    }

    def "#setValue update an existing pair value"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.setValue(keys.size() - ith - 1, ith)
        }

        then: "we expect the index to have been updated"
        index.keys == View.readonly(keys)
        index.values == View.readonly([4, 3, 2, 1, 0])
    }

    def "#setValue update an existing pair value by using their key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.setValue(keys.get(keys.size() - ith - 1), ith)
        }

        then: "we expect the index to have been updated"
        index.keys == View.readonly(keys)
        index.values == View.readonly([4, 3, 2, 1, 0])
    }

    def "#setKey update an existing pair key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.setKey(1, "bird")
        index.setKey(2, "cow")
        index.setKey(4, "cat")

        then: "we expect the index to have been updated"
        index.keys == View.readonly(["first", "bird", "cow", "fourth", "cat"])
        index.values == View.readonly([0, 1, 2, 3, 4])
    }

    def "#setKey update an existing pair key by using its original key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.setKey("second", "bird")
        index.setKey("third", "cow")
        index.setKey("last", "cat")

        then: "we expect the index to have been updated"
        index.keys == View.readonly(["first", "bird", "cow", "fourth", "cat"])
        index.values == View.readonly([0, 1, 2, 3, 4])
    }

    def "#setKey throw an error if the given key to update does not exists"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.setKey("pwet", "bird")

        then: "the method to throw"
        thrown(IllegalArgumentException.class)
    }

    def "#setKey throw an error if the given pair index to update does not exists"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.setKey(120, "bird")

        then: "the method to throw"
        thrown(IllegalArgumentException.class)
    }

    def "#setKey throw an error if the key to assign is already used"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.setKey("first", "last")

        then: "the method to throw"
        thrown(IllegalArgumentException.class)
    }

    def "#remove remove a pair of the index"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.remove(2)
        index.remove(3)

        then: "we expect the index to have been updated"
        index.keys == View.readonly(["first", "second", "fourth"])
        index.values == View.readonly([0, 1, 3])
    }

    def "#remove does not break #getIndexOfKey"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.remove(2)
        index.remove(3)

        then: "we expect #getIndexOfKey to continue to work"
        index.getIndexOfKey("first") == 0
        index.getIndexOfKey("second") == 1
        index.getIndexOfKey("fourth") == 2
    }

    def "#remove remove a pair of the index by using its key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.remove("third")
        index.remove("last")

        then: "we expect the index to have been updated"
        index.keys == View.readonly(["first", "second", "fourth"])
        index.values == View.readonly([0, 1, 3])
    }

    def "#remove throws if the given key does not exists"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.remove("patapwet")

        then: "we expect the method to throw"
        thrown(IllegalArgumentException.class)
    }

    def "#clear empty the index"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        when: "we update values of the index"
        index.clear()

        then: "we expect the index to have been updated"
        index.keys == View.readonly([])
        index.values == View.readonly([])
    }

    def "#getIndexOfKey return the index of the given key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        expect: "#getIndexOfKey to return the index of the given key"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.getIndexOfKey(keys.get(ith)) == ith
        }
    }

    def "#containsKey return true if the key exists into the index"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        expect: "#containsKey to return true if the key exists into the index"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.containsValueWithKey(keys.get(ith))
        }

        !index.containsValueWithKey("patapwe")
    }

    def "#getValue return the value of a key"() {
        given: "an index"
        final ListIndex<String, Integer> index = new ListIndex<>(STRING_COMPARATOR)

        and: "an some pairs into the index"
        final List<String> keys = [
                "first", "second", "third", "fourth", "last"
        ]

        for (int ith = 0; ith < keys.size(); ++ith) {
            index.put(keys.get(ith), ith)
        }

        expect: "#getValue to return the value of a key"
        for (int ith = 0; ith < keys.size(); ++ith) {
            index.getValueWithKey(keys.get(ith)) == ith
        }
    }
}
