package com.example.demo.RestController;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.ChatLieuDAO;
import com.example.demo.repository.ChatLieuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/chatlieu")
public class ChatLieuRestController {
    @Autowired
    ChatLieuDAO chatLieuDAO;

    @GetMapping()
    public List<ChatLieu> getListChatLieu() {
        return chatLieuDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<ChatLieu> getPageChatLieu(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(chatLieuDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public ChatLieu getChatLieuByMa(@PathVariable("ma") String ma) {
        return chatLieuDAO.findChatLieuByMa(ma);
    }

    @PostMapping()
    public ChatLieu createChatLieu(@RequestBody ChatLieu ChatLieu) {
        return chatLieuDAO.save(ChatLieu);
    }

    @PutMapping("/{ma}")
    public ChatLieu updateChatLieu(@PathVariable("ma") String ma, @RequestBody ChatLieu ChatLieu) {
        return chatLieuDAO.save(ChatLieu);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        chatLieuDAO.deleteChatLieuByMa(ma);
    }
}
