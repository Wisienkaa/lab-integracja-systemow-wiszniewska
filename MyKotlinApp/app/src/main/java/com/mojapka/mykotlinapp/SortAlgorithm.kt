class SortAlgorithm {

    // Implementacja sortowania szybkiego (quicksort)
    fun quickSort(input: String): String {
        val numbers = input.split(" ").map { it.toInt() }.toIntArray()
        quickSort(numbers, 0, numbers.size - 1)
        return numbers.joinToString(" ")
    }

    private fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            val pi = partition(arr, low, high)
            quickSort(arr, low, pi - 1)
            quickSort(arr, pi + 1, high)
        }
    }

    private fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1
        for (j in low until high) {
            if (arr[j] < pivot) {
                i++
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }
        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp
        return i + 1
    }

    // Implementacja sortowania bąbelkowego (bubblesort)
    fun bubbleSort(input: String): String {
        val numbers = input.split(" ").map { it.toInt() }.toIntArray()
        val n = numbers.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (numbers[j] > numbers[j + 1]) {
                    val temp = numbers[j]
                    numbers[j] = numbers[j + 1]
                    numbers[j + 1] = temp
                }
            }
        }
        return numbers.joinToString(" ")
    }
}
