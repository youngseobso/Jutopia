FROM python:3.11

WORKDIR /src
COPY ./src /src

RUN pip install -r requirements.txt

EXPOSE 50000

CMD ["uvicorn", "main:app", "--port=50000"]