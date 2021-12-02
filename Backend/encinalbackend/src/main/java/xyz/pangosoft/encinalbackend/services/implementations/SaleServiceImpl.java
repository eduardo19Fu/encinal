package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Sale;
import xyz.pangosoft.encinalbackend.repositories.ISaleRepository;
import xyz.pangosoft.encinalbackend.services.ISaleService;

import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;

    @Override
    public List<Sale> listSales() {
        return saleRepository.findAll(Sort.by(Sort.Direction.DESC, "saleDate"));
    }

    @Override
    public Page<Sale> listSales(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public List<Sale> listSalesByDate(Date initDate, Date endDate) {
        return saleRepository.findBySaleDateBetween(initDate, endDate);
    }

    @Override
    public List<Sale> listSalesByBlock(Integer blockId) {
        return this.saleRepository.findByBlock(blockId);
    }

    @Override
    public List<Sale> listSalesByBlockAndDate(Integer blockId, Date initDate, Date endDate) {
        return this.saleRepository.findByBlockAndDate(blockId, initDate, endDate);
    }

    @Override
    public Sale singleSale(Integer saleId) {
        return saleRepository.findById(saleId).orElse(null);
    }

    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Double reporDailySales() {
        return this.saleRepository.dailyTotalSales();
    }
}
