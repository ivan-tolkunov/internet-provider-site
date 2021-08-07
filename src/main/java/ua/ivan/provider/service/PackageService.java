package ua.ivan.provider.service;

import ua.ivan.provider.model.Packages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan.provider.repository.PackageRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("packageService")
public class PackageService {

    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Packages getPackageByUserId(Long id) {
        return packageRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException("Donate doesn't exists"));
    }

    public Packages findById(Long id) {
        return packageRepository.getById(id);
    }

    public List<Packages> findAll() {
        return packageRepository.findAll();
    }

    public Packages savePackage(Packages myPackage) {
        return packageRepository.save(myPackage);
    }

    public void deleteDonate(Long id) {
        packageRepository.deleteById(id);
    }
}
