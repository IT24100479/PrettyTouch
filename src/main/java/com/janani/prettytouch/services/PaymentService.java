package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.PaymentModel;
import com.janani.prettytouch.util.QuickSort;
import com.janani.prettytouch.util.TypeConverter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentService implements Services{

    private List<Model> allPayments;
    private static PaymentService paymentService;
    private final QuickSort quickSort;


    private PaymentService() {
        this.quickSort = new QuickSort();
        this.readAll();
    }
    public static PaymentService getInstance() {
        if (paymentService == null) {
            paymentService = new PaymentService();
        }
        return paymentService;
    }

    @Override
    public void readAll() {
        allPayments = new ArrayList<>();
        String filePath = FIleConst.FILE_PATH + FIleConst.PAYMENT_FILE;
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row.length >= 7) {
                    PaymentModel payment = new PaymentModel(row[0],
                            row[1], row[2], row[3], row[4], row[5],
                            row[6]
                    );
                    allPayments.add(payment);

                }
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Model> getAll() {
       return this.allPayments;
    }

    @Override
    public Model getById(int id) {
        for (int i = 0; i < this.allPayments.size(); i++) {
            if (this.allPayments.get(i).getId() == id) {
                return this.allPayments.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean add(Model model) {
        PaymentModel paymentModel = (PaymentModel) model;
        paymentModel.checkCreatedAtAndID(allPayments);
        allPayments.add(paymentModel);
        Model[] temp = this.allPayments.toArray(Model[]::new);
        this.quickSort.quickSort(temp, 0, this.allPayments.size() - 1,"id");
        this.allPayments =  new ArrayList<>(Arrays.asList(temp));
        return this.updateFile();

    }

    @Override
    public boolean update(Model model) {
        PaymentModel paymentModel = (PaymentModel) model;
        for (int i = 0; i < this.allPayments.size(); i++) {
            if (this.allPayments.get(i).getId() == paymentModel.getId()) {
                this.allPayments.remove(i);
                return this.add(paymentModel);
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < this.allPayments.size(); i++) {
            if (this.allPayments.get(i).getId() == id) {
                this.allPayments.get(i).setStatus("0");
                this.update(this.allPayments.get(i));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateFile() {
        String filePath = FIleConst.FILE_PATH + FIleConst.PAYMENT_FILE;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(FIleConst.PAYMENT_HEADERS);
            for (int i = 0; i < this.allPayments.size(); i++) {
                writer.writeNext(this.allPayments.get(i).getCSVLine());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String createReturnUrl(String base, String path, PaymentModel paymentModel, String error){
        String url = base+path;
        if(paymentModel!=null){
            url+="?date="+paymentModel.getAppointment().getDate();
            url+="&time="+paymentModel.getAppointment().getTimeSlotId();
        }
        if(TypeConverter.stringIsNotEmpty(error)){
            url+=(paymentModel!=null?"&error=":"?error=")+error;
        }
        return url;
    }
}
