package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Transfer;
import xyz.pangosoft.encinalbackend.repositories.ITransferRepository;
import xyz.pangosoft.encinalbackend.services.ITransferService;

import java.util.List;

@Service
public class TransferServicImpl implements ITransferService {

    @Autowired
    private ITransferRepository transferRepository;

    @Override
    public List<Transfer> listAllTransfers() {
        return this.transferRepository.findAll();
    }

    @Override
    public Transfer singleTransfer(Integer idtransfer) {
        return this.transferRepository.findById(idtransfer).orElse(null);
    }

    @Override
    public Transfer save(Transfer transfer) {
        return this.transferRepository.save(transfer);
    }
}
