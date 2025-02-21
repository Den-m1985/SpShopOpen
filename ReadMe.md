Вэб сайт для сервиса и автоматизации работ с совместными покупками


### Запуск из конфигурации "run all" в idea
Запустятся 5 модулей.

API микросервисов:

Eurika server запускаются на порту: http://localhost:8761

Главная страница:  http://localhost:3000

Регистрация (post запрос):  http://localhost:8000/api/auth/register

Авторизация (post запрос): http://localhost:8000/api/auth/login

Сервис по сравнению счетов: http://localhost:8082/resourceservice/findSameName



Используемые материалы:
Реализация JWT в Spring Boot
приложение, которое будет совмещать в себе бизнес-логику и выдачу токенов
https://struchkov.dev/blog/ru/jwt-implementation-in-spring/

Строим свой SSO сервер используя Spring Authorization Server
https://habr.com/ru/articles/737548/

Полное руководство по управлению JWT во фронтенд-клиентах (GraphQL)
https://medium.com/nuances-of-programming/%D0%BF%D0%BE%D0%BB%D0%BD%D0%BE%D0%B5-%D1%80%D1%83%D0%BA%D0%BE%D0%B2%D0%BE%D0%B4%D1%81%D1%82%D0%B2%D0%BE-%D0%BF%D0%BE-%D1%83%D0%BF%D1%80%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D1%8E-jwt-%D0%B2%D0%BE-%D1%84%D1%80%D0%BE%D0%BD%D1%82%D0%B5%D0%BD%D0%B4-%D0%BA%D0%BB%D0%B8%D0%B5%D0%BD%D1%82%D0%B0%D1%85-graphql-b9b5103062a3
