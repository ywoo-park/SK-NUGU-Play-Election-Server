package sk.nugu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegiMapper {
    @Insert("INSERT INTO transaction(registration) " +
            "VALUES(#{sdName}")
    @Options(useGeneratedKeys = true, keyProperty = "bookReq.id", keyColumn="id")
    void saveSdName(@Param("sdName") final String sdName);
}
