package org.robogit.controllers

import lombok.extern.slf4j.Slf4j
import org.apache.commons.io.FilenameUtils
import org.robogit.domain.*
import org.robogit.dto.*
import org.robogit.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/product")
@Slf4j
class ProductController {
  @Autowired
  private val informationRepository: InformationRepository? = null
  @Autowired
  private val platformRepository: PlatformRepository? = null
  @Autowired
  private val controllerRepository: ControllerRepository? = null
  @Autowired
  private val mechanicDetailRepository: MechanicDetailRepository? = null
  @Autowired
  private val motorRepository: MotorRepository? = null
  @Autowired
  private val sensorRepository: SensorRepository? = null
  @Autowired
  private val productUserRepository: ProductUserRepository? = null
  @Autowired
  private val productOrderRepository: ProductOrderRepository? = null
  @Autowired
  private val productCardRepository: ProductCardRepository? = null

  private fun addImage(infoWithId: Information, img: MultipartFile) {
    val extension = if (FilenameUtils.getExtension(img.originalFilename).isNotEmpty())
      FilenameUtils.getExtension(img.originalFilename) else ""

    val filePath = "/external/${infoWithId.id}" + extension
    val dest = File(filePath)
    img.transferTo(dest)
    infoWithId.image = filePath
    informationRepository?.save(infoWithId)
  }

