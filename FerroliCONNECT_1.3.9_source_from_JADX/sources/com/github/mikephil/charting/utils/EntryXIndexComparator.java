package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.data.Entry;
import java.util.Comparator;

public class EntryXIndexComparator implements Comparator<Entry> {
    public int compare(Entry entry, Entry entry2) {
        return entry.getXIndex() - entry2.getXIndex();
    }
}
