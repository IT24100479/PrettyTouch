package com.janani.prettytouch.util;

import com.janani.prettytouch.model.Model;

import java.time.LocalDateTime;

public class QuickSort {
    public void quickSort(Model[] arr, int low, int high) {
        quickSort(arr,low,high,"createdDate");
    }

    public void quickSort(Model[] arr, int low, int high,String type) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pi=0;
            switch ((type+"").toLowerCase()){
                case "createddate":
                    pi=partitionByCreatedDate(arr, low, high);
                    break;
                case "id":
                    pi=partitionById(arr, low, high);
                    break;
                default:
                    pi=partitionByCreatedDate(arr, low, high);
            }

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1,type);
            quickSort(arr, pi + 1, high,type);
        }
    }
    private int partitionById(Model[] arr, int low, int high) {
        int pivot = arr[high].getId(); // Choose the last element as pivot
        int i = low; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j].getId()<=pivot) {
                // Swap arr[i] and arr[j]
                Model temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }

        // Swap arr[i] and arr[high] (or pivot)
        Model temp = arr[i];
        arr[i] = arr[high];
        arr[high] = temp;

        return i;
    }
    // Method to partition the array
    private int partitionByCreatedDate(Model[] arr, int low, int high) {
        LocalDateTime pivot = arr[high].getCreatedAt(); // Choose the last element as pivot
        int i = low; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j].getCreatedAt().isBefore(pivot) || arr[j].getCreatedAt().equals(pivot)) {
                // Swap arr[i] and arr[j]
                Model temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }

        // Swap arr[i] and arr[high] (or pivot)
        Model temp = arr[i];
        arr[i] = arr[high];
        arr[high] = temp;

        return i;
    }
}
