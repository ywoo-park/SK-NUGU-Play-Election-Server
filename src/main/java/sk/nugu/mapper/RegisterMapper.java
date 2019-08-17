package sk.nugu.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegisterMapper {
    @Insert("INSERT INTO sd(sd_name) " +
            "VALUES(#{sdName})")
    @Options(useGeneratedKeys = true, keyProperty = "sdName", keyColumn="sd_name")
    void saveSdName(@Param("sdName") final String sdName);

    @Insert("INSERT INTO sgg(sgg_name) " +
            "VALUES(#{sggName})")
    @Options(useGeneratedKeys = true, keyProperty = "sggName", keyColumn="sgg_name")
    void saveSggName(@Param("sggName") final String sggName);

    @Select("SELECT * FROM sgg")
    @Results(value = {
            @Result(property = "sggName", column = "sgg_name"),
    })
    public String findSggName();

    @Delete("DELETE FROM sgg")
    void deleteTable();
}
