package com.urise.webapp.storage;/*
  Array based storage for Resumes
 */


import com.urise.webapp.model.Resume;

import static java.lang.System.arraycopy;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;
        size = 0;
    }

    public void save(Resume r) {
        if (size <= storage.length) {
            int id = indexOf(r.getUuid());
            if (!isInArray(id)) {
                storage[size] = r;
                size++;
            }

        } else System.out.println("Storage overflow");
    }

    public Resume get(String uuid) {
        int id = indexOf(uuid);
        if (isInArray(id))
            return storage[id];
        return null;
    }

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
    public Resume[] getAll() {
        Resume[] newStorage = new Resume[this.size()];
        arraycopy(storage, 0, newStorage, 0, size);
        return newStorage;
    }

    public int size() {
        return size;
    }

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
