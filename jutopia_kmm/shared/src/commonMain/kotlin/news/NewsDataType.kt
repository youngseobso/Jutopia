package news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class NewsCategory(val idx: Int, val brand: String, val imgSrc: String)

data class NewsDetail(val title: String, val description: String, val publisher: String, val date: String, val time: String, val link: String)



@Serializable
data class NewsItem(
    @SerialName("title")
    val title: String,
    @SerialName("link")
    val link: String,
    @SerialName("description")
    val description: String,
    @SerialName("pubDate")
    val pubDate: String,
)

@Serializable
data class ResponseNewsData(
    @SerialName("items")
    val body: List<NewsItem>
)

@Serializable
data class RequestSummary(
    @SerialName("link")
    val link: String
)