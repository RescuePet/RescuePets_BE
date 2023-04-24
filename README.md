# 구해줘! 펫츠 🐶

<img width='750px' src=https://user-images.githubusercontent.com/95469708/233132774-e2e4f806-bcba-4db8-a189-a5ff977eb94b.png>

## 🐶 구해줘! 펫츠 소개 

저희 팀은 수익적인 방향보다 공공이익적인 아이디어를 활용하고 싶어서 유기견과 관련된 프로젝트를 시작하기로 하였습니다. 현재 소규모라서 사용자 참여로 가치를 증대 하고 싶었고, 이때 1인 가구 증가 및 코로나 19로 근 5년간 반려동물 양육 가구수가 증가했다는 소식을 접했습니다. 하지만 코로나 19가 종료되면서 유기 동물이 증가되는 현상 또한 발생했습니다.

저희 팀은 사용자가 참여해서 유기 동물을 구하고, 자신의 반려동물을 잃어버렸다면 실종 신고를 할 수 있고, 반려동물을 구매하는 것 보다 입양을 권장하는 입장에서 프로젝트를 기획하게 되었습니다.

<br>





## 📍 바로가기
- [🐶 구해줘! 펫츠 바로가기](https://www.rescuepets.co.kr/)
- [프로젝트 발표 영상](https://www.youtube.com/watch?v=excz-lSRTsk)

### 깃허브 레포

> FE :
[구해줘! 펫츠 FE 깃허브 바로가기](https://github.com/RescuePet/RescuePets_FE)


> BE : 
[구해줘! 펫츠 BE 깃허브 바로가기](https://github.com/RescuePet/RescuePets_BE)

### 노션

>프로젝트 노션 :
[프로젝트 노션 바로가기](https://www.notion.so/RescuePets-A-2-b35947780b5143b4a6ea6836a513b870)


>원페이지 노션 :
[원페이지 노션 바로가기](https://www.notion.so/b553ab28d3f744a383fd36663197fef1)






## 👨‍👩‍👧‍👦 Team
|이현동|음지훈|유영우|김관희|박성현|오영석|김정임|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|[@hdlee0619](https://github.com/hdlee0619)|[@JiHun-yam](https://github.com/JiHun-yam)|[@HEUKWU](https://github.com/HEUKWU)|[@PracticingGit](https://github.com/PracticingGit)|[@seonghyun519](https://github.com/seonghyun519)|[@youngseokaa](https://github.com/youngseokaa)|@|
|FE(L)|FE|BE(TL)|BE|BE|BE|DE|

<br>

## 🗓 프로젝트 기간
-  2023년 03월 10일 ~ 4월 21일 (6주)

<br>

## 주요 기능 

### 1. 동물보호관리시스템 유기동물 정보 조회 (open API 사용)

> 사용한 open API 주소 https://www.data.go.kr/data/15098931/openapi.do

- 공공 API를 이용해 현재 유기동물의 정보를 가져와 사용자에게 제공합니다.
- 스케줄러를 활용하여 30분마다 최신정보를 가지고 옵니다. 
- “문의하기 기능”을 이용해 현재 유기 동물이 보호되고 있는 보호소와 연결합니다.
- 스크랩 기능”을 이용해 게시물을 마이페이지에서 다시 확인할 수 있습니다.
- KakaoMap API를 이용해 유기 동물이 구조된 위치, 현재 보호소 위치를 확인할 수 있습니다. 
<details>
<summary>자세하기 더보기</summary>
<img src="https://user-images.githubusercontent.com/95469708/233530296-1101dd9a-b69b-403f-abbd-9ee9ca7b72cc.png">
  
</details>


### 2.  KakaoMap API
-   KakaoMap API를 사용해 사용자가 올린 게시물(반려 동물 실종, 유기 동물 의심)의 위치를 확인할 수 있습니다.
-   “Link 기능”을 통해 사용자 게시물들이 같은 동물들로 의심 된다면, 지도 상에서 시각적(거리별, 같다고 의심되는 동물들의 연관성)으로 확인할 수 있습니다.
-   마커를 클릭 시 해당 게시물에 대한 상세 정보와 동물의 이미지를 확인할 수 있습니다.

<details>
<summary>자세하기 더보기</summary>
<img width="600" alt="스크린샷 2023-04-20 16 04 27" src="https://user-images.githubusercontent.com/95469708/233286991-d115c379-dcd2-4e37-98ec-820ad64ac5b8.png">
  
 <img width="600" alt="스크린샷 2023-04-20 16 06 03" src="https://user-images.githubusercontent.com/95469708/233287260-265c9268-2f99-4123-8da0-129194e8b508.png">

  
</details>

### 3.반려 동물 실종 신고 & 유기 동물 의심 신고

-   내 반려 동물이 실종 되었다면 Petwork에 게시물을 올릴 수 있습니다.
-   실종 신고 게시물 작성 이후 “포스터 만들기 기능”을 이용해 이미지 파일로 다운로드 가능합니다.
-   “실종 신고 포스터” 에는 “QR코드”가 있어서 “구해줘! 펫츠”의 실종 신고 게시물로 접속 가능합니다.
-   “채팅 기능”을 이용해 실종 신고 및 유기 동물 의심신고 게시물을 올린 사용자와 채팅이 가능해 소통이 가능하며, “댓글” 기능으로도 소통이 가능합니다.
-   “스크랩 기능”을 이용해 게시물을 스크랩 하고, 마이페이지에서 스크랩한 게시물을 확인 가능합니다.
-   게시물을 삭제 한다고 해서 바로 삭제되지 않습니다. 게시물이 불량이라면 Admin 또는 Manager가 임시 삭제 후에 강제 삭제, 복원이 가능합니다.


### 4.  검색 기능
-   “검색 기능”을 통해서 게시물 검색이 가능합니다.
-   유기 동물 입양 정보는 거리, 종류, 품종, 보호소 별로 검색이 가능하고, 사용자가 올린 게시물에서는 거리, 종류, 품종 별로 검색이 가능합니다.
-   종류, 품종, 보호소는 모두 거리 설정을 추가해서 검색이 가능합니다.


<details>
<summary>자세하기 더보기</summary>
<br>
<img width: "800px" src="https://user-images.githubusercontent.com/95469708/233395943-7cefd2fa-96ab-4d28-a260-99b5d5079fbd.gif"/>
</details>


### 5. 채팅 기능

-   SockJS, Stompjs를 이용해 채팅기능을 구현하였습니다.
-   게시물을 올린 사용자와 채팅이 가능해서 해당 유기 동물에 대한 정보를 교환할 수 있습니다.

<details>
<summary>자세하기 더보기</summary>
<br>
<img width: "800px" src="https://user-images.githubusercontent.com/95469708/233387608-c089a819-6d7b-4edd-9f52-0cf81c714b6d.gif"/>
</details>

### 6.  실시간 알림 기능

-   내가 올린 게시물이 스크랩이 되었거나, 댓글이 달렸다면 “실시간 알림 기능”으로 알려줍니다.
-   채팅이 오면 실시간 알림으로 알려줍니다.

<details>
<summary>자세하기 더보기</summary>
<br>
<img width: "800px" src="https://user-images.githubusercontent.com/95469708/233387608-c089a819-6d7b-4edd-9f52-0cf81c714b6d.gif"/>
</details>

### 7. 불량 게시물 및 사용자 신고 기능

-   “신고 기능”을 이용해 올바르지 않은 게시물 및 댓글, 채팅 기능 상의 사용자에 대한 신고가 가능합니다.
-   불량 게시물은 Admin이 판단 후 완전 삭제가 가능합니다.

### 8. 관리자 기능(유저 등급)

-   신고 내용을 바탕으로 관리자(Admin)가 사용자의 등급(Manager, Member, BadMember)을 결정할 수 있습니다.
-   Manager는 신고 내역을 조회할 수 있고 이를 통해 사용자 등급을 결정할 수 있습니다.
-   BadMember는 주요 기능을 사용할 수 없습니다.

<br>

## 기술적 선택🔨

### FE

<details>
<summary>더보기</summary>

| 요구사항 | 선택 | 선택 이유 및 근거|
| --- | --- | --- |
| CSS | styled-components | - CSS파일을 따로 나누지 않고 한 파일을 통해 관리할 수 있음 Global Style, Mixin 기능을 사용할 수 있음 SVG 파일을 컴포넌트화 해서 색 변경|
| 입력 | react-hook-form | - 라이브러리를 사용해 input 요소 여러개일 때 불필요한 리-렌더링 막기, 정규식을 간편하게 사용가능하며 유효성 검사를 간편하게 할 수 있음 |
| 전역 상태 관리 및 통신 | redux-toolkit, axios | - redux-toolkit 을 사용하여 기존 redux 의 보일러플레이트를 줄이고 기본으로 제공되는 immer를 통해 State의 불변성을 유지할 수 있음, axios 를 통해 interceptors를 설정하여 통신하고, thunk 를 통해 통신과 동시에 State를 관리할 수 있음 |
| 캐러셀 | react-slick, slick-carousel | - 캐러셀 구현의 시간 단축을 위해 간편한 라이브러리 사용, 화살표, 도트를 디자인(위치, 컬러)대로 커스터마이징 가능해 선택 |
| 무한 스크롤 | react-intersection-observer | - html 요소가 화면에 표시 됐음을 간편하게 관찰하기 위해 라이브러리 사용, 라이브러리의 inView 를 통해서 스크롤의 위치를 감지해 해당 html 요소가 화면에 노출시 서버 통신을 통해 리스트를 받아옴 |
| Map | Kakao Map API | - Kakao Map API 사용을 통해 좌표를 주소로 검색, 주소를 좌표로 검색, 게시물에 대한 위치를 마커로 표시 |
| 알림창 | framer-motion | - 사용자에게 에러 메세지나 알림을 모달로 만들고 간편하게 모달을 열고 닫기위해 라이브러리 사용, 애니메이션을 쉽게 만들고 성능을 향상, 사용자에게 보다 나은 UX를 주기 위해 사용 |
| QR코드 | qrcode.react | - 원래는 QR코드로 변경해주는 구글 API를 사용하기로 했었으나 서비스 종료로 인해 qrcode.react 라이브러리를 사용해 원하는 주소를 간편하게 QR코드로 변경 |
| 포스터 저장 | html2canvas, file-saver | - 포스터 기능에서 원하는 html 요소를 간편하게 이미지 파일로 변환, 저장하기 위해 두 라이브러리 사용, html2canvas 라이브러리를 통해 html요소를 이미지로 변환하고, file-saver 라이브러리를 통해 변환한 이미지를 파일로 저장 |
| 채팅 | SockJS, Stompjs | - 백엔드가 Spring이라서 SockJS 를 공식 지원하기 때문에 선택했고, SockJS를 통해 웹소캣이 지원되지 않는 브라우저에서도 실시간 통신 가능하게 처리해 Websocket 연결 , Stompjs 의 사용을 통해 메세지 전송 처리방식을 간편하게 함 |
| 실시간 알림 | SSE, event-source-polyfill | - 채팅 내 게시물 댓글이 달리면 실시간으로 알림을 주기 위해 SSE 기능 도입, event-source-polyfill  는 SSE를 지원하지않는 브라우저 에서도 SSE를 사용할 수 있어서 도입  |
| 암호화  | CryptoJS | - 유저 위치를 암호화하여 사용하기 위해 도입, 유저 위치를 한번만 받고 그값을 가져다가 사용하기 위해 (불필요한 로딩 줄이기 위해서 ) |

</details>

<br>

### BE

<details>
<summary>더보기</summary>

| 요구사항 | 선택 | 선택 이유 및 근거 |
| --- | --- | --- |
| 자동 배포 | Github Action,Code Deploy | 클라우드에서 호스팅되는 서비스로 별도의 서버나 인프라를 구축하지 않고도 바로 사용할 수 있는 github action과 code deploy를 활용해 자동배포 |
| 동물보호관리시스템 Open API 호출 및 데이터 수집 | Scheduler,kakaoMapAPI| 동물보호관리시스템 Open API는 데이터 갱신 주기는 5분이며 해당 데이터에는 좌표 정보가 포함되지 않음KakaoMapAPI를 활용 좌표 정보를 수집하며, 스케줄러를 활용하여 데이터를 수집, 관리함 |
| 무한 스크롤 | Pageable | 스프링 데이터 JPA의 페이징과 정렬기능을 활용한 무한스크롤 |
| 채팅 | WebSocket,SockJs,STOMP | 서버와 클라이언트 간 양방향 통신을 제공하는 WebSocket을 사용하여 통신함 ,WebSocket 위에서 작동하는 프로토콜로 메시지 브로커를 사용하여 메시지를 전달하는데 있어 더욱 효율적으로 구현할 수 있는 STOMP를 사용해 메시징 기능 구현, SockJs를 통해 웹소켓이 지원되지 않는 브라우저에서도 실시간 통신 가능하게 처리 |
| 메일 전송 | JavaMailSender,Thymeleaf | JavaMail api를 이용하여 계정 정지된 회원에게 메일 전송,Thymeleaf를 이용해 메일 html파일 작성 |
| 실시간 알림 | SSE | 양방향 통신이 가능한 웹소켓을 사용해도 되지만 보다 가볍고 기존의 HTTP 연결을 사용할 수 있는 (서버 - 클라이언트) 단방향 통신의 SSE를 사용해 실시간 알림 기능 구현 |
| 에러 및 로그 수집 | sentry | 자동배포 후 에러와 로그 수집이 번거로워져, sentry를 활용해 에러, 로그 수집, 슬랙과 연동하여 오류/이슈 발생 시 즉각적인 알림을 받을 수 있고 사용성이 간편하고 쉬운 Sentry를 채택 |
<br>
</details>

## 🚨 트러블 슈팅  

### FE 트러블 슈팅  
<details>
<summary>자세히 보기</summary>
  <br>
  <details>
<summary> 1️. Websocket 재연결 문제</summary>
  <br>
최초에 웹소켓 연결 후 다시 연결 시 연결이 되지 않는 문제
-   원인: 연결(구독해제)이 잘 끊기지 않아서 재연결 되지 않다고 생각했음.
-   해결: useEffect를 사용해 컴포넌트 최초 마운트 시에만 연결하고 언마운트시에 웹소켓 연결과 구독해제를 하도록 함. 그리고 구독 연결과 구독해제시 id값을 roomId로 지정해 해제함.
</details>

<details>
  <summary> 2️.  포스터 파일 저장 문제</summary>
   <br>
 백엔드 S3주소를 사용하면 CORS에러가 발생해 이미지가 나오지 않는 문제.
-   원인: `html2canvas` 를 사용할 시 S3로 url을 변경하여 사용하면 CORS에러가 발생하는데, 이미지 사용 권한이 없기 때문에 발생
-   해결: 사용자가 게시물을 작성할 때 첫번째 이미지 파일만 `redux`를 이용해 `store`에 저장한 후, poster 페이지로 이동한 뒤 `html2canvas`, `file-saver`를 이용하여 포스터 이미지로 변환해 CORS에러를 해결함.
</details>


<details>
  <summary> 3️. SSE 연결이 되면 45초가 지나면 SSE 연결이 끊기는 문제 </summary>
 <br>
- 원인: `event-source-polyfill`  이 라이브러리를 사용하면서 45초 마다 연결이 끊기는 문제 발생

> 해결 : useEffect를 사용해 연결이 끊기면 다시 연결하는 로직을 연결을 했고
> heartbeatTimeout (SSE 클라이언트가 서버로부터 응답을 받지 못했을 때 연결을 끊는 시간 )
withCredentials  (SSE 요청에 대해 자격 증명(쿠키 및 인증 헤더)) 를 사용하여 SSE 연결을 해서 해결했다
	
수정코드 
```jsx 
useEffect(() => {
      eventSource = new EventSourcePolyfill(
        `${process.env.REACT_APP_SIGN_TEST}/sse/`,
        {
          headers: {
            Authorization: token,
          },
        });

    eventSource.onmessage = (event) => {
	   ...
    };
	
    return () => {
      eventSource.close();
    };
	...
  }, [eventSource]);
```

</details>

<details>
  <summary> 4. 포스터 파일 저장 문제</summary>
   <br>
  1. polyline 그려진 지도를 이미지 형태로 변환하여 서버에 저장하려고 했으나, 카카오 API를 사용하여 발생한 결과물을 서버에 저장하는 것이 카카오 정책에 의해 불가능 (canvas 객체를 선언해서 png 파일로 변환 뒤 서버에 보내려고 했음)
  
  2. 5초마다 위도, 경도를 배열 형태 만들어 종료시 전체 배열을 서버로 보내고, 기록을 확인하는 페이지에서는 이미지 대신 지도를 생성하고 그 위에 다시 값을 받아 그려주는 방식으로 해결
</details>


<details>
  <summary> 5. 현재위치 사용하는 카카오맵을 통해 마커로 표시하는 경우 로딩이 오래걸리는 문제</summary>
 <br>
> 원인 : 로그인 후 Home으로 이동시 유저 동의 시 유저 위치를 받아와
리덕스에 저장 하지만 언마운트시 시 리덕스스토어에 저장된 값이 사라져 다시 위치를 받아오는 현상 발생
> 해결 : CryptoJS 라이브러리를 사용해서 Home페이지에서 유저에 위치좌표로 암호화 후
로컬스토리지에 저장 유저 위치가 필요한 부분 복호화하여 로딩문제를 해결
</details>
<br>
</details>

### BE 트러블 슈팅  
<details>
<summary>자세히 보기</summary>
  <br>
  
  <details>
  <summary> 1. Spring JPSA에서 Pageable을 활용한 Pagination 사용 시 중복 데이터 문제</summary>
 <br>
원인: 오프셋 기반의 페이지네이션으로 중복이 발생하였음
해결: 서브 정렬 기준에 고유 값을 추가하여 중복을 방지
</details>
  
  <details>
<summary> 2. 동일한 클래스내에서 발생한 엔티티간 연관관계 구성시 구조에 대한 고민(1)</summary>
  <br>
최초에 웹소켓 연결 후 다시 연결 시 연결이 되지 않는 문제
-   원인: 연결(구독해제)이 잘 끊기지 않아서 재연결 되지 않다고 생각했음.
-   해결: useEffect를 사용해 컴포넌트 최초 마운트 시에만 연결하고 언마운트시에 웹소켓 연결과 구독해제를 하도록 함. 그리고 구독 연결과 구독해제시 id값을 roomId로 지정해 해제함.
</details>

<details>
  <summary> 3. 동일한 클래스내에서 발생한 엔티티 간 연관관계 구성시 구조에 대한 고민(2)</summary>
   <br>
 원인: Repository에서 유저에 대한 정보를 활용해 엔티티(게시글링크)들을 관리하기 위해 아이디뿐만 아니라 유저정보를 함께 담는 엔티티를 정의해야했음(Member와 Member간의 N:N 관계를 간접적으로 구성)

해결 : URI에서 시작점을 정의하고 Dto 에서 끝점을 정의후
양 게시글에서링크를함께(한 번에 두 개씩) 생성,
(트위터 팔로우 투 팔로우 모델에서 팔로잉 목록 불러오기만 일부 차용해보려 했지만 해당 링크를 작성한 작성자를 함께 보관해야했기 때문에 해당방법을 사용하는 것으로 결정)
</details>


<details>
  <summary> 4. 악의적 트래픽 공격에 대한 대비 </summary>
 <br>
 원인 : 악의적으로 댓글, 게시글을 다수 올리는 방법을 통한 공격에 대한 대비책 마련

해결 : 사용자가 글 작성시 최근에 작성한 N번째 게시물이 M분 지났는지를 기점으로 경고를 띄울지 여부를 결정함. (예) 작성 전, Repository에서 해당 사용자의 5번째 댓글을 검색, 작성 시간 CreatedAt을 현재 시간과 비교하여 5분이 지났으면 댓글 작성 가능, 5분이 채 지나지 않았다면 댓글 작성 불가능, 동시에 서버 전체를 기준으로 서버전체 기준으로는 15번째, 15분 기준으로 추가 적용)
</details>

<details>
  <summary> 5. 메서드 내에서 중복 코드 제거에 대한 문제 해결</summary>
   <br>
원인 : 동일 중복 코드가 자주 발생 하여  근본적으로 중복 코드를 글로벌 하게 관리 하기 위해 AOP 적용
해결 : 어노테이션을 구현 후 Before 메서드로 중복코드를 글로벌 하게 처리하였음
</details>


<details>
  <summary> 6. WebSocket/SSE </summary>
 <br>
 문제 : WebSocket/SSE 연결 불가

원인 : 프록시 서버로 사용하는 Nginx 설정이 http version 1.0으로 되어있음

    WebSocket과 SSE 모두 HTTP/1.1에서 도입된 기술로 1.0 버전에서는 지원하지 않음 (SSE는           

    WebSocket과 달리 일반 적인 HTTP 연결을 사용해 일부 브라우저에서 사용 가능하지만 성능이 

    떨어지고 서버 부하가 증가할 수 있음)

해결 : Nignx 설정을 http version 1.1로 변경
</details>


<details> 
  <summary> 7. SSE 관련 </summary>
 <br>
    
    문제 : 로컬에서 SSE연결시에는 SseEmitter 객체에 설정해 놓은 시간 만큼 연결이 지속됨
    
        하지만 EC2 서버 환경에서 연결시에는 1분후 연결 끊어짐
    
    원인 : Nginx 에서 프록시 서버와의 통신에서 요청을 보내고 응답을 받을 때의 제한 시간을 설정하는 옵            
    
        션인 proxy_read_timeout과 proxy_send_timeout의 기본값이 60초로 설정돼있음
    
    해결 : 타입아웃 값을 1시간으로 변경
</details>
<br>
</details>

<br>

## 유저 Tracking 통계 (Amplitude)
<br>
<details> 
  <summary> 페이지 방문 통계 </summary>
 <br>
    <img  width='800px' src="https://file.notion.so/f/s/f61f370f-0e8d-4d82-b7b9-59aab2b4c117/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-04-18_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_2.32.13.png?id=16efed88-4e93-4ace-8032-03727d12c1f6&table=block&spaceId=0bce464c-a908-4cab-85b1-79089b1ca00e&expirationTimestamp=1682076404894&signature=rX-_PxwNffE97wdScqmjK40vYQnFDMUzqAaT_U2jFlY&downloadName=%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2023-04-18+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+2.32.13.png"/>
>  <span>
 - 페이치 첫 방문을 고려 했을 때, Home에 가장 많이 접속
- Petwork(사용자가 작성하는 게시물 페이지)에서 Map 페이지가 먼저 나오기 때문에 Map에 많이 접속 함
- Petwork에서 Map은 접속을 많이 했지만 Petwork List를 보는 페이지는 많이 접속 하지 않은 것을 보아 List가 있는 플로팅 버튼을 많이 누르지 않았음을 알 수 있음
  </sapn>
</details>

<details> 
  <summary> 카테고리 검색 비교 통계 </summary>
 <br>
    <img width='800px' src="https://file.notion.so/f/s/919e9029-1af9-4a27-825d-30ee7a7aad71/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-04-18_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_2.32.32.png?id=a9d0a308-1b21-4878-9a3f-f06cc083f91e&table=block&spaceId=0bce464c-a908-4cab-85b1-79089b1ca00e&expirationTimestamp=1682076594167&signature=5z1P4dlD7RqLVkRWi5wQQEi50UhhPxg8xsc3Nf7tFdY&downloadName=%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2023-04-18+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+2.32.32.png"/>
<span>
- 사용자가 거리 별 검색을 사용했을 때 가장 가까운 위치를 많이 사용했음을 알 수 있음
  </sapn>
</details>

<details> 
  <summary> 거리 별 검색 비교 통계 </summary>
 <br>
    <img width='800px' src="https://file.notion.so/f/s/0b8f652f-21e2-4837-bd3f-a028cc49ce69/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-04-18_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_2.32.44.png?id=0f8caf6b-d114-48b4-bb22-ffd290b198e3&table=block&spaceId=0bce464c-a908-4cab-85b1-79089b1ca00e&expirationTimestamp=1682076671250&signature=6as11b7BcfO-ThWRqC_-EMd5lK8pLY4zVCSilfouCgc&downloadName=%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2023-04-18+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+2.32.44.png"/>
<span>
- 동물 종류 (KindType - 강아지, 고양이, 기타) 검색 기능 중에서 사용자가 가장 많이 검색한 종류는 “강아지”임을 알 수 있음
  </sapn>
</details>

<details> 
  <summary> 동물 종류 별 검색 비교 통계</summary>
 <br>
    <img width='800px' src="https://file.notion.so/f/s/a3fc80d9-e982-4eed-bef0-0a10c817fb47/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-04-18_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_2.33.04.png?id=4b892b44-80b6-4a52-afad-455e73136a19&table=block&spaceId=0bce464c-a908-4cab-85b1-79089b1ca00e&expirationTimestamp=1682076656656&signature=l-dAp1RbNkbsQFvPY5A-IzKu2-IGg3yPqIMJ5s7UuL0&downloadName=%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2023-04-18+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+2.33.04.png"/>
<span>
- 사용자들은 내 위치 기반 거리별 검색과 동물의 종류(KindType - 강아지, 고양이, 기타) 검색을 가장 많이 사용했음을 알 수 있음
  </sapn>
</details>




<br>

### 🌐 아키텍쳐 

<img width='600px' src='https://file.notion.so/f/s/d467577c-d9e7-46e7-9b7c-1a6cafa38c65/03_03.png?id=73657ae5-6101-4485-892c-6cbbf5af0fef&table=block&spaceId=0bce464c-a908-4cab-85b1-79089b1ca00e&expirationTimestamp=1682005302739&signature=nTpyFFyBrZuSjAwYvaRMTyo3hNhgs0ja_1HTGazl-j4&downloadName=03_03.png'>


### ERD
<img width='600px' src='https://user-images.githubusercontent.com/95469708/233294279-a02696be-12b9-415d-b1f3-b0629b59c4a4.png'>








<br>

### FE 기술스택 
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"/> <img src="https://img.shields.io/badge/.ENV-ECD53F?style=for-the-badge&logo=.ENV&logoColor=white"/> <img src="https://img.shields.io/badge/Stomp-000000?style=for-the-badge&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/SSE-000000?style=for-the-badge&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"/> <img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=white"/> <img src="https://img.shields.io/badge/reacthookform-EC5990?style=for-the-badge&logo=reacthookform&logoColor=white"/> <img src="https://img.shields.io/badge/styledcomponents-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white"/> 

### BE 기술스택 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/JSONWebToken-000000?style=for-the-badge&logo=JSONWebTokens&logoColor=white"/> <img src="https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/Stomp-000000?style=for-the-badge&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/SSE-000000?style=for-the-badge&logo=&logoColor=white"/>

<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/> <img src="https://img.shields.io/badge/LINUX-FCC624?style=for-the-badge&logo=linux&logoColor=black"/> <img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white"/>

<img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonS3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=for-the-badge&logo=AmazonRDS&logoColor=white"/> <img src="https://img.shields.io/badge/CODEDEPLOY-181717?style=for-the-badge"/>

<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white"/> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/GithubActions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"/>

<img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/> 
  
### 팀 협업툴 

### ⚙️ 협업 Tools

<img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/>

<br><br><br><br>
  
  
<div align=center>

<img width='700px' scr="https://user-images.githubusercontent.com/95469708/233304903-abf71860-e254-45ea-889d-0556c1d4a40c.jpg"/>

![Desktop (1)](https://user-images.githubusercontent.com/95469708/233304903-abf71860-e254-45ea-889d-0556c1d4a40c.jpg)
</div>


<br>

◻ Copyright ©2023 Hang-Hae99 12th Final : A team 2 all rights reserved.


