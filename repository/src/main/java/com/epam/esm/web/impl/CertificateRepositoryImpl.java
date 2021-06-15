package com.epam.esm.web.impl;

import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.Certificate;
import com.epam.esm.web.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private final String GET_ALL_CERTIFICATES = "SELECT * FROM certificate";
    private final String GET_CERTIFICATE_BY_ID = "SELECT * FROM certificate where id=?";
    private final String CREATE_CERTIFICATE = "INSERT INTO certificate" +
            "(id, name, description, price, duration, create_date, last_update_date)" +
            "VALUES(null, :name, :description, :price, :duration, :create_date, :last_update_date)";

    private final String DELETE_CERTIFICATE = "delete from certificate where id=?";

    private final String GET_CERTIFICATES_BY_TAG_ID = "select certificate.id as id, name, description, price, " +
            "duration, create_date, last_update_date " +
            "from certificateTags join certificate on certificateTags.certificate_id=certificate.id" +
            " where tag_id=?";


    private final String FULL_UPDATE_CERTIFICATE = "update certificate set name=:name, description=:description, " +
            "price=:price, duration=:duration, create_date=:create_date, last_update_date=:last_update_date " +
            "where id=:id";

    private NamedParameterJdbcOperations namedJdbcOperations;

    @Autowired
    public CertificateRepositoryImpl(NamedParameterJdbcOperations jdbcOperations){
        this.namedJdbcOperations = jdbcOperations;
    }

    @Override
    public List<Certificate> retrieveAll() {
        return namedJdbcOperations.query(GET_ALL_CERTIFICATES, (rs, rowNum) -> new Certificate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("duration"),
                rs.getString("create_date"),
                rs.getString("last_update_date")
        ));
    }

    @Override
    public Certificate retrieveOne(int id) {
        return namedJdbcOperations.getJdbcOperations().queryForObject(GET_CERTIFICATE_BY_ID, (rs, rowNum) -> new Certificate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("duration"),
                rs.getString("create_date"),
                rs.getString("last_update_date")) , id);
    }

    @Override
    public int delete(int id) {
        return namedJdbcOperations.getJdbcOperations().update(DELETE_CERTIFICATE, id);
    }

    @Override
    public List<Certificate> retrieveByQuery(String sql,Class<Certificate> elemType, Object... params) {
        return namedJdbcOperations.getJdbcOperations().queryForList(sql, elemType, params);
    }

    @Override
    public int create(Certificate certificate) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate())
                .addValue("last_update_date", certificate.getLastUpdateDate());
        int affectedRows = namedJdbcOperations.update(CREATE_CERTIFICATE, params, holder);
        if(affectedRows > 0){
            if(holder.getKey() == null){
                return 0;
            }else {
                return holder.getKey().intValue();
            }
        }else {
            return 0;
        }
    }

    @Override
    public Certificate update(String sql, Map<String, Object> paramsMap, int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(paramsMap);
        int affectedRows =  namedJdbcOperations.update(sql, parameterSource);
        if(affectedRows > 0){
            return retrieveOne(id);
        }else {
            throw new UpdateResourceException("An error occurred on server during updating resource");
        }
    }

    @Override
    public List<Certificate> retrieveCertificatesByTagId(int tagId) {
       return namedJdbcOperations
                    .getJdbcOperations()
                    .query(GET_CERTIFICATES_BY_TAG_ID, (rs, rowsNum) -> new Certificate(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("duration"),
                            rs.getString("create_date"),
                            rs.getString("last_update_date")
                    ), tagId);
    }

    @Override
    public Certificate update(Certificate certificate) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", certificate.getName())
                .addValue("id", certificate.getId())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate())
                .addValue("last_update_date", certificate.getLastUpdateDate());
        int affectedRows =  namedJdbcOperations.update(FULL_UPDATE_CERTIFICATE, parameterSource);
        if(affectedRows > 0){
            return retrieveOne(certificate.getId());
        }else {
            throw new NoSuchElementException("Something went wrong on the server");
        }
    }

    @Override
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>(namedJdbcOperations.getJdbcOperations()
                .queryForMap("Select name, description, price, duration from certificate limit 1").keySet());
        columnNames.add("tags");
        return columnNames;
    }
}