  @PostMapping("/addPlatform")
  fun addPlatform(@RequestBody platformDto: PlatformDto,
                  @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val platform = Platform()
    val information = Information()
    information.type = Type.PLATFORM
    information.model = platformDto.model
    information.name = platformDto.name
    information.price = platformDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = platformDto.provider
    information.description = platformDto.description
    information.amount = platformDto.amount
    platform.controller = platformDto.controller
    platform.minVoltage = platformDto.min_voltage
    platform.maxVoltage = platformDto.max_voltage
    platform.freq = platformDto.freg
    platform.analogInputs = platformDto.analog_inputs
    platform.flashmemory = platformDto.flashMemory
    platform.ram = platformDto.ram
    platform.information = information
    val infoWithId = informationRepository?.save(information)
    platformRepository?.save(platform)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/addController")
  fun addController(@RequestBody controllerDto: ControllerDto,
                    @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val controller = Controller()
    val information = Information()
    information.type = Type.CONTROLLER
    information.model = controllerDto.model
    information.name = controllerDto.name
    information.price = controllerDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = controllerDto.provider
    information.description = controllerDto.description
    information.amount = controllerDto.amount
    controller.minVoltage = controllerDto.min_voltage
    controller.maxVoltage = controllerDto.max_voltage
    controller.analogInputs = controllerDto.analog_inputs
    controller.ram = controllerDto.ram
    controller.information = information
    controller.controllerInterface = controllerDto.interface_
    val infoWithId = informationRepository?.save(information)
    controllerRepository?.save(controller)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/addMechanicDetail")
  fun addMechanicDetail(@RequestBody mechanicDetailDto: MechanicDetailDto,
                        @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val mechanicDetail = MechanicDetail()
    val information = Information()
    information.type = Type.MECHANIC_DETAIL
    information.model = mechanicDetailDto.model
    information.name = mechanicDetailDto.name
    information.price = mechanicDetailDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = mechanicDetailDto.provider
    information.description = mechanicDetailDto.description
    information.amount = mechanicDetailDto.amount
    mechanicDetail.material = mechanicDetailDto.material
    mechanicDetail.information = information
    val infoWithId = informationRepository?.save(information)
    mechanicDetailRepository?.save(mechanicDetail)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/addMotor")
  fun addMotor(@RequestBody motorDto: MotorDto,
               @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val motor = Motor()
    val information = Information()
    information.type = Type.MOTOR
    information.model = motorDto.model
    information.name = motorDto.name
    information.price = motorDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = motorDto.provider
    information.description = motorDto.description
    information.amount = motorDto.amount
    motor.minVoltage = motorDto.min_voltage
    motor.maxVoltage = motorDto.max_voltage
    motor.motorInterface = motorDto.interface_
    motor.power = motorDto.power
    motor.information = information
    val infoWithId = informationRepository?.save(information)
    motorRepository?.save(motor)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/addSensor")
  fun addSensor(@RequestBody sensorDto: SensorDto,
                @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val sensor = Sensor()
    val information = Information()
    information.type = Type.SENSOR
    information.model = sensorDto.model
    information.name = sensorDto.name
    information.price = sensorDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = sensorDto.provider
    information.description = sensorDto.description
    information.amount = sensorDto.amount
    sensor.minVoltage = sensorDto.min_voltage
    sensor.maxVoltage = sensorDto.max_voltage
    sensor.sensorInterface = sensorDto.interface_
    sensor.information = information
    val infoWithId = informationRepository?.save(information)
    sensorRepository?.save(sensor)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/addOther")
  fun addOther(@RequestBody otherDto: OtherDto,
               @RequestParam("file") img: MultipartFile?): ResponseEntity<HttpStatus> {
    val information = Information()
    information.type = Type.OTHER_RESOURCES
    information.model = otherDto.model
    information.name = otherDto.name
    information.price = otherDto.price
    information.creationDate = Date.from(Instant.now())
    information.provider = otherDto.provider
    information.description = otherDto.description
    information.amount = otherDto.amount
    val infoWithId = informationRepository?.save(information)
    if (img != null && infoWithId != null) addImage(infoWithId, img)
    return ResponseEntity(HttpStatus.CREATED)
  }

  @PostMapping("/updatePlatform")
  fun updatePlatform(@RequestBody platformDto: PlatformDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val platform = platformRepository?.findPlatformById(id)
    val information = informationRepository?.findInformationById(id)
    information?.type = Type.PLATFORM
    information?.model = platformDto.model
    information?.name = platformDto.name
    information?.price = platformDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = platformDto.provider
    information?.description = platformDto.description
    information?.amount = platformDto.amount
    platform?.controller = platformDto.controller
    platform?.minVoltage = platformDto.min_voltage
    platform?.maxVoltage = platformDto.max_voltage
    platform?.freq = platformDto.freg
    platform?.analogInputs = platformDto.analog_inputs
    platform?.flashmemory = platformDto.flashMemory
    platform?.ram = platformDto.ram
    platform?.information = information
    informationRepository?.save(information!!)
    platformRepository?.save(platform!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/updateController")
  fun updateController(@RequestBody controllerDto: ControllerDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val controller = controllerRepository?.findControllerById(id)
    val information = informationRepository?.findInformationById(id)
    information?.type = Type.CONTROLLER
    information?.model = controllerDto.model
    information?.name = controllerDto.name
    information?.price = controllerDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = controllerDto.provider
    information?.description = controllerDto.description
    information?.amount = controllerDto.amount
    controller?.minVoltage = controllerDto.min_voltage
    controller?.maxVoltage = controllerDto.max_voltage
    controller?.analogInputs = controllerDto.analog_inputs
    controller?.ram = controllerDto.ram
    controller?.information = information
    controller?.controllerInterface = controllerDto.interface_
    informationRepository?.save(information!!)
    controllerRepository?.save(controller!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/updateMechanicDetail")
  fun updateMechanicDetail(@RequestBody mechanicDetailDto: MechanicDetailDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val mechanicDetail = mechanicDetailRepository?.findMechanicDetailById(id)
    val information = informationRepository?.findInformationById(id)
    information?.type = Type.MECHANIC_DETAIL
    information?.model = mechanicDetailDto.model
    information?.name = mechanicDetailDto.name
    information?.price = mechanicDetailDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = mechanicDetailDto.provider
    information?.description = mechanicDetailDto.description
    information?.amount = mechanicDetailDto.amount
    mechanicDetail?.material = mechanicDetailDto.material
    mechanicDetail?.information = information
    informationRepository?.save(information!!)
    mechanicDetailRepository?.save(mechanicDetail!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/updateMotor")
  fun updateMotor(@RequestBody motorDto: MotorDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val motor = motorRepository?.findMotorById(id)
    val information = informationRepository?.findInformationById(id)
    information?.type = Type.MOTOR
    information?.model = motorDto.model
    information?.name = motorDto.name
    information?.price = motorDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = motorDto.provider
    information?.description = motorDto.description
    information?.amount = motorDto.amount
    motor?.minVoltage = motorDto.min_voltage
    motor?.maxVoltage = motorDto.max_voltage
    motor?.motorInterface = motorDto.interface_
    motor?.power = motorDto.power
    motor?.information = information
    informationRepository?.save(information!!)
    motorRepository?.save(motor!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/updateSensor")
  fun updateSensor(@RequestBody sensorDto: SensorDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val sensor = sensorRepository?.findSensorById(id)
    val information = informationRepository?.findInformationById(id)
    information?.type = Type.SENSOR
    information?.model = sensorDto.model
    information?.name = sensorDto.name
    information?.price = sensorDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = sensorDto.provider
    information?.description = sensorDto.description
    information?.amount = sensorDto.amount
    sensor?.minVoltage = sensorDto.min_voltage
    sensor?.maxVoltage = sensorDto.max_voltage
    sensor?.sensorInterface = sensorDto.interface_
    sensor?.information = information
    informationRepository?.save(information!!)
    sensorRepository?.save(sensor!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/updateOther")
  fun updateOther(@RequestBody otherDto: OtherDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val information = informationRepository?.findOtherById(id)
    information?.type = Type.OTHER_RESOURCES
    information?.model = otherDto.model
    information?.name = otherDto.name
    information?.price = otherDto.price
    information?.creationDate = Date.from(Instant.now())
    information?.provider = otherDto.provider
    information?.description = otherDto.description
    information?.amount = otherDto.amount
    informationRepository?.save(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removePlatform")
  fun removeFromPlatform(@RequestParam id: Int): ResponseEntity<HttpStatus> {
    println("ID: $id")
    val platform = platformRepository?.findPlatformById(id)
    println(platform)
    val information = informationRepository?.findInformationById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    productUser?.forEach {
      productUserRepository?.delete(it)
    }
    platformRepository?.delete(platform!!)
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removeController")
  fun removeController(@RequestParam id: Int): ResponseEntity<HttpStatus> {
    val controller = controllerRepository?.findControllerById(id)
    val information = informationRepository?.findInformationById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productUser?.forEach {
      productUserRepository?.delete(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    controllerRepository?.delete(controller!!)
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removeMechanicDetail")
  fun removeMechanicDetail(@RequestParam id: Int): ResponseEntity<HttpStatus> {
    val mechanicDetail = mechanicDetailRepository?.findMechanicDetailById(id)
    val information = informationRepository?.findInformationById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productUser?.forEach {
      productUserRepository?.delete(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    mechanicDetailRepository?.delete(mechanicDetail!!)
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removeMotor")
  fun removeMotor(@RequestBody motorDto: MotorDto, @RequestParam id: Int): ResponseEntity<HttpStatus> {
    val motor = motorRepository?.findMotorById(id)
    val information = informationRepository?.findInformationById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    productUser?.forEach { productUserRepository?.delete(it) }
    motorRepository?.delete(motor!!)
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removeSensor")
  fun removeSensor(@RequestParam id: Int): ResponseEntity<HttpStatus> {
    val sensor = sensorRepository?.findSensorById(id)
    val information = informationRepository?.findInformationById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    productUser?.forEach { productUserRepository?.delete(it) }
    sensorRepository?.delete(sensor!!)
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }

  @PostMapping("/removeOther")
  fun removeOther(@RequestParam id: Int): ResponseEntity<HttpStatus> {
    val information = informationRepository?.findOtherById(id)
    val productUser = productUserRepository?.findAllByInformationId(id)
    val productOrder = productOrderRepository?.findAllByInformationId(id)
    val productCard = productCardRepository?.findAllByInformationId(id)
    productCard?.forEach {
      it.information = null
      productCardRepository?.save(it)
    }
    productOrder?.forEach {
      it.information = null
      productOrderRepository?.save(it)
    }
    productUser?.forEach { productUserRepository?.delete(it) }
    informationRepository?.delete(information!!)
    return ResponseEntity(HttpStatus.OK)
  }
}
