package com.example.dietitian_plus.seeder.table;

import com.example.dietitian_plus.domain.unit.Unit;
import com.example.dietitian_plus.domain.unit.UnitRepository;
import com.example.dietitian_plus.seeder.DataSeeder;
import com.example.dietitian_plus.utils.CsvUtils;
import com.example.dietitian_plus.utils.DoubleParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UnitSeeder implements DataSeeder {

    private final UnitRepository unitRepository;

    private static final String UNITS_DATA_FILE_PATH = DATA_DIRECTORY + "/units.csv";

    @Override
    public void seed() throws IOException {
        if (unitRepository.count() == 0) {
            List<Unit> units = CsvUtils.loadFromCsv(
                    UNITS_DATA_FILE_PATH,
                    2,
                    parts -> {
                        Unit unit = new Unit();

                        unit.setUnitName(parts[0].trim());
                        unit.setGrams(DoubleParser.parse(parts[1]));

                        return unit;
                    }
            );

            unitRepository.saveAll(units);
        }
    }

}
