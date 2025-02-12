package com.coopbank.reports_engine.features.reports

import com.coopbank.reports_engine.shared.dto.InvoiceDto
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.io.ByteArrayOutputStream

@CrossOrigin(origins = ["*"])
@RequestMapping("/api/reports")
@RestController
class ReportsController {
    @Autowired
    private val reportsService: ReportsService? = null

    @PostMapping("/generateInvoice")
    fun generateInvoice(
        @RequestBody payload:InvoiceDto,
        response: HttpServletResponse
    ): ResponseEntity<ByteArray> {
        try {
            val httpHeaders = HttpHeaders()
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-type", "application/octet-stream");
            response.setHeader("Content-type", "application/pdf");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-disposition", "attachment;filename=report.pdf");
            val reportStream: ByteArrayOutputStream = reportsService!!.generateInvoiceStatement(payload)
            return ResponseEntity(reportStream.toByteArray(), httpHeaders, HttpStatus.OK)
        } catch (ex: Exception) {
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }




}