package com.miluna.springvehicleguide.entities;

import com.google.gson.Gson;
import com.miluna.springvehicleguide.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "id")
    @ManyToOne(targetEntity = BrandEntity.class)
    private BrandEntity brand;

    @Column(name = "id")
    @ManyToMany(targetEntity = EngineEntity.class, mappedBy = "engines")
    private List<EngineEntity> engines;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private Date year;

    @Column(name = "weight")
    private Number weight;

    @Column(name = "doors")
    private Number doors;

    @Column(name = "segment")
    private Character segment;

    @Column(name = "basePrice")
    private Number basePrice;

    public VehicleEntity(Vehicle dto){
        setId(dto.getId());
        setBrand(new BrandEntity(dto.getBrand()));
        setEngines(dto.getEngines()
                .stream()
                .map(e -> new EngineEntity(e))
                .collect(Collectors.toList()));
        setName(dto.getName());
        setDescription(dto.getDescription());
        setYear(dto.getYear());
        setWeight(dto.getWeight());
        setDoors(dto.getDoors());
        setSegment(dto.getSegment());
        setBasePrice(dto.getBasePrice());
    }

    // VALIDATION

    public void setYear(Date year){
        if (this.brand.getYear().getTime() < year.getTime()) this.year = null;
        else this.year = year;
    }

    public void setWeight(Number weight){
        if (weight.longValue() < 0) this.weight = 0;
        else this.weight = weight.longValue();
    }

    public void setDoors(Number doors){
        if (doors.intValue() < 0) this.doors = 0;
        else this.doors = doors.intValue();
    }

    public void setBasePrice(Number basePrice){
        if (basePrice.doubleValue() < 0) this.basePrice = 0;
        else this.basePrice = basePrice.doubleValue();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
