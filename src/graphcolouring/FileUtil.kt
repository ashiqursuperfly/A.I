package graphcolouring

import java.io.File

object FileUtil {

    fun readFileLineByLineUsingForEachLine(fileName: String, operation: (line: String) -> Unit) {
        File(fileName).forEachLine {
            operation(it)
        }
    }


}