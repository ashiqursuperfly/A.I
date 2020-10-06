package graphcolouring

import java.io.File
import java.io.FileWriter
import java.io.IOException

object FileUtil {

    fun readFileLineByLineUsingForEachLine(fileName: String, operation: (line: String) -> Unit) {
        File(fileName).forEachLine {
            operation(it)
        }
    }

    fun writeToFile(fileName: String, data: String) {
        val path = System.getProperty("user.dir") + "/src/graphcolouring/solving_timetabling/ai_lab_class/mysolutions/$fileName"

        try {
            val fw = FileWriter(path, false)
            fw.write(data)
            fw.close()
        } catch (e: IOException) {
        }

    }


}