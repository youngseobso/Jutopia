package news

data class NewsCategory(val idx: Int, val brand: String, val imgSrc: String)

data class NewsDetail(val title: String, val publisher: String, val date: String, val time: String, val link: String)