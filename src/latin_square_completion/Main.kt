package latin_square_completion

import latin_square_completion.permutation.Permutations

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            for(configuration in Permutations(10)) {
                println(configuration)
            }
        }

    }

}