package org.robogit.dto
import org.robogit.domain.Controller
import java.util.*

class PlatformDto (val model: String, val name: String, val price: Float,
                   val provider: String, val description: String, val amount: Int, val controller: Controller,
                   val min_voltage: Float, val max_voltage: Float, val freg: Float, val analog_inputs: Int,
                   val flashMemory: Int, var ram: Int){

}