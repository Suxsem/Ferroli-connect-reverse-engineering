package com.chad.library.adapter.base.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExpandableItem<T> implements IExpandable<T> {
    protected boolean mExpandable = false;
    protected List<T> mSubItems;

    public boolean isExpanded() {
        return this.mExpandable;
    }

    public void setExpanded(boolean z) {
        this.mExpandable = z;
    }

    public List<T> getSubItems() {
        return this.mSubItems;
    }

    public boolean hasSubItem() {
        List<T> list = this.mSubItems;
        return list != null && list.size() > 0;
    }

    public void setSubItems(List<T> list) {
        this.mSubItems = list;
    }

    public T getSubItem(int i) {
        if (!hasSubItem() || i >= this.mSubItems.size()) {
            return null;
        }
        return this.mSubItems.get(i);
    }

    public int getSubItemPosition(T t) {
        List<T> list = this.mSubItems;
        if (list != null) {
            return list.indexOf(t);
        }
        return -1;
    }

    public void addSubItem(T t) {
        if (this.mSubItems == null) {
            this.mSubItems = new ArrayList();
        }
        this.mSubItems.add(t);
    }

    public void addSubItem(int i, T t) {
        List<T> list = this.mSubItems;
        if (list == null || i < 0 || i >= list.size()) {
            addSubItem(t);
        } else {
            this.mSubItems.add(i, t);
        }
    }

    public boolean contains(T t) {
        List<T> list = this.mSubItems;
        return list != null && list.contains(t);
    }

    public boolean removeSubItem(T t) {
        List<T> list = this.mSubItems;
        return list != null && list.remove(t);
    }

    public boolean removeSubItem(int i) {
        List<T> list = this.mSubItems;
        if (list == null || i < 0 || i >= list.size()) {
            return false;
        }
        this.mSubItems.remove(i);
        return true;
    }
}
