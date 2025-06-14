package com.example.uploadservice.infrastructure;

import com.example.common.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jetbrains.annotations.NotNull;

@Mapper
public interface FileInfoMapper {
    @Insert({
        "INSERT INTO file_info (file_id, file_type, file_url, file_size, hash_sha256, encoding,owner)",
        "VALUES (",
        "#{fileId}, #{fileType}, #{fileUrl}, #{fileSize}, #{hashSha256},",
        "#{encoding},#{owner})"
    })
    public void addFile(FileInfo fileInfo);

    @Select("select file_path from file_info where file_id = #{fileId}")
    public String findFilePathById(String fileId);

    @Select("select file_name from file_info where file_id = #{fileId}")
    String findFileNameById(String fileId);

    @Update("update file_info set task_id = #{taskId} where file_id = #{fileId}")
    void updateFileTaskIdByFileId(String fileId,String taskId);

    @Select("select file_path from file_info where owner = #{owner}")
    String findFilePathByOwner(String owner);

    @Select("select file_id from file_info where owner = #{owner}")
    String findFileIdByOwner(String owner);

    @Select("select file_name from file_info where owner = #{owner}")
    String findFileNameByOwner(String owner);

    @Select("select file_url from file_info where file_id = #{fileId}")
    String findFileByFileId(@NotNull String fileId);
}

