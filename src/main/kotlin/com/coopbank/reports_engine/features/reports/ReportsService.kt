package com.coopbank.reports_engine.features.reports

import com.coopbank.reports_engine.shared.dto.InvoiceDto
import com.coopbank.reports_engine.shared.dto.Product
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimplePdfExporterConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*


@Service
class ReportsService {

    @Autowired
    private val rLoader: ResourceLoader? = null


    fun generateInvoiceStatement(payload: InvoiceDto): ByteArrayOutputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        try {
            println("WE ARE HERE")

            val beanCollectionDataSource = JRBeanCollectionDataSource(
                Arrays.asList(
                    Product(id = 121, name = "Keyboard", price = 54884f, referenceNumber = "eeee"),
                    Product(id =122, name = "Mouse", price =54884f, referenceNumber ="ffff"),
                    Product(id =123,name =  "Laptop",price = 54884f,referenceNumber = "jjjj"),
                    Product(id =124,name = "Mobile", price =54884f,referenceNumber = "kkkk"),
                    Product(id =125,name = "Headphone",price = 54884f,referenceNumber = "ooppd")
                ), false
            )

            val parameters: MutableMap<String, Any> = HashMap()
            parameters["total"] = payload.total
            parameters["merchantName"] = payload.merchantName
            parameters["tillCode"] = payload.tillCode
            parameters["email"] = payload.email
            parameters["statementDate"] = payload.statementDate

            val logo: InputStream = rLoader!!.getResource("classpath:/reports/coop.png")!!.inputStream
            parameters["logo"] = logo

            val compileReport = JasperCompileManager
                .compileReport(rLoader.getResource("classpath:/reports/invoice.jrxml").inputStream)

            val jasperPrint: JasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource)

            val exporter = JRPdfExporter()
            val configuration = SimplePdfExporterConfiguration()
            configuration.isCompressed = true
            exporter.setConfiguration(configuration)
            exporter.setExporterInput(SimpleExporterInput(jasperPrint))
            exporter.exporterOutput = SimpleOutputStreamExporterOutput(byteArrayOutputStream)
            exporter.exportReport()
        }catch (e:Exception){
            e.printStackTrace()
        }
        println("Success")
        return byteArrayOutputStream


    }
}