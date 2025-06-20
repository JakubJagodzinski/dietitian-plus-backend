package com.example.dietitian_plus.seeder.table;

import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.ProductRepository;
import com.example.dietitian_plus.seeder.DataSeeder;
import com.example.dietitian_plus.utils.CsvUtils;
import com.example.dietitian_plus.utils.DoubleParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements DataSeeder {

    private final ProductRepository productRepository;

    private static final String PRODUCTS_DATA_FILE_PATH = DATA_DIRECTORY + "/products.csv";

    @Override
    public void seed() throws IOException {
        if (productRepository.count() == 0) {
            List<Product> products = CsvUtils.loadFromCsv(
                    PRODUCTS_DATA_FILE_PATH,
                    8,
                    parts -> {
                        Product product = new Product();

                        product.setProductName(parts[0].trim());
                        product.setKcal(DoubleParser.parse(parts[1]));
                        product.setFats(DoubleParser.parse(parts[2]));
                        product.setCarbs(DoubleParser.parse(parts[3]));
                        product.setProtein(DoubleParser.parse(parts[4]));
                        product.setFiber(DoubleParser.parse(parts[5]));
                        product.setGlycemicIndex(DoubleParser.parse(parts[6]));
                        product.setGlycemicLoad(DoubleParser.parse(parts[7]));

                        return product;
                    }
            );

            productRepository.saveAll(products);
        }
    }

}
