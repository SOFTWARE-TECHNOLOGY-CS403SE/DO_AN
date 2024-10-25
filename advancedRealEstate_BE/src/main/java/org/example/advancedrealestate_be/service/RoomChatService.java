package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.RoomChatDto;

import java.util.List;

public interface RoomChatService {

    RoomChatDto create(RoomChatDto roomChatDto);
    List<RoomChatDto> findAll();

    RoomChatDto findById(String id);

    RoomChatDto updateById(String id, RoomChatDto roomChatDto);

    RoomChatDto deleteById(String id);
}
