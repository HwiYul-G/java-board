## 사용 라이브러리
- data-jpa : spring-data-jpa로 손쉽게 datsource에 접근 하기 위함
- mysql-connector-j : production 환경을 위한 mysql 연결
- h2 : development 환경을 위한 인메모리 DB
- thymeleaf-extra-springsecurity-6 : 타임리프에서 security 사용을 위한것 sec:로 시작하는 구문
- thymeleaf : 
- bootstrap
- devtools : 재시작 없이 하려고
- azure-starter-keyvault-secret : azure에서 keyvault로 private를 두기 위함
- azure-cloud-azure-starter-storage-blob : 이미지 저장을 위한 blob stroage 이용을 위함
- commons-lang3 : 이미지 저장시 StringUtils를 사용하기 위함.
- lombok : @Getter, @Setter 등 간편하게 하려고
- mockito-core : unit test를 위해 mock 객체 생성용
## 기능 구현 목록
### Article
- [x] id, title, content, createdAt, updatedAt, writer
  - [x] comments
  - [x] createdAt, updateAt같은 DateTime 가 TimeZone sync가 맞지 않는 문제 해결 (docker에서 timezone을 설정함)
- [x] 작성하기(Create)
  - [x] writer의 값으로 User의 nickname을 넣는다.
- [x] 개별 조회하기(retrieve)
- [x] 목록 조회하기
  - [x] 페이징 적용 
- [x] 수정하기(update)
  - [x] 작성한 User만 수정이 가능하게 한다.
  - [x] 이를 위해선 사용자 nickname(writer)가 중복이 안 되게 해야한다.
    - [x] 이를 위해 nicknme 기반으로 email 찾기 기능 추가
- [x] 삭제하기(delete)
  - [x] 작성한 User만 삭제가 가능하게 한다.
  - [x] 관련된 comment를 먼저 지우고 현재 게시글이 지워지게 한다.
