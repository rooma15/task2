package com.epam.esm.web.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.web.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TagRepositoryImpl implements TagRepository {
    private final NamedParameterJdbcOperations namedJdbcOperations;

    private final String GET_ALL_TAGS = "select * from tag";

    private final String GET_TAG_BY_ID = "select * from tag where id=?";

    private final String CREATE_TAG = "insert into tag(id, name) values (null, :name)";

    private final String DELETE_TAG = "delete from tag where id=?";

    private final String GET_TAG_BY_NAME = "select * from tag where name=?";

    private final String GET_TAGS_BY_CERTIFICATE_ID = "select tag.id as id, tag.name as name " +
            "from tag join certificateTags on tag.id=certificateTags.tag_id " +
            "where certificateTags.certificate_id=?";

    @Autowired
    public TagRepositoryImpl(NamedParameterJdbcOperations namedJdbcOperations) {
        this.namedJdbcOperations = namedJdbcOperations;
    }

    @Override
    public List<Tag> retrieveAll() {
        return namedJdbcOperations.getJdbcOperations().query(GET_ALL_TAGS, (rs, rowNum) -> new Tag(
                rs.getInt("id"),
                rs.getString("name")
                )
        );
    }

    @Override
    public List<Tag> retrieveByQuery(String sql, Class<Tag> elemType, Object... params) {
        return namedJdbcOperations.getJdbcOperations().queryForList(sql, elemType, params);
    }

    @Override
    public Tag retrieveByName(String name) {
        return namedJdbcOperations.getJdbcOperations().queryForObject(GET_TAG_BY_NAME, (rs, rowNum) -> new Tag(
                rs.getInt("id"),
                rs.getString("name")) , name);
    }

    @Override
    public Tag retrieveOne(int id) {
        return namedJdbcOperations.getJdbcOperations().queryForObject(GET_TAG_BY_ID, (rs, rowNum) -> new Tag(
                rs.getInt("id"),
                rs.getString("name")) , id);
    }

    @Override
    public int create(Tag tag) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", tag.getName());
        int affectedRows = namedJdbcOperations.update(CREATE_TAG, params, holder);
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
    public int delete(int id) {
        return namedJdbcOperations.getJdbcOperations().update(DELETE_TAG, id);
    }

    @Override
    public List<Tag> retrieveTagsByCertificateId(int certId) {
        return namedJdbcOperations.getJdbcOperations()
                .query(GET_TAGS_BY_CERTIFICATE_ID, (rs, rowNum) -> new Tag(
                      rs.getInt("id"),
                      rs.getString("name")
                ), certId);
    }
}
