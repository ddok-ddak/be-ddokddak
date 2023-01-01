# be-ddokddak
be-ddokddak 저장소입니다.

### 실행 방법
```bash
docker-compose -f ./docker-compose-local.yml up -d --build
```
- 현재 도커 허브 레포 생성 전이므로, 해당 레포지토리 클론한 다음, 위 명령어를 수행합니다.
- 로컬의 8080 포트와 3307 포트가 활용 중이라면, 충돌이 발생할 수 있습니다.
- db 클라이언트 서비스(디비버 등)로 도커의 디비 컨테이너에 접속할 때는 localhost:3307 주소 (계정 root / 비번 1234)를 활용할 수 있습니다.

### swagger 링크 주소
```
http://localhost:8080/swagger-ui/index.html
```
- 로컬에서 프로젝트를 실행했을 때, 위 주소로 접속하여 api를 간단히 테스트할 수 있습니다.
