package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.System.arraycopy;


public abstract class AbstractArrayStorage extends AbstractStorage {

    final Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume r, Object id) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insert(r, (Integer) id);
        size++;
    }

    @Override
    Resume doGet(Object id) {
        return storage[(Integer) id];
    }

    @Override
    public void doDelete(Object id) {
        Integer index = (Integer) id;
        int headSize = index + 1;
        arraycopy(storage, headSize, storage, index, size - headSize);
        size--;
    }

    @Override
    public void doUpdate(Resume updateResume, Object id) {
        storage[(Integer) id] = updateResume;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    boolean isExist(Object id) {
        return (Integer) id >= 0;
    }

    abstract void insert(Resume what, int where);

}