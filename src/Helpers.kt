import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class FileDataHelper {

    fun getContent(source: String): String {
        var result = ""
        try {
            val bufferedReader: BufferedReader = Files.newBufferedReader(Paths.get(source))
            result = bufferedReader.use { it.readText() }
        } catch (error: IOException) {
            error.printStackTrace()
        }
        return result
    }

    fun writeContent(source: String, content: String) {
        try {
            Files.write(Paths.get(source), content.toByteArray(), StandardOpenOption.APPEND, StandardOpenOption.CREATE)
        } catch (error: IOException) {
            error.printStackTrace()
        }
    }

}

class RequestHelper {
    fun getData(url: String): String {
        val sb = StringBuilder()

        with(URL(url).openConnection() as HttpURLConnection) {
            addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/87.0.4280.141 Safari/537.36")
            connectTimeout=5000

            try {
                val text = inputStream.bufferedReader().readText()
                sb.append(text)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                disconnect()
            }

        }
        return sb.toString()
    }
}

