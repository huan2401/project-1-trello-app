package com.example.projecti_trello_app_backend.services.upload_files;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.board.BoardRepo;
import com.example.projecti_trello_app_backend.repositories.task.TaskRepo;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private BoardRepo boardRepo;


    public Map upload(MultipartFile files,Map fileConfig)
    {
        log.trace("In CloudinaryService : call upload()");
        try {
            File uploadFile = convertMultipartToFile(files);
            Map uploadRes = cloudinaryConfig.uploader().upload(uploadFile,ObjectUtils.emptyMap());
            boolean isDeleted = uploadFile.delete(); // delete created file in local after uploading
            if(isDeleted)
                log.info("The file is deleted after being uploaded");
            return uploadRes;
        } catch (Exception ex){
            log.error("Upload file"+ files.getOriginalFilename() +"error",ex);
           return ObjectUtils.emptyMap();
        }
    }

    /*
     * Upload user's avatar images
     * */
    public Map uploadAvatar(MultipartFile file, int userId)
    {
        log.trace("In CloudinaryService : call uploadAvatar");
        try {
            User user = userRepo.findByUserId(userId).get();
            Map uploadRes = upload(file,ObjectUtils.asMap("resource_type","image"));
            user.setAvatarUrl(uploadRes.get("secure_url").toString());
            userRepo.save(user);
            return uploadRes;
        } catch (Exception exception)
        {
            log.error("Upload Avatar error :",exception);
            return ObjectUtils.emptyMap();
        }
    }

    /*
     * Upload background image for tasks
     * */
    public Map uploadTaskBackground(MultipartFile files, int taskId)
    {
        log.trace("In CloudinaryService : call uploadTaskBackground");
        try {
            Task task = taskRepo.findByTaskId(taskId).get();
            Map uploadResult = upload(files,ObjectUtils.asMap("resource_type","image"));
            task.setTaskBackgroundUrl(uploadResult.get("secure_url").toString());
            if(taskRepo.save(task)==null) return ObjectUtils.emptyMap();
            return uploadResult;
        } catch (Exception ex)
        {
            log.error("Upload Task's Background error",ex);
            return ObjectUtils.emptyMap();
        }
    }

    /*
    * Upload background image for boards
    * */
    public Map uploadBoardBackground(MultipartFile files, int boardId)
    {
        log.trace("In CloudinaryService : call uploadBoardBackground");
        try{
            Board board = boardRepo.findByBoardId(boardId).get();
            Map uploadRes = upload(files,ObjectUtils.asMap("resource_type","image"));
            board.setBoardBackground(uploadRes.get("secure_url").toString());
            if(boardRepo.save(board)==null) return ObjectUtils.emptyMap();
            return uploadRes;
        } catch (Exception ex)
        {
            log.error("Upload Board's background error",ex);
            return ObjectUtils.emptyMap();
        }
    }

    public File convertMultipartToFile(MultipartFile file) throws IOException
    {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }




}
