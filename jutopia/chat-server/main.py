from fastapi import FastAPI
from pydantic import BaseModel
from py_eureka_client.eureka_client import EurekaClient
from pymongo import MongoClient
from utils import generate_answer, generate_newssum_answer, news_summary
from datetime import datetime, timedelta
# import httpx
# import openai

# 유레카 관련 설정
INSTANCE_PORT = 9002
INSTANCE_HOST = "j9c108.p.ssafy.io"

# MongoDB 관련 설정
connection_string = "mongodb://juto:juto1234@mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=jutopia-repl"
client = MongoClient(connection_string)
db = client['jutopia']
collection = db['chat']

app = FastAPI()

class Question(BaseModel):
    message: str

class Document(BaseModel):
    role: str
    from_server : bool
    message: str
    time: datetime

class Answer(BaseModel):
    from_server: bool
    message: str
    parsed_time: str
    
class NewsLink(BaseModel):
    link: str

@app.on_event("startup")
async def eureka_init():
    global client
    client = EurekaClient(
        eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        app_name="chat-server",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
@app.on_event("shutdown")
async def destroy():
    await client.stop()

@app.get("/index")
def index():
    return {"message": "Welcome to chat server"}

@app.post("/ask")
async def answer(question: Question):
    ans = generate_answer(question.message)
    now = datetime.now() + timedelta(hours=9)
    
    return Answer(
        from_server=True,
        message=ans,
        parsed_time=f"{'오전' if now.hour < 12 else '오후'} {now.strftime('%I:%M')}"
    )

@app.post("/sumnews")
async def sum_news(news_link: NewsLink):
    summary = news_summary(news_link.link)
    return generate_newssum_answer(summary)