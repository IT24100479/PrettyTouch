package com.janani.prettytouch.util;

import com.janani.prettytouch.model.Model;

import java.time.LocalDateTime;

public class QuickSort {
    public void quickSort(Model[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Method to partition the array
    private int partition(Model[] arr, int low, int high) {
        LocalDateTime pivot = arr[high].getCreatedAt(); // Choose the last element as pivot
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j].getCreatedAt().isBefore(pivot) || arr[j].getCreatedAt().equals(pivot)) {
                i++;

                // Swap arr[i] and arr[j]
                Model temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high] (or pivot)
        Model temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
}
