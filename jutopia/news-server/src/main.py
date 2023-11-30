import uvicorn
import threading
from fastapi import FastAPI
from py_eureka_client.eureka_client import EurekaClient
from news import router as news_router
from realtime import router as realtime_router
# from scraper import start as scraper_start

# 스크래퍼 관련 변수
# stop_thread_event = threading.Event()
# scraper_thread = None

# 유레카 관련 변수
INSTANCE_PORT = 9001
INSTANCE_HOST = "j9c108.p.ssafy.io"

app = FastAPI()

app.include_router(news_router)
app.include_router(realtime_router)

@app.on_event("startup")
async def startup_event():
    global client, scraper_thread
    client = EurekaClient(
        eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        app_name="news-server",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
    # 스크래퍼 스레드 시작
    # scraper_thread = threading.Thread(target=scraper_start, args=(stop_thread_event,))
    # scraper_thread.start()
    
@app.on_event("shutdown")
async def destroy():
    # global scraper_thread
    # stop_thread_event.set()
    # scraper_thread.join()
    await client.stop()
    
@app.get("/index")
def index():
    return {"message": "Welcome FastAPI Nerds"}

@app.get("/health")
def health_check():
    return {"status": "UP"}
