package com.example.firewatch.shared.errors

class UserTypeNotExists(userType: String)
    : IllegalArgumentException("The UserType $userType does not exists") {
}