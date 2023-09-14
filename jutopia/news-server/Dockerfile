FROM python:3.11

COPY ./src/main.py ./main.py

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 50000

CMD ["uvicorn", "main:app", "--port=50000"]