package ua.ivan.provider.service;

import ua.ivan.provider.model.Packages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan.provider.model.Status;
import ua.ivan.provider.model.User;
import ua.ivan.provider.repository.PackageRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("packageService")
public class PackageService {

    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Packages getPackageByUserId(Long id) {
        return packageRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException("Package doesn't exists"));
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

    public void deletePackage(Packages myPackage) {
        packageRepository.delete(myPackage);
    }

    public void deletePackageById(Long id) {
        packageRepository.deleteById(id);
    }

    public List<Packages> sortPackages(String method, User user) {
        switch (method) {
            case "A-Z":
                return user.getPackages()
                        .stream()
                        .sorted(Comparator.comparing(Packages::getName))
                        .collect(Collectors.toList());
            case "Z-A":
                return user.getPackages()
                        .stream()
                        .sorted(Comparator.comparing(Packages::getName).reversed())
                        .collect(Collectors.toList());
            case "price":
                return user.getPackages()
                        .stream()
                        .sorted(Comparator.comparing(Packages::getPrice))
                        .collect(Collectors.toList());
            default:
                return user.getPackages();
        }
    }

    public String buySubscribe(User user, Packages packages, UserDetailsServiceImpl userDetailsService) {
        user.setBalance(user.getBalance() - packages.getPrice());
        savePackage(packages);
        userDetailsService.saveUser(user);
        if (user.getBalance() < 0) {
            user.setStatus(Status.BANNED);
            return "redirect:/auth/logout";
        }
        return "main";
    }

    public boolean alreadySubscribe(List<Packages> packages, String packageName) {
        return packages.stream()
                .filter(i -> i.getType().equals(packageName))
                .count() < 1;
    }
}
