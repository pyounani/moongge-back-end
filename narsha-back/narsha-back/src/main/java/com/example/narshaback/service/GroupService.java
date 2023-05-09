package com.example.narshaback.service;

import com.example.narshaback.dto.CreateGroupDTO;
import org.springframework.stereotype.Service;

public interface GroupService {
    Integer createGroup(CreateGroupDTO createGroupDTO);
}
