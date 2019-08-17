package sk.nugu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

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
}
