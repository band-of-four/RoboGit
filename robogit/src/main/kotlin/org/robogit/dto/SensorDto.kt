package org.robogit.dto
import org.robogit.domain.Controller
import org.robogit.domain.Interface
import java.util.*

class SensorDto (val model: String, val name: String, val price: Float,
                 val provider: String, val description: String, val amount: Int,
                 val min_voltage: Float, val max_voltage: Float, val interface_: Interface){

}