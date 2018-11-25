package org.robogit.domain

import lombok.EqualsAndHashCode
import org.hibernate.annotations.Check
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "motors", schema = "s244707")
@Check(constraints = "max_voltage > min_voltage")
@EqualsAndHashCode
class Motor{
    @Id
    @Column
    var id: Int? = null

    @OneToOne
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    var information: Information? = null

    @Column
    @Min(0)
    var power: Float? = null

    @Column
    var minVoltage: Float? = null

    @Column
    var maxVoltage: Float? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interface_id")
    @NotNull
    var motorInterface: Interface? = null
}