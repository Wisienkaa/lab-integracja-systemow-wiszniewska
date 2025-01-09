package com.mojapka.sortfragments
class SortAlgorithm {

    private var numbers: IntArray = intArrayOf()

    fun setNumbers(input: String) {
        numbers = input.split(" ").map { it.toInt() }.toIntArray()
    }

    fun quickSort(): String {
        quickSort(0, numbers.size - 1)
        return numbers.joinToString(" ")
    }

    private fun quickSort(low: Int, high: Int) {
        if (low < high) {
            val pi = partition(low, high)
            quickSort(low, pi - 1)
            quickSort(pi + 1, high)
        }
    }

    private fun partition(low: Int, high: Int): Int {
        val pivot = numbers[high]
        var i = low - 1
        for (j in low until high) {
            if (numbers[j] < pivot) {
                i++
                swap(i, j)
            }
        }
        swap(i + 1, high)
        return i + 1
    }

    fun bubbleSort(): String {
        val n = numbers.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (numbers[j] > numbers[j + 1]) {
                    swap(j, j + 1)
                }
            }
        }
        return numbers.joinToString(" ")
    }

    private fun swap(i: Int, j: Int) {
        val temp = numbers[i]
        numbers[i] = numbers[j]
        numbers[j] = temp
    }
}
