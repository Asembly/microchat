FROM node:current-alpine3.21 AS builder
WORKDIR /app
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
