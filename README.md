![App service introduction](https://github.com/DS-Narsha/.github/assets/79989242/ff66c3fb-e202-4f91-90be-422c79bafcff)
## 뭉게뭉게, 뭉치자 투게더!


<div align="center">
	

`#저학년 아동 SNS 교육` `#SNS 플랫폼` <br /> <br />
SNS를 처음 접하는 저학년 아동에게 개인정보 보호의 중요성과 비속어 사용의 부적절함을 인지하도록 교육하여 <br /> **건강한 SNS 사용을 위해 도움을 주는 서비스**, 뭉게뭉게입니다.
</div>

### Introduction
> 덕성여자대학교 컴퓨터공학전공 개발 소모임 Corner 2023년 제2회 코너 팀 프로젝트 <br />
> 2023 ICT멘토링 한이음 공모전 출품작 <br />
> 개발 기간: 2023.02 ~

### Link
> Figma: [Go to Figma Project](https://www.figma.com/file/2bH6wDOg67oEG9LybAwNWi/%ED%95%99%EC%83%9D-%EA%B5%90%EC%9C%A1%EC%9A%A9-SNS-UI?type=design&node-id=0:1&mode=design&t=XOyzlJ3pwKmbQI0p-1) <br />
> API Docs: [Go to API Docs](https://otcrotcr.notion.site/API-708b9b8ca2094aedbdc7b797c2c0e4c6?pvs=4) <br />
> ERDCloud: [Go to ERDCloud](https://www.erdcloud.com/d/ctqNWzN7xrpYqEhSs) <br />
> Project Schedule: [Go to Project Schedule](https://docs.google.com/spreadsheets/d/1CPdutJU0A24J4jl9XgP4OY5n4HvCjVwLwzLinrtzVm0/edit?usp=sharing) 


### Repository
> Client: https://github.com/DS-Narsha/front-end <br />
> Spring boot server: https://github.com/DS-Narsha/back-end <br/>
> Flask server: https://github.com/DS-Narsha/AI-server <br />

## Table of contents
- [We are Team, Narsha!](#we-are-team-narsha)
- [Preview](#preview)
- [Features](#features)
- [Tech stack](#tech-stack)
- [Installation](#installation)
	- [Setup](#setup)
	- [Client](#client)
	- [Project Structure](#project-structure)

## We are Team, Narsha!
<img src="https://github.com/DS-Narsha/.github/assets/79989242/5906da7e-4e7e-4329-a642-9c12d2b5e0b8" width="200px" alt="team logo"> <br />
팀 나르샤는 "목표를 향해 날아오르다."는 팀의 의지를 담고 있습니다.

| <img src="https://avatars.githubusercontent.com/u/79989242?s=96&v=4" width=90px alt="유수연"/>  | <img src="https://avatars.githubusercontent.com/u/111184839?v=4" width=90px alt="박유나"/>  | <img src="https://avatars.githubusercontent.com/u/123048615?v=4" width=90px alt="서지혜"/>  | <img src="https://avatars.githubusercontent.com/u/90364541?v=4" width=90px alt="오지"/>  |
| :-----: | :-----: | :-----: | :-----: |
| 유수연(팀장) | 박유나(팀원)  | 서지혜(팀원) | 오지연(팀원) |
| [@otcroz](https://github.com/otcroz) | [@pyounani](https://github.com/pyounani)  | [@Jihye0623](https://github.com/jihye0623) | [@ninano05](https://github.com/ninano05) |
|Client, Server, AI|Client, Server, AI|Client, Server, AI|Client, Server, AI|

## Preview

<div align="center">
	<img src="https://github.com/DS-Narsha/.github/assets/79989242/31f54448-eb16-456b-8cd7-9cb50a049b53" />
</div>

## Features
- 학급 단위 유저 관리
  - 선생님 1명, 학생 n명으로 이루어진 그룹 단위
  - 선생님은 계정 생성 후, 그룹을 생성한다.
  - 학생은 계정 생성 후, 선생님께 받은 그룹 코드로 그룹을 가입한다.
 
- 사용자 간 상호작용을 위한 SNS 기능
  - 게시글 포스팅, 댓글 작성, 좋아요 등록 등 사용자 간 상호작용을 위한 기능을 제공한다.
  - 업적/뱃지 기능을 제공하여 서비스 기능을 익히고 SNS 상호작용을 유도한다.

- 포스팅할 사진에 개인정보가 포함된 객체 여부 검사
  - 사용자가 선택한 사진에 대해 개인정보가 포함된 객체 여부를 검사한다.
  - 해당 객체가 포함되었을 경우 모자이크 처리 후 처리 목록과 함께 제공한다.
  - 모델은 NTIS의 개인정보 영향도 등급에 따라 주민등록번호, 여권, 운전면허증, 신용카드를 탐지하도록 학습했다.

- 작성한 글의 내용에 비속어 및 개인정보 포함 여부 검사
  - 사용자가 작성한 글(게시글, 댓글)에 대해 비속어 및 개인정보 포함 여부를 검사한다.
  - 비속어가 포함되었을 경우 단어를 수정할 것을 제안함과 동시에 대체할 순화어를 제공한다.
  - 비속어가 포함되었지만 모델이 특정 단어를 탐지하지 못할 경우 전체 문장을 수정할 것을 제안한다.
  - 개인정보가 포함되었을 경우 삭제할 것을 제안한다.

- 뭉게뭉게의 마스코트, AI봇 둥실이
  - 서로 다른 성격의 4가지 AI봇을 제작하여 사용자에게 다채로운 댓글과 상호작용을 제공한다.
  - 사용자가 게시글을 작성하고 업로드하면 AI봇 둥실이가 댓글을 달아준다.
  - 댓글은 4가지 AI봇 중 랜덤으로 제공된다.
  - AI봇은 댓글을 생성할 때 글의 내용, 이미지, 글의 내용+이미지 총 3가지의 로직 중 임의의 로직을 적용한다.

- 앱 사용시간 관리
  - 선생님은 앱 내에서 학생들의 앱 사용 시간을 설정한다.
  - 학생은 선생님이 설정한 앱 사용시간 범위 내에서 서비스를 사용할 수 있다.

## Tech stack

<img src="https://github.com/DS-Narsha/.github/assets/79989242/8f9083b1-abcf-4f2f-9b7f-464ca4e29a93" width="700px" /> <br />


> Client: <img src="https://img.shields.io/badge/React Native-61DAFB?style=flat-square&logo=React&logoColor=black"/> <img src="https://img.shields.io/badge/React Query-FF4154?style=flat-square&logo=reactquery&logoColor=white"/> <br />
> Server: <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=black"/> <img src="https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=mysql&logoColor=black"/> <br />
> AI-Server: <img src="https://img.shields.io/badge/Flask-000000?style=flat-square&logo=flask&logoColor=white"/> <img src="https://img.shields.io/badge/yolo-00ffff?style=flat-square&logo=yolo&logoColor=white"/> <img src="https://img.shields.io/badge/opencv-5C3EE8?style=flat-square&logo=opencv&logoColor=white"/> <br />
> Deploy: <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/> <br />

## Installation

### Setup
For building and running the application you need:
- Node.js 16.17.0
- Python 3.10.0
- Npm 8.15.0

### Client
```
$ git clone https://github.com/DS-Narsha/front-end.git
$ cd narsha_front
$ nvm install 16.17.0
$ npm i
$ npm start:dev
```

## Project Structure
```
narshaback
├─ base
│  ├─ code
│  │  ├─ ErrorCode.java
│  │  └─ ResponseCode.java
│  ├─ config
│  │  ├─ AmazonS3Config.java
│  │  └─ FirebaseConfig.java
│  ├─ dto
│  │  ├─ alarm
│  │  │  ├─ FcmAlarmRequestDto.java
│  │  │  └─ FcmTokenRequestDTO.java
│  │  ├─ comment
│  │  │  └─ CreateCommentDTO.java
│  │  ├─ group
│  │  │  ├─ CreateGroupDTO.java
│  │  │  ├─ JoinGroupDTO.java
│  │  │  └─ UpdateTimeDTO.java
│  │  ├─ like
│  │  │  └─ CreateLikeDTO.java
│  │  ├─ notice
│  │  │  └─ CreateNoticeDTO.java
│  │  ├─ post
│  │  │  └─ UploadPostDTO.java
│  │  ├─ response
│  │  │  ├─ ErrorResponseDTO.java
│  │  │  └─ ResponseDTO.java
│  │  ├─ s3
│  │  │  ├─ S3FileDTO.java
│  │  │  ├─ S3PathDTO.java
│  │  │  └─ S3urlDTO.java
│  │  └─ user
│  │     ├─ UpdateUserProfileDTO.java
│  │     ├─ UserLoginDTO.java
│  │     └─ UserRegisterDTO.java
│  ├─ exception
│  │  ├─ AlarmNotFoundException.java
│  │  ├─ DeleteFailedEntityRelatedGroupCodeException.java
│  │  ├─ EmptyCommentContentException.java
│  │  ├─ GlobalExceptionHandler.java
│  │  ├─ GroupCodeNotFoundException.java
│  │  ├─ GroupNotFoundException.java
│  │  ├─ LikeNotFoundException.java
│  │  ├─ LoginIdNotFoundException.java
│  │  ├─ LoginPasswordNotMatchException.java
│  │  ├─ NoticeNotFoundException.java
│  │  ├─ PostNotFoundException.java
│  │  ├─ ProfileNotFoundException.java
│  │  ├─ RegisterException.java
│  │  ├─ UserIdNotFoundException.java
│  │  └─ UserNotFoundException.java
│  └─ projection
│     ├─ alarm
│     │  └─ GetAlarmList.java
│     ├─ comment
│     │  └─ GetComment.java
│     ├─ group
│     │  └─ GetJoinGroupList.java
│     ├─ like
│     │  └─ GetLikeList.java
│     ├─ notice
│     │  ├─ GetNotice.java
│     │  └─ GetRecentNotice.java
│     ├─ post
│     │  ├─ GetMainPost.java
│     │  ├─ GetOneUserPost.java
│     │  ├─ GetPostDetail.java
│     │  └─ GetUserPost.java
│     └─ user
│        ├─ GetFriendsList.java
│        ├─ GetUser.java
│        ├─ GetUserInGroup.java
│        └─ GetUserProfile.java
├─ controller
│  ├─ AIController.java
│  ├─ AlarmController.java
│  ├─ CommentController.java
│  ├─ FCMAlarmApiController.java
│  ├─ GroupController.java
│  ├─ LikeController.java
│  ├─ NoticeController.java
│  ├─ PostController.java
│  └─ UserController.java
├─ entity
│  ├─ AlarmEntity.java
│  ├─ CommentEntity.java
│  ├─ GroupEntity.java
│  ├─ LikeEntity.java
│  ├─ NoticeEntity.java
│  ├─ PostEntity.java
│  └─ UserEntity.java
├─ event
│  ├─ AlarmCreatedEvent.java
│  ├─ CommentCreatedEvent.java
│  ├─ LikeCreatedEvent.java
│  └─ NoticeCreatedEvent.java
├─ listener
│  ├─ AlarmEventListener.java
│  ├─ CommentEventListener.java
│  ├─ LikeEventListener.java
│  └─ NoticeEventListener.java
├─ NarshaBackApplication.java
├─ repository
│  ├─ AlarmRepository.java
│  ├─ CommentRepository.java
│  ├─ GroupRepository.java
│  ├─ LikeRepository.java
│  ├─ NoticeRepository.java
│  ├─ PostRepository.java
│  └─ UserRepository.java
└─ service
   ├─ AlarmService.java
   ├─ AlarmServiceImpl.java
   ├─ AmazonS3Service.java
   ├─ AmazonS3ServiceImpl.java
   ├─ CommentService.java
   ├─ CommentServiceImpl.java
   ├─ FcmAlarmService.java
   ├─ FcmAlarmServiceImpl.java
   ├─ GroupService.java
   ├─ GroupServiceImpl.java
   ├─ LikeService.java
   ├─ LikeServiceImpl.java
   ├─ NoticeService.java
   ├─ NoticeServiceImpl.java
   ├─ PostService.java
   ├─ PostServiceImpl.java
   ├─ TextService.java
   ├─ TextServiceImpl.java
   ├─ UserService.java
   └─ UserServiceImpl.java
```
