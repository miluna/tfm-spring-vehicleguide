package com.miluna.springvehicleguide.services;

import com.miluna.springvehicleguide.entities.BrandEntity;
import com.miluna.springvehicleguide.models.Brand;
import com.miluna.springvehicleguide.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "BrandService")
public class BrandService implements CrudService<Brand> {

    private final BrandRepository repository;

    @Autowired
    private BrandService(@Qualifier(value = "BrandRepository") BrandRepository repository){
        this.repository = repository;
    }

    @Override
    public Brand createOne(Brand b) {
        BrandEntity entity = new BrandEntity(b);

        BrandEntity saved = repository.save(entity);
        return new Brand(saved);
    }

    @Override
    public Brand findOne(Long id) {
        if (id == null) return null;

        Optional<BrandEntity> found = repository.findById(id);
        if (found.isPresent()){
            BrandEntity entity = found.get();
            return new Brand(entity);
        }

        return null;
    }

    @Override
    public Brand updateOne(Long id, Brand brand) {
        if (id == null) return null;
        BrandEntity target = new BrandEntity(brand);

        Optional<BrandEntity> found = repository.findById(id);
        if (found.isPresent()){
            BrandEntity entity = found.get();
            entity.updateProperties(target);

            BrandEntity saved = repository.save(entity);
            return new Brand(saved);
        }
        return null;
    }

    @Override
    public Boolean deleteOne(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> result = repository.findAll()
                .stream()
                .map(e -> new Brand(e))
                .collect(Collectors.toList());
        return result;
    }
}
