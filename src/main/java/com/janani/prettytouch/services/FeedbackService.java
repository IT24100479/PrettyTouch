package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.constVar.GlobalConst;
import com.janani.prettytouch.model.FeedbackModel;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.util.QuickSort;
import com.janani.prettytouch.util.TypeConverter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
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
    public static FeedbackService getInstance() {
        if (feedbackService == null) {
            feedbackService = new FeedbackService();
        }
        return feedbackService;
    }

    @Override
    public void readAll() {
        allRatings = new ArrayList<Model>();
        String filePath = FIleConst.FILE_PATH + FIleConst.RATING_FILE;
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Model> getAll() {
        return allRatings;
    }

    @Override
    public Model getById(int id) {
        for (int i = 0; i < this.allRatings.size(); i++) {
            if(this.allRatings.get(i).getId() == id){
                return this.allRatings.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean add(Model model) {
        FeedbackModel feedbackModel = (FeedbackModel) model;
        feedbackModel.checkCreatedAtAndID(allRatings);
        allRatings.add(feedbackModel);
        Model[] temp = this.allRatings.toArray(Model[]::new);
        this.quickSort.quickSort(temp, 0, this.allRatings.size() - 1,"id");
        this.allRatings =  new ArrayList<>(Arrays.asList(temp));
        return this.updateFile();
    }

    @Override
    public boolean update(Model model) {
        for (int i = 0; i < this.allRatings.size(); i++) {
            if (this.allRatings.get(i).getId() == model.getId()) {
                this.allRatings.remove(i);
                return this.add(model);
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < this.allRatings.size(); i++) {
            if (this.allRatings.get(i).getId() == id) {
                this.allRatings.get(i).setStatus("0");
                return this.update(this.allRatings.get(i));
            }
        }
        return false;
    }

    public FeedbackModel getRatingByUserId(int id) {
        for (int i = 0; i < allRatings.size() ; i++) {
            FeedbackModel model = (FeedbackModel) allRatings.get(i);
            if (model.getStatus() && model.getCreatedBy() == id) {
                return model;
            }
        }
        return null;
    }

    public FeedbackModel getRatingForAppointment(int id) {
        for (int i = 0; i < allRatings.size() ; i++) {
            FeedbackModel model = (FeedbackModel) allRatings.get(i);
            if (model.getAppointmentId() == id) {
                return model;
            }
        }
        return null;
    }

    @Override
    public boolean updateFile() {
        String filePath = FIleConst.FILE_PATH + FIleConst.RATING_FILE;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(FIleConst.RATING_HEADERS);
            for (int i = 0; i < this.allRatings.size(); i++) {
                writer.writeNext(this.allRatings.get(i).getCSVLine());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String createReturnUrl(String base, String path, FeedbackModel model, String error){
        String url = base+path;
        if(model!=null){
            url+="?fid="+model.getId();
            url+="&rating="+model.getRating();
            url+="&comment="+model.getComment();
            url+="&aid="+model.getAppointmentId();

        }
        if(TypeConverter.stringIsNotEmpty(error)){
            url+=(model!=null?"&error=":"?error=")+error;
        }
        return url;
    }

    public int nextStart(int start, int limit){
        start+=limit;
        return start>allRatings.size()?0:start;
    }

    public List<FeedbackModel> getFeedbackWithLimit(int start, int limit) {
        List<FeedbackModel> feedbackModels = new ArrayList<>();
        int end =allRatings.size()-start-1-limit;
        limit = end<0?-1:end;
        for (int i = allRatings.size()-(start+1); i >limit ; i--) {
            FeedbackModel model =(FeedbackModel) allRatings.get(i);
            model.setCreatedByFullUser();
            model.setShortComment(GlobalConst.RATING_COMMENT_SHORT_SIZE);
            feedbackModels.add(model);
        }
        return feedbackModels;
    }

    public List<FeedbackModel> getAllForShow(){
        List<FeedbackModel> feedbackModels = new ArrayList<>();
        for (int i = (allRatings.size()-1); i >-1 ; i--) {
            FeedbackModel model =(FeedbackModel) allRatings.get(i);
            if(model.getStatus()){
                feedbackModels.add(model);
            }
        }
        return feedbackModels;
    }
}
