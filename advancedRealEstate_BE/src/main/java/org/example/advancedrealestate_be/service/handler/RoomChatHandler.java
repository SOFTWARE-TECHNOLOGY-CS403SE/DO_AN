package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.RoomChatDto;
import org.example.advancedrealestate_be.entity.RoomChat;
import org.example.advancedrealestate_be.exception.AppException;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.example.advancedrealestate_be.mapper.RoomChatMapper;
import org.example.advancedrealestate_be.repository.RoleRepository;
import org.example.advancedrealestate_be.repository.RoomChatRepository;
import org.example.advancedrealestate_be.service.RoomChatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomChatHandler implements RoomChatService {
    RoleRepository roleRepository;
    RoomChatRepository roomChatRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public RoomChatHandler(RoleRepository roleRepository, RoomChatRepository roomChatRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.roomChatRepository = roomChatRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public RoomChatDto create(RoomChatDto roomChatDtoRequest) {
        RoomChat roomChat = new RoomChat();
        roomChat.setName(roomChatDtoRequest.getName());
        roomChat.setDescription(roomChatDtoRequest.getDescription());
        RoomChat roomChatNew = roomChatRepository.save(roomChat);
        return new RoomChatDto(
            roomChatNew.getId(),
            roomChatNew.getName(),
            roomChatNew.getDescription()
        );
    }

    @Override
    public List<RoomChatDto> findAll() {
        List<RoomChat> roomChatList = roomChatRepository.findAll();
        return roomChatList.stream()
            .map(RoomChatMapper::mapToRoomChatDto)
            .collect(Collectors.toList());
    }

    @Override
    public RoomChatDto findById(String id) {
        RoomChat roomChat = roomChatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        return new RoomChatDto(
            roomChat.getId(),
            roomChat.getName(),
            roomChat.getDescription()
        );
    }

    @Override
    public RoomChatDto updateById(String id, RoomChatDto roomChatDtoRequest) {
        RoomChat roomChat = roomChatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        roomChat.setName(roomChatDtoRequest.getName());
        roomChat.setDescription(roomChatDtoRequest.getDescription());
        RoomChat roomChatUpdate = roomChatRepository.save(roomChat);
        return new RoomChatDto(
            roomChatUpdate.getId(),
            roomChatUpdate.getName(),
            roomChatUpdate.getDescription()
        );
    }

    @Override
    public RoomChatDto deleteById(String id) {
        RoomChat roomChat = roomChatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        roomChatRepository.delete(roomChat);
        return new RoomChatDto(
            roomChat.getId(),
            roomChat.getName(),
            roomChat.getDescription()
        );
    }
}
