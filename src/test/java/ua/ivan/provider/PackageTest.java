package ua.ivan.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ua.ivan.provider.model.Packages;
import ua.ivan.provider.service.PackageService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PackageTest {

    @Autowired
    @Qualifier("packageService")
    PackageService packageService;

    @Test
    void getPackageByIdTest() {
        assertEquals(packageService.getPackageById(1L).getName(), "Mega");
    }
    @Test
    void savePackageTest() {
        Packages packages = packageService.getPackageById(9L);
        packages.setId(16L);
        Packages packages1 = packageService.savePackage(packages);
        assertEquals(packages1.getName(), packages.getName());
        packageService.deletePackage(packages1);
    }

    @Test
    void deletePackageTest() {
        Packages packages = packageService.getPackageById(9L);
        packages.setId(16L);
        Packages packages1 = packageService.savePackage(packages);
        assertTrue(packageService.deletePackage(packages1));
    }

    @Test
    void sortPackagesTest() {
        List<Packages> packages = packageService.sortPackages("price", packageService.findAll());
        assertEquals(packages.get(0).getPrice(), 100);
    }
}
