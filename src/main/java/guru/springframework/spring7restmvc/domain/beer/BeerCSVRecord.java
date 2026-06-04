package guru.springframework.spring7restmvc.domain.beer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.opencsv.bean.CsvBindByName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerCSVRecord {

    @CsvBindByName
    private Integer row;

    @CsvBindByName(column="count.x")
    private Integer count;

    @CsvBindByName
    private String abv;

    @CsvBindByName
    private String ibu;

    @CsvBindByName
    private Integer id;

    @CsvBindByName
    private String beer;

    @CsvBindByName
    private String style;

    @CsvBindByName(column="brewery_id")
    private Integer breweryId;

    @CsvBindByName
    private Float ounches;

    @CsvBindByName
    private String style2;

    @CsvBindByName(column="count_x")
    private String count_y;

    @CsvBindByName
    private String city;

    @CsvBindByName
    private String state;

    @CsvBindByName
    private String label;

}
