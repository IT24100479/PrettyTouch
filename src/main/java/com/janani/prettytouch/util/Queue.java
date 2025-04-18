package com.janani.prettytouch.util;

import com.janani.prettytouch.model.Model;

import java.util.List;

public class Queue {
    private List<Model> queueList;
    private int size;

    public Queue(int size) {
        this.size = size;
    }

    public Model peekFront(){
        return queueList.getFirst();
    }

    public Model remove(){
        if(queueList.isEmpty()){
            Model model = queueList.getFirst();
            queueList.removeFirst();
            return model;
        }
        return null;
    }

    public boolean findAndRemove(int id){
        for(int i = 0; i < queueList.size(); i++){
            Model model = queueList.get(i);
            if(model.getId() == id){
                queueList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean insert(Model item){
        if (queueList.size() < size) {
            queueList.add(item);
            return true;
        }
        return false;
    }

    public boolean sortAndInsert(Model item){
        if (queueList.size() < size) {
            queueList.add(item);
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return queueList.isEmpty();
    }

    public boolean isFull(){
        return queueList.size() == size;
    }

    public int getCount(){
        return queueList.size();
    }

}
