package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {

    final Storage storage;
    static final String FILE_DIR = Config.get().getStorageDir();
    private static final String UUID_1 = "uuid11111111111111111111111111111111";
    private static final String FULL_NAME_1 = "FULL_NAME_1";
    private static final Resume R1 = new Resume(UUID_1, FULL_NAME_1);
    private static final String UUID_2 = "uuid22222222222222222222222222222222";
    private static final String FULL_NAME_2 = "FULL_NAME_2";
    private static final Resume R2 = new Resume(UUID_2, FULL_NAME_2);
    private static final String UUID_3 = "uuid33333333333333333333333333333333";
    private static final String FULL_NAME_3 = "FULL_NAME_3";
    private static final Resume R3 = new Resume(UUID_3, FULL_NAME_3);
    private static final String UUID_4 = "uuid44444444444444444444444444444444";
    private static final String FULL_NAME_4 = "FULL_NAME_4";
    private static final Resume R4 = new Resume(UUID_4, FULL_NAME_4);

    static {
        R1.setContact(ContactType.EMAIL, "mail1@ya.ru");
        R1.setContact(ContactType.PHONE, "11111");
        R1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.setSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.setSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        R1.setSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
//
        R1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
//
        R2.setContact(ContactType.SKYPE, "skype2");
        R2.setContact(ContactType.PHONE, "22222");
//
        R1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
//
    }

    AbstractStorageTest(Storage testStorage) {
        this.storage = testStorage;
    }

    @Before
    public void setUp() {
        storage.clear();
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
        storage.save(R4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExists() {
        storage.save(new Resume(UUID_2, FULL_NAME_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get("dummy");
    }

    @Test
    public void get() {
        assertEquals(storage.get(UUID_1), R1);
        assertEquals(storage.get(UUID_2), R2);
        assertEquals(storage.get(UUID_3), R3);

    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotFound() {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_2, FULL_NAME_4);
        storage.update(updateResume);
        Resume expected = storage.get(UUID_2);
        assertEquals(expected, updateResume);
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
