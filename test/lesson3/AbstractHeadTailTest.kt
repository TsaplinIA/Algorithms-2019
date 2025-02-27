package lesson3

import java.util.*
import kotlin.test.*

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var newTree: SortedSet<Char>
    private lateinit var randomTree: SortedSet<Int>
    private val randomTreeSize = 1000
    private val randomValues = mutableListOf<Int>()

    protected fun fillTree(create: () -> SortedSet<Int>) {
        this.tree = create()
        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)

        this.randomTree = create()
        val random = Random()
        for (i in 0 until randomTreeSize) {
            val randomValue = random.nextInt(randomTreeSize) + 1
            if (randomTree.add(randomValue)) {
                randomValues.add(randomValue)
            }
        }
    }
    //myTest
    protected fun fillNewTree(create: () -> SortedSet<Char>) {
        this.newTree = create()

        newTree.add('B')
        newTree.add('I')
        newTree.add('N')
        newTree.add('A')
        newTree.add('R')
        newTree.add('Y')
        newTree.add('T')
        newTree.add('R')
        newTree.add('E')
        newTree.add('E')
    }


    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))


        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))


        //myTest
        var newSet = newTree.headSet('R')
        assertEquals(true, newSet.contains('B'))
        assertEquals(true, newSet.contains('I'))
        assertEquals(true, newSet.contains('N'))
        assertEquals(true, newSet.contains('A'))
        assertEquals(false, newSet.contains('R'))
        assertEquals(false, newSet.contains('Y'))
        assertEquals(false, newSet.contains('T'))
        assertEquals(true, newSet.contains('E'))
    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        //myTest
        val newSet = newTree.tailSet('T')

        assertEquals(false, newSet.contains('B'))
        assertEquals(false, newSet.contains('I'))
        assertEquals(false, newSet.contains('N'))
        assertEquals(false, newSet.contains('A'))
        assertEquals(false, newSet.contains('R'))
        assertEquals(true, newSet.contains('Y'))
        assertEquals(true, newSet.contains('T'))
        assertEquals(false, newSet.contains('E'))
    }

    protected fun doHeadSetRelationTest() {
        val set: SortedSet<Int> = tree.headSet(7)
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.add(-2)
        assertTrue(tree.contains(-2))
        tree.add(12)
        assertFalse(set.contains(12))
        assertFailsWith<IllegalArgumentException> { set.add(8) }
        assertEquals(8, set.size)
        assertEquals(13, tree.size)

        //myTest
        val newSet = newTree.headSet('R')
        assertEquals(8, newTree.size)
        assertEquals(5, newSet.size)
        newTree.add('X')
        assertFalse(newSet.contains('X'))
        assertFailsWith<java.lang.IllegalArgumentException> { newSet.add('Y') }
        newSet.add('C')
        assertTrue(newTree.contains('C'))
    }

    protected fun doTailSetRelationTest() {
        val set: SortedSet<Int> = tree.tailSet(4)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.add(42)
        assertTrue(tree.contains(42))
        tree.add(0)
        assertFalse(set.contains(0))
        assertFailsWith<IllegalArgumentException> { set.add(-2) }
        assertEquals(9, set.size)
        assertEquals(13, tree.size)

        //myTest
        val newSet = newTree.tailSet('T')
        assertEquals(8, newTree.size)
        assertEquals(2, newSet.size)
        newTree.add('X')
        assertTrue(newSet.contains('X'))
        assertFailsWith<java.lang.IllegalArgumentException> { newSet.add('D') }
        newSet.add('Y')
        assertTrue(newTree.contains('Y'))
        newTree.add('G')
        assertFalse(newSet.contains('G'))

    }

    protected fun doSubSetTest() {
        val smallSet: SortedSet<Int> = tree.subSet(3, 8)
        assertEquals(false, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
        assertEquals(true, smallSet.contains(3))
        assertEquals(true, smallSet.contains(4))
        assertEquals(true, smallSet.contains(5))
        assertEquals(true, smallSet.contains(6))
        assertEquals(true, smallSet.contains(7))
        assertEquals(false, smallSet.contains(8))
        assertEquals(false, smallSet.contains(9))
        assertEquals(false, smallSet.contains(10))

        assertFailsWith<IllegalArgumentException> { smallSet.add(2) }
        assertFailsWith<IllegalArgumentException> { smallSet.add(9) }

        val allSet = tree.subSet(-128, 128)
        for (i in 1..10)
            assertEquals(true, allSet.contains(i))

        val random = Random()
        val toElement = random.nextInt(randomTreeSize) + 1
        val fromElement = random.nextInt(toElement - 1) + 1

        val randomSubset = randomTree.subSet(fromElement, toElement)
        randomValues.forEach { element ->
            assertEquals(element in fromElement until toElement, randomSubset.contains(element))
        }

        //myTest
        val newSet = newTree.subSet('D', 'T')
        assertEquals(false, newSet.contains('B'))
        assertEquals(true, newSet.contains('I'))
        assertEquals(true, newSet.contains('N'))
        assertEquals(false, newSet.contains('A'))
        assertEquals(false, newSet.contains('Y'))
        assertEquals(false, newSet.contains('T'))
        assertEquals(true, newSet.contains('R'))
        assertEquals(true, newSet.contains('E'))

        assertFailsWith<IllegalArgumentException> { newSet.add('C') }
        assertFailsWith<IllegalArgumentException> { newSet.add('Z') }
    }

    protected fun doSubSetRelationTest() {
        val set: SortedSet<Int> = tree.subSet(2, 15)
        assertEquals(9, set.size)
        assertEquals(10, tree.size)
        tree.add(11)
        assertTrue(set.contains(11))
        set.add(14)
        assertTrue(tree.contains(14))
        tree.add(0)
        assertFalse(set.contains(0))
        tree.add(15)
        assertFalse(set.contains(15))
        assertFailsWith<IllegalArgumentException> { set.add(1) }
        assertFailsWith<IllegalArgumentException> { set.add(20) }
        assertEquals(11, set.size)
        assertEquals(14, tree.size)

        //myTest
        val newSet = newTree.subSet('D', 'T')
        assertEquals(4, newSet.size)
        assertEquals(8, newTree.size)
        newTree.add('F')
        assertTrue(newSet.contains('F'))
        newSet.add('H')
        assertTrue(newTree.contains('H'))
        newTree.add('T')
        assertFalse(newSet.contains('T'))
        newTree.add('C')
        assertFalse(newSet.contains('C'))
    }

}