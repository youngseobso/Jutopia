package com.ssafy.jutopia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform