FROM vault:latest

RUN apk --no-cache add\
     bash\
     ca-certificates\
     wget

ENV PATH="PATH=$PATH:$PWD/vault"

EXPOSE 8200
