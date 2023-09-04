package com.example.narshaback.service;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.exception.AlarmNotFoundException;
import com.example.narshaback.base.exception.GroupNotFoundException;
import com.example.narshaback.base.exception.UserNotFoundException;
import com.example.narshaback.base.projection.alarm.GetAlarmList;
import com.example.narshaback.entity.*;
import com.example.narshaback.repository.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    private final PostRepository postRepository;
    private final FirebaseMessaging firebaseMessaging;


    @Override
    public List<GetAlarmList> getAlarmList(String userId, String groupCode) {
        // userId로 쓴 게시물 가져오기
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        // 사용자가 쓴 포스트 목록
        List<PostEntity> userPosts = postRepository.findByUser(user.get());

        // 포스트 목록을 이용해서 댓글 알람, 좋아요 알람 불러오기
        List<GetAlarmList> commentAndLikeAlarm = new ArrayList<>();
        for (PostEntity post : userPosts) {
            List<GetAlarmList> alarmsForPost = alarmRepository.findByPostId(post);
            commentAndLikeAlarm.addAll(alarmsForPost);
        }

        // groupCode에 존재하는 공지 가져오기
        Optional<GroupEntity> group = groupRepository.findByGroupCode(groupCode);
        if (group.isEmpty()) {
            throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
        }

        List<GetAlarmList> noticeAlarm = alarmRepository.findByGroupCode(group.get());

         //리스트를 합치기
         //리스트를 합치고 createdAt으로 정렬하여 최대 7개로 제한
        List<GetAlarmList> combinedAlarms = Stream.concat(commentAndLikeAlarm.stream(), noticeAlarm.stream())
                .sorted(Comparator.comparing(GetAlarmList::getCreatedAt).reversed())
                .limit(8)
                .collect(Collectors.toList());

        return combinedAlarms;

    }

    @Override
    public void deleteAlarm(Integer alarmId) {
        Optional<AlarmEntity> alarm = alarmRepository.findById(alarmId);
        if (!alarm.isPresent()) {
            throw new AlarmNotFoundException(ErrorCode.ALARM_NOT_FOUND);
        }

        alarmRepository.delete(alarm.get());
    }




}
