FROM python:3.11

COPY ./src/main.py ./main.py

COPY ./requirements.txt ./requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8080

CMD ["uvicorn", "main:app", "--port=8080"]