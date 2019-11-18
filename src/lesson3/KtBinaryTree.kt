package lesson3

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>>() : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    constructor(elementMin: T?, elementMax: T?) : this() {
        max = elementMax
        min = elementMin
    }

    var max: T? = null
    var min: T? = null


    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    val mySubTrees = mutableListOf<KtBinaryTree<T>>()

    override fun add(element: T): Boolean {
        val success = oldAdd(element)
        addInSubTree(element)
        return success
    }

    fun oldAdd(element: T): Boolean {
        require(!(min != null && element.compareTo(min!!) < 0))
        require(!(max != null && element.compareTo(max!!) >= 0))
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }//	Трудоёмкость O(log n), где n - количество узлов

    private fun addInSubTree(element: T): Boolean {
        var result = false
        mySubTrees.forEach {
            if (
                (it.min == null || (it.min != null && element.compareTo(it.min!!) >= 0)) &&
                (it.max == null || (it.max != null && element.compareTo(it.max!!) < 0))
            ) {
                it.oldAdd(element)
                result = true
            }
        }
        return result
    }

    override fun checkInvariant(): Boolean =
        root?.let { checkInvariant(it) } ?: true

    override fun height(): Int = height(root)

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    private fun height(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + max(height(node.left), height(node.right))
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        TODO()
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }//	Трудоёмкость O(log n), где n - количество узлов

    inner class BinaryTreeIterator internal constructor() : MutableIterator<T> {
        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        override fun hasNext(): Boolean {
            // TODO
            throw NotImplementedError()
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        override fun next(): T {
            // TODO
            throw NotImplementedError()
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            // TODO
            throw NotImplementedError()
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Найти множество всех элементов в диапазоне [fromElement, toElement)
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> = createSubTree(fromElement, toElement)
    //	Трудоёмкость O(n * log n)
    //  РесурсоёмкостьO(n)

    private fun fillSubTree(node: Node<T>, fromElement: T?, toElement: T?, tree: KtBinaryTree<T>) {
        val temp1 = fromElement == null || node.value.compareTo(fromElement) >= 0
        val temp2 = toElement == null || node.value.compareTo(toElement) < 0
        when {
            temp1 && temp2 -> {
                if (node.left != null) fillSubTree(node.left!!, fromElement, toElement, tree)
                if (node.right != null) fillSubTree(node.right!!, fromElement, toElement, tree)
                tree.add(node.value)
            }
            temp1 && !temp2 -> if (node.left != null) fillSubTree(node.left!!, fromElement, toElement, tree)
            else -> if (node.right != null) fillSubTree(node.right!!, fromElement, toElement, tree)
        }
    }

    private fun createSubTree(fromElement: T?, toElement: T?): SortedSet<T> {
        val newTree = KtBinaryTree(fromElement, toElement)
        if (root != null) fillSubTree(root!!, fromElement, toElement, newTree)
        mySubTrees.add(newTree)
        newTree.mySubTrees.add(this)
        return newTree
    }
    //	Трудоёмкость O(n * log n), где n - количество узлов ( (O(1) + O(log n)(add)) - n раз)
    //  РесурсоёмкостьO(n)

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> = createSubTree(null, toElement)
    //	Трудоёмкость O(n * log n)
    //  РесурсоёмкостьO(n)

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> = createSubTree(fromElement, null)
    //	Трудоёмкость O(n * log n)
    //  РесурсоёмкостьO(n)

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
