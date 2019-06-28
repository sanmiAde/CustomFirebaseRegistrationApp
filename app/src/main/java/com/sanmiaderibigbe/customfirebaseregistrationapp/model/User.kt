package com.sanmiaderibigbe.customfirebaseregistrationapp.model


import java.io.Serializable

data class User(
    var fullName: String = "", var email: String = "",
    var mobile: String = "", var password: String = "", var verify_password : String ="",
    var accountName : String ="",
    var accountNumber: String = "",
    var BVN : String = "",
    var accountType : String = "",
    var BankName : String  = ""
) : Serializable