package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.ReceiptType;
import xyz.pangosoft.encinalbackend.repositories.IReceiptTypeRepository;
import xyz.pangosoft.encinalbackend.services.IReceiptTypeService;

import java.util.List;

@Service
public class ReceiptTypeServiceImpl implements IReceiptTypeService {

    @Autowired
    private IReceiptTypeRepository receiptTypeRepository;

    @Override
    public List<ReceiptType> listAll() {
        return receiptTypeRepository.findAll();
    }

    @Override
    public ReceiptType singleType(Integer id) {
        return receiptTypeRepository.findById(id).orElse(null);
    }
}
