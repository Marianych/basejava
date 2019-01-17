/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++)
            storage[i] = null;
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++)
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
    }

    Resume get(String uuid) {
        int id = 0;
        for (int i = 0; i < storage.length; i++) {

            if ((storage[i] != null) && (storage[i].uuid.equals(uuid))) {
                id = i;
                break;

            }
        }
        return storage[id];
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if ((storage[i] != null) && (storage[i].uuid.equals(uuid)))
                storage[i] = null;
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int i = 0;
        Resume[] newStorage = new Resume[this.size()];
        for (Resume e : storage) {
            if (e != null) {
                newStorage[i] = e;
                i++;
            }
        }
        return newStorage;
    }


    int size() {
        int s = 0;

        for (Resume e : storage) {
            if (e != null) {
                s++;
            }
        }
        return s;
    }
}
