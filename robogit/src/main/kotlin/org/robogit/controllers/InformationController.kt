package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.robogit.domain.Information
import org.robogit.domain.Type
import org.robogit.dto.InformationSumDto
import org.robogit.dto.MegaInformationDto
import org.robogit.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import java.nio.file.Files
import java.nio.file.Paths
import javax.servlet.ServletContext


@RestController
@RequestMapping("/api")
@Slf4j
class InformationController {
  @Autowired
  private val informationRepository: InformationRepository? = null

  @Autowired
  private val controllerRepository: ControllerRepository? = null

  @Autowired
  private val mechanicDetailRepository: MechanicDetailRepository? = null

  @Autowired
  private val motorRepository: MotorRepository? = null

  @Autowired
  private val platformRepository: PlatformRepository? = null

  @Autowired
  private val sensorRepository: SensorRepository? = null


//  @Autowired
//  private val storageService: StorageService? = null

  @ModelAttribute
  fun setResponseHeaders(response: HttpServletResponse) {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
  }

  @GetMapping("/information")
  fun getInformation(): List<InformationSumDto?>? {
    println("GET ALL INFORMATIONS!")
    return informationRepository?.findPopular()
  }

  @GetMapping("/information/{page}")
  fun getInformationByPage(@PathVariable("page") numPage: Int): List<InformationSumDto?>? {
    println("getInformationByPage")
    val page = PageRequest.of(numPage, 50)
    return informationRepository?.findPagePopular(page)?.content
  }

  @GetMapping("/information/by_id/{id}")
  fun getInformationById(@PathVariable("id") id: Int): MegaInformationDto? {
    println("getInformationById")
    val information = informationRepository?.findById(id)?.get()
    if (information?.type == Type.CONTROLLER){
      return controllerRepository?.selectMegaInformationControllerById(id)
    } else
    if (information?.type == Type.MECHANIC_DETAIL){
      return mechanicDetailRepository?.selectMegaInformationMechanicDetailById(id)
    } else
    if (information?.type == Type.MOTOR){
        return motorRepository?.selectMegaInformationMotorById(id)
    } else
    if (information?.type == Type.PLATFORM){
        return platformRepository?.selectMegaInformationPlatformById(id)
    } else
    if (information?.type == Type.SENSOR){
        return sensorRepository?.selectMegaInformationSensorById(id)
    } else {
    return MegaInformationDto(information!!)
    }
  }

  @GetMapping("/information_only/by_id/{id}")
  fun getOnlyInformationById(@PathVariable("id") id: Int): Information? {
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

  @GetMapping("/information/filter")
  fun filter(@RequestParam(defaultValue = "0", required = false) pageNum: Int,
             @RequestParam(required = false) min_price: Float?,
             @RequestParam(required = false) max_price: Float?) : List<Information?>?{
    println("Controller!")
    val page = PageRequest.of(pageNum, 50)
    return informationRepository?.filter(page, min_price, max_price)?.content
  }

//  @PostMapping("/")
//  fun handleFileUpload(@RequestParam("file") file: MultipartFile,
//                       redirectAttributes: RedirectAttributes): String {
//
//    storageService?.store(file)
//    return "OK"
//  }
}
