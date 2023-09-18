FROM python:3.11

COPY ./src/main.py ./main.py

COPY ./requirements.txt ./requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 9001

CMD ["uvicorn", "main:app", "--host=0.0.0.0", "--port=9001"]