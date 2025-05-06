package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.PaymentModel;
import com.janani.prettytouch.model.ServiceModel;
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

public class ServiceService implements Services {

    private List<Model> allServices;
    private static ServiceService service;
    private final QuickSort quickSort;

    private ServiceService() {
        this.quickSort = new QuickSort();
        this.readAll();
    }

    public static ServiceService getInstance() {
        if (service == null) {
            service = new ServiceService();
        }
        return service;
    }

    @Override
    public void readAll() {
        allServices = new ArrayList<Model>();
        String filePath = FIleConst.FILE_PATH + FIleConst.SERVICE_FILE;
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(filePath);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row.length >= 11) {
                    ServiceModel service = new ServiceModel(row[0],
                            row[1], row[2], row[3], row[4], row[5],
                            row[6], row[7], row[8], row[9], row[10]
                    );
                    allServices.add(service);
                }
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Model> getAll() {
        return this.allServices;
    }

    public List<ServiceModel> getServiceWithFilter(boolean withService, boolean withOffer) {
        List<ServiceModel> data = new ArrayList<>();
        for (int i = 0; i < this.allServices.size(); i++) {
            ServiceModel model = (ServiceModel) this.allServices.get(i);
            if (Boolean.FALSE.equals(model.getStatus())) {
                continue;
            }
            if (Boolean.FALSE.equals(model.getIsOffer()) && withService) {
                data.add(model);
            }
            if (Boolean.TRUE.equals(model.getIsOffer()) && withOffer) {
                data.add(model);
            }
        }
        return data;
    }

    @Override
    public Model getById(int id) {
        for (int i = 0; i < this.allServices.size(); i++) {
            if (this.allServices.get(i).getId() == id) {
                return this.allServices.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean add(Model model) {
        ServiceModel serviceModel = (ServiceModel) model;
        serviceModel.checkCreatedAtAndID(allServices);
        allServices.add(serviceModel);
        Model[] temp = this.allServices.toArray(Model[]::new);
        this.quickSort.quickSort(temp, 0, this.allServices.size() - 1);
        this.allServices = new ArrayList<>(Arrays.asList(temp));
        return this.updateFile();
    }

    @Override
    public boolean update(Model model) {
        for (int i = 0; i < this.allServices.size(); i++) {
            if (this.allServices.get(i).getId() == model.getId()) {
                this.allServices.remove(i);
                return this.add(model);
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < this.allServices.size(); i++) {
            if (this.allServices.get(i).getId() == id) {
                this.allServices.get(i).setStatus("0");
                return this.update(this.allServices.get(i));
            }
        }
        return false;
    }

    public boolean isDuplicate(ServiceModel serviceModel) {
        for (int i = 0; i < this.allServices.size(); i++) {
            ServiceModel model = (ServiceModel) this.allServices.get(i);
            if (model.getServiceName().equals(serviceModel.getServiceName()) && model.getStatus()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateFile() {
        String filePath = FIleConst.FILE_PATH + FIleConst.SERVICE_FILE;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(FIleConst.SERVICE_HEADERS);
            for (int i = 0; i < this.allServices.size(); i++) {
                writer.writeNext(this.allServices.get(i).getCSVLine());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}