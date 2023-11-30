from fastapi import APIRouter, HTTPException
from pydantic import BaseModel
import httpx
import re

NAVER_API_URL = "https://openapi.naver.com/v1/search/news.json"
CLIENT_ID = "OCDILfuJhLKAdvqraNNy"
CLIENT_SECRET = "pi8ZEzmb8L"

router = APIRouter()

class NewsItem(BaseModel):
    title: str
    originallink: str
    link: str
    description: str
    pubDate: str

class NewsResponse(BaseModel):
    lastBuildDate: str
    total: int
    start: int
    display: int
    items: list[NewsItem]
    
def cleanhtml(raw_html):
    cleanr = re.compile('<.*?>|&([a-z0-9]+|#[0-9]{1,6}|#x[0-9a-f]{1,6});')
    cleantext = re.sub(cleanr, '', raw_html)
    return cleantext

@router.get("/orig/{stock_name}/{display}/{start}/{sort}")
async def fetch_news(stock_name: str, display: int, start: int, sort: str):
    print('got here')
    params = {
        "query": stock_name, # 검색할 주식 이름 (전체는 '시황' 으로 검색 추천)
        "display": display, # 검색할 뉴스 개수
        "start": start, # 뉴스 검색 시작점 (1부터 시작, 100까지 가능)
        "sort": sort # 'sim': 정확도 내림차순, 'date': 날짜 내림차순
    }
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,
        "X-Naver-Client-Secret": CLIENT_SECRET
    }
    
    async with httpx.AsyncClient() as client:
        response = await client.get(NAVER_API_URL, params=params, headers=headers)
        
        # Error handling
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Naver API call failed")
    
    return response.json()

@router.get("/naver/{stock_name}/{offset}/{max_num}", response_model=NewsResponse)
async def fetch_naver_news(stock_name: str, offset: int, max_num: int):
    print("got here")
    params={
        "query": stock_name,
        "display": 100,
        "start": offset,
        "sort": "date"
    }
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,
        "X-Naver-Client-Secret": CLIENT_SECRET
    }
    
    async with httpx.AsyncClient() as client:
        response = await client.get(NAVER_API_URL, params=params, headers=headers)
        print('got here 2')
        
        # Error handling
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Naver API call failed")
    
    items = response.json()["items"]
    naver_news = [item for item in items if "n.news.naver.com" in item["link"]]
    for i in range(max_num):
        naver_news[i]["title"] = cleanhtml(naver_news[i]["title"])

    print('got here 3')
    
    return NewsResponse(
        lastBuildDate=response.json()["lastBuildDate"],
        total=response.json()["total"],
        start=response.json()["start"],
        display=int(response.json()["display"]),
        items=naver_news[:max_num]
    )
    
