import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class GetMetaData {

    fun getLinks(user: String, key: String, query: String, page: Int): ArrayList<String> {
        val list = arrayListOf<String>()
        val queryString = query.replace(" ", "+")
        val xml = RequestHelper().getData(
            "https://yandex.ru/search/xml?user=${user}&key=${key}&query=${queryString}&lr=213&page=${page}"
        )
        val doc = Jsoup.parse(xml, "", Parser.xmlParser())

        for (e in doc.select("url")) {
            list.add(e.text())
        }

        return list
    }

    fun getTitle(url: String): String {
        val str = Jsoup.connect(url)
            .followRedirects(false)
            .get()
        return str.title()
    }

    fun getDescription(url: String): String {
        val str = Jsoup.connect(url)
            .followRedirects(false)
            .get()
        val desc = str
            .select("meta[name=description]")
            .attr("content")
        return desc
    }

    fun getKeywords(url: String): List<String> {
        val str = Jsoup.connect(url)
            .followRedirects(false)
            .get()
        val keys = str
            .select("meta[name=keywords]")
            .attr("content")
            .split(",")
            .map { it.trim() }
        return keys
    }

}

class GenerateMetaData {

//    val textMap: MutableMap<String, String> = mutableMapOf(
//        "[site]" to "Магазин.ру",
//        "[price]" to "{this.catalog.price.BASE}",
//        "[name]" to  "{=this.name}",
//        "[city]" to "Москва",
//        "[phone]" to "+ 7 (495) 000-00-00",
//        "[icon_cup]" to "☕",
//        "[icon_star]" to "★",
//        "[icon_phone]" to "☎",
//        "[icon_check]" to "✔"
//    )

    fun generateTagByTemplate(tags: MutableMap<String, String>, text: String): String {
        val str = text.split(Regex("[(\\s).,]")).toMutableList()
        val regex = Regex("[-?!)(:]")

        for (i in 0 until str.size) {
            tags.forEach {
                if(str[i] == it.key || str[i] == "${regex}${it.key}${regex}") {
                    str[i] = it.value
                }
            }
        }

        return str
            .joinToString(separator = " ")
            .replace(Regex("\\s+"), " ")
    }
}