package xyz.pangosoft.encinalbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.pangosoft.encinalbackend.models.Sale;

import java.util.List;

public interface ISaleService {

    public List<Sale> listSales();

    public Page<Sale> listSales(Pageable pageable);

    public Sale singleSale(Integer saleId);

    public Sale save(Sale sale);
}
