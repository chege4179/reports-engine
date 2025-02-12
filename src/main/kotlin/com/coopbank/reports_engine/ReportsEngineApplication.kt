package com.coopbank.reports_engine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan(basePackages = ["com.coopbank.reports_engine"])
@SpringBootApplication
class ReportsEngineApplication

fun main(args: Array<String>) {
	runApplication<ReportsEngineApplication>(*args)
}
