abstract class AbstractSolutionTest<T>(private val year: Int, private val day: Int) {
    val parser get() = parser()

    fun data(modifier: String? = null) = loadData(year, day, modifier)

    fun shortData() = data("short")

    abstract fun parser(): InputParser<T>
}