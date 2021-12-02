package xyz.pangosoft.encinalbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.pangosoft.encinalbackend.models.Sale;

import java.util.Date;
import java.util.List;

public interface ISaleService {

    public List<Sale> listSales();

    public Page<Sale> listSales(Pageable pageable);

    public List<Sale> listSalesByDate(Date initDate, Date endDate);

    public List<Sale> listSalesByBlock(Integer blockId);

    public List<Sale> listSalesByBlockAndDate(Integer blockId, Date initDate, Date endDate);

    public Sale singleSale(Integer saleId);

    public Sale save(Sale sale);

    public Double reporDailySales();
}
