package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

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
    boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    void doSave(Resume r, Integer id) {
        list.add(r);
    }

    @Override
    void doUpdate(Resume r, Integer id) {
        list.set(id, r);
    }

    @Override
    void doDelete(Integer id) {
        list.remove(id.intValue());
    }

    @Override
    Resume doGet(Integer id) {
        return list.get(id);
    }

    @Override
    public List<Resume> doGetAll() {
        return list;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

}
