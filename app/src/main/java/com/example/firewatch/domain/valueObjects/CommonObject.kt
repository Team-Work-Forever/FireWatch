package com.example.firewatch.domain.valueObjects

import com.example.firewatch.R
import com.example.firewatch.shared.errors.DomainException
import com.example.firewatch.shared.utils.TranslateUtil

class CommonObject private constructor(val value: String){
    companion object {
        fun create(value: String, field: String): Result<CommonObject> {
            if (value.isEmpty()) {
                return Result.failure(DomainException("${TranslateUtil.context!!.getString(R.string.please_provide_an)} $field"))
            }

            return Result.success(CommonObject(value))
        }
    }
}