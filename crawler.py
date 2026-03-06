import requests
from bs4 import BeautifulSoup
import json
import time

SERVER_URL = "http://localhost:8080/api/chatbot/save"
LIST_URL = "https://www.hoseo.ac.kr/Home/BBSList.mbz?action=MAPP_1708240139&schIdx=0&schCategorycode=CTG_17082400012"

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}

def crawl():
    print("수집 시작")
    
    try:
        res = requests.get(LIST_URL, headers=HEADERS, verify=False)
        res.encoding = 'utf-8'
        soup = BeautifulSoup(res.text, 'html.parser')

       
        rows = soup.select("tr.board_new")
        print(f"발견된 공지사항 개수: {len(rows)}개")

        for i, row in enumerate(rows):
            
            title_td = row.select_one("td.board-list-title")
            if not title_td: continue
            
            title_tag = title_td.select_one("a")
            if not title_tag: continue

            title = title_tag.get_text(strip=True)
            
            
            date_td = row.find("td", {"data-header": "등록일자"})
            date = date_td.get_text(strip=True) if date_td else "2026-02-20"

            notice_url = LIST_URL 

            payload = {
                "title": title,
                "content": "본문 내용은 이미지 분석 후 연동 예정",
                "date": date,
                "url": notice_url
            }

            # 자바 서버 전송
            try:
                response = requests.post(SERVER_URL, json=payload, timeout=5)
                if response.status_code == 200:
                    print(f" [{i+1}] 전송 성공: {title[:15]}...")
                else:
                    print(f" [{i+1}] 서버 응답 에러: {response.status_code}")
            except Exception as e:
                print(f" [{i+1}] 전송 실패: {e}")

            time.sleep(0.1)

    except Exception as e:
        print(f"에러 발생: {e}")

if __name__ == "__main__":
    crawl()