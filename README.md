# spring-exam
spring example

### 설명
- project, user, task(이슈) 로 최소한으로 가정했습니다.
- project, user 를 먼저 등록 후 task(이슈) 등록이 가능합니다.
- /projects, /users, /tasks 에서 각각 CRUD 실행이 가능합니다.
- /users, /api/... 를 제외하고는 로그인 설정이 되어 있으므로 로그인시 아이디 user1, 비번 1234 로 접근 가능합니다.
- REST API 와 일반 뷰가 섞여 있는 컨트롤러는 분리해서 작업했습니다.
- resources/import.sql : 테스트용 더미 데이터를 기록해 둔 파일이 존재합니다. 

### TODO
- [O] Javascript, Java, Scala 중 한 언어로 구현
- [O] 업무 도메인 선정(이슈 트래커)
- [O] Web Site(/...), REST API(/api/...) 분리
- [O] 프로젝트 등록(/projects) CRUD 구현
- [O] 이슈 등록(/tasks) CRUD 구현
- [O] 유저 등록(/users) CRUD 구현
- [O] API 테스트 케이스 일부 구현(src/test)
- [O] H2 DB 사용
- [O] 로그인 기능 구현
- [ ] 예외 처리
- [ ] 스테이지별 환경 설정
- [ ] 뷰리졸버로 리팩토링
- [ ] 세션 쿠기 별도 사용


