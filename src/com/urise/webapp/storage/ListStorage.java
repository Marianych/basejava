package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    void doSave(Resume r, Integer id) {
        list.add(r);
    }

    @Override
    Resume doGet(Integer id) {
        return list.get(id);
    }

    @Override
    void doDelete(Integer id) {
        list.remove(id.intValue());
    }

    @Override
    void doUpdate(Resume r, Integer id) {
        list.set(id, r);
    }

    @Override
    public List<Resume> doGetAll() {
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
    boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

}
