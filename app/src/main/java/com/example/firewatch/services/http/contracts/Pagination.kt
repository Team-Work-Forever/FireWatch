package com.example.firewatch.services.http.contracts

import com.google.gson.annotations.SerializedName

class Pagination(page: Int, pageSize: Int) {
    companion object {
        const val PAGE_SIZE = 10
        const val PAGE = 1
    }

    @get:SerializedName("page_size")
    val pageSize: Int by lazy {
        pageSize
    }

    @get:SerializedName("page")
    val page: Int by lazy {
        page
    }

    @get:SerializedName("total_pages")
    var totalPages: Int = 0
}