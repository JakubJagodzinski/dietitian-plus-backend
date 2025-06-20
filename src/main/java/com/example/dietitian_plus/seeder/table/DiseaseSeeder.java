package com.example.dietitian_plus.seeder.table;

import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.disease.DiseaseRepository;
import com.example.dietitian_plus.seeder.DataSeeder;
import com.example.dietitian_plus.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiseaseSeeder implements DataSeeder {

    private final DiseaseRepository diseaseRepository;

    private static final String DISEASES_DATA_FILE_PATH = DATA_DIRECTORY + "/diseases.csv";

    @Override
    public void seed() throws IOException {
        if (diseaseRepository.count() == 0) {
            List<Disease> diseases = CsvUtils.loadFromCsv(
                    DISEASES_DATA_FILE_PATH,
                    2,
                    parts -> {
                        Disease disease = new Disease();

                        disease.setDiseaseName(parts[0].trim());
                        disease.setDescription(parts[1].trim());

                        return disease;
                    }
            );

            diseaseRepository.saveAll(diseases);
        }
    }

}
