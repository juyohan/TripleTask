#### π‘ μ¬μ©ν Skill & Tool
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white"/> <img src="https://img.shields.io/badge/My SQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Flyway-CC0200?style=flat-square&logo=Flyway&logoColor=white"/> <img src="https://img.shields.io/badge/IntelliJ-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"/>
<br>
### πΎ DB Schema
- - -
<img width="100%" alt="μ€ν¬λ¦°μ· 2022-06-21 15 20 18" src="https://user-images.githubusercontent.com/20573091/174729897-1ff4de16-fe4c-4ab3-9fe2-0542993560ff.png">

### π Layer
- - -
- Controller Layer
- Business Service Layer
- Common Service Layer
- Data Access Layer
- RDBMS

### π μ€ν λ°©λ²
- - -
##### DB

``` mysql
mysql -u root -p // mysql root κ³μ μ μ μ

CREATE USER 'triple'@'localhost' identified by 'triple'; // μ¬μ©μ κ³μ μ μ΄λ¦ : trple, λΉλ°λ²νΈ : triple λ‘ μμ±
CREATE DATABASE triple; // triple μ΄λ¦μ κ°μ§ λ°μ΄ν° λ² μ΄μ€ μμ±
GRANT ALL PRIVILEGES ON 'triple'.'*' to 'triple'@'localhost' // μ§μ  μ¬μ©μμκ² μ§μ  λ°μ΄ν°λ² μ΄μ€μ κΆνμ λΆμ¬
```
##### Build & λ°°ν¬
```
./gradlew build -x test // νλ‘μ νΈλ₯Ό build νλλ°, test λ μ μΈνκ³  build
java -jar build/libs/{jar νμΌλͺ} // λΉλν νλ‘μ νΈ μ€ν - νμΌλͺ μ) triple-0.0.1-SNAPSHOT.jar
```
