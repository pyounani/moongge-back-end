package com.example.narshaback.service;

import com.example.narshaback.base.dto.alarm.FcmAlarmRequestDto;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class FcmAlarmServiceImpl implements FcmAlarmService{

    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public String sendAlarmByToken(FcmAlarmRequestDto fcmAlarmRequestDto) {
        Optional<UserEntity> user = userRepository.findByUserId(fcmAlarmRequestDto.getUserId());
        if(user.isPresent()) {
            if(user.get().getFcmToken() != null) {
                Notification notification = Notification.builder()
                        .setTitle(fcmAlarmRequestDto.getTitle())
                        .setBody(fcmAlarmRequestDto.getBody())
                        .build();

                Message message = Message.builder()
                        .setToken(user.get().getFcmToken())
                        .setNotification(notification)
                        .build();

                try{
                    String messageId = firebaseMessaging.send(message);
                    return messageId;
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }
}
