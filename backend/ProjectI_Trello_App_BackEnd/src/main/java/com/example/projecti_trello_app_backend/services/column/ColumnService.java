package com.example.projecti_trello_app_backend.services.column;

import com.example.projecti_trello_app_backend.dto.ColumnDTO;
import com.example.projecti_trello_app_backend.entities.column.Columns;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ColumnService {

    List<Columns> findAllByBoard(int boardId);

    Optional<Columns> findByColumnId(int columnId);

    Optional<Columns> add(Columns column);

    Optional<Columns> update(ColumnDTO columnDTO);

    boolean delete(int columnId);

}
