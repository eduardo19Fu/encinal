package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Seller;
import xyz.pangosoft.encinalbackend.repositories.ISellerRepository;
import xyz.pangosoft.encinalbackend.services.ISellerService;

import java.util.List;

@Service
public class SellerSerivceImpl implements ISellerService {

    @Autowired
    private ISellerRepository sellerRepository;

    @Override
    public List<Seller> listSellers() {
        return sellerRepository.findAll(Sort.by(Sort.Direction.DESC, "contractDate"));
    }

    @Override
    public Seller singleSeller(Integer idSeller) {
        return sellerRepository.findById(idSeller).orElse(null);
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void delete(Integer idSeller) {
        sellerRepository.deleteById(idSeller);
    }
}
