package com.example.dietitian_plus.seeder;

import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.disease.DiseaseRepository;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.ProductRepository;
import com.example.dietitian_plus.domain.unit.Unit;
import com.example.dietitian_plus.domain.unit.UnitRepository;
import com.example.dietitian_plus.user.Role;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
import com.example.dietitian_plus.utils.FloatParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DiseaseRepository diseaseRepository;
    private final UnitRepository unitRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String DATA_DIRECTORY = "data";

    private static final String PRODUCTS_DATA_FILE_PATH = DATA_DIRECTORY + "/products.csv";
    private static final String DISEASES_DATA_FILE_PATH = DATA_DIRECTORY + "/diseases.csv";
    private static final String UNITS_DATA_FILE_PATH = DATA_DIRECTORY + "/units.csv";

    @Override
    public void run(ApplicationArguments args) throws IOException {
        seedAdminAccount();
        loadProductsFromCsv();
        loadDiseasesFromCsv();
        loadUnitsFromCsv();
    }

    private void seedAdminAccount() {
        final String adminEmail = "admin@dietitian_plus.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("Dietitian Plus");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }

    private void loadProductsFromCsv() throws IOException {
        loadFromCsv(
                PRODUCTS_DATA_FILE_PATH,
                productRepository.count(),
                parts -> {
                    Product product = new Product();
                    product.setProductName(parts[0].trim());
                    product.setKcal(FloatParser.parse(parts[1]));
                    product.setFats(FloatParser.parse(parts[2]));
                    product.setCarbs(FloatParser.parse(parts[3]));
                    product.setProtein(FloatParser.parse(parts[4]));
                    product.setFiber(FloatParser.parse(parts[5]));
                    product.setGlycemicIndex(FloatParser.parse(parts[6]));
                    product.setGlycemicLoad(FloatParser.parse(parts[7]));
                    return product;
                },
                productRepository::saveAll,
                8
        );
    }

    private void loadDiseasesFromCsv() throws IOException {
        loadFromCsv(
                DISEASES_DATA_FILE_PATH,
                diseaseRepository.count(),
                parts -> {
                    Disease disease = new Disease();

                    disease.setDiseaseName(parts[0].trim());
                    disease.setDescription(parts[1].trim());

                    return disease;
                },
                diseaseRepository::saveAll,
                2
        );
    }

    private void loadUnitsFromCsv() throws IOException {
        loadFromCsv(
                UNITS_DATA_FILE_PATH,
                unitRepository.count(),
                parts -> {
                    Unit unit = new Unit();

                    unit.setUnitName(parts[0].trim());
                    unit.setGrams(FloatParser.parse(parts[1]));

                    return unit;
                },
                unitRepository::saveAll,
                2
        );
    }

    private <T> void loadFromCsv(String filePath, long existingCount, Function<String[], T> mapper, Consumer<List<T>> saver, int expectedColumns) throws IOException {
        if (existingCount == 0) {
            List<T> result = new ArrayList<>();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                boolean isHeaderRead = false;

                while ((line = reader.readLine()) != null) {
                    if (!isHeaderRead) {
                        isHeaderRead = true;
                        continue;
                    }

                    String[] parts = line.split(",");
                    if (parts.length == expectedColumns) {
                        result.add(mapper.apply(parts));
                    }
                }
            }

            saver.accept(result);
        }
    }

}
