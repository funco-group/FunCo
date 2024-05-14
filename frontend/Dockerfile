# 위에서 도커 허브 node 이미지를 기반으로 로컬로 다운로드 및 캐싱 되었기 때문에 이미지를 가져올 수 있다.
FROM node:20.10.0-alpine

WORKDIR /app

COPY package.json /app

RUN npm install --force

COPY . /app

ENV NODE_ENV=production

RUN npm run build

EXPOSE 3000

CMD ["npm","start"]