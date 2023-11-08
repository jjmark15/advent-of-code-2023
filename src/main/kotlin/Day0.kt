fun maxCaloriesCarried(calories: List<List<Int>>): Int = calories.maxOf(List<Int>::sum)

fun totalCaloriesCarriedByTopThreeElves(calories: List<List<Int>>): Int =
    calories.map(List<Int>::sum).sorted().takeLast(3).sum()