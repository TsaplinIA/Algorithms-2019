@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import kotlin.math.abs

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    val streets = mutableMapOf<String, MutableMap<Int, MutableList<String>>>()
    File(outputName).bufferedWriter().use {
        val br = File(inputName).bufferedReader()
        var line: String? = br.readLine()
        while (line != null) {
            line = line.replace(Regex(""" +"""), " ")
            if (Regex("""[A-zА-яёЁ]+ [A-zА-яёЁ]+ - [A-zА-яёЁ-]+ \d+""").matches(line)) {
                val parts = line.split(" ")
                streets.getOrPut(parts[3], { mutableMapOf() })
                    .getOrPut(parts[4].toInt(), { mutableListOf() })
                    .add(parts[0] + " " + parts[1])
                line = br.readLine()
            } else throw Exception("incorrect input")
        }
        for (street in streets.toSortedMap())
            for (number in street.value.toSortedMap())
                it.write(street.key + " " + number.key + " - " + number.value.sorted().joinToString(", ") + "\n")
    }
}// O(street.size * number.size) + O(getOrPut) - в лучшем случае O(n) + O(getOrPut), в худшем O(n/2 * n/2) ~ O(n^2)

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val temperaturesPlus = MutableList(5001) { 0 }
    val temperaturesMinus = MutableList(2731) { 0 }
    File(outputName).bufferedWriter().use {
        val br = File(inputName).bufferedReader()
        var line: String? = br.readLine()
        while (line != null) {
            if (Regex("""-?\d+.\d""").matches(line)) {
                val temp = (abs(line.toFloat()) * 10).toInt()
                if (line[0] != '-')
                    temperaturesPlus[temp]++
                else
                    temperaturesMinus[temp]++
            } else throw Exception("incorrect input")
            line = br.readLine()
        }
        val writer = it
        for (i in temperaturesMinus.lastIndex downTo 1)
            repeat(temperaturesMinus[i]) {
                writer.write("-${i / 10}.${i % 10}\n")
            }
        for (i in 0..temperaturesPlus.lastIndex)
            repeat(temperaturesPlus[i]) {
                writer.write("${i / 10}.${i % 10}\n")
            }
    }
}//Трудоёмкость - O(n), Ресурсоёмкость - O(1)???если не брать в расчёт вес файла вывода???

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

fun main() {
    val a = MutableList(5) { 0 }

    repeat(3) {
        println(1)
    }
}

