package com.example.dietitian_plus.seeder;

import java.io.IOException;

public interface DataSeeder {

    String DATA_DIRECTORY = "data";

    void seed() throws IOException;

}
