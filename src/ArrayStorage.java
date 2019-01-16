/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int id = -1;
    int i=0;

    void clear() {
        for (Resume e : storage) if (e != null) e = null;
    }

    void save(Resume r) {
        for (Resume e : storage){
            if (e == null) {
                e = r;
                break;
            }
            }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {

            if (storage[i].uuid == uuid) {
                id = i;
                break;

            }
        }
        return storage[id];
    }

    void delete(String uuid) {
        for (Resume e : storage) {
            if (e.uuid == uuid) e = null;

        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return 0;
    }
}
