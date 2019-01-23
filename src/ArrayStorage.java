/*
  Array based storage for Resumes
 */


import static java.lang.System.arraycopy;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;
        size = 0;
    }

    void save(Resume r) {
        int id = indexOf(r.uuid);
        if (!isInArray(id)) {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        int id = indexOf(uuid);
        if (isInArray(id))
            return storage[id];
        return null;
    }

    void delete(String uuid) {
        int id = indexOf(uuid);
        int headSize = id + 1;
        if (isInArray(id)) {
            arraycopy(storage, headSize, storage, id, size - headSize);
            size--;
        } else {
            System.out.println("Нет такого резюме.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[this.size()];
        arraycopy(storage, 0, newStorage, 0, size);
        return newStorage;
    }

    int size() {
        return size;
    }

    private int indexOf(String uuid) {
        int nonExistIndex = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return nonExistIndex;

    }

    private boolean isInArray(int id) {
        return id >= 0;
    }
}
