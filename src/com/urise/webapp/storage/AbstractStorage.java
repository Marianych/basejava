package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    static final int STORAGE_LIMIT = 10000;

    abstract Object getSearchKey(String uuid);

    abstract boolean isExist(Object id);

    abstract void doSave(Resume r, Object id);

    abstract Resume doGet(Object id);

    abstract void doDelete(Object id);

    abstract void doUpdate(Resume r, Object id);

    @Override
    public void save(Resume r) {
        Object id = getExistSearchKey(r.getUuid());
        doSave(r, id);
    }

    @Override
    public Resume get(String uuid) {
        Object id = getNotExistSearchKey(uuid);
        return doGet(id);
    }

    @Override
    public void delete(String uuid) {
        Object id = getNotExistSearchKey(uuid);
        doDelete(id);
    }

    @Override
    public void update(Resume r) {
        Object id = getNotExistSearchKey(r.getUuid());
        doUpdate(r, id);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

}
