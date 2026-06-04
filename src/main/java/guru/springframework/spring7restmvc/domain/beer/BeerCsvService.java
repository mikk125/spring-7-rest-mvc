package guru.springframework.spring7restmvc.domain.beer;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File saveFile);
}
