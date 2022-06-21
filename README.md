#### 💡 사용한 Skill & Tool
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white"/> <img src="https://img.shields.io/badge/My SQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/IntelliJ-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"/>
<br>
### 💾 DB Schema
- - -
<img width="100%" alt="스크린샷 2022-06-21 12 17 23" src="https://user-images.githubusercontent.com/20573091/174709051-ec56ffa0-217c-4bd5-87c4-336fb3af56d4.png">

### 🚃 Layer
- - -
- Controller Layer
- Business Service Layer
- Common Service Layer
- Data Access Layer
- RDBMS

### 🚀 실행 방법
- - -
##### DB
``` mysql
mysql -u root -p // mysql root 계정에 접속

CREATE USER 'triple'@'localhost' identified by 'triple'; // 사용자 계정을 이름 : trple, 비밀번호 : triple 로 생성
CREATE DATABASE triple; // triple 이름을 가진 데이터 베이스 생성
GRANT ALL PRIVILEGES ON 'triple'.'*' to 'triple'@'localhost' // 지정 사용자에게 지정 데이터베이스의 권한을 부여
```
##### Build & 배포
```
./gradlew build -x test // 프로젝트를 build 하는데, test 는 제외하고 build
java -jar build/libs/{jar 파일명} // 빌드한 프로젝트 실행 - 파일명 예) triple-0.0.1-SNAPSHOT.jar
```
