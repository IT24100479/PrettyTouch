package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.util.QuickSort;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
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
                            row[1],row[2],row[3],row[4],row[5],
                            row[6],row[7],row[8],row[9],row[10]
                    );
                    allServices.add(service);
                }
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Model> getAll() {
        return this.allServices;
    }

    public List<Model> getAllForUser(int userId) {
        List<Model> data = new ArrayList<>();
        for (int i = 0; i < this.allServices.size(); i++) {
            if(this.allServices.get(i).getCreatedBy() == userId){
                data.add(this.allServices.get(i));
            }
        }
        return data;
    }

    @Override
    public Model getById(int id) {
        for (int i = 0; i < this.allServices.size(); i++) {
            if(this.allServices.get(i).getId() == id){
                return this.allServices.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean add(Model model) {
        return false;
    }

    @Override
    public boolean update(Model model) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean updateFile() {
        return true;
    }
}
