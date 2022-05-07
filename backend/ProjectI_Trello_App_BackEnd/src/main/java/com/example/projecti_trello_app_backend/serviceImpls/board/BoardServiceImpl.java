package com.example.projecti_trello_app_backend.serviceImpls.board;

import com.example.projecti_trello_app_backend.dto.BoardDTO;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.repositories.board.BoardRepo;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepo boardRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Board> findAllByUser(int userId) {
        try {
            return boardRepo.findAllByUser(userId);
        } catch (Exception exp)
        {
            log.error("Find board by board's manager id error",exp);
            exp.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Optional<Board> findByBoardId(int boardId) {
        try {
            return boardRepo.findByBoardId(boardId);
        } catch (Exception exp)
        {
            log.error("Find board by board's id error", exp);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Board> addBoard(Board board) {
        try {
            board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(boardRepo.save(board));
        } catch (Exception exp)
        {
            log.error("add Board error", exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Board> update(BoardDTO boardDTO) {
        try {
            if(!boardRepo.findByBoardId(boardDTO.getBoardId()).isPresent())
            {
                log.warn("Board is not existed");
                return Optional.empty();
            }
            Board boardToUpdate = boardRepo.findByBoardId(boardDTO.getBoardId()).get();
            boardToUpdate.setBoardTitle(boardDTO.getBoardTitle()!=null?boardDTO.getBoardTitle():boardToUpdate.getBoardTitle());
            if(userRepo.findByUserId(boardDTO.getBoardManagerId()).isPresent() && boardDTO.getBoardManagerId()!=0)
                boardToUpdate.setBoardManager(userRepo.findByUserId(boardDTO.getBoardManagerId()).get());
            boardToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(boardRepo.save(boardToUpdate));
        } catch (Exception exp)
        {
            log.error(" update Board error", exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int boardId) {
        return false;
    }
}
