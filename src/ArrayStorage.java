/*
  Array based storage for Resumes
 */


import static java.lang.System.arraycopy;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int s = 0;

    void clear() {
        for (int i = 0; i < s; i++)
            storage[i] = null;
        s = 0;
    }

    void save(Resume r) {
        s++;
        for (int i = 0; i < s; i++)
            if (storage[i] == null) storage[i] = r;
    }

    Resume get(String uuid) {
        Resume found = null;
        int id = indexOf(uuid);
        if (id >= 0) found = storage[id];
        return found;
    }

    void delete(String uuid) {
        int i = indexOf(uuid);
        if (i >= 0) {
            arraycopy(storage, i + 1, storage, i, s - i - 1);
            s--;
        } else
            System.out.println("Нельзя удалить то, чего нет");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[this.size()];
        arraycopy(storage, 0, newStorage, 0, s);
        return newStorage;
    }

    int size() {
        return s;
    }

    private int indexOf(String uuid) {
        int index = -1;
        for (int i = 0; i < s; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;

    }
}
