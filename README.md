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
- [x] 개별 조회하기(retrieve)
- [x] 목록 조회하기
- [x] 수정하기(update)
- [x] 삭제하기(delete)
- [x] comments 가져오기
### Comment
- [x] id, content, createdAt, updatedAt, article(owner)
- [x] 작성하기(create)
- [] 개별 조회하기(retrieve)
- [x] 삭제하기(delete)
- [x] 수정하기(update)
### User
- [x] id, nickname, email, password, name
- [] 등록하기(register)
- [] 수정하기(update)
  - nickname 수정, password수정, email 수정
- [] 탈퇴하기(delete)