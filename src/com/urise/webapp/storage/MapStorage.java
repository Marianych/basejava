package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapStorage extends AbstractStorage {
//    Map<Integer,Resume> storage = new

    @Override
    Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    boolean isExist(Object id) {
        return false;
    }

    @Override
    void doSave(Resume r, Object id) {

    }

    @Override
    Resume doGet(Object id) {
        return null;
    }

    @Override
    void doDelete(Object id) {

    }

    @Override
    void doUpdate(Resume r, Object id) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
