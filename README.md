
스마트폰 게임 프로그래밍 기말 프로젝트
================
                                                                                                         2015184001 강건휘 
----------------

20.04.28
똥피하기 Copy Game
----------------

**1. 개요**
-----------
 화면 상단에서 랜덤으로 생성되는 무수한 장애물들을 캐릭터를 이동시켜 피하는 게임
 피한 똥 하나 당 점수가 1점씩 오른다. 장애물은 시간이 지남에 따라 점점 더 빠른 주기로 떨어진다.
 장애물에 맞으면 바로 게임이 오버되고, 스코어가 저장된다.
 화면 중앙을 기준으로 좌측을 터치하면 캐릭터가 좌측으로, 우측을 터치하면 캐릭터가 우측으로 이동한다.
 장애물은 화면 상단 일정한 y 좌표와 랜덤한 x 좌표에서 생성되며 중력가속도의 영향을 받으며 낙하한다.
 캐릭터 이동에는 가감속을 적용하지 않는다.(즉각적인 반응 가능)
 게임오버 판단을 위해 캐릭터와 장애물의 충돌을 체크한다. 
 기기에 게임을 설치한 시점부터 최고점수는 게임을 초기화하기 전까지 계속 저장된다.
 
 
 
 **2. 특징**
 ----------
화면 구성
              게임 로비 
              - 게임플레이
              - 설정
              - 상점 

- 배경 이미지(하늘)/배경 애니메이션 추가(ex.하늘을 날아가는 새)
- 게임 플레이 시간이 경과함에 따라 낮-밤의 변화가 생긴다.(배경 이미지 교체)
- 게임을 플레이하면서 누적된 점수는 게임머니로 환산된다(계속 저장).
- 게임머니를 이용하여 자신의 캐릭터 스킨을 변경할 수 있다(상점).
- 장애물과 더불어 아이템도 떨어진다. 

   실드 아이템 = 장애물을 한번 막아준다.
   보석 = 보너스 점수.

**3. 구성요소**
 ----------
Gameobj
- 캐릭터 (character)
- 장애물 (obstacle)
- 아이템
  - 실드 (shield)
  - 보석 (gem)

View
- 게임뷰 (gameview)
- 백그라운드 (background)