- [x] comments 가져오기
### Comment
- [x] id, content, createdAt, updatedAt, article(owner
  - [x] createdAt, updateAt같은 DateTime 가 TimeZone sync가 맞지 않는 문제 해결 (docker에서 timezone을 설정함)
- [x] 작성하기(create)
  - [x] writer의 값으로 User의 nickname을 넣는다.
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
  - 등록할 때와 로그인할 때 unsafe password warning이 뜨는 문제가 있었다.
  - 서버에서 프론트로 비밀번호를 보내는 문제가 있다는 이야기를 들어서 코드를 찾아봤다.
  - 코드상에 문제가 없다는 것을 확인하고 나서, 테스트할 때 사용한 비밀번호가 1234여서 추측하기 쉬운 비밀번호여서 그랬다는 것을 깨달았다.
  - 비밀번호 규칙을 5글자 이상 이 중 한 글자는 영어를 포함하도록 정했다.
  - 기존에 UserDto에서 이메일 검증을 하고 있었기 때문에 이 곳으로 비밀번호 검증을 배치하고 Exception을 throw 시켰다.
  - UserController에 userDto가 파라미터로 들어오기 때문에 함수 내부에 있는 try-catch에서 잡히지 않는 것을 발견했다.
  - UserValidator를 별도로 만들어서 service 내에서 검증을 하도록 만들었다.
  - catch에서 잡아서 보여줄 수 있게 되었다.
- [] 수정하기(update)
  - [x] password수정
    - 데이터베이스에 저장된 그 비밀번호와 비교할 수 있어야 한다.
    - 올바른 비밀번호를 입력해도 비교가 제대로 안 되는 문제가 있었다.
    - 이는 비밀번호를 encode해서 입력될 때 같은 값이여도 다르게 되는 문제 때문이다. (random salt사용)
    - 정확히 authentication context의 것을 가져와서 수행한다.
  - [] email 수정
    - 세션에 등록된 email도 변경 되게 해야 한다.
  - [x] 이미지 수정
    - 이미지를 저장할 때 DB에는 경로를 표시하고, 그 경로에서 Image 파일을 지우고 추가하는 행위로 파일에 저장했다.
    - 이 경로가 jar 파일 내부로 고정되어 있기 때문에 Azure에선 문제가 발생했다.
    - 이를 해결하기 위해, 데이터베이스를 인메모리 DB인 H2로 변경해서 H2에 byte[]로 이미지를 저장하게 했다.
    - 추후 Azure에 있는 다른 데이터베이스를 이용하고 파일스토리지를 이용하면서 다시 수정해야할 것 같다.
  - [x] nickname 수정
    - 닉네임 수정시 현재 사용자가 만든 article과 작성한 comment에 대한 닉네임을 모두 변경
    - 세션에 등록된 닉네임도 변경
- [x] 탈퇴하기(delete)
  - [x] 사용자의 저장된 프로필 이미지를 삭제한다.
  - [x] 사용자의 댓글을 전부 삭제 한다.
  - [x] 사용자가 작성했던 게시글을 전부 삭제한다.
- [x] 내가 쓴 게시글 목록 보기
  - [x] 페이징 처리
  - [x] 게시글의 다른 페이지를 클랙해도 기존 댓글 페이지는 유지하기
  - [x] 게시글 삭제 후, 개인정보 페이지에 있게 하기
  - [x] 게시글 보기에서 뒤로가기 버튼 클릭시, 개인정보 페이지에 있게 하기
  - [x] 사용자 정보에서 게시글 보기를 들어가서 아래 행위 후, 뒤로가기 버튼을 누르면 개인정보가 아니라 게시판 있던 부분으로 가지는 문제
    - [x] 새 댓글 작성
    - [x] 댓글 수정
    - [x] 댓글 삭제
    - [x] 게시글 수정
    - [x] 게시글 삭제
- [x] 내가 쓴 댓글 보기, paging 처리
  - [x] 댓글의 다른 페이지를 클릭해도 기존 게시글 페이지는 유지하기

## Collation
#### Collation이란?
collation은 결과들이 정렬, 순서 되는 방식을 결정한다.<br>
MySQL에서 collation은 아래와같이 분리된 collation set을 가진다.
- DB 수준
- table 수준
- column 수준
하나의 컬럼 안의 정보는 부정확하게 인코딩될 수 있다. 이는 컬럼 데이터가 잘못되어 보이는 문제가 있다.

#### Checking the collation and character set
1. DB Collation 확인
  ```MySQL
  use board;
  SELECT @@character_set_database, @@collation_database;
  ```
  - utf8mb4와 utf8mb4_0900_ai_ci가 나왔다.
2. TABLE COLLATION 확인
  ```MySQL
  SELECT TABLE_SCHEMA
       , TABLE_NAME
       , TABLE_COLLATION
  FROM INFORMATION_SCHEMA.TABLES;
  ```
  board DB의 article, comment, user table은 utf8mb4_0900_ai_ci가 나왔다.
3. COLUMN COLLATION 확인
  ```MySQL
  SELECT TABLE_NAME 
    , COLUMN_NAME 
    , COLLATION_NAME 
  FROM INFORMATION_SCHEMA.COLUMNS;
  ```
 - article table의 id(binging), created_at, updated_at(DATETIME) : null
 - article의 title, content, writer는 utf8mb4_0900_ai_ci
 - comment의 id, created_at, updated_at, article_id 는 null
 - comment의 writer, content는 utf8mb4_0900_ai_ci
 - user의 id, profile_image 는 null
 - user의 email, pw, name, nickname는 utf8mb4_0900_ai_ci
#### 참고자료
[How to Fix the Collation and Character set of a mysql db manually](https://confluence.atlassian.com/kb/how-to-fix-the-collation-and-character-set-of-a-mysql-database-manually-744326173.html)