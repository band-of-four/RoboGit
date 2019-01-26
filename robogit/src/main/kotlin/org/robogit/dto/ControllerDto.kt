package org.robogit.dto
import org.robogit.domain.Controller
import org.robogit.domain.Interface
import java.util.*

class ControllerDto (val model: String, val name: String, val price: Float,
                     val provider: String, val description: String, val amount: Int, var ram: Int,
                     val min_voltage: Float, val max_voltage: Float, val analog_inputs: Int,
                     val interface_: Interface)
