package sk.nugu.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RegisterMapper {

    @Insert("INSERT INTO registration(sgg_name) " +
            "VALUES(#{sggName})")
    @Options(useGeneratedKeys = true, keyProperty = "sggName", keyColumn="sgg_name")
    void saveSggNameInRegistration(@Param("sggName") final String sggName);

    @Select("SELECT * FROM registration ORDER BY id DESC LIMIT 1;")
    @Results(value = {
            @Result(property = "sggName", column = "sgg_name"),
    })
    public String findSggNameOfRegistration();

    @Delete("DELETE FROM registration")
    void deleteTable();
}
