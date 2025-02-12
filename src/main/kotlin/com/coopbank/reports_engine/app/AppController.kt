package com.coopbank.reports_engine.app

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RequestMapping("/api/app")
@RestController
class AppController {

    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello, World!"
    }


    @GetMapping("/hello/{name}")
    fun helloName(
        @PathVariable("name") name: String?
    ): String {
        return "Hello, $name!"
    }
}