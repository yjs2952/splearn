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

### 통합 테스트 시 주의할 점

- 테스트 코드 간 영향을 주면 안된다
    - 이전 테스트 코드에서 생성한 엔티티가 다음 테스트 결과에 영향을 주는 경우
    - @Transactional 어노테이션을 사용하여 트랜잭션을 롤백 처리할 수 있음

## 29강 표준 유효성 검사 도구를 이용한 요청 데이터 검증

### 어떤 계층에서 검증을 해야하는가

- 이전 계층에서 검증을 완료했다고 신뢰할 수 있는가
- 애플리케이션 레이어가 중요한 방어막이 되어야 한다
- JSR-303 유효성 검사 도구를 사용하여 요청 데이터 검증
    - spring-boot-starter-validation 의존성 추가
    - @Validated 를 사용하여 파라미터에 설정된 정보를 참고하여 유효성 검증을 먼저 수하는 코드를 자동 삽입
        - 유효성 검증을 수행하려는 대상에 @Valid 어노테이션을 사용해야함
        - 코틀린에서는 @field: 를 붙여야 validation 이 동작함
            - 코틀린은 primary 생성자 필드에 어노테이션을 붙이면 기본적으로 생성자 파라미터에 어노테이션이 붙음
            - spring-validation 은 생성자가 아닌 필드에 어노테이션을 붙여야 적용됨

## 30강 회원 애플리케이션 기능 추가

### spring data jpa 를 사용할 때는 업데이트 할 때에도 ~repository.save() 메서드를 사용한다

- jpa 의 경우 persist(), merge() 밖에 없기 때문에 save 를 할 수도 할 필요도 없지만
  우리는 spring data jpa 를 사용하기 때문에 spring data 의 방식에 따라 save() 를 사용한다
    - 공식 문제에서도 이렇게 가이드 한다
- 이유는?
    - spring data 는 JPA 만을 위해 만들어진 것이 아니다
        - 다양한 기술의 저장 기술들을 추상화하여 공통된 기능을 모아놓은 repository 를 제공하기 위해 만들어졌다
            - 그래서 spring data jpa 를 사용하더라도 save() 를 사용한다
    - spring data 가 제공하는 기능중에 domain event publication 이 있는데 이 기능을 사용하기 위해서는 save()를 해야한다
    - audit 할 때도 필요함

### 도메인 모델의 핵심 로직은 도메인 모델에 정의하자

- 도메인 모델의 핵심 로직은 도메인 모델에 정의하고 애플리케이션 서비스는 도메인 모델을 조합하는 역할로 한정한다
    - 도메인 모델의 핵심 로직을 어플리케이션 서비스에 노출시키는 것은 지양하자
