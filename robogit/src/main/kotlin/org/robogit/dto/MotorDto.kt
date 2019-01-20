package org.robogit.dto
import org.robogit.domain.Controller
import org.robogit.domain.Interface
import java.util.*

class MotorDto (val model: String, val name: String, val price: Float,
                val provider: String, val description: String, val amount: Int, val power: Float,
                val min_voltage: Float, val max_voltage: Float, var interface_: Interface){

}