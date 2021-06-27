# Springboot + Angular 노트앱 만들기.

### 작업내용
- [X] 멀티프로젝트 설정  
- [X] 스프링부트 프로젝트 / 앵귤러 프로젝트 분리  
- [X] 스프링 User Controller 구현  
- [X] swagger 테스트 성공
- [x] 깃헙 프로젝트 생성
- [x] 칸반 작업하기
- [x] 스프링 보안 추가함 -> 필터, 인터셉터, 어드바이스 정리 필요
[여기](https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/)  




---

- [ ] 추가 작업



---

### 주의사항
- 디비 실행  
    - data 폴더 밑에 h2 zip 파일의 압축을 풀고 bin/h2.sh 파일을 실행한다.  
    None Root !!!   
        ```
      $ unzip h2-2019-03-13.zip
      $ cd h2/bin
      $ su ubuntu
      $ ./h2.sh
      or
      $ ./h2.bat
        ```
       generic h2(embedded)로 접속하면 디비를 자동으로 생성가능함  
       그리고 접속은 generic h2(server)로 들어간다
       http://localhost:8082/

- http://localhost:8080/swagger-ui.html
	-  api 명세서 참고



[참조](https://daddyprogrammer.org/post/19/spring-boot2-start-intellij/) 하여 작업중
