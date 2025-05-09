package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.FeedbackModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.servlet.Feedback;
import com.janani.prettytouch.util.QuickSort;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedbackService implements Services{

    private List<Model> allRatings;
    private static FeedbackService feedbackService;
    private final QuickSort quickSort;


    private FeedbackService() {
        this.quickSort = new QuickSort();
        this.readAll();
    }
    public static FeedbackService getInstance()
    {
        if (feedbackService == null) {
            feedbackService = new FeedbackService();
        }
        return feedbackService;
    }

    @Override
    public void readAll() {
        allRatings = new ArrayList<Model>();
        String filepath = FIleConst.FILE_PATH + FIleConst.RATING_FILE;
        try {
            FileReader fileReader = new FileReader(filepath);
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allDate = csvReader.readAll();

            for (int i = 0;i < allDate.size(); i++) {
                String[] row = allDate.get(i);
                if (row.length >= 7) {
                    FeedbackModel rating = new FeedbackModel(row[0],
                            row[1],row[2],row[3],row[4],row[5],
                            row[6]
                    );
                    allRatings.add(rating);

                }

            }
            csvReader.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public List<Model> getAll() {return allRatings;
    }

    @Override
    public Model getById(int id) {
        for (int i =0 ; i < this.allRatings.size(); i++) {
            if (this.allRatings.get(i).getId() == id) {
                return this.allRatings.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean add(Model model)
    {
        ServiceModel serviceModel = (ServiceModel) model;

        serviceModel.checkCreatedAtAndID(allRatings);

        allRatings.add(serviceModel);
        Model[] temp = this. allRatings.toArray(Model[]::new);
        this.quickSort.quickSort(temp, 0, this.allRatings.size() - 1);
        this.allRatings = new ArrayList<>(Arrays.asList(temp));
        return this.updateFile();
    }

    @Override
    public boolean update(Model model)
    {
        for (int i =0 ; i < this.allRatings.size(); i++) {
            if (this.allRatings.get(i).getId() == model.getId()) {
                this.allRatings.remove(i);
                return this.add(model);
            }
        }

        return false;
    }

    @Override
    public boolean delete(int id)
    {
        for (int i =0 ; i < this.allRatings.size(); i++) {
            if (this.allRatings.get(i).getId() == id) {
                this.allRatings.get(i).setStatus("0");
                return this.update(this.allRatings.get(i));
            }
        }
        return false;
    }

    public FeedbackModel getRatingForAppointment(int id) {
        for (int i =0 ; i < this.allRatings.size(); i++) {
            FeedbackModel model = (FeedbackModel) this.allRatings.get(i);
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    @Override
    public boolean updateFile() {

        return true;
    }
}
