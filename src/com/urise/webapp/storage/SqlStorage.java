package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactioalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("insert into resume (uuid, full_name) values (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContact(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume updateResume) {
        sqlHelper.transactioalExecute(conn -> {
                    String uuid = updateResume.getUuid();
                    try (PreparedStatement ps = conn.prepareStatement("update resume r set full_name=? where r.uuid = ?")) {
                        ps.setString(1, updateResume.getFullName());
                        ps.setString(2, uuid);
                        ps.execute();
                    }
                    deleteContacts(updateResume, conn);
                    insertContact(updateResume, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("delete from resume r where r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "   SELECT * FROM resume r  " +
                "LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                "    WHERE r.uuid = ? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));

            do {
                addContact(rs, r);
            } while (rs.next());

            return r;
        });
    }


    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                "   SELECT * FROM resume r  " +
                "LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                " ORDER BY full_name,uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, resume);
                }
                addContact(rs, resume);
            }

            return new ArrayList<>(map.values());

        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("select count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });

    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void insertContact(Resume updateResume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("insert into contact (resume_uuid, type, value) values (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : updateResume.getContacts().entrySet()) {
                ps.setString(1, updateResume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume r, Connection conn) {
        sqlHelper.execute("delete from contact c where c.resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }
}
