FROM python:3.11

COPY main.py ./main.py

COPY ./utils.py ./utils.py

COPY requirements.txt ./requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 9002

CMD ["uvicorn", "main:app", "--host=0.0.0.0", "--port=9002"]