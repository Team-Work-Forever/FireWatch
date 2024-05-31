package com.example.firewatch.shared.errors

import java.lang.Exception

class HttpFailedException :
    Exception("The response is invalid and it cannot be read")
