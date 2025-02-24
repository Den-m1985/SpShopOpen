https://habr.com/ru/articles/716002/
INSTALL Nginx:

    apt update  && \
    apt install -y wget curl gpg && \
    curl https://nginx.org/keys/nginx_signing.key | gpg --dearmor | tee /usr/share/keyrings/nginx-archive-keyring.gpg >/dev/null && \
    gpg --dry-run --quiet --no-keyring --import --import-options import-show /usr/share/keyrings/nginx-archive-keyring.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/nginx-archive-keyring.gpg] http://nginx.org/packages/mainline/debian bullseye nginx" | tee /etc/apt/sources.list.d/nginx.list && \
    echo -e "Package: *\nPin: origin nginx.org\nPin: release o=nginx\nPin-Priority: 900\n" | tee /etc/apt/preferences.d/99nginx && \
    wget http://ftp.ru.debian.org/debian/pool/main/o/openssl/libssl1.1_1.1.1w-0+deb11u1_amd64.deb && \
    dpkg -i libssl1.1_1.1.1w-0+deb11u1_amd64.deb && \
    rm libssl1.1_1.1.1w-0+deb11u1_amd64.deb && \
    apt update && \
    apt install -y nginx && \
    sed -i 32i\ 'include /etc/nginx/sites-enabled/*;' /etc/nginx/nginx.conf && \
    systemctl restart nginx


********************************************

https://help.reg.ru/support/ssl-sertifikaty/3-etap-ustanovka-ssl-sertifikata/kak-nastroit-ssl-sertifikat-na-nginx#0

1 - Объедините три сертификата 
(сам SSL-сертификат, корневой и промежуточный сертификаты) в один файл. 
Для этого создайте на server новый текстовый документ в каталоге:

    cd /etc/nginx/sites-available
с именем:

    закупайумно.рф.crt
Создать его можно при помощи блокнота или другого текстового редактора. 
Поочередно скопируйте и вставьте в созданный документ каждый сертификат. 
После вставки всех сертификатов файл должен иметь вид:

    -----BEGIN CERTIFICATE-----
    #Ваш сертификат#
    -----END CERTIFICATE-----
    -----BEGIN CERTIFICATE-----
    #Промежуточный сертификат#
    -----END CERTIFICATE-----
    -----BEGIN CERTIFICATE-----
    #Корневой сертификат#
    -----END CERTIFICATE-----

Обратите внимание: один сертификат идёт следом за другим, без пустых строк.

2- Создайте файл 

    закупайумно.рф.key 
и скопируйте в него содержание приватного ключа сертификата.

3- Также вы можете дополнительно оптимизировать работу Nginx HTTPS-сервера. 
SSL-операции задействуют дополнительные ресурсы сервера. 
Чтобы снизить количество операций, можно повторно использовать параметры SSL-сессий. 
Они хранятся в кеше SSL-сессий. Можно задать тип кеша 
(в примере это shared-кеш, разделяемый между всеми рабочими процессами) 
и его размер в байтах (в 1 Мб кеша помещается около 4000 сессий) 
с помощью директивы ssl_session_cache. 
Также можно увеличить таймаут кеша 
(время, в течение которого клиент повторно использует параметры сессии) 
директивой ssl_session_timeout: по умолчанию он равен 5 минутам. 
Можно настроить время работы одного keepalive-соединения с
помощью директивы keepalive_timeout.

Добавьте в конфигурационном файле в секции server{} строки:

    ssl_session_cache   shared:SSL:10m;
    ssl_session_timeout 10m;
    keepalive_timeout 70;


nano закупайумно.рф

    server {
        listen 443 ssl;
        server_name закупайумно.рф;
        root /var/www/закупайумно.рф;
        # Указываем путь, где будут лежать сертификаты для шифрования
        ssl_certificate /etc/nginx/sites-available/закупайумно.рф.crt;
        ssl_certificate_key /etc/nginx/sites-available/закупайумно.рф.key
        ssl_session_cache shared:SSL:10m;
        ssl_session_timeout 10m;
        keepalive_timeout 70;
        location / {
            proxy_pass http://ip address:port;
            proxy_set_header Host $host;
            proxy_set_header Connection "upgrade";
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
        location /auth/ {
            proxy_pass http://ip address:port/auth;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection ’upgrade';
            proxy_set_header Host $host;
            proxy_cache_bypass $http_upgrade;
        }
        location /resource/ {
            proxy_pass http://ip address:port/resource;
            proxy_set_header Host $host;
            proxy_set_header Connection "upgrade";
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
        location /gateway/ {
            proxy_pass http://ip address:port/gateway;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }

Exit from nano.

    sudo systemctl reload nginx

**************************************************
To remove or hide the Nginx version number from the server header, 
follow these steps:
Open the Nginx configuration file (nginx.conf)

    cd /etc/nginx/nginx.conf

    nano nginx.conf

Locate the http block in the configuration file.
Inside the http block, add the following line:

    server_tokens off;

This directive will disable the display of the 
Nginx version number in the server header.
Save the changes and exit the text editor.
Test the Nginx configuration to ensure there are no syntax errors:

    sudo nginx -t

If the test is successful, reload or restart Nginx to apply the changes:

    sudo systemctl reload nginx


How to check?

    curl -I http://закупайумно.рф

The -I flag tells curl to fetch only the headers. 
Look for the Server header in the output. 
If the Nginx version number has been removed, 
you should see something like:

    Server: nginx

