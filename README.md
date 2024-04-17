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
  - [x] writer의 값으로 User의 nickname을 넣는다.
- [] 개별 조회하기(retrieve)
- [x] 삭제하기(delete)
  - [x] 작성한 사용자만 수정가능하게 한다.
    - 이를 위해 `customAuthenticationSuccessHandler`와 `CustomLogoutSuccessHandler`를 만들고 `SecurityConfig`의 필터체인에 등록해준다.
    - 사용 하고 자 하는 부분인 ArticleController에 `@SessionAttributes(key)`로 등록한 세션을 접근가능하게 만든다.
    - 필요한 get 함수에서 Model을 통해 view로 보내준다.
    - view에서 작성한 사용자의 nickname 기반으로 게시글의 writer와 현재 로그인한 사용자의 nickname이 일치하지 않으면 삭제, 수정 버튼이 보이지 않게 한다.
- [x] 수정하기(update)
  - [x] 삭제하기와 마찬가지로 작성한 사용자만 수정가능 하게 한다.
### User
- [x] id, nickname, email, password, name
- [x] 등록하기(register)
- [] 수정하기(update)
  - nickname 수정, password수정, email 수정
- [] 탈퇴하기(delete)