package xyz.pangosoft.encinalbackend.services.implementations;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Receipt;
import xyz.pangosoft.encinalbackend.repositories.IReceiptRepository;
import xyz.pangosoft.encinalbackend.services.IReceiptService;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptServiceImpl implements IReceiptService {

    @Autowired
    private IReceiptRepository receiptRepository;

    @Autowired
    protected DataSource localDataSource;

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

    /****************** PDF REPORT SERVICES *******************/

    @Override
    public byte[] rptPrintReceipt(Integer idReceipt) throws JRException, FileNotFoundException, SQLException {
        Connection con = localDataSource.getConnection(); // Obtiene la conexión actual a la base de datos
        Map<String, Object> params = new HashMap<>();
        InputStream file = getClass().getResourceAsStream("/reports/rpt_receipt.jrxml");
        params.put("receiptId", idReceipt);

        JasperReport jasperReport = JasperCompileManager.compileReport(file);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, con);

        ByteArrayOutputStream byteArrayOutputStream = getByteArrayOutputStream(jasperPrint);

        con.close();
        return byteArrayOutputStream.toByteArray();
    }

    protected ByteArrayOutputStream getByteArrayOutputStream(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        return byteArrayOutputStream;
    }

}
