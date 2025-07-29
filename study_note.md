## 26강 회원 애플리케이션 서비스 구현
### 어떤 단위로 기능을 분리할 것인가 하는 기준을 세운것이 provided interface
- 어떤 의도를 가지고 외부에 이 애플리케이션을 구분해 놓았는지 나누는 기준
- 따라서 서비스 빈에 '인터페이스 구현이 하나인데 굳이 인터페이스 만들어야 하냐요?' 라는 말은 헥사고널에서는 하지 말자
- 계층형 아키텍처에서 한 계층이 다른 계층을 사용할 때는 인터페이스를 통해 접근한다

## 27강 회원 애플리케이션 서비스 테스트(1)
### 클린 코드를 하고 싶으면 테스트 코드를 반드시 만들어야 한다
- 헥사고널도 마찬가지이다

## 28강 회원 애플리케이션 서비스 테스트(2)

### 테스트 코드에서 의존성 주입하는 다양한 방법

- @Autowired 사용
- 생성자로 바로 주입
    1. @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) 어노테이션 사용
        - 자바의 경우 record 타입 사용 가능 (스프링부트 3.2 이상)
        - 코틀린의 경우 primary constructor로 바로 받으면 됨
    2. spring.test.constructor.autowire.mode=all 설정
        - [junit-platform.properties](src/test/resources/junit-platform.properties)
