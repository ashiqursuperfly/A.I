package latin_square_completion

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("\npermutations of 3 elements:")
            for(configuration in Permutations(10)) {
                println(configuration)
            }
        }

    }

}