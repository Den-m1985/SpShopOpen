
Deploy на сервер:
Собираем образ
docker build -t authservice .

Сохраняем контейнер в файл (в ту же дирректорию где и образ)
sudo docker save -o authservice.tar authservice

Копируем на сервер по SSH (~ означает сохраниь в домашнюю директорию)
sudo scp authservice.tar root@[12.345.678.9101]:~

Заходим на наш сервер:
ssh root@[12.345.678.9101]

Импортируем образ из файла на сервере:
docker load < authservice.tar

Запускаем контейнер на сервере в режиме демона:
docker run -d -p [port]:[port] authservice

Удаляем файл на сервере
rm authservice.tar

MEM USAGE 157.7MiB
