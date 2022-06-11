package com.developerrr.fitnesstracker.logics;

import com.developerrr.fitnesstracker.roomdb.StepTable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
 //Mit HistoryFragment verbunden
//Partition ==Trennung/Teilung
public final class Partition<T> extends AbstractList<List<T>> {

    //Deklaration
    private final List<T> list;
    private final int chunkSize;

    public Partition(List<T> list, int chunkSize) {
        this.list = new ArrayList<>(list); //ArrayList
        this.chunkSize = chunkSize; //Chunksize (Größe)
    }

    //class StepTable ; neuer Partiton StepTable
    public static <T> Partition<StepTable> ofSize(List<StepTable> list, int chunkSize) {
        return new Partition<StepTable>(list, chunkSize);
    }

    @Override //Index Liste
    public List<T> get(int index) {
        int start = index * chunkSize; //
        int end = Math.min(start + chunkSize, list.size()); //

        //Exception wenn Index nd passt
        if (start > end) {
            throw new IndexOutOfBoundsException("Index " + index + " ist außerhalb des Bereichs <0," + (size() - 1) + ">");
        }

        return new ArrayList<>(list.subList(start, end));
    }

    @Override
    public int size() {
        return (int) Math.ceil((double) list.size() / (double) chunkSize);
    }
}
