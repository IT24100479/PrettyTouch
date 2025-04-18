package com.janani.prettytouch.util;

import com.janani.prettytouch.model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Queue {
    private List<Model> queueList;
    private int size;
    private final QuickSort quickSort;

    public Queue(int size) {
        this.queueList = new ArrayList<>();
        this.size = size;
        this.quickSort = new QuickSort();
    }

    public Model peekFront(){
        return queueList.getFirst();
    }

    public Model remove(){
        if(this.queueList.isEmpty()){
            Model model = this.queueList.getFirst();
            this.queueList.removeFirst();
            return model;
        }
        return null;
    }

    public boolean findAndRemove(int id){
        for(int i = 0; i < this.queueList.size(); i++){
            Model model = this.queueList.get(i);
            if(model.getId() == id){
                this.queueList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean insert(Model item){
        if (this.queueList.size() < this.size) {
            this.queueList.add(item);
            return true;
        }
        return false;
    }

    public boolean sortAndInsert(Model item){
        if (this.queueList.size() < this.size) {
            this.queueList.add(item);
            Model[] temp = this.queueList.toArray(Model[]::new);
            this.quickSort.quickSort(temp, 0, this.queueList.size() - 1);
            this.queueList =  new ArrayList<>(Arrays.asList(temp));
            return true;
        }
        return false;
    }

    public List<Model> getQueueList() {
        return this.queueList;
    }

    public boolean isEmpty(){
        return this.queueList.isEmpty();
    }

    public boolean isFull(){
        return this.queueList.size() == this.size;
    }

    public int getCount(){
        return this.queueList.size();
    }

}
