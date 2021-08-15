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

    public Packages getPackageById(Long id) {
        return packageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Package doesn't exists"));
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

    public List<Packages> sortPackages(String method, List<Packages> packages) {
        switch (method) {
            case "A-Z":
                return packages
                        .stream()
                        .sorted(Comparator.comparing(Packages::getName))
                        .collect(Collectors.toList());
            case "Z-A":
                return packages
                        .stream()
                        .sorted(Comparator.comparing(Packages::getName).reversed())
                        .collect(Collectors.toList());
            case "price":
                return packages
                        .stream()
                        .sorted(Comparator.comparing(Packages::getPrice))
                        .collect(Collectors.toList());
            default:
                return packages;
        }
    }

    public String buySubscribe(User user, Packages packages, UserDetailsServiceImpl userDetailsService) {
        user.setBalance(user.getBalance() - packages.getPrice());
        user.addUserPackage(packages);
        userDetailsService.saveUser(user);
        if (user.getBalance() < 0) {
            user.setStatus(Status.BANNED);
            return "redirect:/auth/logout";
        }
        return "redirect:/auth/main";
    }

    public String unsubscribe(User user, Packages packages, UserDetailsServiceImpl userDetailsService) {
        user.removeUserPackage(packages);
        userDetailsService.saveUser(user);
        return "redirect:/user";
    }

    public static boolean alreadySubscribe(User user, String packageType) {
        return user.getPackages().stream()
                .filter(i -> i.getType().equals(packageType))
                .count() < 1;
    }
}
