package com.urise.webapp.storage;/*
  Array based storage for Resumes
 */


import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        if (size <= STORAGE_LIMIT) {
            int id = indexOf(r.getUuid());
            if (!isInArray(id)) {
                storage[size] = r;
                size++;
            }

        } else System.out.println("Storage overflow");
    }

    @Override
    public Resume get(String uuid) {
        int id = indexOf(uuid);
        if (isInArray(id))
            return storage[id];
        return null;
    }

    @Override
    public void delete(String uuid) {
        int id = indexOf(uuid);
        int headSize = id + 1;
        if (isInArray(id)) {
            arraycopy(storage, headSize, storage, id, size - headSize);
            size--;
        } else {
            System.out.println("Resume "+uuid+" not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void update(Resume updateResume) {
        String uuid = updateResume.getUuid();
        int id = indexOf(uuid);
        if (isInArray(id)) {
            storage[id].setUuid(uuid);
        } else System.out.println("Resume "+uuid+" not found");

    }

    private int indexOf(String uuid) {
        int nonExistIndex = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return nonExistIndex;

    }

    private boolean isInArray(int id) {
        return id >= 0;
    }
}
