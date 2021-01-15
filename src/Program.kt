

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

    val user = "klesareff-igor"
    val key = "03.89507884:2d756821c4ce510ebc11fb97ee7fcf1c"

    val sites = GetMetaData().getLinks(user, key, "как скачать windows 10", 0)

    sites.forEach {
        val title = GetMetaData().getTitle(it)
        val description = GetMetaData().getDescription(it)
        val keywords = GetMetaData().getKeywords(it)

        FileDataHelper().writeConetent("D:/links.txt",
            "${it}\n${title}\n${description}\n${keywords}\n\n"
        )
        println(it)
    }

}