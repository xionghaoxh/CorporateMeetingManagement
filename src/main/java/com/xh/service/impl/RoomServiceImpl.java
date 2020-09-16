package com.xh.service.impl;

import com.xh.entity.Sys_room;
import com.xh.mapper.RoomMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Sys_room> implements RoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Sys_room> roomListByPage(Page<Sys_room> iPage, String roomCondition) {
        List<Sys_room> roomList = roomMapper.roomListByPage(iPage, roomCondition);
        return roomList;
    }

    @Override
    public boolean checkRoomStatus(String roomId) {
        int status = roomMapper.checkRoomStatus(roomId);
        if (status == 2) {
            return true;
        }
        return false;
    }
}
