## Yandex Parser

Парсер выдачи Яндекса посредством XML, который позволяет получить метатеги сайтов (title, description, keywords) по ссылкам из выдачи. Создавался для личных целей - анализа конкурентов и автоматической генерации мета тегов.

### Как настроить?

Вы должны быть зарегистрированы в Яндексе. 

1. Переходим по ссылке https://xml.yandex.ru/settings/
2. Указываем в настройках свой IP
3. Из ссылки вида https://yandex.ru/search/xml?user= сохраняем два параметра user и key
4. Копируем репозиторий себе на ПК и открываем проект в IntelliJ IDEA или любой другой IDE на ваш выбор
5. Устанавливаем зависимости в gradle
6. Пользуемся

### Пример использования

В файле Program.kt в функции main указываем 2 параметра, наши сохраненные user и key. Затем создаем переменную, в которой будут хранится наши ссылки из выдачи. Указываем текстоый запрос и номер страницы (начинается с нуля). 

Пример:

```kotlin
val sites = GetMetaData().getLinks(user, key, "как скачать windows 10", 0)
```

Объявляем переменную, типа StringBulider или просто строковую

```kotlin
val sb = StringBuilder()
```

Затем проходимся по полученному массиву, получаем мета-теги и записываем их в файл

```kotlin
sites.forEach {
       val title = GetMetaData().getTitle(it)
       val description = GetMetaData().getDescription(it)
       val keywords = GetMetaData().getKeywords(it)
     
       sb.append(it+"\n")
       sb.append(title+"\n")
       sb.append(description+"\n")
       sb.append(keywords)
       sb.append("\n\n")
}
FileDataHelper().writeContent("D:/links.txt",sb.toString())
```

Если Вам не важна скорость выполнения, то можно существенно ускорить процесс, вызвав метод parallelStream() перед forEach

```kotlin
sites.parallelstream().forEach {
       val title = GetMetaData().getTitle(it)
       val description = GetMetaData().getDescription(it)
       val keywords = GetMetaData().getKeywords(it)
     
       sb.append(it+"\n")
       sb.append(title+"\n")
       sb.append(description+"\n")
       sb.append(keywords)
       sb.append("\n\n")
}
FileDataHelper().writeContent("D:/links.txt",sb.toString())
```

### Используемые Классы

#### Класс GetMetaData методы

- getLinks(user: String, key: String, query: String, page: Int) принимает 4 параметра: имя пользователя Яндекс, ключ, запрос, и номер страницы и возвращает массив ссылок из выдачи (кроме ссылок на объявления Я.Директ)
- getTitle(url: String) - принимает ссылку на сайт и возвращает его тег <Title>
- getDescription(url: String) - принимает ссылку на сайт и возвращает его тег <Description>
- getKeywords(url: String) - принимает ссылку на сайт и возвращает еготег <Keywords>

#### Класс FileDataHelper методы

- getContent(source: String) - принимает путь к файлу и считывает данные из него
- writeContent(source: String, content: String) - принимает путь к файлу и записывает в него содержимое (при последующих вызовах добавляет содержимое в файл)

#### Класс RequestHelper методы

- getData(url: String) - принимает ссылку и скачивает содержимое в виде текста
