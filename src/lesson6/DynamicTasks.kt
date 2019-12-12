@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val table = MutableList(first.length) { MutableList(second.length) { 0 } }
    for (i in 0..first.lastIndex)
        for (j in 0..second.lastIndex) {
            table[i][j] = maxOf(
                if (i > 0) table[i - 1][j] else 0,
                if (j > 0) table[i][j - 1] else 0,
                if (first[i] == second[j]) {
                    if (i > 0 && j > 0) table[i - 1][j - 1] + 1
                    else 1
                } else 0
            )
        }
    var result = ""
    var i = first.lastIndex
    var j = second.lastIndex
    while (i >= 0 && j >= 0 && table[i][j] != 0) {
        when {
            first[i] == second[j] -> {
                result += first[i]
                i--
                j--
            }
            i > 0 && j > 0 -> if (table[i - 1][j] > table[i][j - 1]) i-- else j--
            i == 0 && j > 0 -> j--
            j == 0 && i > 0 -> i--
        }
    }
    return result.reversed()
}
/*
    Пусть first.size = m, second.size = n
    тогда Трудозатраты  = O(n * m + m + n) в стреднем случае O(n * m)
    Ресурсозатраты O(n * m)
 */

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val tmp = MutableList(list.size) { 1 }
    var maxL = 0
    var maxEl: Int? = null
    for (i in 0..list.lastIndex) {
        var max = 0
        for (j in 0..i) {
            if (list[j] < list[i] && tmp[j] > max) max = tmp[j]
        }
        tmp[i] = max + 1
        if (tmp[i] > maxL) maxL = tmp[i]
    }
    val result = mutableListOf<Int>()
    while (maxL > 0) {
        for (i in 0..tmp.lastIndex)
            if (tmp[i] == maxL && (maxEl == null || list[i] < maxEl)) {
                result.add(0, list[i])
                maxEl = list[i]
                break
            }
        maxL--
    }
    return result
}
/*
    Пусть list.size = n
    тогда Трудозатраты  = O((n^2)/2) = O(n^2)
    Ресурсозатраты O(n)
 */

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    var result = 0
    val pole = File(inputName)
        .readLines()
        .toList()
        .map { line ->
            line.split(" ")
                .map { it.toInt() }
                .toMutableList()
        }
        .toMutableList()

    for (i in 0..pole.lastIndex)
        for (j in 0..pole[i].lastIndex) {
            val tmp = mutableListOf<Int>()
            if (i > 0 && j > 0) tmp.add(pole[i - 1][j - 1])
            if (i > 0) tmp.add(pole[i - 1][j])
            if (j > 0) tmp.add(pole[i][j - 1])
            pole[i][j] = pole[i][j] + if (tmp.size > 0) tmp.min()!! else 0
            result = pole[i][j]
        }
    return result
}
/*
    Пусть ширина поля = m, высота поля = n
    тогда Трудозатраты  = O(n * m)
    Ресурсозатраты O(n * m + const) = O(n * m)
 */
// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5