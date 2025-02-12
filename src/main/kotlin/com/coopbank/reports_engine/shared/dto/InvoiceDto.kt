package com.coopbank.reports_engine.shared.dto

data class InvoiceDto(
    val total:String,
    val merchantName:String,
    val tillCode:String,
    val email:String,
    val statementDate:String,
)


data class Product(
    val id:Int,
    val name:String,
    val price:Float,
    val referenceNumber:String,
)
