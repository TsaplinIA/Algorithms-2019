@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(first: String, second: String): String {
    val substrings = mutableListOf("")// Ресурсоёмкость: O(t)
    val fl = first.lastIndex
    val sl = second.lastIndex
    for (i in 0..fl) {//f раз
        for (j in 0..sl) {//s раз
            var tempStr = ""
            var count = 0
            while ((i + count) <= fl && (j + count) <= sl && first[i + count] == second[j + count]) {//Трудоёмкость O(<k>)
                tempStr += first[i + count]
                count++
            }
            if (tempStr.length > substrings[0].length)
                substrings[0] = tempStr
        }
    }
    return substrings[substrings.lastIndex]
}//Трудоёмкость - O(n*m), Ресурсоёмкость - O(1), n & m - длинны первого и второго слова сответственно
/*ИТОГО:
    s - длина второго слова
    f - длина первого слова
    t - количество "возрастающих" по длинне подстрок подстрок
    k - длина подстроки
    Трудоёмкость: O(s * f * <k>)
    Ресурсоёмкость: O(t)
*/

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    TODO()
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    val result = mutableSetOf<String>()//Ресурсоёмкость: O(l)
    val br = File(inputName).bufferedReader()
    var tempStr: String? = br.readLine()
    val matrix = mutableListOf<MutableList<Char>>()//Ресурсоёмкость: O(k * q)
    var i = 0
    while (tempStr != null) {//k раз
        val row = mutableListOf<Char>()//Ресурсоёмкость: O(q)
        for (j in 0..tempStr!!.lastIndex) //j раз Трудоёмкость: O(j)
            if (j % 2 == 0) row.add(tempStr[j])
        matrix.add(row)
        tempStr = br.readLine()
        i++
    }//Трудоёмкость: O(k * j);Ресурсоёмкость: O(q)
    for (word in words) {//  p раз
        var tmp = 1
        var tempList = mutableListOf<Pair<Pair<Int, Int>, MutableList<Pair<Int, Int>>>>()//Ресурсоёмкость: O(<m>)
        for (i in 0..matrix.lastIndex)//k раз,Трудоёмкость: O(k * q)
            for (j in 0..matrix[i].lastIndex)//q раз
                if (word[0] == matrix[i][j]) tempList.add((i to j) to mutableListOf(i to j))
        var wordFind = false
        while (tmp <= word.lastIndex && !wordFind) {//<s> раз
            val tempList2 = mutableListOf<Pair<Pair<Int, Int>, MutableList<Pair<Int, Int>>>>()//Ресурсоёмкость: O(<m>)
            tempList.forEach {//<d> раз
                val pairNow = it.first
                val usePair = it.second
                mutableListOf(//тут мы используем память, но не храним, так что в ресурсоёмкость не включаю
                    pairNow.first + 1 to pairNow.second,
                    pairNow.first - 1 to pairNow.second,
                    pairNow.first to pairNow.second + 1,
                    pairNow.first to pairNow.second - 1
                ).filter { el ->
                    el.first <= matrix.lastIndex &&
                            el.first >= 0 &&
                            el.second <= matrix.first().lastIndex &&
                            el.second >= 0 &&
                            matrix[el.first][el.second] == word[tmp] &&
                            el !in usePair
                }.map { neigh ->
                    val usePairNeigh = usePair.toMutableList()
                    usePairNeigh.add(neigh)
                    if (word.lastIndex <= usePairNeigh.lastIndex) wordFind = true
                    tempList2.add(neigh to usePairNeigh)
                }
                if (wordFind) println("$word - $usePair")
            }// // Трудоёмкость: O(<d>)
            tempList = tempList2.toMutableList()
            tmp++
        }// Трудоёмкость: O(<d> * <s>)
        if (wordFind) result.add(word)
    }// Трудоёмкость: O(p * <d> * <s>)
    return result
}/*
ИТОГО:
    l - кол-во найденных слов
    k - кол-во строк; Трудоёмкость: O(i * q)
    j - количество символов в строке
    p - кол-во искомых слов
    s - количество симолов в искомом словае
    q - количество символов в строке, исключая пробелы
    d - количество символа (из s) в матрице
    m - количество "путей"(как тупиковых, так и истинных) составления искомого слова по матрице умноженое на среднюю дниту "пути"
    рост q ~ росту j ->
    Трудоёмкость: O(p * k * q + p * <d> * <s>) + O(k * j) =
    = O(p * k * q + p * <d> * <s>) + O(k * q) =
    = O((p + 1) * k * q + p * <d> * <s>) =* рост p + 1 ~ росту p
    =* O(p * (k * q +  <d> * <s>))
    //Ресурсоёмкость: O(l) + O(k * q) + 2O(<m>) =(l <= m, a чаще всего l << m) = O(k * q + <m>)
*/

fun main() {
    val a = baldaSearcher(
        "input/balda_in1.txt",
        setOf("ТРАВКА", "ТРАВА", "КРАН", "АКВА", "НАРТЫ", "РАК")
    ) // "ТРАВА", "КРАН", "АКВА", "НАРТЫ", "РАК"
    println(a)
}