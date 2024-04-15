## 사용 라이브러리
- data-jpa : spring-data-jpa로 손쉽게 datsource에 접근 하기 위함
- thymeleaf : 
- bootstrap
- devtools : 재시작 없이 하려고
- mysql : 데이터베이스
- lombok : @Getter, @Setter 등 간편하게 하려고
- mockito-core : unit test를 위해 mock 객체 생성용
## 기능 구현 목록
### Article
- [x] id, title, content, createdAt, updatedAt, writer
  - [x] comments
- [x] 작성하기(Create)
  - [x] writer의 값으로 User의 nickname을 넣는다.
- [x] 개별 조회하기(retrieve)
- [x] 목록 조회하기
- [x] 수정하기(update)
  - [x] 작성한 User만 수정이 가능하게 한다.
  - [x] 이를 위해선 사용자 nickname(writer)가 중복이 안 되게 해야한다.
    - [x] 이를 위해 nicknme 기반으로 email 찾기 기능 추가
- [x] 삭제하기(delete)
  - [x] 작성한 User만 삭제가 가능하게 한다.
- [x] comments 가져오기
### Comment
- [x] id, content, createdAt, updatedAt, article(owner)
- [x] 작성하기(create)
  - [] writer의 값으로 User의 nickname을 넣는다.
- [] 개별 조회하기(retrieve)
- [x] 삭제하기(delete)
  - [] 작성한 사용자만 수정가능하게 한다.
  - [] article과 마찬가지로 nickname 기반으로 email 찾기 기능을 이용해서
  - [] 현재 로그인된 email과 게시글을 작성한 사용자의 email을 비교한다.
- [x] 수정하기(update)
  - [] 삭제하기와 마찬가지로 작성한 사용자만 수정가능 하게 한다.
### User
- [x] id, nickname, email, password, name
- [x] 등록하기(register)
- [] 수정하기(update)
  - nickname 수정, password수정, email 수정
- [] 탈퇴하기(delete)