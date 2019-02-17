package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume R1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = new Resume(UUID_3);

    AbstractStorageTest(Storage testStorage) {
        this.storage = testStorage;
    }

    @Before
    public void setUp() {
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume());
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExists() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get("dummy");
    }

    @Test
    public void get() {
        assertEquals(UUID_1, storage.get(UUID_1).getUuid());
        assertEquals(UUID_2, storage.get(UUID_2).getUuid());
        assertEquals(UUID_3, storage.get(UUID_3).getUuid());

    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotFound() {
        storage.delete(UUID_2);
        storage.delete(UUID_2);
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_2);
        storage.update(updateResume);
        assertSame(storage.get(UUID_2), updateResume);
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        assertTrue(resumes.contains(R1));
        assertTrue(resumes.contains(R2));
        assertTrue(resumes.contains(R3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}
