import java.lang.StringBuilder
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Создаем массив ссылок из выдачи c помощью метода getLinks()
     * класса GetMetaData
     *
     * @param  user   Имя пользователя в Яндексе без @
     * @param  key    Параметр key в Яндекс.XML
     * @param  query  Запрос для выдачи (напр. Купить чай зеленый)
     * @param  page   Номер страницы, начинается с 0
     * @return        Массив ссылок из выдачи
     */

    val user = "yandex-user"
    val key = "yandex-key"

    val sites = GetMetaData().getLinks(user, key, "как скачать windows 10", 0)
    val sb = StringBuilder()

    sites
        .forEach {
            val title = GetMetaData().getTitle(it)
            val description = GetMetaData().getDescription(it)
            val keywords = GetMetaData().getKeywords(it)

            sb.append(it+"\n")
            sb.append(title+"\n")
            sb.append(description+"\n")
            sb.append(keywords)
            sb.append("\n\n")

            println(sb)
        }
    FileDataHelper().writeContent("D:/links.txt",sb.toString())
}