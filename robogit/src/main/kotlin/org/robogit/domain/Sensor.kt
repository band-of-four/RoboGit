package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(name = "controllers", schema = "s244707")
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Sensor {
    @Id
    @Column
    var id: Int? = null

    @OneToOne
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    var information: Information? = null

    @Column
    var minVoltage: Float? = null

    @Column
    @Min(0)
    var maxVoltage: Float? = null
}