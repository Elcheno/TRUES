import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.service.LineService;
import com.elcheno.trues.model.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class testProductService {
    static ProductService productService;
    static LineService lineService;

    @BeforeAll
    public static void setUpClass(){
        productService = new ProductService();
        lineService = new LineService();
    }

    @Test
    @DisplayName("Save Product")
    void save() throws SQLException {
        Product aux = new Product();
        aux.setCod(5580);
        aux.setDescription("Product");
        aux.setDate(LocalDate.now());
        aux.setLine(lineService.getAll().get(0));
        assertEquals(true, productService.save(aux));
    }

    @Test
    @DisplayName("Remove Product")
    void remove() throws SQLException {
        List<Product> auxList = productService.getAll();
        if(auxList!=null){
            Product aux = auxList.get(0);
            assertEquals(true, productService.remove(aux));
        }
    }

    @Test
    @DisplayName("Get Product by Id")
    void getById() throws SQLException {
        List<Product> auxList = productService.getAll();
        if(auxList!=null){
            Product aux = auxList.get(0);
            assertEquals(aux, productService.getById(aux.getId()));
        }
    }
}
