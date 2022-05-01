package com.example.projecti_trello_app_backend.serviceImpls.column;

import com.example.projecti_trello_app_backend.dto.ColumnDTO;
import com.example.projecti_trello_app_backend.entities.column.Columns;
import com.example.projecti_trello_app_backend.repositories.column.ColumnRepo;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnRepo columnRepo;

    @Override
    public List<Columns> findAllByBoard(int boardId) {
        try {
            return columnRepo.findAllByBoard(boardId);
        } catch (Exception ex)
        {
            log.error("Find all column in a board error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Columns> findByColumnId(int columnId) {
        try {
            return columnRepo.findByColumnId(columnId);
        } catch (Exception ex)
        {
            log.error("find column by column id error", ex);
            return Optional.empty();
        }

    }

    @Override
    public Optional<Columns> add(Columns column) {
        try {
            column.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(columnRepo.save(column));
        } catch (Exception ex){
            log.error("add column error ",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Columns> update(ColumnDTO columnDTO) {
        try {
            if(!columnRepo.findByColumnId(columnDTO.getColumnId()).isPresent())
            {
                log.warn("Column is not existed");
                return Optional.empty();
            }
            Columns columnToUpdate = columnRepo.findByColumnId(columnDTO.getColumnId()).get();
            columnToUpdate.setColumnTitle(columnDTO.getColumnTitle()!=null? columnDTO.getColumnTitle()
                    :columnToUpdate.getColumnTitle());
            columnToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(columnRepo.save(columnToUpdate));
        } catch (Exception ex)
        {
            log.error("update column error", ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int columnId) {
        try
        {
            // còn phải xử lý xóa task trong column
            return columnRepo.delete(columnId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete column error",ex);
            return false;
        }
    }
}
