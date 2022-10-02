FROM nginx:alpine

WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY ./build .

CMD ["/bin/sh" , "-c" , "exec nginx -g 'daemon off;'"]