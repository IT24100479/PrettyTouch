package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.util.Queue;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentService implements Services{

    private List<Model> allAppointments;
    private Map<String,Map<String, Queue>> appointments;
    private static AppointmentService appointmentService;

    private AppointmentService() {
        this.ReadAll();
    }
    public static AppointmentService getInstance() {
        if (appointmentService == null) {
            appointmentService = new AppointmentService();
        }
        return appointmentService;
    }

    @Override
    public void ReadAll() {
        allAppointments = new ArrayList<Model>();
        String filePath = FIleConst.FILE_PATH + FIleConst.APPOINTMENT_FILE;
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row.length >= 8) {
                    AppointmentModel appointment = new AppointmentModel(row[0],
                            row[1],row[2],row[3],row[4],row[5],
                            row[6],row[7]
                    );
                    allAppointments.add(appointment);
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
        return List.of();
    }

    @Override
    public Model getById(int id) {
        return null;
    }

    @Override
    public boolean Add(Model model) {
        return false;
    }

    @Override
    public boolean Update(Model model) {
        return false;
    }

    @Override
    public boolean Delete(Model model) {
        return false;
    }

    private void createQueues(){
        appointments = new HashMap<String,Map<String, Queue>>();
        for (int i = 0; i < allAppointments.size(); i++) {

        }
    }
}
