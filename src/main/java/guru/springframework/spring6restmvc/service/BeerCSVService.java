package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCsv(File csvFile);
}
