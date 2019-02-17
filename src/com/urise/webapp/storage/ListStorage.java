package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    void doSave(Resume r, Object id) {
        list.add(r);
    }

    @Override
    Resume doGet(Object id) {
        return list.get((Integer) id);
    }

    @Override
    void doDelete(Object id) {
        list.remove(((Integer) id).intValue());
    }

    @Override
    void doUpdate(Resume r, Object id) {
        list.set((Integer) id, r);
    }

    @Override
    public List<Resume> getAllSorted() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    boolean isExist(Object searchKey) {
        return searchKey != null;
    }

}
