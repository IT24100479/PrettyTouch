package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.AppointmentModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.util.Queue;
import com.janani.prettytouch.util.QuickSort;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;

public class AppointmentService implements Services {

    private List<Model> allAppointments;
    private Map<String, Map<String, Queue>> appointments;
    private static AppointmentService appointmentService;
    private final QuickSort quickSort;

    private AppointmentService() {
        this.quickSort = new QuickSort();
        this.readAll();
    }

    public static AppointmentService getInstance() {
        if (appointmentService == null) {
            appointmentService = new AppointmentService();
        }
        return appointmentService;
    }

    @Override
    public void readAll() {
        allAppointments = new ArrayList<>();
        String filePath = FIleConst.FILE_PATH + FIleConst.APPOINTMENT_FILE;
        appointments = new HashMap<>();
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row.length >= 9) {
                    AppointmentModel appointment = new AppointmentModel(row[0],
                            row[1], row[2], row[3], row[4], row[5],
                            row[6], row[7],row[8]
                    );
                    allAppointments.add(appointment);
                    this.addToQueue(appointment);

                }
            }
            csvReader.close();
            print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void print() {
        for (String date : appointments.keySet()) {

            System.out.println(date);
            for (String timeSlot : appointments.get(date).keySet()) {
                System.out.println("\t" + GlobalConst.TIME_SLOT_LIST.get(Integer.parseInt(timeSlot)));
                for (Model appointment : appointments.get(date).get(timeSlot).getQueueList()) {
                    System.out.println("\t\t\t" + appointment.getId() + " , " + appointment.getCreatedAt());
                }
            }

        }
    }

    @Override
    public List<Model> getAll() {
        return this.allAppointments;
    }

    @Override
    public Model getById(int id) {
        for (int i = 0; i < this.allAppointments.size(); i++) {
            if (this.allAppointments.get(i).getId() == id) {
                return this.allAppointments.get(i);
            }
        }
        return null;
    }

    private boolean addToQueue(AppointmentModel appointment) {
        if (GlobalConst.APPOINTMENT_STATUS_TYPE.get(1).equalsIgnoreCase(appointment.getStatusForCsv())) {
            String dateKey = appointment.getDate().toString();
            if (!appointments.containsKey(dateKey)) {
                appointments.put(dateKey, new HashMap<>());
            }
            String timeSlot = appointment.getTimeSlotId() + "";
            if (!appointments.get(dateKey).containsKey(timeSlot)) {
                appointments.get(dateKey).put(timeSlot, new Queue(GlobalConst.QUEUE_SIZE));
            }

            return appointments.get(dateKey).get(timeSlot).sortAndInsert(appointment);
        }
        return false;
    }

    @Override
    public boolean updateFile() {
        String filePath = FIleConst.FILE_PATH + FIleConst.APPOINTMENT_FILE;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(FIleConst.APPOINTMENT_HEADERS);
            for (int i = 0; i < this.allAppointments.size(); i++) {
                writer.writeNext(this.allAppointments.get(i).getCSVLine());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Model model) {
        AppointmentModel appointmentModel = (AppointmentModel) model;
        if (appointmentModel.getCreatedAt() == null || appointmentModel.getCreatedAt().toString().isEmpty()) {
            appointmentModel.setCreatedAt(LocalDateTime.now().toString());
        }
        if (this.addToQueue(appointmentModel)) {
            if(appointmentModel.getId()==0){
                int id = 1;
                if(!allAppointments.isEmpty()){
                    id = allAppointments.getLast().getId()+1;
                }
                appointmentModel.setId(id+"");
            }
            allAppointments.add(appointmentModel);
            Model[] temp = this.allAppointments.toArray(Model[]::new);
            this.quickSort.quickSort(temp, 0, this.allAppointments.size() - 1);
            this.allAppointments =  new ArrayList<>(Arrays.asList(temp));
            return this.updateFile();
        }
        return false;
    }

    @Override
    public boolean update(Model model) {
        AppointmentModel appointmentModel = (AppointmentModel) model;
        AppointmentModel oldModel;
        for (int i = 0; i < this.allAppointments.size(); i++) {
            if (this.allAppointments.get(i).getId() == appointmentModel.getId()) {
                oldModel = (AppointmentModel) this.allAppointments.get(i);
                if (this.appointments.containsKey(oldModel.getDate().toString()) &&
                        this.appointments.get(oldModel.getDate().toString()).containsKey(oldModel.getTimeSlotId() + "")) {
                    this.appointments.get(oldModel.getDate().toString()).get(oldModel.getTimeSlotId() + "").findAndRemove(oldModel.getId());
                }
                this.allAppointments.remove(i);
                return this.add(appointmentModel);
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < this.allAppointments.size(); i++) {
            if (this.allAppointments.get(i).getId() == id) {
                this.allAppointments.get(i).setStatus(GlobalConst.APPOINTMENT_STATUS_TYPE.get(3));
                this.update(this.allAppointments.get(i));
                return true;
            }
        }
        return false;
    }

}
