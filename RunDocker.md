
***ПОЛЕЗНЫЕ КОМАНДЫ DOCKER***

sudo docker images  список образов
sudo docker rmi ubuntu:22.04  удаление образа
sudo docker images -aq  показывает id образов
sudo docker rmi $(sudo docker images -aq)  удалит все образы

sudo docker ps -a  команды ранее введенные
sudo docker start serene_moore  запускаем контейнер с именем
sudo docker rm serene_moore  удаляем контейнер
sudo docker ps -q  выведет id запущенных контейнеров
sudo docker ps -a -q  выведет id всех контейнеров
sudo docker rm $(sudo docker ps -a -q) удаляет все остановленные контейнеры
sudo docker stop b9e43c977d87  можно остановить по id контейнера

sudo docker system df  смотрим занимаемый объем на диске
sudo docker system prune -af  очищаем диск от остановленных контейнеров 

sudo docker volume ls  смотрим локальное хранилище
sudo docker volume rm $(sudo docker volume ls -qf dangling=true)  удаляем все


docker exec -it имя_контейнера /bin/bash   заходим внутрь контейнера

docker logs имя_контейнера     просматриваем логи

отобразит информацию о ресурсах, используемых контейнером, включая использование памяти (в колонке MEM USAGE)
docker stats имя_контейнера
