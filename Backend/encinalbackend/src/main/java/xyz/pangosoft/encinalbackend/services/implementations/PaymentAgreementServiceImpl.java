package xyz.pangosoft.encinalbackend.services.implementations;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.repositories.IPaymentAgreementRepository;
import xyz.pangosoft.encinalbackend.services.IPaymentAgreementService;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentAgreementServiceImpl implements IPaymentAgreementService {

    @Autowired
    private IPaymentAgreementRepository paymentAgreementRepository;

    @Autowired
    protected DataSource localDataSource;

    @Override
    public List<PaymentAgreement> listPaymentAgreements() {
        return paymentAgreementRepository.findAll();
    }

    @Override
    public PaymentAgreement singlePaymentAgreement(Integer id) {
        return paymentAgreementRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentAgreement save(PaymentAgreement paymentAgreement) {
        return paymentAgreementRepository.save(paymentAgreement);
    }

    @Override
    public List<PaymentAgreement> listPaymentAgreements(Status status) {
        return paymentAgreementRepository.findByStatus(status);
    }

    @Override
    public List<PaymentAgreement> listPaymentAgreementsByClient(Integer clientId) {
        return paymentAgreementRepository.findAllByClient(clientId);
    }

    @Override
    public List<PaymentAgreement> listPaymentAgreementsByClient(Client client, Status status) {
        return paymentAgreementRepository.findAllByClient2(client, status);
    }

    @Override
    public void delete(Integer id) {
        paymentAgreementRepository.deleteById(id);
    }

    @Override
    public Double getPrincipalTotal(Integer paymentAgreementId) {
        return paymentAgreementRepository.principalRemaining(paymentAgreementId);
    }

    @Override
    public Double getFee(Integer paymentAgreementId) {
        return this.paymentAgreementRepository.fee(paymentAgreementId);
    }

    /****************** PDF REPORT SERVICES *******************/

    @Override
    public byte[] rptPaymentAgreement(Integer idagreement) throws JRException, FileNotFoundException, SQLException {

        Connection con = localDataSource.getConnection(); // Obtiene la conexión actual a la base de datos
        Map<String, Object> params = new HashMap<>();
        InputStream file = getClass().getResourceAsStream("/reports/rpt_payment_agreement.jrxml");
        params.put("paymentId", idagreement);

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
