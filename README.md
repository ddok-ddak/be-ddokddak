# be-ddokddak
be-ddokddak 저장소입니다.

### 실행 방법
```bash
docker-compose -f ./docker-compose-local.yml up -d
```
- 도커 허브 퍼블릭 레포를 활용합니다. 
  - 도커 허브 로그인은 push 하는 경우 필요합니다. (계정 정보는 노션 확인)
  - docker-compose-local.yml 파일을 적절한 경로에 내려받아서 위 명령어를 수행합니다.
- 로컬의 8080 포트와 3307 포트가 활용 중이라면, 충돌이 발생할 수 있습니다.
- db 클라이언트 서비스(디비버 등)로 도커의 디비 컨테이너에 접속할 때는 localhost:3307 주소 (계정 root / 비번 1234)를 활용할 수 있습니다.
  - db 관련 데이터는 mysqldata 라는 디렉토리를 생성하여 저장되게 됩니다.

### swagger 링크 주소
```
http://localhost:8080/swagger-ui/index.html
```
- 로컬에서 프로젝트를 실행했을 때, 위 주소로 접속하여 api를 간단히 테스트할 수 있습니다.

### rest-docs 링크 주소
```
http://localhost:8080/docs/index.html
```
- 로컬에서 프로젝트를 실행했을 때, 위 주소로 접속하여 rest-docs를 활용해 제작된 api 문서를 확인할 수 있습니다.
- [문서의 html 파일 경로](./src/main/resources/static/docs)
  - 위 디렉토리 이하에서 html 파일을 확인해보실 수도 있습니다.

### 프로젝트 구조
각 도메인별로 구분되어 있습니다.

**category**

서비스에서 유저의 활동을 분류할 수 있는 기능을 개발하는 코드

**common**

프로젝트에서 공통으로 사용하는 코드

**member**

서비스를 가입한 유저 기능을 개발하는 코드