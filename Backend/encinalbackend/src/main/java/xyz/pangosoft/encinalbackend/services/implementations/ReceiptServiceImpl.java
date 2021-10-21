package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Receipt;
import xyz.pangosoft.encinalbackend.repositories.IReceiptRepository;
import xyz.pangosoft.encinalbackend.services.IReceiptService;

import java.util.List;

@Service
public class ReceiptServiceImpl implements IReceiptService {

    @Autowired
    private IReceiptRepository receiptRepository;

    @Override
    public List<Receipt> listAll() {
        return receiptRepository.findAll();
    }

    @Override
    public Receipt singleReceipt(Integer id) {
        return receiptRepository.findById(id).orElse(null);
    }

    @Override
    public Receipt create(Receipt receipt) {
        return receiptRepository.save(receipt);
    }
}
