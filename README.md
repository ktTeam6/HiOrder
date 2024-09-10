
# KT 하이오더 프로젝트

![image](https://github.com/user-attachments/assets/12b180db-4810-4acf-8a40-062418850c79)


---

## 목차

1. 서비스 시나리오
    
    1.1 기능적 요구사항
    
    1.2 비기능적 요구사항
    
2. 체크포인트
    
    2.1 이벤트스토밍
    
    2.2 서브 도메인, 바운디드 컨텍스트 분리
    
    2.3 컨텍스트 매핑 / 이벤트 드리븐 아키텍처
    
    2.4 헥사고날 아키텍처
    
    2.5 구현
    
3. 분석/설계
    
    3.1 최적화된 조직 구조 선정
    
    3.2 Event Storming
    
4. 구현
5. 운영
---

## 1. 서비스 시나리오

### 1.1 기능적 요구사항

### 고객 기능 요구사항

1. **서비스 접속 및 로그인**
    - 고객은 테이블에 부착된 QR 코드를 스캔하여 하이오더 서비스에 접속하고, 로그인하여 서비스를 이용한다.
      
2. **메인 페이지 구성**
    - **카테고리별 메뉴 소개**
        - 각 카테고리별로 음식 메뉴가 나열되어 있으며, 메뉴명, 가격, 사진이 포함된다.
    - **하단 기능 버튼**
        - **메뉴 선택**: 장바구니로 이동하여 담은 내역을 확인하고, 수정할 수 있다.
        - **채팅**: 매장 운영자와 실시간으로 문의 및 상담이 가능한 채팅 페이지로 이동한다.
        - **주문 내역**: 해당 테이블의 전체 주문 내역을 확인할 수 있다.
    - **우측 상단 아이콘**
        - **순위**: 매장 전체 메뉴의 인기 순위를 확인할 수 있다.
          
3. **메뉴 선택 및 주문 과정**
    - **메뉴 상세 정보**
        - 고객이 메인 페이지에서 특정 메뉴를 클릭하면, 해당 메뉴의 상세 정보(사진, 메뉴명, 가격, 설명, 주문수, 순위)를 확인할 수 있는 상세 페이지로 이동한다.
    - **장바구니 담기**
        - 고객은 원하는 메뉴를 선택하고 ‘주문하기’ 버튼을 클릭하여 장바구니에 담을 수 있다.
    - **주문 확정**
        - 장바구니에서 선택된 메뉴의 개수와 총 금액을 확인한 후, ‘주문하기’ 버튼을 클릭하면 주문이 최종 확정된다.
          
4. **주문 내역 확인**
    - 고객은 주문 내역 페이지에서 자신이 주문한 메뉴의 상세 내역(메뉴명, 가격, 수량)을 확인할 수 있다.
    - 고객은 매장 운영자가 주문 접수 시, 메뉴 완성 시 해당 내용에 대한 푸시 알림을 받는다.
      
5. **문의 채팅**:
    - 고객은 매장 운영자와 실시간 채팅을 통해 메뉴나 주문에 대한 문의 사항을 해결할 수 있다.

### 매장 운영자 기능 요구사항

1. **서비스 접속 및 로그인**
    - 매장 운영자는 하이오더 서비스에 로그인한다.
    
2. **메인 페이지 구성**
    - **테이블 관리**
        - 각 테이블의 주문 상태를 관리하고, 새로운 주문과 기존 주문 내역, 총 금액을 확인한다.
    - **메뉴 관리**
        - 메뉴 추가, 수정, 제거 등의 메뉴 관리 기능을 수행한다
    - **주문 관리**
        - 실시간으로 업데이트되는 주문을 모니터링하며, 주문 상태를 관리한다.
    - **문의 채팅**
        - 고객의 문의에 실시간으로 응답하여 문제를 해결한다.
        
3. **테이블 관리**
    - 운영자는 테이블 관리 페이지에서 각 테이블의 주문 상태와 내역을 실시간으로 확인하고 관리한다.
    
4. **메뉴 관리**
    - 운영자는 메뉴 관리 페이지에서 새로운 메뉴를 추가하거나 기존 메뉴를 수정, 제거한다.
    
5. **주문 관리**
    - 운영자는 주문 관리 페이지에서 실시간으로 주문을 확인하고, 주문 접수, 메뉴 완성 등의 상태를 업데이트하여 고객에게 푸시 알림을 통해 전달한다.
    
6. **문의 채팅**:
    - 운영자는 문의 채팅 페이지에서 고객의 질문에 실시간으로 응답하고 문제를 해결할 수 있다.

### 1.2 비기능적 요구사항

1. **트랜잭션 관리**
    - **주문 트랜잭션**: 주문이 생성되면 모든 관련 데이터는 트랜잭션 단위로 일관성 있게 처리되어야 하며, 주문 데이터의 무결성을 보장해야 한다. (Sync 호출)
    - **주문 상태 관리**: 주문 상태가 변경될 때마다 고객과 매장 운영자가 정확하고 일관된 정보를 확인할 수 있어야 하며, 시스템은 이와 관련된 데이터를 지속적으로 업데이트하고 반영해야 한다.

2. **장애 격리**
    - **장애 격리**: 메뉴 관리나 채팅 기능에 장애가 발생하더라도, 고객의 주문 처리 기능은 365일 24시간 원활하게 작동해야 한다. (Async, Event-driven, Eventual Consistency)
    
3. **성능**
    - **주문 상태 조회**: 고객이 주문 상태를 실시간으로 확인할 수 있어야 하며, 모든 상태 업데이트는 최대한 신속하게 처리되어야 한다. (CQRS)
    - **알림 서비스**: 주문 상태가 변경되면 고객에게 실시간으로 알림이 전달되어야 한다. 이를 위해 푸시 알림을 사용할 수 있다. (Event-driven)
    - **응답 시간**: 시스템의 모든 사용자 인터페이스(UI)는 3초 이내에 응답해야 하며, 높은 트래픽 상황에서도 성능 저하 없이 원활하게 작동해야 한다.

---

## 2. 체크포인트

### 2.1 이벤트스토밍

- 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
- 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
- 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
- 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?

### 2.2 서브 도메인, 바운디드 컨텍스트 분리

- 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른 Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
    - 적어도 3개 이상 서비스 분리
- 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
- 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?

### 2.3 컨텍스트 매핑 / 이벤트 드리븐 아키텍처

- 업무 중요성과 도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
- Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
- 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
- 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
- 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

### 2.4 헥사고날 아키텍처

- 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?

### 2.5 구현

- [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
- Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여 장애를 격리시킬 수 있는가?
- 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key: 각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?
- 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
- API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?

---

## 3. 분석/설계

### 3.1 최적화된 조직 구조 선정

각 기능별 마이크로 서비스를 기반으로 하이오더 서비스를 구축
서비스 간 독립성과 확장성을 보장하면서도 전체 시스템이 원활하게 동작할 수 있도록 설계


### As-Is 조직

![image](https://github.com/user-attachments/assets/cce90256-425e-4fe0-8c3a-8b09525a6d0e)


- 각 팀의 역할이 기능별로 구성된 수평적 구조
- 팀원은 UI, 백엔드, 데이터베이스 관리 등 특정 기능에만 집중
- 프로젝트 전반에 걸쳐 기능적인 전문성을 발휘
- 기능 간 조율에 장기간 소요 및 기능 간 영향도 高
- 성과/이슈 발생 시 책임 소재 불명확

### To-Be 조직

![image](https://github.com/user-attachments/assets/e63dfea3-e4ac-4d63-b5d3-2f7b7c892f33)


- 서비스별로 팀이 구성된 수직적인 구조
- 특정 마이크로서비스(예: 로그인 및 접근 관리, 메뉴 관리, 주문 관리 등)를 책임
- PO, UI 개발자, 백엔드 개발자, DBA가 한 팀으로 구성되어 서비스를 독립적으로 운영
- 서비스 간의 독립성을 강화하여 빠른 개발과 배포가 가능

---

### 3.2 Event Storming

- Event 도출
- Actor, Command 추가
- Aggregate 분류
- Bounded Context 집합
- Policy 생성
- Context 매핑
- 요구사항 커버 검증
- 헥사고날 아키텍처 다이어그램 도출


## Model
www.msaez.io/#/4814719/storming/airbnb-ex-240903 변경예정

1. Event 도출
    
    ![image](https://github.com/user-attachments/assets/2fc6a471-cf8c-4351-aa43-e88e45c9c8f1)

    

2. Actor, Command 추가
    
    ![image](https://github.com/user-attachments/assets/3208fad3-d284-4bcf-a3eb-d49eb59d3ea8)

    
    ![image](https://github.com/user-attachments/assets/653e3c1b-6a21-4879-a96b-e8eea94ea332)

    

3. Aggregate 분류
    
    ![image](https://github.com/user-attachments/assets/1be9807b-4275-4004-a25c-7268c130922a)

    

4. Bounded Context 집합
    
    ![image](https://github.com/user-attachments/assets/5453da0f-033c-4255-b054-98ba2a2f7e18)

    
5. Policy 생성
    
    ![image](https://github.com/user-attachments/assets/7c36b884-0af0-4fef-a9e4-772bf37095b6)

    
6. Context 매핑
    
    ![image](https://github.com/user-attachments/assets/9e720795-4d14-4599-af27-c078cfd5bbfd)

    
7. 요구사항 충족 검증
    a. 기능적 요구사항
        
        ![image](https://github.com/user-attachments/assets/1a1ef485-39aa-40e6-a75f-976e365e61cc)

        
        상기 모델은 고객 기능 요구사항 3가지, 매장 운영자 요구사항 4가지의 모든 요구사항을 충족함
        
        ### **고객 기능 요구사항**
        
    
        **Order Bounded Context 내 고객의 메뉴 선택 및 주문 과정 시나리오 충족**
        
     
        
        ![image](https://github.com/user-attachments/assets/20dd78e4-0df1-4ec9-abf8-ae04786996d4)

        
        - **메뉴 상세 정보**
            
            고객이 메인 페이지에서 특정 메뉴를 클릭하면, 해당 메뉴의 상세 정보(사진, 메뉴명, 가격, 설명, 주문수, 순위)를 확인할 수 있는 상세 페이지로 이동한다.
            
        - **장바구니 담기**
            
            고객은 원하는 메뉴를 선택하고 ‘주문하기’ 버튼을 클릭하여 장바구니에 담을 수 있다.
            
        - **주문 확정**
            
            장바구니에서 선택된 메뉴의 개수와 총 금액을 확인한 후, ‘주문하기’ 버튼을 클릭하면 주문이 최종 확정된다.

   
        

        **고객의 주문 내역 확인 및 푸시 알림 시나리오 충족**
        
        
        ![image](https://github.com/user-attachments/assets/06bc98c0-b030-4674-868f-09ab734a9e28)

        
        - 고객은 주문 내역 페이지에서 자신이 주문한 메뉴의 상세 내역(메뉴명, 가격, 수량)을 확인할 수 있다.
        - 고객은 매장 운영자가 주문 접수 시, 메뉴 완성 시 해당 내용에 대한 푸시 알림을 받는다.

   

        
        **고객의 문의 채팅 시나리오 충족**
        

        
        ![image](https://github.com/user-attachments/assets/73727e4c-1c7a-4630-b308-c18b1b369fa7)

        
        - 고객은 매장 운영자와 실시간 채팅을 통해 메뉴나 주문에 대한 문의 사항을 해결할 수 있다.

   
        ### **매장 운영자 기능 요구사항**
        
        
        **매장 운영자의 하이오더 서비스 접속 및 로그인 시나리오 충족**
        
        
        ![image](https://github.com/user-attachments/assets/7c114bac-a1de-42de-b720-1c9498ac02f1)

        
        - 매장 운영자는 하이오더 서비스에 로그인한다.
          
        

        **매장 운영자의 메뉴 관리 시나리오 충족**

        ![image](https://github.com/user-attachments/assets/a073acab-82e6-4642-9218-7b3b538ee3e8)

        
        - 운영자는 메뉴 관리 페이지에서 새로운 메뉴를 추가하거나 기존 메뉴를 수정, 제거한다.
          
        

        **매장 운영자의 주문 관리 및 테이블 관리 시나리오 충족**
        

        
        ![image](https://github.com/user-attachments/assets/84ec0be0-d86a-4392-a584-8e3c5c234b7c)

        
        - 운영자는 테이블 관리 페이지에서 각 테이블의 주문 상태와 내역을 실시간으로 확인하고 관리한다.
        - 운영자는 주문 관리 페이지에서 실시간으로 주문을 확인하고, 주문 접수, 메뉴 완성 등의 상태를 업데이트하여 고객에게 푸시 알림을 통해 전달한다.
          

        
        **매장 운영자의 문의 채팅 시나리오 충족**
        

        ![image](https://github.com/user-attachments/assets/dfacfe2e-45a9-4bb6-8a8b-9228d922a0fe)

        
        - 운영자는 문의 채팅 페이지에서 고객의 질문에 실시간으로 응답하고 문제를 해결할 수 있다.
        
    2. 비기능적 요구사항 
        
        ![image](https://github.com/user-attachments/assets/c807d943-7054-4509-8b4c-85ce26afe576)

        
        상기 이벤트 스토밍 다이어그램은 비기능적 세부 요구사항 3가지를 모두 충족함
        
        
        **1. 트랜잭션 관리**
        1.1 주문 트랜잭션
        
        - 이벤트 스토밍 다이어그램에서는 Order와 관련된 주문 생성, 상태 업데이트가 명확하게 정의
        - AddToCart 및 Order 커맨드와 이를 반영하는 OrderPlaced 이벤트는 Sync 호출 방식으로 처리
        
        1.2 주문 상태 관리
        
        - 주문이 생성된 후, 주문 상태는 StatusUpdated 이벤트를 통해 관리
        - OrderManagement 서비스와 Push 서비스가 상호작용하여 주문 상태가 변경될 때마다 이벤트를 발생
        - Eventual Consistency 방식을 적용해 주문 상태가 일관되게 유지되고, 데이터가 확실히 반영되는지 검증 가능
          
        
        
        **2. 장애 격리**

        
        - 다이어그램에서 Menu, Chat, Order 서비스가 각각 독립적인 마이크로서비스로 처리
        - Menu나 Chat 서비스에 장애가 발생하더라도, Order 서비스는 비동기 호출 및 이벤트 기반(Event-driven) 구조로 설계되어 있어 고객의 주문 처리는 영향받지 않음
          
        

        
        **3. 성능**
        3.1 주문 상태 조회
        
        - 주문 상태는 OrderManagement에서 관리
        - 고객이 실시간으로 자신의 주문 상태를 조회
        
        3.2 알림 서비스
        
        - 주문 상태가 변경되면 Push 서비스에서 StatusUpdated 이벤트에 따라 푸시 알림이 전송
        - 주문 상태나 채팅 등의 이벤트 발생 시 고객에게 즉시 알림을 보냅니다.
        
        3.3 응답 시간
        
        - Login, Order, Menu, Chat 등의 서비스는 각각 독립적인 마이크로서비스로 운영 및 처리
        시스템은 트래픽 증가 상황에서도 마이크로서비스 간 독립성을 통해 성능 저하 없이 빠르게 응답 가능

        
5. 헥사고날 아키텍처 다이어그램 도출

미정

---

## 5. 구현

## 6. 운영
azure 관련 캡처 후 설명


## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- room
- reservation
- review
- payment
- dashboard


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- room
```
 http :8088/rooms id="id" name="name" description="description" price="price" status="status" 
```
- reservation
```
 http :8088/reservations id="id" customerId="customerId" roomId="roomId" date="date" price="price" status="status" 
```
- review
```
 http :8088/reviews id="id" roomId="roomId" content="content" customerId="customerId" 
```
- payment
```
 http :8088/payments id="id" customerId="customerId" reservationId="reservationId" price="price" status="status" 
```
- dashboard
```
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

