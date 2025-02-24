
Deploy на сервер:
Собираем образ
docker build -t gateway .

Сохраняем контейнер в файл (в ту же дирректорию где и образ)
sudo docker save -o gateway.tar gateway

Копируем на сервер по SSH (~ означает сохраниь в домашнюю директорию)
sudo scp gateway.tar root@[12.234.567.891]:~

Заходим на наш сервер:
ssh root@[12.234.567.891]

Импортируем образ из файла на сервере:
docker load < gateway.tar

Запускаем контейнер на сервере в режиме демона:
docker run -d -p [port]:[port] gateway

Удаляем файл на сервере
rm gateway.tar

MEM USAGE 149MiB
