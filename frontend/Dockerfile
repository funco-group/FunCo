# 빌드를 위한 이미지
FROM node:20.10.0-alpine

# root에 /app 폴더 생성
RUN mkdir /app

WORKDIR /app

# 환경 변수 설정
ENV NODE_ENV=production

# 필요한 파일 복사
COPY . /app

# 포트 열기
EXPOSE 3000

# 서버 실행
CMD ["npm", "start"]