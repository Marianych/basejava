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
        for (int i = 0; i <= size; i++) {
            if (i == size) {
                storage[i] = r;
                size++;
                break;
            } else if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Такое резюме уже есть.");
                break;

            }
        }

    }

    Resume get(String uuid) {
        int id = indexOf(uuid);
        if (id >= 0)
            return storage[id];
        else
            return null;
    }

    void delete(String uuid) {
        int i = indexOf(uuid);
        if (i >= 0) {
            arraycopy(storage, i + 1, storage, i, size - i - 1);
            size--;
        } else
            System.out.println("Нет такого резюме.");
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
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;

    }
}
