package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.dto.InformationSumDto
import org.robogit.repository.InformationRepository
import org.robogit.service.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.ModelAttribute
import java.nio.file.Files
import java.nio.file.Paths
import javax.servlet.ServletContext
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping


@RestController
@RequestMapping("/api")
@Slf4j
class InformationController {
  @Autowired
  private val informationRepository: InformationRepository? = null

  @Autowired
  private val storageService: StorageService? = null

  @ModelAttribute
  fun setResponseHeaders(response: HttpServletResponse) {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
  }

  @GetMapping("/information")
  fun getInformation(): List<InformationSumDto?>? {
    println("Controller!")
    return informationRepository?.findPopular()
  }

  @GetMapping("/information/{page}")
  fun getInformationByPage(@PathVariable("page") numPage: Int): List<InformationSumDto?>? {
    println("getInformationByPage")
    val page = PageRequest.of(numPage, 50)
    return informationRepository?.findPagePopular(page)?.content
  }

  @GetMapping("/information/by_id/{id}")
  fun getInformationById(@PathVariable("id") id: Int): Information? {
    println("getInformationByPage")
    return informationRepository?.findById(id)?.get()
  }

  @GetMapping("/information/image/{id}")
  fun getImage(@PathVariable id: Int, ctx: ServletContext): ByteArray {
    println("getImage called")
    var rpath = ctx.getRealPath("/external/")
    rpath = "$rpath/$id"
    val path = Paths.get(rpath)
    return Files.readAllBytes(path)
  }

  @PostMapping("/")
  fun handleFileUpload(@RequestParam("file") file: MultipartFile,
                       redirectAttributes: RedirectAttributes): String {

    storageService?.store(file)
    return "OK"
  }
}
