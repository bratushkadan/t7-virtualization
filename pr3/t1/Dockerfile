FROM python:3.9

LABEL version="0.1.0"
LABEL description="A simple Flask web application"

ENV APP_HOME /app

WORKDIR $APP_HOME

COPY . $APP_HOME

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 5000

VOLUME ["/app/data"]

# can be overriden
CMD ["flask", "run", "--host=0.0.0.0", "--port=5000"]
