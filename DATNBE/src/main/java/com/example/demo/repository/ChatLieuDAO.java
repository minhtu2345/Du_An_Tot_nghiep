package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ChatLieuDAO extends JpaRepository<ChatLieu, UUID> {
    @Query("select p from ChatLieu p where p.ma=?1")
    ChatLieu findChatLieuByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from ChatLieu where ma=?1")
    void deleteChatLieuByMa(String ma);
}
